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

/**
 * Core of the data representation
 */
public class CoreDataObject {
    private static Logger logger = LogManager.getLogger(CoreDataObject.class.getName());

    public static Method invoke(Object obj, String methodName) {
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

    public static String getGetterInCamelCase(String propertyName) {
        String camelCaseObjectName = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        return "get" + camelCaseObjectName;
    }

    public static Method getGetter(Object obj, String propertyName) {
        return invoke(obj, getGetterInCamelCase(propertyName));
    }

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

    public static String getWritableString(String[] param, Object object) {
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
}
