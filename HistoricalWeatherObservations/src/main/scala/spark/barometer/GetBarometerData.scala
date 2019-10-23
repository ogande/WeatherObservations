package spark.barometer

/** Required import(s) */
import utilities.DownloadUitlity

/** Defining an object to get the barometer data */
object GetBarometerData {
  
   private[this] val barometerData = new DownloadUitlity /** Creating a utility instance for downloading the barometer data */ 
  
  /** Method to download barometer data
   *  
   *	@param None
 	 *	@return Unit
 	 */
  def downloadBarometerData = {
    /** Downloads the historical barometer data for the given source */
    barometerData.downloadFromGivenURL("https://bolin.su.se/data/stockholm/files/stockholm-historical-weather-observations-2017/air_pressure/raw/stockholm_barometer_1756_1858.txt")
    barometerData.downloadFromGivenURL("https://bolin.su.se/data/stockholm/files/stockholm-historical-weather-observations-2017/air_pressure/raw/stockholm_barometer_1859_1861.txt")
    barometerData.downloadFromGivenURL("https://bolin.su.se/data/stockholm/files/stockholm-historical-weather-observations-2017/air_pressure/raw/stockholm_barometer_1862_1937.txt")
    barometerData.downloadFromGivenURL("https://bolin.su.se/data/stockholm/files/stockholm-historical-weather-observations-2017/air_pressure/raw/stockholm_barometer_1938_1960.txt")
    barometerData.downloadFromGivenURL("https://bolin.su.se/data/stockholm/files/stockholm-historical-weather-observations-2017/air_pressure/raw/stockholm_barometer_1961_2012.txt")
    barometerData.downloadFromGivenURL("https://bolin.su.se/data/stockholm/files/stockholm-historical-weather-observations-2017/air_pressure/raw/stockholm_barometer_2013_2017.txt")
    barometerData.downloadFromGivenURL("https://bolin.su.se/data/stockholm/files/stockholm-historical-weather-observations-2017/air_pressure/raw/stockholmA_barometer_2013_2017.txt")
  }  
}