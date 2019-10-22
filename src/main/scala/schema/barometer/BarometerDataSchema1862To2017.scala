package schema.barometer

/** Defining a case class for the barometer data from 1862 to 2017 year format */
case class BarometerDataSchema1862To2017(year:String, month:String, day:String, mAirPressure:String,
    nAirPressure:String, eAirPressure:String)