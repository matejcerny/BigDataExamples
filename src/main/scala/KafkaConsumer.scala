import java.util.concurrent.TimeUnit

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.streaming.{ OutputMode, Trigger }

import scala.concurrent.duration.FiniteDuration

object KafkaConsumer extends App with LocalSparkSession {

  val options = Map(
    ("kafka.bootstrap.servers", "localhost:9092"),
    ("subscribe", "topic1")
  )
  val duration = FiniteDuration(2, TimeUnit.SECONDS)

  val df = sparkSession.readStream
    .format("kafka")
    .options(options)
    .load()

  val stream = df.writeStream
    .trigger(Trigger.ProcessingTime(duration))
    .outputMode(OutputMode.Append())
    .foreachBatch { (ds: DataFrame, _: Long) =>
      ds.show()
    }
    .start()

  stream.awaitTermination()
}
