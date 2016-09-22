*************************
Configure Mongo Databases
*************************

Here we will look into creating sample data bases in Mongo for integrating and querying with Data Cafe.

* Import the collections from the csv files

 mongoimport --db pathology --collection pathologyData --type csv --headerline --file pathology.csv

 mongoimport --db clinical --collection clinicalData --type csv --headerline --file clinical.csv


* Execute mongo commands.

 $ mongo

 > use pathology

 > db.pathologyData.find()


 > use clinical

 > db.clinicalData.find()
