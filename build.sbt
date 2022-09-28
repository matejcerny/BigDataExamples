import Dependencies._

ThisBuild / name := "BigDataExamples"
ThisBuild / organization := "cz.matejcerny"
ThisBuild / scalaVersion := "2.13.9"

lazy val BigDataExamples = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Delta ++ Frameless ++ Logging ++ ScalaTest ++ Spark
  )

Global / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-language:higherKinds", // Allow higher-kinded types
  "-Ywarn-unused:imports" // Warn if an import selector is not referenced.
)
