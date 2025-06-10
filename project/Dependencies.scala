import sbt._

object Dependencies {

  object Versions {
    val Delta = "4.0.0"
    val Frameless = "0.16.0"
    val Logback = "1.5.18"
    val ScalaTest = "3.2.19"
    val SLF4J = "2.0.17"
    val Spark = "4.0.0"
  }

  object Modules {
    val Delta = "io.delta"
    val Frameless = "org.typelevel"
    val Logback = "ch.qos.logback"
    val ScalaTest = "org.scalatest"
    val SLF4J = "org.slf4j"
    val Spark = "org.apache.spark"
  }

  val Delta: Seq[ModuleID] = Seq(
    Modules.Delta %% "delta-spark" % Versions.Delta
  )

  val Frameless: Seq[ModuleID] = Seq(
    Modules.Frameless %% "frameless-dataset" % Versions.Frameless,
    Modules.Frameless %% "frameless-cats" % Versions.Frameless
  )

  val ScalaTest: Seq[ModuleID] = Seq(
    Modules.ScalaTest %% "scalatest" % Versions.ScalaTest % Test
  )

  val Spark: Seq[ModuleID] = Seq(
    Modules.Spark %% "spark-core" % Versions.Spark,
    Modules.Spark %% "spark-sql" % Versions.Spark,
    Modules.Spark %% "spark-sql-kafka-0-10" % Versions.Spark,
    Modules.Spark %% "spark-streaming" % Versions.Spark
  )

}
