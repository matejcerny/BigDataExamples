package cz.matejcerny.bigdataexamples

import org.scalatest.FunSuite

class LocalSparkSessionTest extends FunSuite with LocalSparkSession {

  test("Init spark in local mode") {
    assert(sparkSession.conf.get("spark.master") == "local[*]")
  }

}
