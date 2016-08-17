***************
Configure Drill
***************


*To ensure that the name node configuration persists even after a reboot.*

 /apache-drill-1.6.0/conf/drill-override.conf

 drill.exec: {
     cluster-id: "drillbits1",
     zk.connect: "localhost:2181",
     sys.store.provider.local.path="/home/pradeeban/programs/apache-drill-1.6.0/storage"
 }



**Configure and enable storage plugin for HDFS in Drill through its web user interface.**

 {
   "type": "file",
   "enabled": true,
   "connection": "hdfs://localhost:9000",
   "config": null,
   "workspaces": {
     "root": {
       "location": "/user/hdfs/",
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



.. toctree::
   :maxdepth: 1

   src/main/resources/Drill-REST.rst
   src/main/resources/Drill-with-MySQL-JDBC.rst
   src/main/resources/Drill-Output-to-CSV.rst
   src/main/resources/Drill-Node.rst
