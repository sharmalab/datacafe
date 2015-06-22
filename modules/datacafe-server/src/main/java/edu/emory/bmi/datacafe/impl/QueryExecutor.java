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
import edu.emory.bmi.datacafe.core.Merger;
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
    }

    /**
     * Initializes Jongo for Slices information
     * @param database database name
     * @param collection collection name
     */
    public static void initializeSlice(String database, String collection) {
        MongoCollection slices = JongoConnector.initialize(database, collection);

        sliceCursors = slices.find().as(Slice.class);
    }

    public static void join() {
        Merger merger;
        mergerList = new ArrayList<>();

        for (Patient patient: patientCursors) {
            if (patient.getAge_at_Initial_Diagnosis() > 70) {
                for (Slice slice: sliceCursors) {
                    merger = new Merger();
                    String currentKey =  patient.getKey();
                    merger.addEntry("PatientID", currentKey);
                    merger.addEntry("Age", String.valueOf(patient.getAge_at_Initial_Diagnosis()));
                    if (slice.getBCR_Patient_UID_From_Pathology().equals(currentKey)) {
                        String slideBarCode = slice.getSlide_Barcode();
                        String sliceID = slice.getKey();
                        merger.addEntry("SlideBarCodeID", sliceID);
                        merger.addEntry("SlideBarCode", slideBarCode);
                        mergerList.add(merger);
                    }
                }
            }
        }
        for (Merger merg : mergerList) {
            merg.print();
        }

    }

    public static void main(String[] args) {
        initializePatient("clinical", "clinicalData");
        initializeSlice("pathology", "pathologyData");
        join();
    }

}
