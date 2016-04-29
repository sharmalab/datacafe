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
import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * The Thread class that writes to the HDFS.
 */
public class HdfsWriterThread extends Thread {
    private static Logger logger = LogManager.getLogger(HdfsWriterThread.class.getName());

    private String datasourcesName;
    private List<String> text;
    private FileSystem hdfs = HdfsUtil.getFileSystem();


    public HdfsWriterThread(String datasourceName, List<String> text) throws IOException {
        this.datasourcesName = datasourceName;
        this.text = text;
    }

    public void run() {
        String temp = "";

        String outputFile = ConfigReader.getHdfsPath() + datasourcesName +
                ConfigReader.getFileExtension();
        OutputStream os = null;
        try {
            os = hdfs.create(new Path(outputFile));
        } catch (IOException e) {
            logger.error("IOException in creating the file: " + outputFile + " in hdfs.", e);
        }
        assert os != null;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        for (String aText : text) {
            temp += aText + "\n";
        }

        try {
            writer.write(temp);
        } catch (IOException e) {
            logger.error("IOException in writing to hdfs.", e);
        }
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("IOException in closing the writer", e);
            }
        }
        long endTime = System.currentTimeMillis();

        long timeConsumed = endTime - CoreExecutorEngine.getStartTime();
        logger.info("Successfully written to the data lake: " + outputFile + " in " + timeConsumed/1000.0 + " s.");
    }
}
