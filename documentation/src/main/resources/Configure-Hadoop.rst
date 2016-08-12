*****************************
Hadoop Initial Configurations
*****************************

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
 <property>
     <name>dfs.permissions</name>
     <value>false</value>
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



Format the name node before the initial execution
#################################################

  $HADOOP_HOME/bin/hdfs namenode -format


Create a directory in hdfs for Data Cafe
########################################

 $HADOOP_HOME/bin/hadoop fs -mkdir       /user/hdfs