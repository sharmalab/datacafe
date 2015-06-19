/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe;

import com.mongodb.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatacafeEngine {
    private static Logger logger = LogManager.getLogger(DatacafeEngine.class.getName());

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
        DB db = mongoClient.getDB("pathology");
        DBCollection pathologyDataCollection = db.getCollection("pathologyData");

        DBCursor cursor = pathologyDataCollection.find();

        while(cursor.hasNext()) {
            logger.info(cursor.next());
        }
    }
}
