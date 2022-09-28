import io.delta.tables.DeltaTable
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{ col, lit }

object DeltaLakeInsertUpdateDelete extends App with LocalSparkSession {

  case class City(id: Long, name: String, district: String)
  val path = "data/population/city"

  /** Insert a new row */
  sparkSession
    .createDataFrame(Seq(City(-999L, "AAA", "AAA")))
    .write
    .format("delta")
    .mode(SaveMode.Append)
    .save(path)

  /** Update and delete it via delta API */
  val deltaTable = DeltaTable.forPath(sparkSession, path)

  deltaTable.update(
    col("name") === lit("AAA"),
    Map("name" -> lit("BBB"))
  )

  deltaTable.delete(
    col("id") === lit(-999L)
  )

  /** Compact files if needed */
  sparkSession.read
    .format("delta")
    .load(path)
    .repartition(1)
    .write
    .option("dataChange", value = false)
    .format("delta")
    .mode(SaveMode.Overwrite)
    .save(path)

  /** Flush history to keep just the newest data (only where no other process is using this table)
    */
  deltaTable.vacuum(0)

}
