*********************************
Configure MySQL Data Data Sources
*********************************

Data Cafe can be used to leverage MySQL data sources. Here we will look into a sample MySQL data base configuration for
integration and querying with Data Cafe.


* Make sure to include the driver to the modules' classpath.

 Install csvkit for easy conversion and creation of SQL databases from CSV files.

 https://csvkit.readthedocs.org/en/540/


* Create the data bases.

 mysql> create database pathology;

 csvsql --dialect mysql clinical.csv > clinical.sql

 csvsql --dialect mysql pathology.csv > pathology.sql


* Start MySQL server console with load from local file flag.

 mysql --local-infile -u root -p


* Create the Tables

 * Table 1

 use clinical

 source clinical.sql

 LOAD DATA LOCAL INFILE 'clinical.csv'
 INTO TABLE clinical
 FIELDS TERMINATED BY ','
     ENCLOSED BY '"'
 LINES TERMINATED BY '\n'
 IGNORE 1 LINES
 (Race, Gender, Supratentorial_Localization, Tumor_Site, Laterality, Histologic_Diagnosis, Age_at_Initial_Diagnosis, Karnofsky_Score, Patient_Barcode, Cancer_Type, _id);

 * Table 2

 use pathology

 source pathology.sql

  LOAD DATA LOCAL INFILE 'pathology.csv'
  INTO TABLE pathology
  FIELDS TERMINATED BY ','
      ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (BCR_Patient_UID_From_Pathology, _id, Sample_Barcode, Slide_Barcode, Image_File_Name, Section_Location, Tumor_Cells_Percentage, Necrosis_Percentage, Stromal_Cells_Percentage, Normal_Cells_Percentage, Tumor_Nuclei_Percentage);


