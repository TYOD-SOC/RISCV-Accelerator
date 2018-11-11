organization := "ISL USETC"
version := "0.1"
name := "TYOD SoC"

lazy val commonSettings = Seq(
  scalaVersion := "2.11.12",  // This needs to match rocket-chip's scalaVersion
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-language:reflectiveCalls"
  )
)

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
// The following are the default development versions, not the "release" versions.
val defaultVersions = Map(
  "chisel3" -> "3.1-SNAPSHOT",
  "chisel-iotesters" -> "1.2-SNAPSHOT"
  )

libraryDependencies ++= (Seq("chisel3","chisel-iotesters").map {
  dep: String => "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version", defaultVersions(dep)) })

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

// Recommendations from http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false

// Disable parallel execution when running te
//  Running tests in parallel on Jenkins currently fails.
parallelExecution in Test := false
// A RootProject (not well-documented) tells sbt to treat the target directory
// as its own root project, reading its build settings. If we instead used the
// normal `project in file()` declaration, sbt would ignore all of rocket-chip's
// build settings, and therefore not understand that it has its own dependencies
// on chisel, etc.

//lazy val rocketChip = RootProject(file("rocket-chip"))

  lazy val rocketChip = RootProject(file("../rocket-chip"))


lazy val sifiveBlocks = (project in file("sifive-blocks")).
  dependsOn(rocketChip).
  settings(commonSettings: _*)

lazy val TYOD_SOC = (project in file(".")).
  dependsOn(rocketChip,sifiveBlocks).
  settings(commonSettings: _*)

/*lazy val TYOD_SOC = (project in file(".")).
  dependsOn(rocketChip).
  settings(commonSettings: _*)*/

