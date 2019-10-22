/** Required import(s) */
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import resources._

/** Defining a Barometer Data Tests class for checking the barometer data */
class BarometerDataTests extends FunSuite with BeforeAndAfter {
  
  /** Defining a variable to get the count */
  var totalCountOfReadings1859To1861 :Long =  _
  
  before{
    totalCountOfReadings1859To1861= SparkInstance.sc.textFile("file:/databricks/driver/stockholm_barometer_1859_1861.txt").count
  }
  
  /** SIMILAR TESTS CAN BE WRITTEN FOR OTHER TIME PERIODS AND AFTER UNIFICATION */
  test("matching the count before and after transforming the barometer data from 1859 to 1861") {
    assert( totalCountOfReadings1859To1861 === SparkInstance.spark.read.format("avro").load("file:/databricks/driver/BarometerData/1859_1961").count())
  } 
}