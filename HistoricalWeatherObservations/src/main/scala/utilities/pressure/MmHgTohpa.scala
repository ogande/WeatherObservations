package utilities.pressure

/** Defining a class for implicit conversion of mmHg to Inches
 * 
 * @param mmhg of Double type
 */
class MmHgTohpa(mmhg:Double){
  
  /** Defining the two decimal format for the consistency of the numbers */
  val twoDecimalFormat = new java.text.DecimalFormat("0.00")
  twoDecimalFormat.setRoundingMode(java.math.RoundingMode.UP) /** Setting to Rouning up so that we will get much accurate result */
  
  /** Defining mmHgToHpa converter using implicits
   *  
   *	@param Unit
   *	@return String
   */
  def mmHgToHpa = twoDecimalFormat.format(mmhg*1.33322).toString
}