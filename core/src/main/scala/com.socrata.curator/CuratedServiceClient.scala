package com.socrata.curator

import org.slf4j.LoggerFactory

import com.socrata.http.client.{RequestBuilder, Response, SimpleHttpRequest}

import ServerProvider.Complete

/**
  * Manages connections and requests to the provided service.
  * @param provider Service discovery object.
  * @param config The configuration for this client.
  */
case class CuratedServiceClient(provider: ServerProvider,
                                config: CuratedClientConfig) {
  private val logger = LoggerFactory.getLogger(getClass)
  private val connectTimeout = config.connectTimeout
  private val maxRetries = config.maxRetries

  /**
    * Sends a get request to the provided service.
    * @return HTTP response code and body
    */
  def execute[T](request: RequestBuilder => SimpleHttpRequest,
                 callback: Response => T): T = {
    // Not using a default paramter to avoid breaking binary compatibility.
    execute(request, callback, ServerProvider.standardRetryOn)
  }

  /**
    * Sends a get request to the provided service.
    * @return HTTP response code and body
    */
  def execute[T](request: RequestBuilder => SimpleHttpRequest,
                 callback: Response => T,
                 retryWhen: ServerProvider.RetryWhen): T = {
    val requestWithTimeout = { base: RequestBuilder =>
      val req = base.connectTimeoutMS match {
        case Some(timeout) => base
        case None => base.connectTimeoutMS(connectTimeout)
      }

      request(req)
    }

    provider.withRetries(maxRetries,
                         requestWithTimeout,
                         retryWhen) {
      case Some(response) =>
        Complete(callback(response))
      case None =>
        throw ServiceDiscoveryException(s"Failed to discover service: ${config.serviceName}")
    }
  }
}
