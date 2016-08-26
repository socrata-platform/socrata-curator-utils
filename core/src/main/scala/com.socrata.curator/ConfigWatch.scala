package com.socrata.curator

import java.io.Closeable
import java.nio.charset.StandardCharsets

import com.rojoma.json.v3.codec.JsonDecode
import com.rojoma.json.v3.util.JsonUtil
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.cache.{NodeCacheListener, NodeCache}
import org.slf4j.LoggerFactory

class ConfigWatch[T : JsonDecode](curator: CuratorFramework, path: String, default: T) extends (() => T) with Closeable {
  import ConfigWatch._

  @volatile private var currentValue = Option.empty[T]
  @volatile private var previousValue = Option.empty[T]

  private val cache = new NodeCache(curator, path)
  private val listener = new NodeCacheListener {
    override def nodeChanged(): Unit = postRefresh()
  }
  cache.getListenable.addListener(listener)

  def start(): Unit = cache.start(true)

  override def close(): Unit = cache.close()

  override def apply(): T = {
    // Not sure this temporary is necessary, but it would be bad if
    // scalac were to generate multiple reads of the `currentValue`
    // variable during pattern-matching outside the synchronization
    // boundary.
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
              // Mildly complex logic here:
              //    If the node is gone, use the default value
              //    If the node exists but is not parsable for whatever reason, keep the previous value (or
              //      the default if there was no valid previous value)
              //    Otherwise use the new value and forget the previous value.
              val newData =
                for {
                  data <- Option(cache.getCurrentData)
                  bytes <- Option(data.getData)
                } yield {
                  try {
                    JsonUtil.parseJson[T](new String(bytes, StandardCharsets.UTF_8)) match {
                      case Right(t) =>
                        Some(t)
                      case Left(error) =>
                        log.warn("Undecodable data in config watch {}: {}", path: Any, error.english)
                        previousValue
                    }
                  } catch {
                    case e: Exception =>
                      log.warn("Unparsable data in config watch {}: {}", path: Any, e)
                      previousValue
                  }
                }
              val result = newData.flatten.getOrElse(default)
              log.debug("Config watch {} now has value {}", path: Any, result)
              currentValue = Some(result)
              previousValue = None
              result
          }
        }
    }
  }

  private def postRefresh(): Unit =
    synchronized {
      if(currentValue != None) {
        previousValue = currentValue
        currentValue = None
      }
    }
}

object ConfigWatch {
  private val log = LoggerFactory.getLogger(classOf[ConfigWatch[_]])
}
