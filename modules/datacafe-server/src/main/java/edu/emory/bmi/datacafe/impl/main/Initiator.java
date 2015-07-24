/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.impl.main;

import edu.emory.bmi.datacafe.core.CoreDataObject;
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import edu.emory.bmi.datacafe.mongo.MongoEngine;
import edu.emory.bmi.datacafe.hdfs.HiveConnector;
import edu.emory.bmi.datacafe.core.WarehouseConnector;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;
import edu.emory.bmi.datacafe.util.DatacafeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * An Initiator implementation for testing the Mongo data integration
 */
public class Initiator {
    private static Logger logger = LogManager.getLogger(Initiator.class.getName());
    private static MongoCursor<Patient> patientCursors;
    private static MongoCursor<Slice> sliceCursors;

    private static List<Patient> patientList = new ArrayList<>();
    private static List<Slice> sliceList = new ArrayList<>();


    public static void main(String[] args) {
        String[] datasourceNames = {"patients", "slices"};

        Class<Patient> clazz = Patient.class;
        Class<Slice> clazz1 = Slice.class;
        initiate(datasourceNames, clazz, clazz1);


    }

    private static void initiate(String[] datasourceNames, Class<Patient> clazz, Class<Slice> clazz1) {
        MongoEngine dataSourceEngine = new MongoEngine();
        dataSourceEngine.addDataSource("clinical", "clinicalData");
        dataSourceEngine.addDataSource("pathology", "pathologyData");

        // Get the IDs
        patientCursors = (MongoCursor<Patient>) MongoEngine.initializeEntry("clinical", "clinicalData",
                "{Age_at_Initial_Diagnosis: {$gt: 70}}, {_id:1}", clazz);


        sliceCursors = (MongoCursor<Slice>) MongoEngine.initializeEntry("pathology",
                "pathologyData",
                "{Tumor_Nuclei_Percentage: {$gt: 65}}, {_id:1}", clazz1);

        for (Patient patient : patientCursors) {
            Patient tempPatient = (Patient) MongoEngine.findEntry("clinical", "clinicalData",
                    "{_id:'" + patient.getKey() + "'}, {Gender:1}, {Laterality:1}", clazz);
            patientList.add(tempPatient);
        }

        for (Slice slice : sliceCursors) {
            Slice tempSlice = (Slice) MongoEngine.findEntry(
                    "pathology", "pathologyData", "{_id:'" + slice.getKey() + "'}, ", clazz1);
            sliceList.add(tempSlice);
        }

        String[][] params = {{"patientID", "gender", "laterality"}, {"sliceID", "patientID", "slideBarCode"}};

        // patientID, gender, laterality.


        String[] queries = MongoConnector.constructQueries(params);

        List<String> patientsText = CoreDataObject.getWritableString(params[0], patientList);

        List<String> slicesText = CoreDataObject.getWritableString(params[1], sliceList);

        List<String>[] texts = DatacafeUtil.generateArrayOfLists(patientsText, slicesText);

        WarehouseConnector warehouseConnector = new HiveConnector();
        warehouseConnector.writeToWarehouse(datasourceNames, texts, queries);
    }

}
