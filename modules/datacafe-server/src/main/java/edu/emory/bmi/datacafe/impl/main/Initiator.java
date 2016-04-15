/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.impl.main;

import edu.emory.bmi.datacafe.core.DataSource;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample main execution.
 */
public class Initiator {
    public static void main(String[] args) {

        List<DataSource> dataSourceList = new ArrayList<>();
        dataSourceList.add(new DataSource("clinical", "clinicalData"));
        dataSourceList.add(new DataSource("pathology", "pathologyData"));

        InitiatorEngine initiatorEngine = new InitiatorEngine(dataSourceList);

        Class<Patient> clazz = Patient.class;
        Class<Slice> clazz1 = Slice.class;
        initiatorEngine.initiate (clazz, clazz1);
    }

}
