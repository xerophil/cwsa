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
package de.cwclan.cwsa.serverendpoint.main;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Properties;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public class ServerEndpoint {

    private static final Logger log = LoggerFactory.getLogger(ServerEndpoint.class);
    private Properties properties;

    public ServerEndpoint(Properties properties) {
	this.properties = properties;
    }

    public void run() {
	
    }

    private Thread getShutdownHook() {
	return new Thread() {

	    @Override
	    public void run() {
		log.info("CTRL-C detected. Shutting down gracefully.");
	    }
	};
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Options options = new Options();
	options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("Used to enter path of configfile. Default file is endpoint.properties. NOTE: If the file is empty or does not exsist, a default config is created.").create("config"));
	options.addOption("h", "help", false, "displays this page");
	CommandLineParser parser = new PosixParser();
	Properties properties = new Properties();
	try {
	    /*
	     * parse default config shipped with jar
	     */
	    CommandLine cmd = parser.parse(options, args);

	    InputStream in = ServerEndpoint.class.getResourceAsStream("/endpoint.properties");
	    if (in == null) {
		throw new IOException("Unable to load default config from JAR. This should not happen.");
	    }

	    properties.load(in);
	    in.close();
	    log.debug("Loaded default config base: {}", properties.toString());
	    if (cmd.hasOption("help")) {
		printHelp(options);
		System.exit(0);
	    }
	    if (cmd.hasOption("config")) {
		/*
		 * parse cutom config if exists, otherwise create default cfg
		 */
		File file = new File(cmd.getOptionValue("config", "endpoint.properties"));
		if (file.exists() && file.canRead() && file.isFile()) {
		    in = new FileInputStream(file);
		    properties.load(in);
		    log.debug("Loaded custom config from {}: {}", file.getAbsoluteFile(), properties);
		} else {
		    log.warn("Config file does not exsist. A default file will be created.");
		}
		FileWriter out = new FileWriter(file);
		properties.store(out, "Warning, this file is recreated on every startup to merge missing parameters.");

	    }

	    log.info("Config read successfull. Values are: {}", properties);
	    ServerEndpoint endpoint = new ServerEndpoint(properties);
	    Runtime.getRuntime().addShutdownHook(endpoint.getShutdownHook());
	    endpoint.run();
	} catch (IOException ex) {
	    log.error("Error while reading config.", ex);
	} catch (ParseException ex) {
	    log.error("Error while parsing commandline options: {}", ex.getMessage());
	    printHelp(options);
	    System.exit(1);
	}
    }

    public static void printHelp(Options options) {
	HelpFormatter formatter = new HelpFormatter();
	String fileName = "<jar-file>";
	try {
	    File jarFile = new File(ServerEndpoint.class.getProtectionDomain().getCodeSource().getLocation().toURI());
	    fileName = jarFile.getName();
	} catch (URISyntaxException ex) {
	}
	formatter.printHelp("java -jar " + fileName, "", options, "", true);
    }
}
