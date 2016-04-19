/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.datacafe.impl.clinical1.main;

import edu.emory.bmi.datacafe.core.DataSource;
import edu.emory.bmi.datacafe.mongo.ExecutorEngine;
import edu.emory.bmi.datacafe.core.DataSourceBean;
import edu.emory.bmi.datacafe.impl.clinical1.data.Patient;
import edu.emory.bmi.datacafe.impl.clinical1.data.Slice;
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
