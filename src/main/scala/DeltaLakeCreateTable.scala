import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{ col, lit }

/** Datová sada obsahuje statistické údaje o počtu obyvatel s trvalým nebo dlouhodobým pobytem a o počtu domů v obcích
  * České republiky na základě výsledků sčítání lidu a domů od roku 1869 do roku 2011. Statistické údaje jsou přepočteny
  * na územní strukturu obcí v roce 2015.
  *
  * [[https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2F00025593%2Ff9a50772981c2bb3237277fff76bd969]]
  */
object DeltaLakeCreateTable extends App with LocalSparkSession {

  val path = "data/population_delta"

  /** Read the CSV */
  val df = sparkSession.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .csv("data/population.csv")

  /** Save as a delta table */
  df
    .coalesce(1)
    .write
    .format("delta")
    .mode(SaveMode.Overwrite)
    .save(path)

  /** Read a delta table */
  val dfDelta = sparkSession.read
    .format("delta")
    .load(path)

  /** Filter and show a result */
  dfDelta
    .filter(col("city") === lit("Praha"))
    .filter(col("year") <= lit(1900))
    .show()

}
