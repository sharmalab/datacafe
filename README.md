# DataCafe - Data Federation and Integration
A Dynamic Data Warehousing System

Apache Drill can be used to query Hadoop and HDFS in a very efficient way. The steps are as below. Refer to the linked pages for each step.

 Configuring Mongo in EC2.

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


* Configure Hadoop 

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




  $HADOOP_HOME/bin/hdfs namenode -format



* Start Hadoop NameNode daemon and DataNode daemon -

 $HADOOP_HOME/sbin/start-dfs.sh

* Create a directory in hdfs for Data Cafe
 bin/hdfs dfs -mkdir datacafe

Once you are done with the experiments, you may stop the daemons with

 $HADOOP_HOME/sbin/stop-dfs.sh


* Browse the web interface for the name node - http://localhost:50070/


* Configure Drill

* To ensure that the name node configuration persists even after a reboot.
 /apache-drill-1.6.0/conf/drill-override.conf

 drill.exec: {
     cluster-id: "drillbits1",
     zk.connect: "localhost:2181",
     sys.store.provider.local.path="/home/pradeeban/programs/apache-drill-1.6.0/storage"
 }

* Launch Drill in Embedded mode -

 $DRILL_HOME/bin/drill-embedded 


* Launch Drill during EMR instance bootstrap action

 s3://maprtech-emr/scripts/mapr_drill_bootstrap.sh


* Browse the web interface for Drill - http://localhost:8047/

For Data Cafe storage in hdfs without hive.
 $HADOOP_HOME/bin/hadoop fs -mkdir       /user/hdfs

Hive configuration can be found in the relevant documentation.


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






* Executing DataCafe

 $ mvn clean install

 $ java -classpath lib/datacafe-server-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.datacafe.impl.main.ExecutorEngine


* SSH to remote instance

 $ ssh -i "pradeeban.pem" ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/


* Copy All to the EC2 Instance

 $ scp -r -i "pradeeban.pem" . ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/datacafe


* Copy only the datacafe jar

 $ scp -i "pradeeban.pem" lib/datacafe-server-1.0-SNAPSHOT.jar ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/datacafe/lib


Current AWS Deployment:

1 node for Mongo.

1 node for Java - datacafe.

1 node for Drill + EMR Hadoop/Hive master.

2 nodes for EMR Hadoop/Hive core. (Can be more than 2, with no code change. Currently 0 Task instances.

Task instances can be introduced without code changes as well).

Currently, 5 EC2 nodes in total.
