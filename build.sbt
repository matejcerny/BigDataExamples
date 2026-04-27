import Dependencies.*

ThisBuild / organization := "cz.matejcerny"
ThisBuild / scalaVersion := "3.7.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "BigDataExamples",
    publish / skip := true
  )
  .aggregate(spark4, spark3)

lazy val spark4 = project
  .in(file("spark4"))
  .settings(
    name := "spark4",
    libraryDependencies ++= Delta ++ Spark4 ++ SparkOn3
  )

lazy val spark3 = project
  .in(file("spark3"))
  .settings(
    name := "spark3",
    libraryDependencies ++= Spark3 ++ Wick
  )

Global / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-language:higherKinds", // Allow higher-kinded types
  "-Wunused:imports" // Warn if an import selector is not referenced.
)
