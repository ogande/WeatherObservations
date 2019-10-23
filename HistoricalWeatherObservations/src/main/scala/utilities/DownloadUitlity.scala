package utilities

/** Required import(s) */
import sys.process._ /** imported this for #> */
import java.net.URL
import java.io.File

/** Download utility class - used for downloading the files from the given URL */
class DownloadUitlity {

  /** Downloads the file with the given URL
   *  
   * @param path to be downloaded of String type
   * @return Any - As it is having an exception handling, the super type of all the types is Any
   */
  def downloadFromGivenURL(urlToBeDownloaded:String) = {
    try{
      ScalaLogger.log.info("Going to download the data from the given URL")
      new URL(urlToBeDownloaded) #> new File(urlToBeDownloaded.split("/").last) !! /** Download the file in the given location, preserves the filename */
    }
    catch{
      case e:Exception => println("Exception occured in the method... " +  ScalaLogger.getMethodName)
                         ScalaLogger.log.error("failed to download from the given URL" + e.getStackTrace)
      }
    }
}