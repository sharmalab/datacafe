/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.impl;

import edu.emory.bmi.datacafe.core.JongoConnector;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

/**
 * Testing the Mongo data integration
 */
public class QueryExecutor {
    private static Logger logger = LogManager.getLogger(QueryExecutor.class.getName());

    /**
     * Initializes Jongo for patients information
     * @param database database name
     * @param collection collection name
     */
    public static void initializePatient(String database, String collection) {
        MongoCollection patients = JongoConnector.initialize(database, collection);

        MongoCursor<Patient> all = patients.find().as(Patient.class);

        for (Patient patient : all) {
            logger.info(patient.getSupratentorial_Localization());
        }
    }

    /**
     * Initializes Jongo for Slices information
     * @param database database name
     * @param collection collection name
     */
    public static void initializeSlice(String database, String collection) {
        MongoCollection slices = JongoConnector.initialize(database, collection);

        MongoCursor<Slice> all = slices.find().as(Slice.class);

        for (Slice slice : all) {
            logger.info(slice.getKey());
        }
    }

    public static void main(String[] args) {
        initializePatient("clinical", "clinicalData");
        initializeSlice("pathology", "pathologyData");
    }

}
