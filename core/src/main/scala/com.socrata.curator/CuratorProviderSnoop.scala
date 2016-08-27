package com.socrata.curator

import scala.collection.JavaConverters._
import scala.util.matching.Regex
import java.io.Closeable

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.cache.PathChildrenCache
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener

/** Utility to keep an eye on which services that match some regex exist. */
class CuratorProviderSnoop(curatorClient: CuratorFramework, discoveryRoot: String, serviceMatch: Regex) extends (() => Set[String]) with Closeable {
  @volatile private var currentValue = Option.empty[Set[String]]

  private val childCache = new PathChildrenCache(curatorClient, discoveryRoot, false)
  private val listener = new PathChildrenCacheListener {
    override def childEvent(curator: CuratorFramework, event: PathChildrenCacheEvent): Unit = postRefresh()
  }
  childCache.getListenable.addListener(listener)

  def start(): Unit = {
    childCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE)
  }

  def close(): Unit = {
    childCache.close()
  }

  def apply(): Set[String] = {
    val cv = currentValue
    cv match {
      case Some(cached) =>
        cached
      case None =>
        synchronized {
          currentValue match {
            case Some(cached) =>
              cached
            case None =>
              val newCache =
                childCache.getCurrentData.asScala.flatMap { cd =>
                  val p = cd.getPath
                  val lastSlash = p.lastIndexOf('/')
                  val service = p.substring(lastSlash + 1)
                  if(serviceMatch.pattern.matcher(service).matches) List(service)
                  else Nil
                }.toSet
              currentValue = Some(newCache)
              newCache
          }
        }
    }
  }

  private def postRefresh(): Unit =
    synchronized {
      currentValue = None
    }
}
