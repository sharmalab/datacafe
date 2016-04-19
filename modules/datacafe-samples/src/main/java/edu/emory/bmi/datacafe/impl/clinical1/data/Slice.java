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
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Class to represent a Slice from the pathology data.
 * BCR Slide UID in pathology.csv is the _id.
 * BCR Patient ID is the foreign key.
 *
 * 19:39:25.619 [main] INFO  main.java.edu.emory.bmi.datacafe.impl.clinical1.main.DatacafeEngine - {
 * "BCR_Patient_UID_From_Pathology" : "2272DFF7-A509-496F-B500-467C1BCE2F79" ,
 * "_id" : "17F9BDA2-9B25-45DA-9A55-EDE15C14195F" , "Sample_Barcode" : "TCGA-WY-A85E-01A" ,
 * "Slide_Barcode" : "TCGA-WY-A85E-01A-01-TS1" ,
 * "Image_File_Name" : "TCGA-WY-A85E-01A-01-TS1.17F9BDA2-9B25-45DA-9A55-EDE15C14195F.svs" ,
 * "Section_Location" : "TOP" , "Tumor_Cells_Percentage" : 50 , "Necrosis_Percentage" : 0 ,
 * "Stromal_Cells_Percentage" : 40 , "Normal_Cells_Percentage" : 10 , "Tumor_Nuclei_Percentage" : 85}
 */
public class Slice implements DataSourceBean {
    @MongoId // auto
    @MongoObjectId
    private String _id;

    @MongoObjectId
    private String BCR_Patient_UID_From_Pathology;

    @MongoObjectId
    private String Sample_Barcode;

    @MongoObjectId
    private String Slide_Barcode;

    @MongoObjectId
    private String Image_File_Name;

    @MongoObjectId
    private String Section_Location;

    @MongoObjectId
    private String Tumor_Cells_Percentage;

    @MongoObjectId
    private String Necrosis_Percentage;

    @MongoObjectId
    private String Stromal_Cells_Percentage;

    @MongoObjectId
    private String Normal_Cells_Percentage;

    @MongoObjectId
    private String Tumor_Nuclei_Percentage;

    public String getKey() {
        return _id;
    }

    public String getSliceID() {
        return _id;
    }

    public String getBCRPatientUIDFromPathology() {
        return BCR_Patient_UID_From_Pathology;
    }

    public String getPatientID() {
        return BCR_Patient_UID_From_Pathology;
    }

    public String getSampleBarCode() {
        return Sample_Barcode;
    }

    public String getSlideBarCode() {
        return Slide_Barcode;
    }

    public String getImageFileName() {
        return Image_File_Name;
    }

    public String getSectionLocation() {
        return Section_Location;
    }

    public String getTumorCellsPercentage() {
        return Tumor_Cells_Percentage;
    }

    public String getNecrosisPercentage() {
        return Necrosis_Percentage;
    }

    public String getStromalCellsPercentage() {
        return Stromal_Cells_Percentage;
    }

    public String getNormalCellsPercentage() {
        return Normal_Cells_Percentage;
    }

    public String getTumorNucleiPercentage() {
        return Tumor_Nuclei_Percentage;
    }
}
