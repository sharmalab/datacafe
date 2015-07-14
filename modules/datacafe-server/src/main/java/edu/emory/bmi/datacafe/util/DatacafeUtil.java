/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods of Datacafe
 */
public class DatacafeUtil {
    /**
     * Generates an array of lists from a variable number of lists
     * @param text text in the lists
     * @return the array of lists.
     */
    public static List<String>[] generateArrayOfLists(List<String>... text) {
        List<String> arrayLists[] = new ArrayList[text.length];
        System.arraycopy(text, 0, arrayLists, 0, text.length);
        return arrayLists;
    }
}
