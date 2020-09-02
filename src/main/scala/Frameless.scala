import frameless.TypedDataset

object Frameless extends App with LocalSparkSession {

  import sparkSession.implicits._
  case class Obec(id: Long, nazev: String, okres: String)

  val ds = Seq(
    Obec(-997L, "AAA", "AAA"),
    Obec(-998L, "BBB", "BBB"),
    Obec(-999L, "CCC", "CCC")
  ).toDS

  val typedDS = TypedDataset.create(ds)

}
