import org.scalatest.funsuite.AnyFunSuite

class LocalSparkSessionTest extends AnyFunSuite with LocalSparkSession {

  test("Init spark in local mode") {
    assert(sparkSession.conf.get("spark.master") == "local[*]")
  }

}
