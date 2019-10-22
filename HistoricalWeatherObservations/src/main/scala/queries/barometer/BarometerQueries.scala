package queries.barometer

/** Required import(s) */
import resources.SparkInstance

/** Defining an object for barometer queries */
object BarometerQueries {

  /** creating a DataFrame for the entire barometer data */
  val barometerData = SparkInstance.spark.read.format("parquet").load("file:/databricks/driver/BarometerData/")
  /** Defining a sample table for querying..  */
  barometerData.createOrReplaceTempView("BAROMETERDATA")
  /** Sample query */
  val toatlCountOfBarometerData = SparkInstance.spark.sql("select count(*) from BAROMETERDATA")
  println(toatlCountOfBarometerData)
}