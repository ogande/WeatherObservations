package schema.barometer

/** Defining a case class for the barometer data from 1756 to 1858 year format */
case class BarometerDataSchema1756To1858(year:String, month:String, day:String, mBaro:String, mBaroTemp:String,
    nBaro:String, nBaroTemp:String, eBaro:String, eBaroTemp:String)
