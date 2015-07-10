/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.impl.main;

import edu.emory.bmi.datacafe.core.Composer;
import edu.emory.bmi.datacafe.core.Merger;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;
import edu.emory.bmi.datacafe.mongo.JongoConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

/**
 * An Initiator implementation for testing the Mongo data integration
 */
public class Initiator3 {
    private static Logger logger = LogManager.getLogger(Initiator3.class.getName());
    private static MongoCursor<Patient> patientCursors;


    /**
     * Initializes the patient cursors
     * @param database database name
     * @param collection collection name
     * @param constraint constraint to be satisfied
     */
    public static void initializePatient(String database, String collection, String constraint) {
        MongoCollection patients = JongoConnector.initCollection(database, collection);

        patientCursors = patients.find(constraint).as(Patient.class);
    }

    /**
     * Finds the patient with the given id with more details.
     * @param database database name
     * @param collection collection name
     * @param constraint constraint to be satisfied
     * @return the Patient details
     */
    public static Patient findPatient(String database, String collection, String constraint) {
        MongoCollection patients = JongoConnector.initCollection(database, collection);

        return patients.findOne(constraint).as(Patient.class);
    }


    /**
     * Initializes Jongo for Slices information
     *
     * @param database   database name
     * @param collection collection name
     */
    public static MongoCursor<Slice> initializeSlice(String database, String collection, String constraint) {
        MongoCollection slices = JongoConnector.initCollection(database, collection);

        return slices.find(constraint).as(Slice.class);
    }

    public static void main(String[] args) {
        Composer composer = Composer.getComposer();

        Merger merger;

        initializePatient("clinical", "clinicalData", "{Age_at_Initial_Diagnosis: {$gt: 70}}, {_id:1}");

        for (Patient patient : patientCursors) {
            MongoCursor<Slice> sliceCursors = initializeSlice("pathology", "pathologyData",
                    "{BCR_Patient_UID_From_Pathology: '" + patient.getKey() + "'}, {Slide_Barcode:1}, {_id:1}");
            for (Slice slice : sliceCursors) {
                Patient tempPatient = findPatient("clinical", "clinicalData", "{_id:'" + patient.getKey() + "'}, " +
                        "{Gender:1}, {Laterality:1}");
                merger = new Merger();

                // separate into two csv.
                String currentKey = tempPatient.getKey();
                String gender = tempPatient.getGender();
                String laterality = tempPatient.getLaterality();
                merger.addEntry("PatientID", currentKey);
                merger.addEntry("Gender", gender);
                merger.addEntry("Laterality", laterality);
                String slideBarCode = slice.getSlide_Barcode();
                String sliceID = slice.getKey();
                merger.addEntry("SlideBarCodeID", sliceID);
                merger.addEntry("SlideBarCode", slideBarCode);
                composer.addEntry(merger);
            }
        }
        Merger.join();
    }
}
