var http = require("http");

var options = {
  "method": "POST",
  "hostname": "170.140.61.191",
  "port": "8047",
  "path": "/query.json",
  "headers": {
    "content-type": "application/json",
    "cache-control": "no-cache",
    "postman-token": "a2024f84-6f4a-23ac-7a86-53644ead3347"
  }
};

var req = http.request(options, function (res) {
  var chunks = [];

  res.on("data", function (chunk) {
    chunks.push(chunk);
  });

  res.on("end", function () {
    var body = Buffer.concat(chunks);
    console.log(body.toString());
  });
});

req.write(JSON.stringify({ queryType: 'SQL',
  query: 'SELECT t1.SUBJECT_ID, t1.DOB, t2.DEATHTIME, t3.CALLOUT_OUTCOME, t4.LOS, t4.FIRST_WARDID FROM hdfs.root.`input/PATIENTS_DATA_TABLE.csv` t1, hdfs.root.`input/ADMISSIONS_DATA_TABLE.csv` t2, hdfs.root.`input/CALLOUT_DATA_TABLE.csv` t3, hdfs.root.`input/ICUSTAYS_DATA_TABLE.csv` t4 WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t1.SUBJECT_ID = t3.SUBJECT_ID AND t1.SUBJECT_ID = t4.SUBJECT_ID' }));
req.end();