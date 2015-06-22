/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.impl.data;

/**
 * Merged Slice and Patient
 */
public class Merger {
    private int age;
    private String patientID;
    private String slideBarCode;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getSlideBarCode() {
        return slideBarCode;
    }

    public void setSlideBarCode(String slideBarCode) {
        this.slideBarCode = slideBarCode;
    }
}
