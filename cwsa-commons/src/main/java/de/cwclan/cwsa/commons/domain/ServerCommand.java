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

import java.io.File;

/**
 *
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public class ServerCommand {

    private File shellScriptPath;
    private String startCmd;
    private String stopCmd;
    private String statusCmd;

    public ServerCommand(String shellScriptPath) {
        this(shellScriptPath, "start", "stop", "status");
    }

    public ServerCommand(String shellScriptPath, String startCmd, String stopCmd, String statusCmd) {
        this.shellScriptPath = new File(shellScriptPath);
        this.startCmd = startCmd;
        this.stopCmd = stopCmd;
        this.statusCmd = statusCmd;
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
