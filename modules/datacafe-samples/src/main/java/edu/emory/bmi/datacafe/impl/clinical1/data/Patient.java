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
package edu.emory.bmi.datacafe.impl.clinical1.data;

import edu.emory.bmi.datacafe.core.DataSourceBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Class to represent a Patient from the clinical data.
 * BCR Patient UID (From Clinical) in clinical.csv is the _id.
 * <p/>
 * 19:13:04.177 [main] INFO  main.java.edu.emory.bmi.datacafe.impl.clinical1.main.DatacafeEngine - { "Race" : "WHITE" , "Gender" : "FEMALE" ,
 * "Supratentorial_Localization" : "[Not Available]" , "Tumor_Site" : "Supratentorial, Frontal Lobe" ,
 * "Laterality" : "Left" , "Histologic_Diagnosis" : "Oligoastrocytoma" , "Age_at_Initial_Diagnosis" : 48 ,
 * "Karnofsky_Score" : "[Not Available]" , "Patient_Barcode" : "TCGA-WY-A85E" , "Cancer_Type" : "LGG" ,
 * "_id" : "2272DFF7-A509-496F-B500-467C1BCE2F79"}
 */
public class Patient implements DataSourceBean {
    private static Logger logger = LogManager.getLogger(Patient.class.getName());

    @MongoId // auto
    @MongoObjectId
    private String _id;

    @MongoObjectId
    private String Race;

    @MongoObjectId
    private String Gender;

    @MongoObjectId
    private String Supratentorial_Localization;

    @MongoObjectId
    private String Tumor_Site;

    @MongoObjectId
    private String Laterality;

    @MongoObjectId
    private String Histologic_Diagnosis;

    @MongoObjectId
    private String Age_at_Initial_Diagnosis;

    @MongoObjectId
    private String Karnofsky_Score;

    @MongoObjectId
    private String Patient_Barcode;

    @MongoObjectId
    private String Cancer_Type;

    public String getKey() {
        return _id;
    }

    public String getPatientID() {
        return _id;
    }

    public String getRace() {
        return Race;
    }

    public String getGender() {
        return Gender;
    }

    public String getSupratentorialLocalization() {
        return Supratentorial_Localization;
    }

    public String getTumorSite() {
        return Tumor_Site;
    }

    public String getLaterality() {
        return Laterality;
    }

    public String getHistologicDiagnosis() {
        return Histologic_Diagnosis;
    }

    public int getAgeatInitialDiagnosis() {
        int age = -1;
        if (!Age_at_Initial_Diagnosis.trim().equals("")) {
            try {
                age = Integer.parseInt(Age_at_Initial_Diagnosis);
            } catch (NumberFormatException e) {
                logger.error("Error in parsing the age", e);
            }
        }
        return age;
    }

    public String getKarnofskyScore() {
        return Karnofsky_Score;
    }

    public String getPatientBarcode() {
        return Patient_Barcode;
    }

    public String getCancerType() {
        return Cancer_Type;
    }
}
