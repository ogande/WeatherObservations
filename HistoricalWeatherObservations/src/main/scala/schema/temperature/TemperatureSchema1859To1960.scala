package schema.temperature

/** Defining a case class for the temperature data from 1859 to 1960 year format */
case class TemperatureSchema1859To1960(year:Integer, month:Integer, day:Integer, morning:String, noon:String,
    evening:String, tmin:String,tmax:String)