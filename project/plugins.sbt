resolvers ++= Seq("Socrata Artifactory" at "https://repo.socrata.com/artifactory/libs-release/",
                  Resolver.url("Socrata Artifactory", url("https://repo.socrata.com/artifactory/ivy-libs-release-local"))(Resolver.ivyStylePatterns))

addSbtPlugin("com.socrata" % "socrata-sbt-plugins" %"1.6.8")
