package spark.barometer


// Required import(s)
import org.apache.spark.sql.functions.{col, lit}
import org.apache.spark.sql._
import schema.barometer._
import resources.SparkInstance
import resources.SparkInstance.sqlContext.implicits._
import utilities.ScalaLogger
import utilities.custom._
import utilities.pressure.ToHpa
import utilities.pressure.MmHgTohpa

// Defining an object for processing barometer data
object ProcessBarometerData {

  /** Description: implicit method for conversion of the inch from 0.1* mm into inches
   *  Parameters: String type
   *  Return Type: String
   */
  implicit def swedishInchtoHpaConversion(si:String) = new ToHpa(si)
  
  /** Description: implicit method for conversion of the inch from 0.1* mm into inches
   *  Parameters: Double type
   *  Return Type: String
   */
  implicit def airPressureConverstionmmToHpa(mm:Double) = new MmHgTohpa(mm)

  /** Description: Converting the raw barometer data from 1756 to 1858 in the required format
   *  Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
   *  Return type: Unit - nothing but void, in Scala it is Unit
   */
  def From1756To1858(inputPath:String,requiredFormat:String, destPath:String = "") = {
     try{
        val inputRDD = SparkInstance.sc.textFile(inputPath) //Creating RDD with the given input path, used sc.textFile instead of spark.read.text, which creates DF
        val destinationpath = if(destPath == "") inputPath + "/BarometerData/1756_1858/" else destPath
        val dfFor1756To1858 = inputRDD.map{ line => BarometerDataSchema1756To1858(line.substring(0,4).trim, line.substring(4,8).trim,line.substring(8,11).trim,
              line.substring(11,18).trim, line.substring(18,25).trim, line.substring(25,32).trim, line.substring(32,39).trim, line.substring(39,46).trim, line.substring(46,line.length).trim)
              
        }.toDF
        dfFor1756To1858.repartition(1).write.format(requiredFormat).option("header", "true").option("delimiter","\t").mode(SaveMode.Append).save(destinationpath)
        ScalaLogger.log.info("Processed 1756 to 1858 barometer data")
       } catch {
      case e:Exception => ScalaLogger.log.error("error occured while processing 1756 to 1858 barometer file" + e.getStackTrace)
    }
  }
  
  /** Description: Converting the raw barometer data from 1859 to 1861 in the required format
   *  Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
   *  Return type: Unit - nothing but void, in Scala it is Unit
   */
  def From1859To1861(inputPath:String,requiredFormat:String, destPath:String = "") = {
     try{
           val inputRDD = SparkInstance.sc.textFile(inputPath) //Creating RDD with the given input path, used sc.textFile instead of spark.read.text, which creates DF
           val destinationpath = if(destPath == "") inputPath + "/BarometerData/1859_1961/" else destPath
           val dfFor18591861 = inputRDD.map{ line => BarometerDataSchema1859To1861(line.substring(0,4).trim, line.substring(4,8).trim,line.substring(8,11).trim,line.substring(11,19).trim.convert,
                                line.substring(19,25).trim, line.substring(25,32).trim.convert, line.substring(32,40).trim.convert, line.substring(40,46).trim, 
                                line.substring(46,53).trim.convert,line.substring(53,61).trim.convert, line.substring(61,67).trim, line.substring(67,73).trim.convert)}.toDF
           dfFor18591861.repartition(1).write.format("parquet").option("header", "true").option("delimiter","\t").mode(SaveMode.Append).save("file:/databricks/driver/BarometerData/1859_1861")
           ScalaLogger.log.info("Processed 1859 to 1861 barometer data")
        } catch {
      case e:Exception => ScalaLogger.log.error("error occured while processing 1859 to 1861 barometer file" + e.getStackTrace)
    }
  }   
  
  /** Description: Converting the raw barometer data from 1862 to 1937 in the required format
 	 *  Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
 	 *	Return type: Unit - nothing but void, in Scala it is Unit
 	 */
  def From1862To1937(inputPath:String,requiredFormat:String, destPath:String = "") = {
     try{
          val input18621937 = SparkInstance.sc.textFile(inputPath) //Creating RDD with the given input path, used sc.textFile instead of spark.read.text, which creates DF
          val destinationpath = if(destPath == "") inputPath + "/BarometerData/1862_1937/" else destPath
          val dfFor18621937 = input18621937.map(record => BarometerDataSchema1862To2017(record.substring(0,6).trim, record.substring(6,10).trim, record.substring(10,14).trim, record.substring(21,28).trim.toDouble.mmHgToHpa, record.substring(21,28).trim.toDouble.mmHgToHpa, record.substring(28,34).trim.toDouble.mmHgToHpa)).toDF
            dfFor18621937.repartition(1).write.format(requiredFormat).option("header", "true").option("delimiter","\t").mode(SaveMode.Append).save(destinationpath)
          ScalaLogger.log.info("Processed 1862 to 1937 barometer data")
        } catch {
      case e:Exception => ScalaLogger.log.error("error occured while processing 1862 to 1937 barometer file" + e.getStackTrace)
    }
  }
  
  /** Description: Converting the raw barometer data from 1938 to 1960 in the required format
   *  Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
   *  Return type: Unit - nothing but void, in Scala it is Unit
   */
  def From1938To1960(inputPath:String,requiredFormat:String, destPath:String = "") = {
     try{
           val inputRDD = SparkInstance.sc.textFile("file:/databricks/driver/stockholm_barometer_1938_1960.txt")
           val destinationpath = if(destPath == "") inputPath + "/BarometerData/1938_1960/" else destPath
           val reqDF = inputRDD.map(entry => BarometerDataSchema1862To2017(entry.substring(0,6).trim,entry.substring(6,10).trim, entry.substring(10,14).trim, entry.substring(14,22).trim, entry.substring(22,30).trim, entry.substring(30,37).trim)).toDF
           reqDF.repartition(1).write.format(requiredFormat).option("header", "true").option("delimiter","\t").mode(SaveMode.Append).save(destinationpath)
           ScalaLogger.log.info("Processed 1938 to 1960 barometer data")
         } catch {
          case e:Exception => ScalaLogger.log.error("error occured while processing 1938 to 1960 barometer file" + e.getStackTrace)
      }
   }
  
  /** Description: Converting the raw barometer data from 1961 to 2017 in the required format
   *	 Parameters: path of the file, required format ( e.g. AVRO/Parquet) and the destination path, default is blank, all are of String type
   *  Return type: Unit - nothing but void, in Scala it is Unit
   */
  def From1961To2017(inputPath:String,requiredFormat:String, destPath:String = "") = {
     try{
            val inputRDD = SparkInstance.sc.textFile(inputPath)
            val requiredDF = inputRDD.map(entry =>BarometerDataSchema1862To2017(entry.substring(0,5).trim, entry.substring(5,8).trim, entry.substring(8,11).trim, doublePrecision(entry.substring(11,18).trim.toDouble), doublePrecision(entry.substring(18,25).trim.toDouble), doublePrecision(entry.substring(25,31).trim.toDouble))).toDF
            //display(requiredDF)
            val destinationPath = if(destPath == "") inputPath + "/BarometerData/"+ inputPath.split("/").last.dropRight(4).takeRight(9) else destPath //Handling in programmatic way, using Scala's Expression Oriented way
            val withSourceType = requiredDF.withColumn("SourceType",lit(getSourceType(inputPath)))
            withSourceType.repartition(1).write.format("parquet").option("header", "true").option("delimiter","\t").mode(SaveMode.Append).save(destinationPath)
            ScalaLogger.log.info("Processed one of the file from 1961 to 2017 barometer data")
     } catch {
        case e:Exception => ScalaLogger.log.error("error occured while processing 1961 to 2017 barometer file"+ e.getStackTrace)
      }
  }
 
    
  /** Description: Unifies all the barometer data from 1756 to 2017
   *  Parameters: type of Format of String type
   *  Returns Unit
   */
  def UnifyingAllBarometerData(typeOfFormat:String) = {
    try {
      val Data1756To1858 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/BarometerData/1756_1858")
      val Data1859To1861 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/BarometerData/1859_1961")
      val Data1862To1937 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/BarometerData/1862_1937")
      val Data1938To1960 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/BarometerData/1938_1960")
      val Data1961To2012 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/BarometerData/1961_2012")
      val Data2013To2017 = SparkInstance.spark.read.format(typeOfFormat).load("file:/databricks/driver/BarometerData/2013_2017")
     
      val cols1756To1858 = Data1756To1858.columns.toList
      val cols1859To1861 = Data1859To1861.columns.toList
      val cols1862To1937 = Data1862To1937.columns.toList
      val cols1398To1960 = Data1938To1960.columns.toList
      val cols1961To2012 = Data1961To2012.columns.toList
      val cols2013To2017 = Data2013To2017.columns.toList
      
      val requiredColumns = cols1756To1858 ++ cols1859To1861 ++ cols1862To1937 ++ cols1398To1960 ++ cols1961To2012 ++ cols2013To2017 distinct //Getting required columns by removing the duplicates using distinct
      val resultantDF = Data1756To1858.select(unifiedColumns(cols1756To1858, requiredColumns):_*).union(Data1859To1861.select(unifiedColumns(cols1859To1861, requiredColumns):_*)
          .union(Data1862To1937.select(unifiedColumns(cols1862To1937, requiredColumns):_*).union(Data1938To1960.select(unifiedColumns(cols1398To1960, requiredColumns):_*)
          .union(Data1961To2012.select(unifiedColumns(cols1961To2012, requiredColumns):_*).union(Data2013To2017.select(unifiedColumns(cols2013To2017, requiredColumns):_*)))))).toDF()
      resultantDF.coalesce(1).write.partitionBy("SourceType").format(typeOfFormat).mode(SaveMode.Append).save("file:/databricks/driver/ConsolidatedDataInUnifiedForm/Barometer")
      ScalaLogger.log.info("Unified the complete barometer data")
    } catch {
      case e:Exception => ScalaLogger.log.error("error occured while unifying the complete barometer data"+ e.getStackTrace)
    }
  }
  
}