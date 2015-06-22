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
import edu.emory.bmi.datacafe.impl.data.Merger;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing the Mongo data integration
 */
public class QueryExecutor {
    private static Logger logger = LogManager.getLogger(QueryExecutor.class.getName());
    private static MongoCursor<Patient> patientCursors;
    private static MongoCursor<Slice> sliceCursors;
    private static List<Merger> mergerList;


    /**
     * Initializes Jongo for patients information
     * @param database database name
     * @param collection collection name
     */
    public static void initializePatient(String database, String collection) {
        MongoCollection patients = JongoConnector.initialize(database, collection);

        patientCursors = patients.find().as(Patient.class);

        for (Patient patient : patientCursors) {
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

        sliceCursors = slices.find().as(Slice.class);

        for (Slice slice : sliceCursors) {
            logger.info(slice.getKey());
        }
    }

    public static void join() {
        Merger merger = new Merger();
        mergerList = new ArrayList<>();
        int jj = 0;

        for (Patient patient: patientCursors) {
            if (patient.getAge_at_Initial_Diagnosis() > 70) {
                merger.setAge(patient.getAge_at_Initial_Diagnosis());
                String currentKey = patient.getKey();
                merger.setPatientID(currentKey);

                for (Slice slice: sliceCursors) {
                    if (slice.getBCR_Patient_UID_From_Pathology().equals(currentKey)) {
                        String slideBarCodeID = slice.getSlide_Barcode();
                        merger.setSlideBarCode(slideBarCodeID);
                        mergerList.add(merger);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        initializePatient("clinical", "clinicalData");
        initializeSlice("pathology", "pathologyData");
        join();
    }

}
