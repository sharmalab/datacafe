*******************************
Mongo to Drill Query Conversion
*******************************

As Drill queries are essentially SQL queries, when a Mongo or any other NoSQL data source is queried through Drill, the
query should be changed to fit the SQL format.

For example consider the below Mongo query.

 use CAMICROSCOPE;

 db.testUAIM2.find({ "provenance.image.case_id": "TCGA-02-0001-01Z-00-DX1", "provenance.analysis.execution_id": "tammy-test:7", footprint: { $gte: 800 }, x: { $gte: 0, $lte: 1 }, y: { $gte: 0, $lte: 1 } });



This can be expressed as the below in Drill:
// Not working
 select camic._id, camic.type, camic.parent_id, camic.randval, camic.creation_date, camic.object_type, camic.x, camic.y, camic.normalized, camic.bbox, camic.geometry, camic.footprint, camic.properties, camic.provenance, camic.submit_date from mongo.CAMICROSCOPE.`testUAIM2` as camic WHERE ((camic.provenance.image.case_id = 'TCGA-02-0001-01Z-00-DX1') AND (camic.provenance.analysis.execution_id  = 'tammy-test:7') AND (camic.footprint >= 800) AND (camic.x >= 0) AND (camic.x <=1) AND (camic.y >= 0) AND (camic.y <= 1));

, camic.geometry (fails with geometry)


// Working
 select camic._id, camic.type, camic.parent_id, camic.randval, camic.creation_date, camic.object_type, camic.x, camic.y, camic.normalized, camic.bbox, camic.footprint, camic.properties, camic.provenance, camic.submit_date
 from mongo.CAMICROSCOPE.`testUAIM2` as camic WHERE ((camic.provenance.image.case_id = 'TCGA-02-0001-01Z-00-DX1') AND (camic.provenance.analysis.execution_id  = 'tammy-test:7') AND (camic.footprint >= 800) AND (camic.x >= 0) AND (camic.x <=1) AND (camic.y >= 0) AND (camic.y <= 1));


// Working
 select camic._id, camic.type, camic.parent_id, camic.randval, camic.creation_date, camic.object_type, camic.x, camic.y, camic.normalized, camic.bbox
 from mongo.CAMICROSCOPE.`testUAIM2` as camic WHERE ((camic.provenance.image.case_id = 'TCGA-02-0001-01Z-00-DX1') AND (camic.provenance.analysis.execution_id  = 'tammy-test:7') AND (camic.footprint >= 800) AND (camic.x >= 0) AND (camic.x <=1) AND (camic.y >= 0) AND (camic.y <= 1));



or just a few attributes:

 select camic._id, camic.type, camic.parent_id,camic.provenance.image.case_id as caseid, camic.provenance.analysis.execution_id as executionid from mongo.CAMICROSCOPE.`testUAIM2` as camic WHERE ((camic.provenance.image.case_id = 'TCGA-02-0001-01Z-00-DX1') AND (camic.provenance.analysis.execution_id  = 'tammy-test:7') AND (camic.footprint >= 800) AND (camic.x >= 0) AND (camic.x <=1) AND (camic.y >= 0) AND (camic.y <= 1));



