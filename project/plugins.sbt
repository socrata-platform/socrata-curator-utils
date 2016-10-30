resolvers ++= Seq("sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
                  "Socrata Cloudbees" at "https://repo.socrata.com/artifactory/libs-release")

addSbtPlugin("com.socrata" % "socrata-sbt-plugins" %"1.6.1")
