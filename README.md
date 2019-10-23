Common Assumptions for both barometer and temperature data
1. Length of the input is Fixed for different years formats.
2. Double precision to have data integrity/consistency across all years data because different years reading are having different precisions. 
3. Used local File System in Databricks community Edition(Spark doesn't differentiate the functionality based on the File System, as we know spark is a processing Engine - doesn't have storage, so supports other File Systems). To place the files in HDFS, we can use hadoop fs -copyFromLocal <<source path>> <<Destination path>>.
4. Unification can be done for all the years of data in one method to have consistent format of the data and ready for consumption.
5. Structure of the project is for on-prem, considering the scalability(for the new schema's/formats) and low coupling & high cohesion for development/maintenance
6. Data cleansing have been instrumented while extracting and storing in the resultant data frames
7. Manual/Automatic/Unknown have been added for the source type, if it is unclear it will be null
8. Tables/Views are created in the program. Querying can be done on the created tables/views.
9. Used scalatest framework for writing the test cases, wrote sample tests, needs more test cases to test all the data.
10. Build is based on the Maven with pom.xml file

Assumptions specific to temperature data:
1. For good schema evolution and efficient storage we will be using AVRO, a row major file format (we don't have mulitple/nested values in any of the columns).
2. All the temperature readings are in degC, so kept as it is
3. Each individual temperature readings have been stored in the AVRO format, the format can be changed with the minimal code i.e. passing the format type
4. All the available historical data is stored in one AVRO file, the columns which are not available are having null 

Assumptions specific to barometer data:
1. For good an efficient storage decided to use Parquet format, this can be changed with the minimal code i.e. passing the format type
2. Air pressure is unified and stored in hpa (other units found are mmhg and swedish inches)
3. Not included correction period of 1996 to 2000 period

How to Run/Flow of the program:
1. object HistoricalDataUnificationAndProcessing having the flow of downloading, cleansing/formatting and saving in the tables with the required conversions and followed by unification of all the data, divided into two broad categories: temperature and barometer
2. Testing can be done using the structure at \src\test\scala
3. Querying can be done using the structure at \src\main\scala\queries\
4. Command to run on the cluster: spark-submit --class HistoricalDataUnificationAndProcessing <<pathOf the Jar file with .jar as extension>>

Structure of the project:
1. Main program is \src\main\scala\HistoricalDataUnificationAndProcessing.scala
2. Resource(s) required to run are at \src\main\scala\resources
3. Queries: \src\main\scala\queries\barometer and \main\scala\queries\temperature
4. Schema: \src\main\scala\schema\barometer and \src\main\scala\schema\temperature
5. Spark code : \src\main\scala\spark\barometer and \src\main\scala\spark\temperature
6. Test: \src\test\scala for both temperature and barometer data
7. Common utilities: \src\main\scala\utilities
8. Specific utilities: \src\main\scala\utilities\pressure, temperature doesn't need any specific utility
9. Build: \pom.xml

Improvements further can be made:
1. Joining of both barometer and temperature data for further easiness/conciseness for data scientists/other consumers
2. Dynamic Schema construction
3. Hard coded the databricks paths in the project, can be changed depending on the File System type which is compliance to HDFS API's and supported by spark
4. Adding more test cases using the scalatest framework
