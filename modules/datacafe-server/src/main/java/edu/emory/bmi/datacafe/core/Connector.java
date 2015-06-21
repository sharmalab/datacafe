/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.core;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * The connector interface for each of the data sources
 */
public interface Connector {
    /**
     * Execute a query on db1.dbCollection1 and db2.dbCollection2 and returns the final result as a string
     * @param db1 database1
     * @param dbCollection1 collection1 in database1
     * @param db2 database2
     * @param dbCollection2 collection2 in database2
     * @param joinQuery the query to be executed
     * @return the results as a String
     */
    public String getJoinedResult(DB db1, DBCollection dbCollection1, DB db2, DBCollection dbCollection2, String
            joinQuery);
}
