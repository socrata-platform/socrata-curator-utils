ThisBuild / organization := "com.socrata"

ThisBuild / scalaVersion := "2.12.8"

ThisBuild / crossScalaVersions := Seq("2.10.4", "2.11.7", "2.12.8")

ThisBuild / resolvers := Seq("Socrata Artifactory Libs Release" at "https://repo.socrata.com/artifactory/libs-release")

ThisBuild / testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oFD")

ThisBuild / PreviousVersion.previousVersion := "1.2.0"

publishArtifact := false

// disable mima for the root project
mimaPreviousArtifacts := Set()
mimaFailOnNoPrevious := false

val core = project in file("core")

val test = (project in file("test")).
  dependsOn(core)
