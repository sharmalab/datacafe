curl -X POST -H "Content-Type: application/json" -d '{"queryType":"SQL", "query": "SELECT t1.SUBJECT_ID, t1.DOB, t2.DEATHTIME, t3.CALLOUT_OUTCOME, t4.LOS, t4.FIRST_WARDID FROM hdfs.root.`input/PATIENTS_DATA_TABLE.csv` t1, hdfs.root.`input/ADMISSIONS_DATA_TABLE.csv` t2, hdfs.root.`input/CALLOUT_DATA_TABLE.csv` t3, hdfs.root.`input/ICUSTAYS_DATA_TABLE.csv` t4 WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t1.SUBJECT_ID = t3.SUBJECT_ID AND t1.SUBJECT_ID = t4.SUBJECT_ID"}' http://170.140.61.191:8047/query.json

curl -X POST -H "Content-Type: application/json" -d '{"queryType":"SQL", "query": "SELECT * FROM hdfs.root.`patients.csv`"}' http://170.140.61.191:8047/query.json

