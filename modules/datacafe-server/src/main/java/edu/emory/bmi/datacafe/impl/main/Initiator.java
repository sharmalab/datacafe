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
import edu.emory.bmi.datacafe.core.DatacafeEngine;
import edu.emory.bmi.datacafe.hdfs.HiveConnector;
import edu.emory.bmi.datacafe.hdfs.WarehouseConnector;
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

    private static List<Slice> initSliceList = new ArrayList<>();

    private static List<Patient> patientList = new ArrayList<>();
    private static List<Slice> sliceList = new ArrayList<>();


    public static void main(String[] args) {
        String[] datasourceNames = {"patients", "slices"};

        Class<Patient> clazz = Patient.class;
        Class<Slice> clazz1 = Slice.class;
        initiate(datasourceNames, clazz, clazz1);


    }

    private static void initiate(String[] datasourceNames, Class<Patient> clazz, Class<Slice> clazz1) {
        // Get the IDs
        patientCursors = (MongoCursor<Patient>) DatacafeEngine.initializeEntry("clinical", "clinicalData",
                "{Age_at_Initial_Diagnosis: {$gt: 70}}, {_id:1}", clazz);

        for (Patient patient : patientCursors) {
            Patient tempPatient = (Patient) DatacafeEngine.findEntry("clinical", "clinicalData",
                    "{_id:'" + patient.getKey() + "'}, {Gender:1}, {Laterality:1}", clazz);
            patientList.add(tempPatient);

        }


        for (Patient patient : patientCursors) {
            MongoCursor<Slice> tempSliceCursors = (MongoCursor<Slice>) DatacafeEngine.initializeEntry("pathology",
                    "pathologyData",
                    "{BCR_Patient_UID_From_Pathology: '" + patient.getKey() + "'}, {_id:1}", clazz1);
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


        /*Remove this*/
        String[] queries = {" (PatientID string, Gender string, Laterality string) row format delimited fields " +
                "terminated by ',' stored as textfile", " (sliceID string, patientID string, slideBarCode string) " +
                "row format delimited fields terminated by ',' stored as textfile"};

        for (Slice slice : sliceList) {
            String sliceID = slice.getKey();
            String patientID = slice.getBCR_Patient_UID_From_Pathology();
            String slideBarCode = slice.getSlide_Barcode();

            String line = sliceID + DatacafeConstants.DELIMITER + patientID + DatacafeConstants.DELIMITER + slideBarCode;
            slicesText.add(line);
        }

        List<String>[] texts = DatacafeUtil.generateArrayOfLists(patientsText, slicesText);

        WarehouseConnector warehouseConnector = new HiveConnector();
        warehouseConnector.writeToWarehouse(datasourceNames, texts, queries);
    }
}
