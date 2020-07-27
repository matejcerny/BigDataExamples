Global / name := "BigDataExamples"
Global / organization := "cz.matejcerny"
Global / scalaVersion := "2.12.12"
Global / assemblyJarName := s"${name.value}.jar"

val sparkVersion = "3.0.0"
val deltaVersion = "0.7.0"
val slf4jVersion = "1.7.25"

lazy val BigDataExamples = project
  .in(file("."))
  .settings(
    libraryDependencies ++= dependencies
  )

lazy val dependencies = Seq(
  // TEST
  "org.scalatest" %% "scalatest" % "3.1.2" % Test,
  "org.mockito" % "mockito-core" % "2.23.4" % Test,
  // LOGS
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
  // SPARK
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "io.delta" %% "delta-core" % deltaVersion
)

Global / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-language:higherKinds", // Allow higher-kinded types
  "-Ywarn-unused:imports" // Warn if an import selector is not referenced.
)