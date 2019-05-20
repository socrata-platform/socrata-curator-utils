name := "socrata-curator-utils"

val commonSettings = Seq(
    organization := "com.socrata",
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.4", scalaVersion.value),
    externalResolvers := Seq("Socrata Artifactory Libs Release" at "https://repo.socrata.com/artifactory/libs-release"),
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oFD"),
    com.socrata.sbtplugins.findbugs.JavaFindBugsPlugin.JavaFindBugsKeys.findbugsFailOnError in Compile := false,
    com.socrata.sbtplugins.StylePlugin.StyleKeys.styleCheck in Compile := {},
    com.socrata.sbtplugins.StylePlugin.StyleKeys.styleCheck in Test := {}
  )

commonSettings

publish := {} // do not publish the root jar

val commonDeps = Seq(
    "com.rojoma"        %% "rojoma-json-v3-grisu"  % "1.0.0" % "provided",
    "com.socrata"       %% "socrata-http-client" % "[3.4, 4.0)" % "provided",
    "com.socrata"       %% "socrata-http-jetty"  % "[3.4, 4.0)" % "provided",
    "org.apache.curator" % "curator-x-discovery" % "2.4.2" % "provided"
  )

val testDeps = Seq(
    "org.slf4j"          % "slf4j-simple"        % "1.7.5" % "test",
    "org.apache.curator" % "curator-test"        % "2.4.2" % "provided"
  )

val core = project.settings(
    name := "socrata-curator-utils",
    libraryDependencies ++= commonDeps
  ).settings(commonSettings : _*)

val test = project.settings(
    name := "socrata-curator-test-utils",
    libraryDependencies ++= commonDeps ++ testDeps
  ).settings(commonSettings : _*)
   .dependsOn(core)
