********************
Query S3 using Drill
********************

Files and folders in Amazon S3 buckets can be queried using Drill.

Follow the below documentation to get the dependencies and configurations to your local Drill installation.

https://drill.apache.org/blog/2014/12/09/running-sql-queries-on-amazon-s3/


Upload the files to the S3 bucket and make sure to give the correct ID and key in the Drill configuration.


# Configure and enable the s3 storage

{
  "type": "file",
  "enabled": true,
  "connection": "s3a://datacafe-data",
  "config": {
    "fs.s3a.access.key": "XXXXX",
    "fs.s3a.secret.key": "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  },
  "workspaces": {
    "root": {
      "location": "/",
      "writable": false,
      "defaultInputFormat": null
    },
    "tmp": {
      "location": "/tmp",
      "writable": true,
      "defaultInputFormat": null
    }
  },
  "formats": {
    "psv": {
      "type": "text",
      "extensions": [
        "tbl"
      ],
      "delimiter": "|"
    },
    "csv": {
      "type": "text",
      "extensions": [
        "csv"
      ],
      "extractHeader": true,
      "delimiter": ","
    },
    "tsv": {
      "type": "text",
      "extensions": [
        "tsv"
      ],
      "delimiter": "\t"
    },
    "parquet": {
      "type": "parquet"
    },
    "json": {
      "type": "json",
      "extensions": [
        "json"
      ]
    },
    "avro": {
      "type": "avro"
    },
    "sequencefile": {
      "type": "sequencefile",
      "extensions": [
        "seq"
      ]
    },
    "csvh": {
      "type": "text",
      "extensions": [
        "csvh"
      ],
      "extractHeader": true,
      "delimiter": ","
    }
  }
}


# Query the CSV files uploaded to S3.


SELECT t1.SUBJECT_ID, t1.DOB, t2.DEATHTIME, t3.CALLOUT_OUTCOME, t4.LOS, t4.FIRST_WARDID
FROM s3.root.`PATIENTS_DATA_TABLE.csv` t1, s3.root.`ADMISSIONS_DATA_TABLE.csv` t2, s3.root.`CALLOUT_DATA_TABLE.csv` t3, s3.root.`ICUSTAYS_DATA_TABLE.csv` t4
WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t1.SUBJECT_ID = t3.SUBJECT_ID AND t1.SUBJECT_ID = t4.SUBJECT_ID




SELECT * FROM s3.root.`patients.csv`


You may alternatively query an S3 bucket from Drill hosted in an EC2 instance following the below blog post

http://technivore.org/posts/2016/04/12/querying-s3-data-with-drill.html