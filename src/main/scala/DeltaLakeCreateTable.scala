import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{col, monotonically_increasing_id}

object DeltaLakeCreateTable extends App with LocalSparkSession {

  /** Read CSV */
  val df = sparkSession.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .csv("data/population.csv")

  /** Extract reference table */
  val dfRef = df
    .select(col("city"), col("district"))
    .distinct()
    .sort(col("city"), col("district"))
    .withColumn("id", monotonically_increasing_id + 1)
    .select(
      col("id"),
      col("city").as("name"),
      col("district")
    )

  dfRef.printSchema()

  /** Save reference table as delta table */
  dfRef
    .repartition(1)
    .write
    .format("delta")
    .mode(SaveMode.Overwrite)
    .save("data/population/city")

  /** Replace reference columns with id */
  val dfData = df
    .join(
      dfRef.select(
        col("id").as("cityId"),
        col("name").as("cityName"),
        col("district").as("cityDistrict")
      ),
      col("district") === col("cityDistrict")
        && col("city") === col("cityName")
    )
    .select(
      col("cityId"),
      col("year"),
      col("quantity")
    )

  dfData.printSchema()

  /** Save data as delta table */
  dfData
    .repartition(1)
    .write
    .format("delta")
    .mode(SaveMode.Overwrite)
    .save("data/population/quantity")

}
