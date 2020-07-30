import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{col, monotonically_increasing_id}

object DeltaLakeCreateTable extends App with LocalSparkSession {

  /** Read CSV */
  val df = sparkSession.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .csv("data/obyvatelstvo.csv")

  /** Extract reference table */
  val dfRef = df
    .select(col("obec"), col("okres"))
    .distinct()
    .sort(col("obec"), col("okres"))
    .withColumn("id", monotonically_increasing_id + 1)
    .select(
      col("id"),
      col("obec").as("nazev"),
      col("okres")
    )

  dfRef.printSchema()

  /** Save reference table as delta table */
  dfRef
    .repartition(1)
    .write
    .format("delta")
    .mode(SaveMode.Overwrite)
    .save("data/obyvatelstvo/obec")

  /** Replace reference columns with id */
  val dfData = df
    .join(
      dfRef.select(
        col("id").as("obec_id"),
        col("nazev").as("obec_nazev"),
        col("okres").as("obec_okres")
      ),
      col("okres") === col("obec_okres")
        && col("obec") === col("obec_nazev")
    )
    .select(
      col("obec_id"),
      col("rok"),
      col("pocet")
    )

  dfData.printSchema()

  /** Save data as delta table */
  dfData
    .repartition(1)
    .write
    .format("delta")
    .mode(SaveMode.Overwrite)
    .save("data/obyvatelstvo/data")

}
