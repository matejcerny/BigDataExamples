object SparkPLSQL extends App with LocalSparkSession {

  sparkSession.sql(
    """
      |BEGIN
      |  DECLARE i INT DEFAULT 0;
      |
      |  CREATE TABLE IF NOT EXISTS my_table (number INT);
      |
      |  WHILE i < 10 DO
      |    SET i = i + 1;
      |    INSERT INTO my_table (number) VALUES (i);
      |  END WHILE;
      |
      |  SELECT * FROM my_table ORDER BY number;
      |END
      |""".stripMargin
  ).show()

  sparkSession.sql("DROP TABLE IF EXISTS my_table")
}
