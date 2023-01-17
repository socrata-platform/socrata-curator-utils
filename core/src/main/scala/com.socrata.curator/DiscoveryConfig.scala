package com.socrata.curator

import com.socrata.thirdparty.typesafeconfig.ConfigClass
import com.typesafe.config.Config

/**
 * Contains service discovery/advertisement config for use with Curator
 * @param config Configuration object
 * @param root Root of the curator configuration subset
 */
class DiscoveryConfig(config: Config, root: String) extends ConfigClass(config, root) {
  val serviceBasePath = getString("service-base-path")
  val name = getString("name")
  val address = getString("address")
  val port = optionally(getInt("port"))
}
