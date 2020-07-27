object DeltaLake extends App with LocalSparkSession {

  sparkSession.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .csv("data/obyvatelstvo.csv")
    .write
    .format("delta")
    .save("data/obyvatelstvo")

  val df = sparkSession.read
    .format("delta")
    .load("data/obyvatelstvo")

  df.printSchema()
  df.show()

}
