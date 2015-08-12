# datacafe
A Dynamic Data Warehousing System

Apache Drill can be used to query Hadoop and HDFS in a very efficient way. The steps are as below. Refer to the linked pages for each step.

1. Configuring Mongo in EC2.

Connecting to the remote EC2/Mongo Instance
$ ssh -i "pradeeban.pem" ubuntu@ec2-54-166-90-207.compute-1.amazonaws.com

Copy files to the remote instance.

$ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/clinical.csv ubuntu@ec2-54-166-90-207.compute-1.amazonaws.com:/home/ubuntu

$ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/pathology.csv ubuntu@ec2-54-166-90-207.compute-1.amazonaws.com:/home/ubuntu


Importing the collections from the csv files

mongoimport --db pathology --collection pathologyData --type csv --headerline --file pathology.csv
mongoimport --db clinical --collection clinicalData --type csv --headerline --file clinical.csv


Executing mongo commands.

mongo

use pathology

db.pathologyData.find()


use clinical

db.clinicalData.find()


2. Configure Hadoop 

There may be some errors encountered when executing Hadoop with Java 8 with the default configurations. Further, formatting the file system will fix any error from Hadoop.

  $ $HADOOP_HOME/bin/hdfs namenode -format



3. Start Hadoop NameNode daemon and DataNode daemon -

$ $HADOOP_HOME/sbin/start-dfs.sh


Once you are done with the experiments, you may stop the daemons with

$ $HADOOP_HOME/sbin/stop-dfs.sh


4. Browse the web interface for the name node - http://localhost:50070/


5. Configure Drill

* Launch Drill in Embedded mode -

$ $DRILL_HOME/bin/drill-embedded 

6. Browse the web interface for Drill - http://localhost:8047/

7. Configure Hive

$ $HADOOP_HOME/bin/hadoop fs -mkdir       /tmp
$ $HADOOP_HOME/bin/hadoop fs -chmod g+w   /tmp
$ $HADOOP_HOME/bin/hadoop fs -mkdir       /user/
$ $HADOOP_HOME/bin/hadoop fs -mkdir       /user/hive
$ $HADOOP_HOME/bin/hadoop fs -mkdir       /user/hive/warehouse
$ $HADOOP_HOME/bin/hadoop fs -chmod g+w   /user/hive/warehouse

Configure Hive Metastore with MySQL.

8. Run Hive Metastore and Hive

$ $HIVE_HOME/bin/hive --service metastore &

Start HiveServer2

$HIVE_HOME/bin/hiveserver2


9.  Configure and enable Storage Plugin for Hive in Drill

{
  "type": "hive",
  "enabled": true,
  "configProps": {
    "hive.metastore.uris": "thrift://localhost:9083",
    "hive.metastore.sasl.enabled": "false"
  }
}


10. Query Hive from Drill.

SELECT firstname,lastname FROM hive.`customers` 

 SELECT * FROM hive.`patients`
 SELECT * FROM hive.`slices`

 SELECT slices.sliceID, slices.slideBarCode, patients.patientID, patients.gender, patients.laterality
 FROM hive.`patients`, hive.`slices`
 WHERE patients.patientID = slices.patientID






