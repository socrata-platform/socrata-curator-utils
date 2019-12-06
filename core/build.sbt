name := "socrata-curator-utils"

libraryDependencies ++= CommonDeps.commonDeps

mimaPreviousArtifacts := Set(organization.value %% name.value % PreviousVersion.previousVersion.value)
