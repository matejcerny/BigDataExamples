import org.apache.spark.sql.functions._

object SparkDML extends App with LocalSparkSession {

  val df = sparkSession.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .csv("data/zvirata.csv")

  df.select(
      col("hodnota").as("pocet"),
      col("rok"),
      col("DRUHZVIRE_txt").as("zvire")
    )
    .filter(col("uzemi_txt") === lit("Olomoucký kraj"))
    .filter(col("rok") === lit("2018"))
    .orderBy(desc("pocet"))
    .show()

  df.createOrReplaceTempView("zvirata")

  sparkSession.sql(
    """SELECT hodnota AS pocet
      |     , rok
      |     , DRUHZVIRE_txt AS zvire
      |  FROM zvirata
      |  WHERE (uzemi_txt='Olomoucký kraj')
      |    AND (rok='2018')
      |  ORDER BY 1 DESC
      |""".stripMargin
  ).show()


}
