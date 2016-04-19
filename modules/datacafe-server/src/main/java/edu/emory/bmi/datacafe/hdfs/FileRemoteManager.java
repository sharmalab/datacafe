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

import com.jcraft.jsch.*;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Copies the file to the remote server
 */
public class FileRemoteManager {
    private static Logger logger = LogManager.getLogger(FileRemoteManager.class.getName());

    /**
     * Copies the file to the remote location
     * @param fileName the file
     */
    public static void copyFile(String fileName) {
        JSch jSch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            jSch.addIdentity(DatacafeConstants.PRIVATE_KEY);
            session = jSch.getSession(ConfigReader.getSftpUser(), ConfigReader.getHiveServer(),
                    ConfigReader.getSftpPort());
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();

            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(ConfigReader.getRemoteTargetDir());

            File f = new File(ConfigReader.getClientCSVDir() + fileName);
            channelSftp.put(new FileInputStream(f), f.getName());
            logger.info("File, " + fileName + " copied successfully to the remote location..");

        } catch (JSchException e) {
            logger.error("JSchException when attempting to copy the file to remote location", e);
        } catch (SftpException e) {
            logger.error("SftpException when attempting to copy the file to remote location", e);
        } catch (FileNotFoundException e) {
            logger.error("File not found when attempting to copy the file to remote location", e);
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
                channelSftp.exit();
            }
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
    }
}
