import org.apache.spark.sql.SparkSession
import org.slf4j.{ Logger, LoggerFactory }

trait LocalSparkSession {

  @transient lazy val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  val sparkSession: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
    .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
    .config("spark.databricks.delta.retentionDurationCheck.enabled", value = false)
    .getOrCreate()
}
