name := "socrata-curator-utils"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.10.4", scalaVersion.value)

libraryDependencies ++= Seq(
  "com.rojoma"        %% "rojoma-json-v3-grisu"  % "1.0.0" % "provided",
  "com.socrata"       %% "socrata-http-client" % "[3.4, 4.0)" % "provided",
  "com.socrata"       %% "socrata-http-jetty"  % "[3.4, 4.0)" % "provided",
  "org.apache.curator" % "curator-x-discovery" % "2.4.2" % "provided"
)

libraryDependencies ++= Seq(
  "org.apache.curator" % "curator-test"        % "2.4.2" % "provided"
)

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oFD")

com.socrata.sbtplugins.findbugs.JavaFindBugsPlugin.JavaFindBugsKeys.findbugsFailOnError in Compile := false`
