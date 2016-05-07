# Configure Hive
Hive is optional to Data Cafe. It can be configured following the below steps once Hadoop is configured.

 $HADOOP_HOME/bin/hadoop fs -mkdir       /tmp

 $HADOOP_HOME/bin/hadoop fs -chmod g+w   /tmp

 $HADOOP_HOME/bin/hadoop fs -mkdir       /user/

 $HADOOP_HOME/bin/hadoop fs -mkdir       /user/hive

 $HADOOP_HOME/bin/hadoop fs -mkdir       /user/hive/warehouse

 $HADOOP_HOME/bin/hadoop fs -chmod g+w   /user/hive/warehouse



* Configure Hive Metastore with MySQL.




## Configure and enable Storage Plugin for Hive in Drill

 {

   "type": "hive",

   "enabled": true,

   "configProps": {

     "hive.metastore.uris": "thrift://localhost:9083",

     "hive.metastore.sasl.enabled": "false"

   }

 }


## Query Hive from Drill.

 SELECT firstname,lastname FROM hive.`customers`

 SELECT * FROM hive.`patients`

 SELECT * FROM hive.`slices`

 SELECT slices.sliceID, slices.slideBarCode, patients.patientID, patients.gender, patients.laterality

 FROM hive.`patients`, hive.`slices`

 WHERE patients.patientID = slices.patientID



