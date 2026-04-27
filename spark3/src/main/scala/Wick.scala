import com.netflix.wick.functions.{ desc, sum }
import com.netflix.wick.{ *, given }
import org.apache.spark.sql.Encoder
import scala3encoders.given

case class Livestock(
    entity: String,
    code: String,
    year: Int,
    asses: Double,
    buffalo: Double,
    cattle: Double,
    goats: Double,
    horses: Double,
    mules: Double,
    pigs: Double,
    sheep: Double,
    turkeys: Double,
    chickens: Double
)

object Wick extends App with LocalSparkSession:

  val df = sparkSession.read
    .option("header", value = true)
    .schema(summon[Encoder[Livestock]].schema)
    .csv("data/livestock.csv")

  val livestock = DataSeq[Livestock](df)

  // Typed arithmetic: `+` compiles only across numeric columns.
  // Swap `row.cattle` for `row.entity` below and the compiler rejects it.
  livestock
    .filter(_.year === 2014)
    .select(row =>
      (
        entity = row.entity,
        total_millions = (row.cattle + row.sheep + row.chickens) / 1_000_000
      )
    )
    .orderBy(row => desc(row.total_millions))
    .show()

  // Aggregation: `agg` only accepts scalar expressions.
  // Drop the `sum(...)` wrapper and the code won't compile.
  livestock
    .groupBy(row => (entity = row.entity))
    .agg(row => (cattle_total_millions = sum(row.cattle) / 1_000_000))
    .orderBy(row => desc(row.cattle_total_millions))
    .show()

  // Bugs Wick catches at compile time (uncomment any line to see the error):
  // livestock.filter(_.cattle === "lots")                     // String vs Double
  // livestock.select(r => (x = r.entity + r.cattle))          // String + Double
  // livestock.orderBy(_.cttle)                                // typo - no such field

  df.createOrReplaceTempView("livestock")

  // Untyped SQL twin: a typo in `cattle` or a bad `cattle + entity`
  // only blows up after `spark-submit` lands on the cluster.
  sparkSession
    .sql(
      """SELECT entity
        |     , (cattle + sheep + chickens) / 1000000 AS total_millions
        |  FROM livestock
        |  WHERE year = 2014
        |  ORDER BY total_millions DESC
        |""".stripMargin
    )
    .show()
