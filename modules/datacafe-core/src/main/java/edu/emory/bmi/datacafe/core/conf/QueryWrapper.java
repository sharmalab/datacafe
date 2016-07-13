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
package edu.emory.bmi.datacafe.core.conf;

import edu.emory.bmi.datacafe.core.hazelcast.HzConfigReader;
import edu.emory.bmi.datacafe.core.kernel.DataSourcesRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility methods to wrap the query attributes
 */
public final class QueryWrapper {
    private static Logger logger = LogManager.getLogger(QueryWrapper.class.getName());


    /**
     * Get the destination from drill, if the original data sources are known.
     *
     * @param database   the database
     * @param collection the collection in the database
     * @return the destination, complete reference.
     */
    public static String getDestinationInDataLakeFromDrill(String database, String collection) {
        return HzConfigReader.getDrillHdfsNameSpace() + DatacafeConstants.PERIOD +
                DataSourcesRegistry.sqlWrapTheDataSource(
                        DataSourcesRegistry.getFullDSNameWithExtension(database, collection));
    }
}
