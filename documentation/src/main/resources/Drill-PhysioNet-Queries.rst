SELECT t1.SUBJECT_ID, t1.DOB
FROM hdfs.root.`physionet_patients.csv` t1

----------------

SELECT t1.SUBJECT_ID, t1.DOB, t2.HADM_ID
FROM hdfs.root.`physionet_patients.csv` t1
JOIN hdfs.root.`physionet_diagnosesicd.csv` t2
ON t1.SUBJECT_ID = t2.SUBJECT_ID;

----------------

SELECT t1.SUBJECT_ID, t1.DOB, t2.HADM_ID, t3.ICD9_CODE, t3.SHORT_TITLE, t3.LONG_TITLE
FROM hdfs.root.`physionet_patients.csv` t1,
hdfs.root.`physionet_diagnosesicd.csv` t2,
hdfs.root.`physionet_dicddiagnosis.csv` t3
WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t2.ICD9_CODE = t3.ICD9_CODE;

----------------

SELECT t1.SUBJECT_ID, t1.DOB, t2.HADM_ID, t3.ICD9_CODE, t3.SHORT_TITLE, t4.`VALUE`
FROM hdfs.root.`physionet_patients.csv` t1,
hdfs.root.`physionet_diagnosesicd.csv` t2,
hdfs.root.`physionet_dicddiagnosis.csv` t3,
hdfs.root.`physionet_datetimeevents.csv` t4
WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t2.ICD9_CODE = t3.ICD9_CODE AND t4.SUBJECT_ID = t1.SUBJECT_ID;

----------------

SELECT t1.SUBJECT_ID, t1.DOB, t2.HADM_ID, t3.ICD9_CODE, t3.SHORT_TITLE, t4.`VALUE`, t5.DESCRIPTION
FROM hdfs.root.`physionet_patients.csv` t1,
hdfs.root.`physionet_diagnosesicd.csv` t2,
hdfs.root.`physionet_dicddiagnosis.csv` t3,
hdfs.root.`physionet_datetimeevents.csv` t4,
hdfs.root.`physionet_caregivers.csv` t5
WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t2.ICD9_CODE = t3.ICD9_CODE AND t4.SUBJECT_ID = t1.SUBJECT_ID AND t4.CGID = t5.CGID;

----------------

SELECT t1.SUBJECT_ID, t1.DOB, t2.HADM_ID, t3.ICD9_CODE, t3.SHORT_TITLE, t5.DESCRIPTION, t6.FLUID
FROM hdfs.root.`physionet_patients.csv` t1,
hdfs.root.`physionet_diagnosesicd.csv` t2,
hdfs.root.`physionet_dicddiagnosis.csv` t3,
hdfs.root.`physionet_datetimeevents.csv` t4,
hdfs.root.`physionet_caregivers.csv` t5,
hdfs.root.`physionet_dlabitems.csv` t6
WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t2.ICD9_CODE = t3.ICD9_CODE AND t4.SUBJECT_ID = t1.SUBJECT_ID AND t4.CGID = t5.CGID AND t4.ITEMID = t6.ITEMID;

-----------------

SELECT t1.SUBJECT_ID, t1.DOB, t2.HADM_ID, t3.ICD9_CODE, t3.SHORT_TITLE, t5.DESCRIPTION
FROM hdfs.root.`physionet_patients.csv` t1,
hdfs.root.`physionet_diagnosesicd.csv` t2,
hdfs.root.`physionet_dicddiagnosis.csv` t3,
hdfs.root.`physionet_datetimeevents.csv` t4,
hdfs.root.`physionet_caregivers.csv` t5
WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t2.ICD9_CODE = t3.ICD9_CODE AND t4.SUBJECT_ID = t1.SUBJECT_ID AND t4.CGID = t5.CGID