import frameless.{ Job, TypedDataset }
import frameless.functions.aggregate.avg
import frameless.syntax._

object Frameless extends App with LocalSparkSession {

  case class Population(cityId: Long, year: Int, quantity: Int)

  import sparkSession.implicits._

  val df = sparkSession.read
    .format("delta")
    .load("data/population/quantity")
    .as[Population]

  val tds = TypedDataset.create(df)

  def filter(tds: TypedDataset[Population], year: Int): TypedDataset[Population] =
    tds.filter(tds('year) === year).as[Population]

  def average(tds: TypedDataset[Population]): Job[Double] =
    tds.agg(avg(tds('quantity))).firstOption().map(_.getOrElse(0))

  val program = for {
    avg2001 <- average(filter(tds, 2001))
    avg2011 <- average(filter(tds, 2011))
  } yield s"Average population in 2001 was $avg2001, in 2011: $avg2011"

  println(program.run())

}
