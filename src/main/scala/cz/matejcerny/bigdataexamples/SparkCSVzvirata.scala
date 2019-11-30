package cz.matejcerny.bigdataexamples

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

/**
  * Hospodářská zvířata podle krajů, Český statistický úřad
  * Datová sada obsahuje časovou řadu s údaji o stavech hospodářských
  * zvířat v krajích České republiky od roku 2002.
  *
  * [[https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2Fhttp---vdb.czso.cz-pll-eweb-package_show-id-270230]]
  */
object SparkCSVzvirata extends App with LocalSparkSession {

  val df = sparkSession.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .csv("data/zvirata.csv")

  df.printSchema()

  val schema = StructType(
    Seq(
      StructField("idhod", StringType),
      StructField("hodnota", IntegerType),
      StructField("stapro_kod", StringType),
      StructField("DRUHZVIRE_cis", StringType),
      StructField("DRUHZVIRE_kod", StringType),
      StructField("refobdobi", DateType),
      StructField("rok", IntegerType),
      StructField("uzemi_cis", StringType),
      StructField("uzemi_kod", StringType),
      StructField("STAPRO_TXT", StringType),
      StructField("uzemi_txt", StringType),
      StructField("DRUHZVIRE_txt", StringType)
    )
  )

  val dfWithSchema = sparkSession.read
    .schema(schema)
    .option("header", value = true)
    .option("dateFormat", "yyyyMMdd")
    .csv("data/zvirata.csv")

  dfWithSchema.printSchema()

  dfWithSchema
    .filter(col("rok") === lit("2018"))
    .coalesce(1)
    .write
    .option("header", value = true)
    .mode("overwrite")
    .csv("data/zvirata_2018.csv")

}
