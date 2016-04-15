# DataCafe - Data Federation and Integration
A Dynamic Data Warehousing System

Apache Drill can be used to query Hadoop and HDFS in a very efficient way. The steps are as below. Refer to the linked pages for each step.

1. Configuring Mongo in EC2.

Connecting to the remote EC2/Mongo Instance

$ ssh -i "pradeeban.pem" ubuntu@ec2-54-166-90-207.compute-1.amazonaws.com

Copy files to the remote instance.

$ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/clinical.csv ubuntu@ec2-54-157-62-174.compute-1.amazonaws.com:/home/ubuntu

$ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/pathology.csv ubuntu@ec2-54-157-62-174.compute-1.amazonaws.com:/home/ubuntu


* Importing the collections from the csv files

mongoimport --db pathology --collection pathologyData --type csv --headerline --file pathology.csv

mongoimport --db clinical --collection clinicalData --type csv --headerline --file clinical.csv


* Executing mongo commands.

$ mongo

> use pathology

> db.pathologyData.find()


> use clinical

> db.clinicalData.find()


2. Configure Hadoop 

/hadoop-2.7.2/etc/hadoop/hdfs-site.xml
* To ensure that the name node configuration persists even after a reboot.

<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>

<!-- Put site-specific property overrides in this file. -->
<configuration>
<property>
  <name>dfs.name.dir</name>
  <value>file:///home/pradeeban/programs/hadoop-2.7.2/dfs/name</value>
</property>
<property>
  <name>dfs.data.dir</name>
  <value>file:///home/pradeeban/programs/hadoop-2.7.2/dfs/data</value>
</property>
<property>
    <name>dfs.replication</name>
    <value>1</value>
</property>

</configuration>



/hadoop-2.7.2/etc/hadoop/core-site.xml

<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>


<!-- Put site-specific property overrides in this file. -->
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>




 $ $HADOOP_HOME/bin/hdfs namenode -format



3. Start Hadoop NameNode daemon and DataNode daemon -

$ $HADOOP_HOME/sbin/start-dfs.sh

* Create a directory in hdfs for Data Cafe
bin/hdfs dfs -mkdir datacafe

Once you are done with the experiments, you may stop the daemons with

$ $HADOOP_HOME/sbin/stop-dfs.sh


4. Browse the web interface for the name node - http://localhost:50070/


5. Configure Drill

* To ensure that the name node configuration persists even after a reboot.
/apache-drill-1.6.0/conf/drill-override.conf

drill.exec: {
    cluster-id: "drillbits1",
    zk.connect: "localhost:2181",
    sys.store.provider.local.path="/home/pradeeban/programs/apache-drill-1.6.0/storage"
}

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


For Data Cafe storage in hdfs without hive.
$ $HADOOP_HOME/bin/hadoop fs -mkdir       /user/hdfs



* Configure Hive Metastore with MySQL.

8. Run Hive Metastore and Hive

$ $HIVE_HOME/bin/hive --service metastore &

Start HiveServer2

$ HIVE_HOME/bin/hiveserver2


9.  Configure and enable storage plugin for HDFS in Drill
{
  "type": "file",
  "enabled": true,
  "connection": "hdfs://localhost:9000",
  "config": null,
  "workspaces": {
    "root": {
      "location": "/user/hdfs",
      "writable": true,
      "defaultInputFormat": null
    }
  },
  "formats": {
    "csv": {
      "type": "text",
      "extensions": [
        "csv"
      ],
      "extractHeader": true,
      "delimiter": ","
    }
  }
}


** or **
Configure and enable Storage Plugin for Hive in Drill

{

  "type": "hive",

  "enabled": true,

  "configProps": {

    "hive.metastore.uris": "thrift://localhost:9083",

    "hive.metastore.sasl.enabled": "false"

  }

}



10. 

SELECT * FROM hdfs.root.`patients.csv` WHERE Gender='MALE'
** or **
SELECT * FROM hdfs.`/user/pradeeban/patients.csv` WHERE Gender='MALE'
** or **
SELECT * FROM hdfs.root.`patients.csv` WHERE columns[1]='MALE'

** or **
SELECT `slices.csv`.sliceID, `slices.csv`.slideBarCode, `patients.csv`.patientID, `patients.csv`.gender, `patients.csv`.laterality 
FROM hdfs.root.`slices.csv`, hdfs.root.`patients.csv`
WHERE CAST(`patients.csv`.patientID AS VARCHAR) = CAST(`slices.csv`.patientID AS VARCHAR)

** or **
SELECT `pathology_pathologyData.csv`.columns[0], `pathology_pathologyData.csv`.columns[2], `clinical_clinicalData.csv`.columns[0], `clinical_clinicalData.csv`.columns[1], `clinical_clinicalData.csv`.columns[2] 
FROM hdfs.root.`pathology_pathologyData.csv`, hdfs.root.`clinical_clinicalData.csv`
WHERE CAST(`clinical_clinicalData.csv`.columns[0] AS VARCHAR) = CAST(`pathology_pathologyData.csv`.columns[1] AS VARCHAR) AND `clinical_clinicalData.csv`.columns[1]='MALE'



** or **
Query Hive from Drill.

SELECT firstname,lastname FROM hive.`customers` 

 SELECT * FROM hive.`patients`

 SELECT * FROM hive.`slices`

 SELECT slices.sliceID, slices.slideBarCode, patients.patientID, patients.gender, patients.laterality

 FROM hive.`patients`, hive.`slices`

 WHERE patients.patientID = slices.patientID



11. Executing DataCafe

$ mvn clean install

$ java -classpath lib/datacafe-server-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.datacafe.impl.main.ExecutorEngine


12. SSH to remote instance

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





Further Extension Points and Future Work
========================================

Evaluation of

>> Apache Accumulo https://accumulo.apache.org/

>> Tyk https://tyk.io/





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
