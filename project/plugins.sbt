externalResolvers ++= Seq(Resolver.url("Socrata Artifactory Ivy Libs Release", url("https://repo.socrata.com/artifactory/ivy-libs-release-local"))(Resolver.ivyStylePatterns))

addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.6.1")
