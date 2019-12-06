import sbt._

object PreviousVersion {
  val previousVersion = settingKey[String]("The previous version of the library, for mima checks")
}
