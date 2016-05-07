# Data Café — <br/> A Platform For Creating Biomedical Data Lakes

A Dynamic Data Warehousing System

Apache Drill can be used to query Hadoop and HDFS in a very efficient way. The steps are outlined below. 
Refer to the relevant files in documentation for detailed instructions on each of these steps.


## Configure the original data sources.


## Configure Hadoop 


## Execute Hadoop

* Start Hadoop NameNode daemon and DataNode daemon 

 $HADOOP_HOME/sbin/start-dfs.sh

* Browse the web interface for the name node - http://localhost:50070/

* Once the execution is complete, you may stop the daemons with

 $HADOOP_HOME/sbin/stop-dfs.sh


## Configure Drill

* Launch Drill in Embedded mode 

 $DRILL_HOME/bin/drill-embedded 


* Browse the web interface of Drill - http://localhost:8047/


Make sure to set extractHeader element appropriately in the Drill storage according to your data sources.

       "extractHeader": true,


## Configure Hive

Optional.


## Execute Hive

Optional.

* Run Hive Metastore and Hive

 $HIVE_HOME/bin/hive --service metastore &

* Start HiveServer2

 $HIVE_HOME/bin/hiveserver2


## Building Data Cafe

Data Cafe can be built using Apache Maven 3.x and Java 1.8.x or higher.

 $ mvn clean install

Built and tested with Apahe Maven 3.1.1 and Oracle Java 1.8.0.


## Executing Data Cafe

You may execute Data Cafe by writing a client to it.

Make sure to include log4j2-test.xml into your class path to be able to configure and view the logs. Default log level is [WARN].

Samples are provided in the datacafe-samples module.

For example, to execute the clinical-1 sample,

 $ java -classpath lib/datacafe-samples-1.0-SNAPSHOT.jar:lib/*:conf/ main.java.edu.emory.bmi.datacafe.impl.clinical1.main.Initiator


## Using Data Cafe in your research

Please cite the below, if you use Data Cafe in your research.

"Kathiravelu, P., Kazerouni, A., & Sharma, A. (2016). Data Café — A Platform For Creating Biomedical Data
 Lakes. In AMIA 2016 Joint Summits on Translational Science. Mar. 2016."