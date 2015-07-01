/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.core;

import edu.emory.bmi.datacafe.hdfs.HadoopConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Merging properties from two data sources into a single object
 */
public class Merger {
    private Map<String, String> joinedMap;
    private static Logger logger = LogManager.getLogger(Merger.class.getName());

    public Merger() {
        if (joinedMap == null) {
            joinedMap = new HashMap<>();
        }
    }

    public static void join() {
        WarehouseComposer.writeToFile();
        HadoopConnector.writeToHDFS();
    }

    public Map<String, String> getJoinedMap() {
        return joinedMap;
    }

    public void setJoinedMap(Map<String, String> joinedMap) {
        this.joinedMap = joinedMap;
    }

    public void addEntry(String key, String value) {
        joinedMap.put(key, value);
    }

    public void print() {
        for (String key : joinedMap.keySet()) {
            logger.info(key + ": " + joinedMap.get(key) + "\t");
        }
    }
}
