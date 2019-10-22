package schema.temperature

/** Defining a case class for the temperature data from 1756 to 1858 year format */
case class TemperatureSchema1756To1858(year:Integer, month:Integer, day:Integer,morning:String, noon:String,evening:String)