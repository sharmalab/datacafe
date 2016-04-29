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
package edu.emory.bmi.datacafe.hdfs;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Cleans up the HDFS folders.
 */
public class HdfsCleaner {

    private static Logger logger = LogManager.getLogger(HdfsCleaner.class.getName());

    /**
     * Just to delete the entire content of an hdfs folder.
     *
     * @param folder the folder to be deleted
     * @throws java.io.IOException if the deletion failed.
     */
    private static void delete(String folder) throws IOException {
        FileSystem fs = HdfsUtil.getFileSystem();
        fs.delete(new Path(ConfigReader.getHdfsPath() + folder), true);
        logger.info("Successfully deleted the contents of the HDFS folder: " + folder);
    }

    public static void main(String[] args) {
        ConfigReader.readConfig();
        try {
            delete(ConfigReader.getInputBulkDir());
        } catch (IOException e) {
            logger.error("Exception in deleting the contents of the directory: " + ConfigReader.getInputBulkDir(), e);
        }
    }
}
