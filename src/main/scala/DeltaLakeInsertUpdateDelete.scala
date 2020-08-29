import io.delta.tables.DeltaTable
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{col, lit}

object DeltaLakeInsertUpdateDelete extends App with LocalSparkSession {

  case class Obec(id: Long, nazev: String, okres: String)
  val path = "data/obyvatelstvo/obec"

  /** Insert new row */
  sparkSession
    .createDataFrame(Seq(Obec(-999L, "AAA", "AAA")))
    .write
    .format("delta")
    .mode(SaveMode.Append)
    .save(path)

  /** Update and delete it via delta API */
  val deltaTable = DeltaTable.forPath(sparkSession, path)

  deltaTable.update(
    col("nazev") === lit("AAA"),
    Map("nazev" -> lit("BBB"))
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

  /**
    * Flush history to keep just the newest data
    * (only where no other process is using this table)
    */
  deltaTable.vacuum(0)

}
