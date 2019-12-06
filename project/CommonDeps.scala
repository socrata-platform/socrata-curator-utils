import sbt._

object CommonDeps {
  val commonDeps = Seq(
    "com.rojoma"        %% "rojoma-json-v3-grisu"  % "1.0.0" % "provided",
    "com.socrata"       %% "socrata-http-client" % "3.12.0" % "provided",
    "com.socrata"       %% "socrata-http-jetty"  % "3.12.0" % "provided",
    "org.apache.curator" % "curator-x-discovery" % "2.4.2" % "provided"
  )
}
