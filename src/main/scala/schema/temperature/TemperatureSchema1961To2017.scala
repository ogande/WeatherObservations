package schema.temperature

/** Defining a case class for the temperature data from 1961 to 2017 year format */
case class TemperatureSchema1961To2017(year:Integer, month:Integer, day:Integer, morning:String, noon:String, 
    evening:String, tmin:String,tmax:String,estimated_diurnal_mean:String)