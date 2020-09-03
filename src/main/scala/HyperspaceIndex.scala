import com.microsoft.hyperspace.Hyperspace
import com.microsoft.hyperspace.index.IndexConfig

object HyperspaceIndex extends App with LocalSparkSession {

  val df = sparkSession.read
    .format("delta")
    .load("data/population/quantity")

  df.printSchema()
  println(df.queryExecution.optimizedPlan)

  /** Currently not working with Delta */
  val hs = Hyperspace()
  hs.createIndex(df, IndexConfig("idx", Seq("cityId"), Seq("year", "quantity")))
  hs.indexes.show()

}
