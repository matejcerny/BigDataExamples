import org.apache.spark.sql.functions._

object SparkDML extends App with LocalSparkSession {

  val df = sparkSession.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .csv("data/zvirata.csv")

  df.select(
    col("hodnota").as("quantity"),
    col("rok").as("year"),
    col("DRUHZVIRE_txt").as("animal")
  ).filter(col("uzemi_txt") === lit("Olomoucký kraj"))
    .filter(col("year") === lit("2018"))
    .orderBy(desc("quantity"))
    .show()

  df.createOrReplaceTempView("animals")

  sparkSession
    .sql(
      """SELECT hodnota AS quantity
      |     , rok AS year
      |     , DRUHZVIRE_txt AS animal
      |  FROM animals
      |  WHERE (uzemi_txt='Olomoucký kraj')
      |    AND (rok='2018')
      |  ORDER BY 1 DESC
      |""".stripMargin
    )
    .show()

}
