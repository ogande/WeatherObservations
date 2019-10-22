/** Required import(s) */
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import resources._

/** Defining a Temperature Data Tests class for checking the temperature data */
class TemperatureDataTests extends FunSuite with BeforeAndAfter {
  
  /** Defining a variable to get the count */
  var totalCountOfReadings1756To1858:Long =  _
  
  before{
    totalCountOfReadings1756To1858 = SparkInstance.sc.textFile("file:/databricks/driver/stockholm_daily_temp_obs_1756_1858_t1t2t3.txt").count
  }
  
  /** SIMILAR TESTS CAN BE WRITTEN FOR OTHER TIME PERIODS AND AFTER UNIFICATION */
  test("matching the count before and after transforming the temperature data from 1756 to 1858") {
    assert( totalCountOfReadings1756To1858 === SparkInstance.spark.read.format("avro").load("file:/databricks/driver/TemperatureData/1756_1858").count())
  }
  
  
}