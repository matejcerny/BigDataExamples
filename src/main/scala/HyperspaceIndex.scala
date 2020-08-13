import com.microsoft.hyperspace.Hyperspace
import com.microsoft.hyperspace.index.IndexConfig

object HyperspaceIndex extends App with LocalSparkSession {

  val df = sparkSession.read
    .format("delta")
    .load("data/obyvatelstvo/data")

  df.printSchema()

  /** Hyperspace is currently not working with Spark 3.0 */
  val hs = Hyperspace()
  hs.createIndex(df, IndexConfig("idx", Seq("obec_id"), Seq("rok", "pocet")))
  hs.indexes.show()

}
