package com.socrata.curator

import com.typesafe.config.Config

import com.socrata.thirdparty.typesafeconfig.ConfigClass

class CuratedClientConfig(config: Config,
                          root: String) extends ConfigClass(config, root) {
  val serviceName = getString("service-name")
  val maxRetries = getInt("max-retries")
  val connectTimeout = {
    val ct = getDuration("connect-timeout").toMillis
    if (ct != ct.toInt) {
      throw new IllegalArgumentException("Connect timeout out of range (must fit in an Int).")
    }

    ct.toInt
  }
}
