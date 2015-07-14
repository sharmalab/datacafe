# datacafe
A Dynamic Data Warehousing System

Apache Drill can be used to query Hadoop and HDFS in a very efficient way. The steps are as below. Refer to the linked pages for each step.

1. Configure Hadoop 

There may be some errors encountered when executing Hadoop with Java 8 with the default configurations. Further, formatting the file system will fix any error from Hadoop.

  $ $HADOOP_HOME/bin/hdfs namenode -format



2. Start Hadoop NameNode daemon and DataNode daemon -

$ $HADOOP_HOME/sbin/start-dfs.sh


Once you are done with the experiments, you may stop the daemons with

$ $HADOOP_HOME/sbin/stop-dfs.sh


3. Browse the web interface for the name node - http://localhost:50070/


4. Configure Drill

5. Launch Drill in Embedded mode -

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

