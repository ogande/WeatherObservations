/** Required import(s) */
import spark.temperature._
import spark.barometer._

/** Defining main object for handling the barometer and temperature data */
object HistoricalDataUnificationAndProcessing {

  /** main method which does the downloading, processing and 
   *  unifying the data and stores in the required format, which will be ready to use in an unified and consistent format for all the data
   */
  def main(args: Array[String]): Unit = {
   
      /** Get Temperature data */
      GetTemperatureData.downloadTemperatureData
      
      /** clean/format(e.g. double precision) and store in the given format */
      ProcessTemperatureData.From1756To1858("file:/databricks/driver/stockholm_daily_temp_obs_1756_1858_t1t2t3.txt","avro")
      ProcessTemperatureData.From1859To1960("file:/databricks/driver/stockholm_daily_temp_obs_1859_1960_t1t2t3txtn.txt", "avro")
      ProcessTemperatureData.From1961To2017("file:/databricks/driver/stockholm_daily_temp_obs_1961_2012_t1t2t3txtntm.txt", "avro")
      ProcessTemperatureData.From1961To2017("file:/databricks/driver/stockholm_daily_temp_obs_2013_2017_t1t2t3txtntm.txt", "avro")
      ProcessTemperatureData.From1961To2017("file:/databricks/driver/stockholmA_daily_temp_obs_2013_2017_t1t2t3txtntm.txt","avro")
      
      /** Unify the whole data into one file, so that it will be easy to query whatever data is required. */
      ProcessTemperatureData.UnifyingAllTemperatureData("avro")
      
      /** Get Barometer data */
      GetBarometerData.downloadBarometerData
      
      /** clean/format(e.g. double precision and converting into one unique measurement e.g. all data will be in hpa units) and store in the given format */
      ProcessBarometerData.From1756To1858("file:/databricks/driver/stockholm_barometer_1756_1858.txt", "parquet")
      ProcessBarometerData.From1859To1861("file:/databricks/driver/stockholm_barometer_1859_1861.txt", "parquet")
      ProcessBarometerData.From1862To1937("file:/databricks/driver/stockholm_barometer_1862_1937.txt", "parquet")
      ProcessBarometerData.From1938To1960("file:/databricks/driver/stockholm_barometer_1938_1960.txt", "parquet")
      ProcessBarometerData.From1961To2017("file:/databricks/driver/stockholm_barometer_1961_2012.txt", "parquet")
      ProcessBarometerData.From1961To2017("file:/databricks/driver/stockholm_barometer_2013_2017.txt", "parquet")
      ProcessBarometerData.From1961To2017("file:/databricks/driver/stockholmA_barometer_2013_2017.txt", "parquet")
      
      /** Unify the whole data into one file, so that it will be easy to query whatever data is required. */
      ProcessBarometerData.UnifyingAllBarometerData("parquet")
  } 
}