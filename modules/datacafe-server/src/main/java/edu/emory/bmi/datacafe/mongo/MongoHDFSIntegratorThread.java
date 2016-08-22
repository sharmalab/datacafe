/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.datacafe.mongo;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.hdfs.HdfsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * The thread that reads from a Mongo Data store.
 */
public class MongoHDFSIntegratorThread extends Thread {
    private static Logger logger = LogManager.getLogger(MongoHDFSIntegratorThread.class.getName());
    private static MongoConnector mongoConnector;

    private String database;
    private String collection;
    private Document document;
    private String dataSourcesName;
    private String[] attributes;
    private String datalakeID;

    public MongoHDFSIntegratorThread(String database, String collection, Document document,
                                     String dataSourcesName, String[] attributes) {
        this.database = database;
        this.collection = collection;
        this.document = document;
        this.dataSourcesName = dataSourcesName;
        this.attributes = attributes;
        mongoConnector = new MongoConnector();
    }

    public MongoHDFSIntegratorThread(String database, String collection, Document document,
                                     String dataSourcesName, String[] attributes, String datalakeID) {
        this(database, collection, document, dataSourcesName, attributes);
        mongoConnector = new MongoConnector(datalakeID);
        this.datalakeID = datalakeID;
    }

    public void run() {
        List ids = mongoConnector.getIDs(database, collection, document);
        List<String> chosenAttributes = new ArrayList<>();
        if (attributes == null || attributes.length == 0) {
            chosenAttributes =
                    mongoConnector.getAllAttributeValuesExceptAutoGenMongoId(database, collection, ids);
        } else if (attributes.length > 0) {
            chosenAttributes = mongoConnector.getAttributeValuesExceptAutoGenMongoId(database, collection, ids,
                    attributes);
        }
        ids.clear();

        String outputFile = ConfigReader.getHdfsPath() + dataSourcesName +
                ConfigReader.getFileExtension();
        HdfsUtil.write(chosenAttributes, outputFile);
        chosenAttributes.clear();
    }
}