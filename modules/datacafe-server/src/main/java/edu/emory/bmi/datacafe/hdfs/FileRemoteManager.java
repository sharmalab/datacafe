/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.hdfs;

import com.jcraft.jsch.*;

import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import edu.emory.bmi.datacafe.constants.HDFSConstants;
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
//
//
//    public static void main(String[] args) {
//        copyFile("clinical.csv");
//    }

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
            session = jSch.getSession(HDFSConstants.SFTP_USER, HDFSConstants.HIVE_SERVER, HDFSConstants.HIVE_SFTP_PORT);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();

            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(HDFSConstants.HIVE_CSV_DIR);
            if (logger.isDebugEnabled()) {
                logger.debug("********************** Changed the directory...");
            }

            File f = new File(HDFSConstants.CLIENT_CSV_DIR + fileName);
            channelSftp.put(new FileInputStream(f), f.getName());

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
