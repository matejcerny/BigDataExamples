ThisBuild / organization := "cz.matejcerny.bigdataexamples"
ThisBuild / scalaVersion := "2.11.8"

val sparkVersion = "2.4.0"
val slf4jVersion = "1.7.25"

lazy val bigDataExamples = (project in file("."))
  .settings(
    name := "BigDataExamples",
    assemblySettings,
    libraryDependencies ++= dependencies
  )

lazy val dependencies = Seq(
  // TEST
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "org.mockito" % "mockito-core" % "2.23.4" % Test,
  // LOGS
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
  // SPARK
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)

lazy val assemblySettings = Seq(
  assembly / assemblyJarName := s"${name.value}.jar",
  assembly / test := {},
  assembly / assemblyMergeStrategy := {
    case PathList("META-INF", _ @_*) => MergeStrategy.discard
    case x =>
      val oldStrategy = (assembly / assemblyMergeStrategy).value
      oldStrategy(x)
  }
)