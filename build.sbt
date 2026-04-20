import Dependencies._

ThisBuild / name := "BigDataExamples"
ThisBuild / organization := "cz.matejcerny"
ThisBuild / scalaVersion := "3.7.4"

lazy val BigDataExamples = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Delta ++ Spark ++ SparkOn3
  )

Global / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-language:higherKinds", // Allow higher-kinded types
  "-Wunused:imports" // Warn if an import selector is not referenced.
)
