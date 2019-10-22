package utilities

/** Required import(s) */
import org.apache.spark.sql.functions.{col, lit}

/** custom object to have required custom utilities */
object custom {
  
  /** Description: DoublePrecision method to have two digits after the decimal point
   *  Parameters: in Double
	 *  Return type: String
	 */
  def doublePrecision(in:Double):String = f"$in%2.2f"
  
  /** Description : Unifies the columns that are required for the given table
	 *  Parameters: column names of the given table in String and also List of total columns that we are expecting as String
	 *  Return type: List[org.apache.spark.sql.Column], which may have value if the column already exists or null
	 */
  def unifiedColumns(availableColumns:List[String],totalColumns:List[String]) = {
      totalColumns.map(clm => clm match{
      case c if availableColumns.contains(clm) => col(c)
      case _ => lit(null).as(clm)
    })
  }
  
  /** Description : Get the column name dynamically based on the name of the file
	 *  Parameters: name of the source file, String type
	 *	Return type: String i.e. source of the input file
	 */
  def getSourceType(pathOfSource:String) =   pathOfSource match {
    case a if(pathOfSource.contains('A')) => "Automatic" // e.g. stockholmA_daily_temp_obs_2013_2017_t1t2t3txtntm
    case m if(pathOfSource.contains("2013")) => "Manual" // e.g. stockholm_daily_temp_obs_2013_2017_t1t2t3txtntm
    case _ => "Unknown" // e.g. stockholm_daily_temp_obs_1961_2012_t1t2t3txtntm
  }
}