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
package edu.emory.bmi.datacafe.impl.clinical1.main;

import com.mongodb.client.FindIterable;
import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import org.bson.Document;

public class Tester {
    public static void main(String[] args) {
        CoreExecutorEngine.init();
        String database = "clinical";
        String collection = "clinicalData";

//        String[][] interestedAttributes = {{"Race", "Gender", "Laterality"};, {"sliceID", "patientID", "slideBarCode"}};

        String[] interestedAttributes = {"Race", "Gender", "Laterality"};


        FindIterable<Document> iterable = MongoConnector.getCollection(database, collection, interestedAttributes);
        MongoConnector.printMongoCollection(iterable);
    }
}

