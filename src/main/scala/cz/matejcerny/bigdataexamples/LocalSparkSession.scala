package cz.matejcerny.bigdataexamples

import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

trait LocalSparkSession {

  @transient lazy val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  val sparkSession: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    .getOrCreate()
}
