import sbt.*

object Dependencies:

  object Versions:
    val Delta = "4.2.0"
    val Spark4 = "4.1.1"
    val Spark3 = "3.5.8"
    val SparkOn3 = "0.3.2"
    val Wick = "0.0.4"

  object Modules:
    val Delta = "io.delta"
    val Spark = "org.apache.spark"
    val SparkOn3 = "io.github.vincenzobaz"
    val Wick = "com.netflix.wick"

  val Delta: Seq[ModuleID] = Seq(
    Modules.Delta %% "delta-spark" % Versions.Delta
  ).map(_.cross(CrossVersion.for3Use2_13))

  val Spark4: Seq[ModuleID] = Seq(
    Modules.Spark %% "spark-core" % Versions.Spark4,
    Modules.Spark %% "spark-sql" % Versions.Spark4,
    Modules.Spark %% "spark-sql-kafka-0-10" % Versions.Spark4,
    Modules.Spark %% "spark-streaming" % Versions.Spark4
  ).map(_.cross(CrossVersion.for3Use2_13))

  val SparkOn3: Seq[ModuleID] = Seq(
    Modules.SparkOn3 %% "spark4-scala3-encoders" % Versions.SparkOn3,
    Modules.SparkOn3 %% "spark4-scala3-udf" % Versions.SparkOn3
  )

  val Spark3: Seq[ModuleID] = Seq(
    Modules.Spark %% "spark-core" % Versions.Spark3,
    Modules.Spark %% "spark-sql" % Versions.Spark3
  ).map(_.cross(CrossVersion.for3Use2_13))

  val Wick: Seq[ModuleID] = Seq(
    Modules.Wick %% "wick" % Versions.Wick
  )
