package utilities.pressure

/** Required import(s) */
import utilities.custom

/** Defining a class for the inches conversion to hpa */ 
class ToHpa(data:String) {
  
  /** Description: method for conversion from swedish mm(inches) to hpa
   *  Parameters: String type
   *  Return Type: String 
   */
  def convert = custom.doublePrecision((data.toDouble/10.toDouble)*33.86389.toDouble).toString()
  
  /** Description: method for conversion from inches to hpa
   *  Parameters: String type
   *  Return Type: String
   */
  def convertFromInch = custom.doublePrecision(data.toDouble*33.86389.toDouble).toString()
}