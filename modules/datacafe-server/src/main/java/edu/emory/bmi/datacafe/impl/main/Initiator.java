/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.impl.main;

import edu.emory.bmi.datacafe.core.DataSource;
import edu.emory.bmi.datacafe.mongo.ExecutorEngine;
import edu.emory.bmi.datacafe.core.DataSourceBean;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;
import edu.emory.bmi.datacafe.mongo.QueryConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample main execution.
 */
public class Initiator {
    private static Map<Class<? extends DataSourceBean>, DataSource> dataSourceMap;
    private static Map<Class<? extends DataSourceBean>, String> idQueriesMap;

    public static void main(String[] args) {
        init();
        dataSourceMap.put(Patient.class, new DataSource("clinical", "clinicalData"));
        dataSourceMap.put(Slice.class, new DataSource("pathology", "pathologyData"));
        String[][] interestedAttributes = {{"patientID", "gender", "laterality"}, {"sliceID", "patientID", "slideBarCode"}};
        execute(interestedAttributes);
    }

    private static void init() {
        dataSourceMap = new HashMap<>();
        idQueriesMap = new HashMap<>();
    }

    private static void execute(String[][] attributes) {
        ExecutorEngine executorEngine = new ExecutorEngine(dataSourceMap);

        String patientIDConstraintString = "{Age_at_Initial_Diagnosis: {$gt: 70}}, {_id:1}";
        String sliceIDConstraintString = "{Tumor_Nuclei_Percentage: {$gt: 65}}, {_id:1}";

        idQueriesMap.put(Patient.class, patientIDConstraintString);
        idQueriesMap.put(Slice.class, sliceIDConstraintString);

        executorEngine.retrieveDataLakeIDs(idQueriesMap);

        QueryConstructor patientQueryConstructor = new QueryConstructor(dataSourceMap.get(Patient.class).getDatabase(),
                dataSourceMap.get(Patient.class).getCollection(), "{Gender:1}, {Laterality:1}", attributes[0]);

        QueryConstructor sliceQueryConstructor = new QueryConstructor(dataSourceMap.get(Slice.class).getDatabase(),
                dataSourceMap.get(Slice.class).getCollection(), attributes[1]);

        executorEngine.createList(Patient.class, patientQueryConstructor);
        executorEngine.createList(Slice.class, sliceQueryConstructor);

        executorEngine.constructDataLake(attributes);
    }
}
