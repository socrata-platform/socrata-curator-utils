package com.socrata.curator

import com.rojoma.simplearm.v2.Managed
import org.apache.curator.x.discovery.ServiceDiscovery

import com.socrata.http.client.HttpClient

class DiscoveryBroker private[curator] (discovery: ServiceDiscovery[Void],
                                        httpClient: HttpClient) {
  def clientFor(config: CuratedClientConfig): Managed[CuratedServiceClient] = {
    for {
      sp <- ServiceProviderFromName[Void](discovery, config.serviceName)
    } yield {
      val provider = CuratorServerProvider(httpClient, sp, identity)
      CuratedServiceClient(provider, config)
    }
  }
}
