import org.apache.spark.sql.types.{ StringType, StructField, StructType }

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object KafkaProducer extends App with LocalSparkSession {

  val schema = StructType(Seq(StructField("text", StringType)))
  val duration = FiniteDuration(2, TimeUnit.SECONDS)

  /** Streaming text files from disk */
  val df = sparkSession.readStream
    .schema(schema)
    .text("data/stream")

  val options = Map(
    ("kafka.bootstrap.servers", "localhost:9092"),
    ("topic", "topic1")
  )

  df
    // .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
    .writeStream
    .format("kafka")
    .options(options)
    .start()

}
