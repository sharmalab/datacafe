/*
* Title:        S²DN
* Description:  Orchestration Middleware for Incremental
*               Development of Software-Defined Cloud Networks.
* Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
*
* Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
*/
package edu.emory.bmi.datacafe.impl.main;

import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import edu.emory.bmi.datacafe.constants.HDFSConstants;
import edu.emory.bmi.datacafe.core.DatacafeEngine;
import edu.emory.bmi.datacafe.core.WarehouseComposer;
import edu.emory.bmi.datacafe.hdfs.HiveConnector;
import edu.emory.bmi.datacafe.impl.data.Patient;
import edu.emory.bmi.datacafe.impl.data.Slice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.MongoCursor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An Initiator implementation for testing the Mongo data integration
 */
public class Initiator {
    private static Logger logger = LogManager.getLogger(Initiator.class.getName());
    private static MongoCursor<Patient> patientCursors;

    private static List<Slice> initSliceList = new ArrayList<>();

    private static List<Patient> patientList = new ArrayList<>();
    private static List<Slice> sliceList = new ArrayList<>();


    public static void main(String[] args) {
        Class<Patient> clazz = Patient.class;

        // Get the IDs
        patientCursors = (MongoCursor<Patient>) DatacafeEngine.initializeEntry("clinical", "clinicalData",
                "{Age_at_Initial_Diagnosis: {$gt: 70}}, {_id:1}", clazz);

        for (Patient patient : patientCursors) {
            Patient tempPatient = (Patient) DatacafeEngine.findEntry("clinical", "clinicalData",
                    "{_id:'" + patient.getKey() + "'}, {Gender:1}, {Laterality:1}", clazz);
            patientList.add(tempPatient);

        }

        Class<Slice> clazz1 = Slice.class;

        for (Patient patient : patientCursors) {
            MongoCursor<Slice> tempSliceCursors = (MongoCursor<Slice>) DatacafeEngine.initializeEntry("pathology",
                    "pathologyData",
                    "{BCR_Patient_UID_From_Pathology: '" + patient.getKey() + "'}, {Slide_Barcode:1}, {_id:1}", clazz1);
            while (tempSliceCursors.hasNext()) {
                initSliceList.add(tempSliceCursors.next());
            }
        }

        for (Slice slice : initSliceList) {
            Slice tempSlice = (Slice) DatacafeEngine.findEntry(
                    "pathology", "pathologyData", "{_id:'" + slice.getKey() + "'}, ", clazz1);
            sliceList.add(tempSlice);
        }

        // patientID, gender, laterality.
        List<String> patientsText = new ArrayList<>();
        List<String> slicesText = new ArrayList<>();

        for (Patient patient : patientList) {
            String currentKey = patient.getKey();
            String gender = patient.getGender();
            String laterality = patient.getLaterality();
            String line = currentKey + DatacafeConstants.DELIMITER + gender + DatacafeConstants.DELIMITER + laterality;
            patientsText.add(line);
        }

        WarehouseComposer.createFile("patients", patientsText);

        /*Remove this*/
        String query = " (PatientID string, Gender string, Laterality string) row format delimited fields " +
                "terminated by ',' stored as textfile";

        for (Slice slice : sliceList) {
            String sliceID = slice.getKey();
            String patientID = slice.getBCR_Patient_UID_From_Pathology();
            String slideBarCode = slice.getSlide_Barcode();

            String line = sliceID + DatacafeConstants.DELIMITER + patientID + DatacafeConstants.DELIMITER + slideBarCode;
            slicesText.add(line);
        }

        WarehouseComposer.createFile("slices", slicesText);

        String query1 = " (sliceID string, patientID string, slideBarCode string) row format delimited fields " +
                "terminated by ',' stored as textfile";

        try {
            HiveConnector.writeToHive("patients.csv", HDFSConstants.HIVE_FIRST_TABLE_NAME, query);
            HiveConnector.writeToHive("slices.csv", HDFSConstants.HIVE_SECOND_TABLE_NAME, query1);
        } catch (SQLException e) {
            logger.error("SQL Exception in writing to Hive", e);
        }
    }
}
