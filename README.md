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


## Building Data Cafe

 $ mvn clean install


## Executing Data Cafe

You may execute Data Cafe by writing a client to it.

Samples are provided in the datacafe-samples module.

For example, to execute the clinical-1 sample,

 $ java -classpath lib/datacafe-server-1.0-SNAPSHOT.jar:lib/*:conf/ main.java.edu.emory.bmi.datacafe.impl.clinical1.main.Initiator


## Using Data Cafe in your research

Please cite the below, if you use Data Cafe in your research.

"Kathiravelu, P., Kazerouni, A., & Sharma, A. (2016). Data Café — A Platform For Creating Biomedical Data
 Lakes. In AMIA 2016 Joint Summits on Translational Science. Mar. 2016."