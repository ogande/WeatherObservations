package schema.barometer

/**Defining a case class for the barometer data from 1859 to 1861 year format */
case class BarometerDataSchema1859To1861(year:String, month:String, day:String, mBaro:String, mBaroTemp:String, 
    mAirPressure:String, nBaro:String, nBaroTemp:String,nAirPressure:String, eBaro:String, eBaroTemp:String,eAirPressure:String)
