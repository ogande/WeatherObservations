package utilities.pressure

/* Defining a class for implicit conversion of mmHg to Inches
 * Parameters: mmhg of Double type
 */
class MmHgTohpa(mmhg:Double){
  
  /** Defining the two decimal format for the consistency of the numbers */
  val twoDecimalFormat = new java.text.DecimalFormat("0.00")
  twoDecimalFormat.setRoundingMode(java.math.RoundingMode.UP) //Setting to Rouning up so that we will get much accurate result
  
  /** Description : Defining mmHgToHpa converter using implicits
   *	 Parameters : Unit
   *	 Return type: String
   */
  def mmHgToHpa = twoDecimalFormat.format(mmhg*1.33322).toString
}