externalResolvers ++= Seq(Resolver.url("Socrata Artifactory Ivy Libs Release", url("https://repo.socrata.com/artifactory/ivy-libs-release-local"))(Resolver.ivyStylePatterns))

addSbtPlugin("com.socrata" % "socrata-sbt-plugins" %"1.6.8")
