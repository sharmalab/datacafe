# Download MySQL JDBC connector

https://dev.mysql.com/downloads/connector/j/

# Extract and copy mysql-connector-java-<version>-bin.jar to apache-drill/jars/3rdparty folder.

# Configure Storage Plugin for mysql

 {
  "type": "jdbc",
  "driver": "com.mysql.jdbc.Driver",
  "url": "jdbc:mysql://localhost:3306",
  "username": "root",
  "password": "root",
  "enabled": true
 }

 # Sample queries

  SELECT * FROM mysql.`CDS`;
