resolvers ++= Seq("sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
                  Resolver.url("Socrata Artifactory", url("https://repo.socrata.com/artifactory/ivy-libs-release-local"))(Resolver.ivyStylePatterns))

addSbtPlugin("com.socrata" % "socrata-sbt-plugins" %"1.6.8")
