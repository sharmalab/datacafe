*********************
Enable Drill Security
*********************

You may alternatively enable security in drill such that only the authenticated users will have access to the fast
querying capabilities of Data Cafe.


Download and extract JPam as explained in https://drill.apache.org/docs/configuring-user-authentication/



Include the auth details in the ${DRILL-HOME}/conf/drill-override.conf
######################################################################

 drill.exec: {
   security.user.auth {
         enabled: true,
         packages += "org.apache.drill.exec.rpc.user.security",
         impl: "pam",
         pam_profiles: [ "pradeeban", "login" ]
   }
    cluster-id: "drillbits1",
    zk.connect: "localhost:2181",
    sys.store.provider.local.path="/home/pradeeban/programs/apache-drill-1.6.0/storage"
 }



Configure ${DRILL-HOME}/conf/drill-env.sh
#########################################


 DRILL_MAX_DIRECT_MEMORY="8G"

 DRILL_HEAP="4G"


 export DRILL_JAVA_OPTS="-Xms$DRILL_HEAP -Xmx$DRILL_HEAP -XX:MaxDirectMemorySize=$DRILL_MAX_DIRECT_MEMORY -XX:MaxPermSize=512M -XX:ReservedCodeCacheSize=1G -Ddrill.exec.enable-epoll=true -Djava.library.path=/home/pradeeban/programs/JPam-1.1/drill-pam"


 # Class unloading is disabled by default in Java 7

 # http://hg.openjdk.java.net/jdk7u/jdk7u60/hotspot/file/tip/src/share/vm/runtime/globals.hpp#l1622

 export SERVER_GC_OPTS="-XX:+CMSClassUnloadingEnabled -XX:+UseG1GC "