import sbt.*

object Dependencies:

  object Versions:
    val Delta = "4.2.0"
    val Spark = "4.1.1"
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

  val Spark: Seq[ModuleID] = Seq(
    Modules.Spark %% "spark-core" % Versions.Spark,
    Modules.Spark %% "spark-sql" % Versions.Spark,
    Modules.Spark %% "spark-sql-kafka-0-10" % Versions.Spark,
    Modules.Spark %% "spark-streaming" % Versions.Spark
  ).map(_.cross(CrossVersion.for3Use2_13))

  val SparkOn3: Seq[ModuleID] = Seq(
    Modules.SparkOn3 %% "spark4-scala3-encoders" % Versions.SparkOn3,
    Modules.SparkOn3 %% "spark4-scala3-udf" % Versions.SparkOn3
  )

  val Wick: Seq[ModuleID] = Seq(
    Modules.Wick %% "wick" % Versions.Wick
  )
