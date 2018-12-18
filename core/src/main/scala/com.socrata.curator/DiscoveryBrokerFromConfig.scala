package com.socrata.curator

import java.util.concurrent.{Executor, Executors}

import com.rojoma.simplearm.v2.{Managed, Resource, managed}

import com.socrata.http.client.{HttpClient, HttpClientHttpClient}

object DiscoveryBrokerFromConfig {
  private def defaultExecutor = {
    // Never timeout shutting down an executor.
    implicit val timeout = Resource.executorShutdownNoTimeout

    managed(Executors.newCachedThreadPool())
  }

  /** Builds a DiscoveryBroker from configuration.
    *
    * @param discoveryConfig discovery config.
    * @param http The http client to use when making HTTP requests.
    */
  def apply(config: DiscoveryBrokerConfig, http: HttpClient): Managed[DiscoveryBroker] = {
    for {
      curator <- CuratorFromConfig(config.curator)
      discovery <- DiscoveryFromConfig(classOf[Void], curator, config.discovery)
    } yield new DiscoveryBroker(discovery, http)
  }

  /** Builds a DiscoveryBroker from configuration.
    *
    * @param discoveryConfig discovery config.
    * @param userAgent The user agent to use when making HTTP requests.
    * @param executor The executor to use for HTTP requests,
    *    defaults to a cached thread pool.
    */
  def apply(config: DiscoveryBrokerConfig,
            userAgent: String,
            executor: Managed[Executor] = defaultExecutor) : Managed[DiscoveryBroker] = {
    for {
      executor <- executor
      curator <- CuratorFromConfig(config.curator)
      discovery <- DiscoveryFromConfig(classOf[Void], curator, config.discovery)
      http <- managed(new HttpClientHttpClient(executor,
                                               HttpClientHttpClient.
                                                 defaultOptions.
                                                 withUserAgent(userAgent)))
    } yield new DiscoveryBroker(discovery, http)
  }
}
