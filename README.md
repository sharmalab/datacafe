# Data Café — <br/> A Platform For Creating Biomedical Data Lakes

Data Cafe can be used to construct data lakes in Hadoop HDFS from heterogeneous data sources, and query the data lakes
efficiently by leveraging Apache Drill.

The steps are outlined below:

## Configure the Origin Data Sources

* Start MongoDB.

 $ sudo systemctl start mongodb


* You may choose to configure MongoDB to start at the system start-up time.

 $ sudo systemctl enable mongodb


## Configure Hadoop 


## Execute Hadoop

* Start Hadoop NameNode daemon and DataNode daemon 

 $HADOOP_HOME/sbin/start-dfs.sh

* Browse the web interface for the name node - http://localhost:50070/

* Once the execution is complete, you may stop the daemons with

 $HADOOP_HOME/sbin/stop-dfs.sh


## Configure Drill

Make sure Java is installed in order to start Drill.

* Launch Drill in Embedded mode 

 $DRILL_HOME/bin/drill-embedded 

* If Drill is secured, log in with the credentials.

 $DRILL_HOME/bin/drill-embedded -n username -p password


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

Built and tested with Apache Maven 3.1.1 and Oracle Java 1.8.0.


## Executing Data Cafe

You may execute Data Cafe by writing a client to it.

Make sure to include log4j2-test.xml into your class path to be able to configure and view the logs. Default log level is [WARN].

Samples are provided in the datacafe-samples module.

For example, to execute the clinical-1 sample,

 $ java -classpath lib/datacafe-samples-1.0-SNAPSHOT.jar:lib/*:conf/ main.java.edu.emory.bmi.datacafe.impl.clinical1.main.Initiator


PhysioNetIntegratedExecutor offers a server sample with Mongo as the original data source and HDFS as the integrated 
data source. QueryExecutor on the other hand, offers a sample client implementation with queries partially auto-generated.


## Dependencies

This project depends on the below major projects.

* Hazelcast

* MongoDB

* Apache Hadoop

* Apache Hive

* Apache Drill

* Apache Log4j2

* MySQL


## Using Data Cafe in your research

Please cite the below, if you use Data Cafe in your research.

 [1] Kathiravelu, P. & Sharma, A. (2016). A Dynamic Data Warehousing Platform for Creating and Accessing
     Biomedical Data Lakes. In Second International Workshop on Data Management and Analytics for Medicine
     and Healthcare (DMAH'16), co-located with 42 nd International Conference on Very Large Data Bases (VLDB
     2016). Sep. 2016. LNCS. 18 pages. To Appear.

 [2] Kathiravelu, P., Kazerouni, A., & Sharma, A. (2016). Data Café — A Platform For Creating Biomedical Data Lakes.  
     In AMIA 2016 Joint Summits on Translational Science. Mar. 2016.