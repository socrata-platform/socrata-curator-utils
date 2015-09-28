name := "socrata-curator-utils"

val settings = Seq(
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.4", scalaVersion.value),
    libraryDependencies ++= Seq(
        "com.rojoma"        %% "rojoma-json-v3-grisu"  % "1.0.0" % "provided",
        "com.socrata"       %% "socrata-http-client" % "[3.4, 4.0)" % "provided",
        "com.socrata"       %% "socrata-http-jetty"  % "[3.4, 4.0)" % "provided",
        "org.apache.curator" % "curator-x-discovery" % "2.4.2" % "provided"
      ),
    libraryDependencies in Test ++= Seq(
        "org.apache.curator" % "curator-test"        % "2.4.2" % "provided"
      ),
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oFD"),
    com.socrata.sbtplugins.findbugs.JavaFindBugsPlugin.JavaFindBugsKeys.findbugsFailOnError in Compile := false
  )

val core = project.settings(
  ((name := "socrata-curator-utils") +: settings) : _*
)

val testSettings = settings ++ Seq(
    libraryDependencies ++= Seq(
        "org.slf4j"          % "slf4j-simple"        % "1.7.5" % "test",
        "org.apache.curator" % "curator-test"        % "2.4.2" % "provided"
      )
  )

val test = project.settings(
             ((name := "socrata-curator-test-utils") +: testSettings) :_*
           ).dependsOn(core)
