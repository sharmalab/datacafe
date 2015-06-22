/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.impl.main;

import com.mongodb.*;
import edu.emory.bmi.datacafe.core.MongoConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Core class of Datacafe
 */
public class DatacafeEngine {
    private static Logger logger = LogManager.getLogger(DatacafeEngine.class.getName());
    private static MongoConnector mongoConnector = new MongoConnector();

    public static void main(String[] args) {

        DBCursor pathologyCursor = mongoConnector.getCursor("pathology", "pathologyData");

        while (pathologyCursor.hasNext()) {
            logger.info(pathologyCursor.next());
        }

        DBCursor clinicalCursor = mongoConnector.getCursor("clinical", "clinicalData");

        while (clinicalCursor.hasNext()) {
            logger.info(clinicalCursor.next());
        }

    }
}
