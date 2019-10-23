package resources

/** Required import(s) */
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/** Defining a spark instance to access the spark associated variables */
object SparkInstance {
  /**  Configuration instance */
  val conf:SparkConf = new SparkConf().setAppName("Stockholm Weather Data Processing").setMaster("local") /** setting the Application name and master as local */
	val sc:SparkContext = new SparkContext(conf) /**  Spark context creation */
 
  val sqlContext = new org.apache.spark.sql.SQLContext(sc) /**  creating a sql context in association with spark context */
  import sqlContext.implicits._ /**  importing the sql context implicits */

  val spark = SparkSession.builder.getOrCreate() /**  creating or getting a spark session */
}