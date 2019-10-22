package spark.temperature

/** Required import(s) */
import org.apache.spark.sql._
import org.apache.spark.sql.functions.{col, lit}
import utilities.ScalaLogger
import utilities.custom._
import resources.SparkInstance
import schema.temperature._
import resources._
import resources.SparkInstance.sqlContext.implicits._

/** Defining an object for processing temperature data */
object ProcessTemperatureData {
  
  /** Description: Converting the raw temperature data from 1756 to 1858 in the required format
	 *  Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
	 *  Return type: Unit - nothing but void, in Scala it is Unit
	 */
  def From1756To1858(inputPath:String,requiredFormat:String, destPath:String = "") = {
    try{
      val inputRDD = SparkInstance.sc.textFile(inputPath) //Creating RDD with the given input path, used sc.textFile instead of spark.read.text, which creates DF
      val destinationpath = if(destPath == "") inputPath + "/TemperatureData/1756_1858/" else destPath
      if(inputRDD.first.length == 34){
          val data1756To1858 = inputRDD.map(line => TemperatureSchema1756To1858(line.substring(1,5).trim.toInt, line.substring(7,9).trim.toInt,line.substring(11,13).trim.toInt,line.substring(15,21).trim,line.substring(22,27).trim,line.substring(29,34).trim)).toDF
          data1756To1858.coalesce(1).write.format(requiredFormat).mode(SaveMode.Append).save(destinationpath)
          ScalaLogger.log.info("Processed 1756 to 1858 temperature data")
        }
      else
      {
        ScalaLogger.log.info("Check the given input, seems it is not in the expected length/format")
      }
    }catch {
      case e:Exception => ScalaLogger.log.error("error occured while processing 1756 to 1858 temperature file"+e.getStackTrace)
    }
  }
  
  /** Description:  Description: Converting the raw temperature data from 1859 to 1960 in the required format
   *  Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
   *  Return type: Unit - nothing but void, in scala it is Unit
   */
  def From1859To1960(inputPath:String,requiredFormat:String, destPath:String = "") = {
    try{
      val inputRDD = SparkInstance.sc.textFile(inputPath) //Creating RDD with the given input path
      val destinationpath = if(destPath == "") inputPath + "/TemperatureData/1859_1960/" else destPath
      if(inputRDD.first.length == 40){
         val data1859To1960 = inputRDD.map(reading =>TemperatureSchema1859To1960(reading.substring(0,4).trim.toInt, reading.substring(5,8).trim.toInt, reading.substring(8,11).trim.toInt,doublePrecision(reading.substring(11,17).trim.toDouble),doublePrecision(reading.substring(17,22).trim.toDouble),doublePrecision(reading.substring(22,29).trim.toDouble),doublePrecision(reading.substring(29,34).trim.toDouble),doublePrecision(reading.substring(34,40).trim.toDouble))).toDF
       // println("count is: "+ data1859To1960.count)
        data1859To1960.coalesce(1).write.format(requiredFormat).mode(SaveMode.Append).save(destinationpath)
        ScalaLogger.log.info("Processed 1859 to 1960 temperature data")
        }
      else
      {
        ScalaLogger.log.info("Check the given input, seems it is not in the expected length/format")
      }
    }catch {
    case e:Exception => ScalaLogger.log.error("error occured while processing 1859 to 1960 temperature file" + e.getStackTrace)
    }
  }
  
  /** Description:  Description: Converting the raw temperature data from 1961 to 2017 in the required format
   *  Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
   *  Return type: Unit - nothing but void, in scala it is Unit
   */
  def From1961To2017(inputPath:String,requiredFormat:String, destPath:String = "") = {
    try{
      val inputRDD1 = SparkInstance.sc.textFile(inputPath)
      val destinationpath = if(destPath == "") inputPath + "/TemperatureData/1961_2017/" else destPath
      val inputLengths = List(46,47) // Defining the lengths of different files
      
      if(inputLengths.contains(inputRDD1.first.length)){ // Have hard code to consider 3 different sources with 46 and 47 length, needs to be improved
         val data1961To2017 =  inputRDD1.map(t =>TemperatureSchema1961To2017(t.substring(0,4).trim.toInt, t.substring(5,8).trim.toInt, t.substring(8,11).trim.toInt,doublePrecision(t.substring(11,17).trim.toDouble),doublePrecision(t.substring(17,22).trim.toDouble),doublePrecision(t.substring(22,29).trim.toDouble),doublePrecision(t.substring(29,34).trim.toDouble),doublePrecision(t.substring(34,40).trim.toDouble),doublePrecision(t.substring(40,t.length).trim.toDouble))).toDF
         val withSourceType = data1961To2017.withColumn("SourceType", lit(getSourceType(inputPath)))
         withSourceType.coalesce(1).write.format(requiredFormat).mode(SaveMode.Append).save(destinationpath)
         ScalaLogger.log.info("Processed 1961 to 2017 temperature data")
        }
      else
      {
       ScalaLogger.log.info("Check the given input, seems it is not in the expected length/format")
      }
    }catch {
      case e:Exception => ScalaLogger.log.error("error occured while processing 1961 to 2017 temperature file" + e.getStackTrace)
    }
  }
  
  /** Description: Unifies all the temperature data from 1756 to 2017
   *  Parameters: type of Format of String type
   * Returns Unit
   */
  def UnifyingAllTemperatureData(typeOfFormat:String) = {
    try {
        val Data1756To1858 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/TemperatureData/1756_1858")
        val Data1859To1960 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/TemperatureData/1859_1960")
        val Data1961To2017  = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/TemperatureData/1961_2017")
        
        val cols1756To1858 = Data1756To1858.columns.toList
        val cols1859To1960 = Data1859To1960.columns.toList
        val cols1961_TillDate = Data1961To2017.columns.toList
        val requiredColumns = cols1756To1858 ++ cols1859To1960 ++ cols1961_TillDate distinct //Getting required columns by removing the duplicates using distinct
        
        val resultantDF = Data1756To1858.select(unifiedColumns(cols1756To1858, requiredColumns):_*).union(Data1859To1960.select(unifiedColumns(cols1859To1960, requiredColumns):_*)
            .union(Data1961To2017.select(unifiedColumns(cols1961_TillDate, requiredColumns):_*))).toDF()
        resultantDF.coalesce(1).write.partitionBy("SourceType").format(typeOfFormat).mode(SaveMode.Append).save("file:/databricks/driver/ConsolidatedDataInUnifiedForm/Temperature")
        ScalaLogger.log.info("Unified the complete barometer data")
      } catch {
      case e:Exception => ScalaLogger.log.error("error occured while unifying the complete barometer data"+ e.getStackTrace)
    }
  }
}