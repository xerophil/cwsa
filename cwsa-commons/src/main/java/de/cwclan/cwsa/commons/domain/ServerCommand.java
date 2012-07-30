/*
 * Copyright 2012 CWCLAN.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cwclan.cwsa.commons.domain;

import de.cwclan.cwsa.commons.exceptions.ServerException;
import java.io.File;
import java.util.Properties;

/**
 * 
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public class ServerCommand {

    private File shellScriptPath;
    private String startCmd;
    private String stopCmd;
    private String statusCmd;
    public static final String DEFAULT_START_CMD = "start";
    public static final String DEFAULT_STOP_CMD = "stop";
    public static final String DEFAULT_STATUS_CMD = "status";

    public ServerCommand(String shellScriptPath) throws ServerException {
	this(shellScriptPath, DEFAULT_START_CMD, DEFAULT_STOP_CMD, DEFAULT_STATUS_CMD);
    }

    public ServerCommand(String shellScriptPath, String startCmd, String stopCmd, String statusCmd) throws ServerException {
	setShellScriptPath(new File(shellScriptPath));
	setStartCmd(startCmd);
	setStopCmd(stopCmd);
	setStatusCmd(statusCmd);
    }

    public ServerCommand(Properties serviceSettings) throws ServerException {
	this(serviceSettings.getProperty("service.scriptPath"),
		serviceSettings.getProperty("service.startCmd", DEFAULT_START_CMD),
		serviceSettings.getProperty("service.stopCmd", DEFAULT_STOP_CMD),
		serviceSettings.getProperty("service.statusCmd", DEFAULT_STATUS_CMD));
    }

    private void setShellScriptPath(File shellScriptPath) throws ServerException {
	if (shellScriptPath == null) {
	    throw new ServerException("path to service script not set");
	}
	if (!(shellScriptPath.isFile() && shellScriptPath.canExecute())) {
	    throw new ServerException("service script cannot be executed or is not a file");
	}
	this.shellScriptPath = shellScriptPath;
    }

    private void setStartCmd(String startCmd) {
	if (startCmd == null || startCmd.isEmpty()) {
	    stopCmd = DEFAULT_START_CMD;
	}
	this.startCmd = startCmd;
    }

    private void setStatusCmd(String statusCmd) {
	if (statusCmd == null || statusCmd.isEmpty()) {
	    stopCmd = DEFAULT_STATUS_CMD;
	}
	this.statusCmd = statusCmd;
    }

    private void setStopCmd(String stopCmd) {
	if (stopCmd == null || stopCmd.isEmpty()) {
	    stopCmd = DEFAULT_STOP_CMD;
	}
	this.stopCmd = stopCmd;
    }

    public File getShellScriptPath() {
	return shellScriptPath;
    }

    public String getStartCmd() {
	return startCmd;
    }

    public String getStatusCmd() {
	return statusCmd;
    }

    public String getStopCmd() {
	return stopCmd;
    }
}
