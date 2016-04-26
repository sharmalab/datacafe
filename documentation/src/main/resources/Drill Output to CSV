use hdfs.root;

alter session set `store.format`='json';

create table dfs.tmp.drill_union_output as SELECT columns[0] as SUBJECT_ID, columns[1] as HADM_ID, columns[2] as OUTCOME, 'N/A' as STATUS FROM hdfs.root.`admissions_admissionsData.csv` WHERE columns[3]=''
union SELECT columns[0] as SUBJECT_ID, columns[1] as HADM_ID, columns[2] as OUTCOME, columns[3] as STATUS FROM hdfs.root.`admissions_admissionsData.csv` WHERE columns[3]<>'';

Output files in /tmp/drill_output folder.


https://issues.apache.org/jira/browse/DRILL-2727 prevents us from writing to CSV files from Drill.

Hence the decision to write to a json file.