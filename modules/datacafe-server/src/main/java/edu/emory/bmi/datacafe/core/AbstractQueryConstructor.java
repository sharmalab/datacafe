/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.core;

/**
 * Abstract class representing the query constructor. QueryConstructor should be implemented for each of the dataserver.
 */
public abstract class AbstractQueryConstructor {
    protected String collection;

    protected String[] interestedAttributes;

    public AbstractQueryConstructor(String collection, String... interestedAttributes) {
        this.collection = collection;
        this.interestedAttributes = interestedAttributes;
    }

    public String getCollection() {
        return collection;
    }

    public String[] getInterestedAttributes() {
        return interestedAttributes;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public void setInterestedAttributes(String[] interestedAttributes) {
        this.interestedAttributes = interestedAttributes;
    }
}
