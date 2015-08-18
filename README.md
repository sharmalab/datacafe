# DataCafe - Data Federation and Integration
A Dynamic Data Warehousing System

Apache Drill can be used to query Hadoop and HDFS in a very efficient way. The steps are as below. Refer to the linked pages for each step.

1. Configuring Mongo in EC2.

Connecting to the remote EC2/Mongo Instance
$ ssh -i "pradeeban.pem" ubuntu@ec2-54-166-90-207.compute-1.amazonaws.com

Copy files to the remote instance.

$ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/clinical.csv ubuntu@ec2-54-157-62-174.compute-1.amazonaws.com:/home/ubuntu

$ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/pathology.csv ubuntu@ec2-54-157-62-174.compute-1.amazonaws.com:/home/ubuntu


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


* Launch Drill during EMR instance bootstrap action
s3://maprtech-emr/scripts/mapr_drill_bootstrap.sh


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

$ HIVE_HOME/bin/hiveserver2


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



11. Executing DataCafe
$ mvn clean install
$ java -classpath lib/datacafe-server-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.datacafe.impl.main.Initiator


12. SCP to remote instance
$ ssh -i "pradeeban.pem" ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/


13. Copy All to the EC2 Instance
$ scp -r -i "pradeeban.pem" . ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/datacafe


14. Copy only the datacafe jar

$ scp -i "pradeeban.pem" lib/datacafe-server-1.0-SNAPSHOT.jar ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/datacafe/lib


Current AWS Deployment:
1 node for Mongo.
1 node for Java - datacafe.
1 node for Drill + EMR Hadoop/Hive master.
2 nodes for EMR Hadoop/Hive core. (Can be more than 2, with no code change. Currently 0 Task instances.
Task instances can be introduced without code changes as well).

Currently, 5 EC2 nodes in total.












API Guide
=========
API for Datacafe
Public facing interface of DataCafe.
CreateDatacafe/
> Create/Add data source
>> Choose data provider

Project > *(High level folder)

Create
Insert
>> Add the query methods.

Delete
DescribeRelationships

1. Connection between data sources
2. API of each data source <-- Query Parameter and return parameters
2.2 How to limit the query to give IDs / all data.


Query : 
CreateWarehouse (_in_drill)/ 
<User write APIs - store as statements>
>> Names of the data sources, <key, value> pairs.


Warehouse of following DS.



TODO:
2.1 Add/remove data sources from data cafe.
* Connect the ID <-- ID Intersection.

2. API of each data sources. 
<-- REST/Mongo/ODBC ..


Bindaas Model::
Projects > Providers > APIs
Projects: (keys to keep bundles together)
Group of providers together as a project.

Provider/
Providers: 1:1 relationship with dataProvider (providers as plugins)
Plugins for Mongo, Mysql, postgres, http (REST API for another REST API)

MongoPlugin >>> 
user name, password, database, collection


<< Meta-data >> Datacafe.

IQueryHandler Interface
(-) submit or delete

RequestContext - who is making the request -- for secury
1 - 2 3 5 << Query Interface

Bindaas Data Providers

Datacafe (including the individual types of data sources >> adds types of data sources. Connect data sources.) and individual data warehouses

Create a project
> Create a data source.

Create data warehouse
