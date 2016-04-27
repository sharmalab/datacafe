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
package edu.emory.bmi.datacafe.core;

import java.util.List;

/**
 * Interface of the source data servers.
 */
public interface SourceConnector {

    /**
     * Gets the list of IDs
     *
     * @param database    the data base
     * @param collection  the collection in the data base
     * @param idAttribute the id Attribute
     */
    public List getAllIDs(String database, String collection, String idAttribute);

    /**
     * Get only the values for a chosen sub set of attributes
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param idAttribute         The attribute key that is used as the ID.
     * @param preferredAttributes the attributes to be added.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids, String idAttribute,
                                           String[] preferredAttributes);

    /**
     * Closes all the data server connections.
     */
    public void closeConnections();

}
