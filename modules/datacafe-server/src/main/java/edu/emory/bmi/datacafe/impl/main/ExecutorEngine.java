/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.impl.main;

import edu.emory.bmi.datacafe.core.DataSource;
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import edu.emory.bmi.datacafe.mongo.MongoEngine;
import edu.emory.bmi.datacafe.hdfs.HdfsConnector;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * An ExecutorEngine implementation for testing the Mongo data integration
 */
public class ExecutorEngine {
    private static Logger logger = LogManager.getLogger(ExecutorEngine.class.getName());
    private static List<DataSource> dataSourceList;

    private static List<Patient> patientList = new ArrayList<>();
    private static List<Slice> sliceList = new ArrayList<>();
    private String[] datasourceNames;

    public ExecutorEngine(List<DataSource> dataSources) {
        dataSourceList = dataSources;
    }

    public void initiate() {
        MongoEngine dataSourceEngine = new MongoEngine();
        int numOfDataSources = dataSourceList.size();
        datasourceNames = new String[numOfDataSources];
        for (int i = 0; i < dataSourceList.size(); i++) {
            dataSourceEngine.addDataSource(dataSourceList.get(i));
            datasourceNames[i] = dataSourceList.get(i).getFullName();
        }
    }

    public void constructWarehouse(Class<Patient> clazz, Class<Slice> clazz1) {
        // Get the IDs
        MongoCursor<Patient> patientCursors = (MongoCursor<Patient>) MongoEngine.initializeEntry(
                dataSourceList.get(0).getDatabase(), dataSourceList.get(0).getCollection(),
                "{Age_at_Initial_Diagnosis: {$gt: 70}}, {_id:1}", clazz);


        MongoCursor<Slice> sliceCursors = (MongoCursor<Slice>) MongoEngine.initializeEntry(
                dataSourceList.get(1).getDatabase(), dataSourceList.get(1).getCollection(),
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
        String[] queries = MongoConnector.constructQueries(params);

        HdfsConnector.writeDataSourcesToWarehouse(datasourceNames, params, queries, new List[]{patientList, sliceList});
    }
}
