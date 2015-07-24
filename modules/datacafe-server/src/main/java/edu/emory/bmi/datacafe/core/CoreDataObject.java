/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.core;

import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Core of the data representation
 */
public class CoreDataObject {
    private static Logger logger = LogManager.getLogger(CoreDataObject.class.getName());

    /**
     * Gets a reflection to the method, given the name of the method.
     * @param obj the object under consideration
     * @param methodName name of the method.
     * @return the Method object
     */
    public static Method getMethod(Object obj, String methodName) {
        Method method = null;
        try {
            method = obj.getClass().getMethod(methodName);
        } catch (SecurityException e) {
            logger.error("SecurityException when invoking " + methodName, e);
        } catch (NoSuchMethodException e) {
            logger.error("No such method: " + methodName, e);
        }
        return method;
    }

    /**
     * Creates the getter name considering the camelCaseStandard (camelCaseStandard -> getCamelCaseStandard)
     * @param propertyName the property of the object considered
     * @return the getter name. Camel case standard is assumed.
     */
    public static String getGetterInCamelCase(String propertyName) {
        String camelCaseObjectName = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        return "get" + camelCaseObjectName;
    }

    /**
     * Gets the getter method given a property name and object
     * @param obj the object under consideration
     * @param propertyName the property of the object considered
     * @return the Method object
     */
    public static Method getGetter(Object obj, String propertyName) {
        return getMethod(obj, getGetterInCamelCase(propertyName));
    }

    /**
     * Gets the output string from the relevant getter, given a property name
     * @param obj the object under consideration
     * @param propertyName the property of the object considered
     * @return the value - currently only String considered. TODO - fix for all types.
     */
    public static String getStringFromGetter(Object obj, String propertyName) {
        Method method = getGetter(obj, propertyName);
        String output = "";
        try {
            output = (String) method.invoke(obj);
        } catch (IllegalAccessException e) {
            logger.error("Illegal Access Exception for the Getter of " + propertyName);
        } catch (InvocationTargetException e) {
            logger.error("Invocation Target Exception for the Getter of " + propertyName);
        }
        return output;
    }

    /**
     * Gets a line that can be written to the csv.
     * @param param the parameters to be included in the csv
     * @param object the object under consideration
     * @return String line
     */
    public static String getWritableStringLine(String[] param, Object object) {
        String line = "";
        for (int i = 0; i < param.length; i++) {
            String currentVal = getStringFromGetter(object, param[i]);
            line += currentVal;
            if (i < param.length-1) {
                line += DatacafeConstants.DELIMITER;
            }
        }
        return line;
    }

    /**
     * Gets the final writable string to be written to the csv.
     * @param param the parameters to be included in the csv
     * @param objectList the list of objects to be written
     * @return the list of String
     */
    public static List<String> getWritableString(String[] param, List<?> objectList) {
        List<String> objectsText = new ArrayList<>();
        for (Object object : objectList) {
            String line = getWritableStringLine(param, object);
            objectsText.add(line);
        }
        return objectsText;
    }
}
