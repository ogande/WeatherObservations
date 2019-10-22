package queries.temperature

/** Required import(s) */
import resources.SparkInstance

/** Defining an object for temperature queries */
object TemperatureQueries {
  
  /** creating a DataFrame for the entire barometer data */
  val temperatureData = SparkInstance.spark.read.format("parquet").load("file:/databricks/driver/TemperatureData/")
  /** Defining a sample table for querying..  */
  temperatureData.createOrReplaceTempView("TEMPERATUREDATA")
  /** Sample query */
  val toatlCountOfTemperatureData = SparkInstance.spark.sql("select count(*) from TEMPERATUREDATA")
  println(toatlCountOfTemperatureData)
}