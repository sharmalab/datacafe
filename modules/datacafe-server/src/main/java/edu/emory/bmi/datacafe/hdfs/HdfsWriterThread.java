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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * The Thread class that writes to the HDFS.
 */
public class HdfsWriterThread extends Thread {
    private static Logger logger = LogManager.getLogger(HdfsWriterThread.class.getName());

    private String datasourcesName;
    private List<String> chosenAttributes;

    public HdfsWriterThread(String datasourceName, List<String> chosenAttributes) throws IOException {
        this.datasourcesName = datasourceName;
        this.chosenAttributes = chosenAttributes;
    }

    public void run() {
        String outputFile = ConfigReader.getHdfsPath() + datasourcesName +
                ConfigReader.getFileExtension();
        HdfsUtil.write(chosenAttributes, outputFile);
    }
}
