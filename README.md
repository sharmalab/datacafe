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


## Configure Hive

Optional.


## Executing DataCafe

 $ mvn clean install

 $ java -classpath lib/datacafe-server-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.datacafe.impl.main.ExecutorEngine
