# Socrata Curator Utils #

* `CuratorConfig`, a common config class for Curator services
* `CuratorInitializer`, common initialization stuff for Curator service discovery
* `CuratorServiceBase`, a Trait for curator-based service clients
* `ProviderCache`, a cache for Curator service providers
* `CuratorBroker`, a class for registering a service with Curator
* `CuratorServiceIntegration`, a helper trait to spin up ZK, Curator,
  discovery for testing
* `ServerProvider` Makes requests to an HTTP service with configurable retry

## Releasing ##

Run `sbt-release` and set an appropriate version.
