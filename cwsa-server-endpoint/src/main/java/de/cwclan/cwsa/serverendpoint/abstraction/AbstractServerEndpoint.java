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
package de.cwclan.cwsa.serverendpoint.abstraction;

import de.cwclan.cwsa.commons.abstraction.ServerInterface;
import de.cwclan.cwsa.commons.domain.ServerCommand;
import de.cwclan.cwsa.commons.domain.ServerStatus;
import de.cwclan.cwsa.commons.exceptions.ServerException;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public abstract class AbstractServerEndpoint implements ServerInterface {

    private static Logger log = LoggerFactory.getLogger(AbstractServerEndpoint.class);
    private ServerCommand shellScript;
    private boolean running;

    public AbstractServerEndpoint(ServerCommand shellScript) {
	this.shellScript = shellScript;
    }

    @Override
    public ServerStatus getStatus() throws ServerException {
	String output = executeOperation(shellScript.getStatusCmd());
	return parseOutput(output);
    }

    @Override
    public ServerStatus startServer() throws ServerException {
	String output = executeOperation(shellScript.getStartCmd());
	return parseOutput(output);
    }

    @Override
    public ServerStatus stopServer() throws ServerException {
	String output = executeOperation(shellScript.getStopCmd());
	return parseOutput(output);
    }

    @Override
    public ServerStatus restartServer() throws ServerException {
	executeOperation(shellScript.getStopCmd());
	String output = executeOperation(shellScript.getStartCmd());
	return parseOutput(output);
    }

    @Override
    public ServerStatus customOperation(String operationName) throws ServerException {
	String output = executeOperation(operationName);
	return parseOutput(output);
    }

    public String executeOperation(String operationName) {
	StringBuilder output = new StringBuilder();
	try {

	    Process process = Runtime.getRuntime().exec(shellScript.getShellScriptPath().getAbsolutePath());
	    InputStream in = process.getInputStream();

	    int c;
	    while ((c = in.read()) != -1) {
		output.append(c);
	    }
	    in.close();
	    process.waitFor();


	} catch (InterruptedException ex) {
	} catch (IOException ex) {
	}
	return output.toString();
    }

    public abstract ServerStatus parseOutput(String output);
}
