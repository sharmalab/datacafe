/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.core;

/**
 * Representation of a generic DataSource class
 */
public class DataSourceWrapper<T extends DataSourceBean> {
    private T dataSource;
    private int autoIncrementDataSourceIndex = 0;


    public Class getType() {
        return dataSource.getClass();
    }

    public T getDataSource() {
        return dataSource;
    }

    public String getKey() {
        return dataSource.getKey();
    }

    public int getAndIncrement() {
        int current = autoIncrementDataSourceIndex;
        autoIncrementDataSourceIndex++;
        return current;
    }
}
