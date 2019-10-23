package utilities.pressure

/** Required import(s) */
import utilities.custom

/** Defining a class for the inches conversion to hpa */ 
class ToHpa(data:String) {
  
  /** Method for conversion from swedish mm(inches) to hpa
   *  
   *  @param String type
   *  @return String 
   */
  def convert = custom.doublePrecision((data.toDouble/10.toDouble)*33.86389.toDouble).toString()
  
  /** Method for conversion from inches to hpa
   *  
   *  @param String type
   *  @return String
   */
  def convertFromInch = custom.doublePrecision(data.toDouble*33.86389.toDouble).toString()
}