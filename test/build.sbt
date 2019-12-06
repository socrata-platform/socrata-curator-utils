name := "socrata-curator-test-utils"

libraryDependencies ++= CommonDeps.commonDeps ++ Seq(
  "org.slf4j"          % "slf4j-simple"        % "1.7.5" % "test",
  "org.apache.curator" % "curator-test"        % "2.4.2" % "provided"
)

mimaPreviousArtifacts := Set(organization.value %% name.value % PreviousVersion.previousVersion.value)
