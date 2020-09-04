import sbt._

object Dependencies {

  object Versions {
    val Delta = "0.6.0"
    val Frameless = "0.8.0"
    val Hyperspace = "0.2.0"
    val ScalaLogging = "3.9.2"
    val ScalaTest = "3.1.2"
    val SLF4J = "1.7.25"
    val Spark = "2.4.6"
  }

  object Modules {
    val Delta = "io.delta"
    val Frameless = "org.typelevel"
    val Hyperspace = "com.microsoft.hyperspace"
    val ScalaLogging = "com.typesafe.scala-logging"
    val ScalaTest = "org.scalatest"
    val SLF4J = "org.slf4j"
    val Spark = "org.apache.spark"
  }

  val Delta = Seq(Modules.Delta %% "delta-core" % Versions.Delta)

  val Frameless = Seq(
    Modules.Frameless %% "frameless-dataset" % Versions.Frameless,
    Modules.Frameless %% "frameless-cats" % Versions.Frameless
  )

  val Hyperspace = Seq(Modules.Hyperspace %% "hyperspace-core" % Versions.Hyperspace)

  val Logging = Seq(
    Modules.ScalaLogging %% "scala-logging" % Versions.ScalaLogging,
    Modules.SLF4J % "slf4j-api" % Versions.SLF4J
  )

  val ScalaTest = Seq(Modules.ScalaTest %% "scalatest" % Versions.ScalaTest % Test)

  val Spark = Seq(
    Modules.Spark %% "spark-core" % Versions.Spark,
    Modules.Spark %% "spark-sql" % Versions.Spark,
    Modules.Spark %% "spark-streaming" % Versions.Spark
  )

}
