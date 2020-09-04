import java.util.concurrent.TimeUnit

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

import scala.concurrent.duration.FiniteDuration

object StructuredStreaming extends App with LocalSparkSession {

  val schema = StructType(Seq(StructField("text", StringType)))
  val duration = FiniteDuration(2, TimeUnit.SECONDS)

  /** Streaming text files from disk */
  val ds = sparkSession.readStream
    .schema(schema)
    .text("data/stream")

  val stream = ds.writeStream
    .trigger(Trigger.ProcessingTime(duration))
    .outputMode(OutputMode.Append())
    .foreachBatch { (df: DataFrame, _: Long) =>
      df.show()
    }
    .start()

  stream.awaitTermination()

}
