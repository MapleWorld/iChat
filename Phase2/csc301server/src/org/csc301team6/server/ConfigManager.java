package org.csc301team6.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;

/* This class is implemented as a Singleton.
 * It contains all global configuration for 
 * the server. The configuration is loaded from
 * two configuration files: config/db.json, and
 * config/general.json
 * */
public class ConfigManager {

	private static ConfigManager mgr = null;
	private static String dbURL = "";
	private static int SESSION_DURATION_MINUTES;
	private static int REPLIES_PER_PAGE;
	private static int THREADS_PER_PAGE;
	private static int CSC301_HTTP_PORT;
	private static int MAX_REPLY_LENGTH;
	private static int MAX_THREAD_BODY_LENGTH;

	private ConfigManager() {
		// The first time we access this class, the config values are loaded
		// from the JSON config files.
		loadDBConfig();
		loadGeneralConfig();
	}

	/* Load the database configuration into memory from the file
	 * config/db.json
	 */
	private void loadDBConfig() {
		InputStream in = ClassLoader.getSystemResourceAsStream("db.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer file_contents = new StringBuffer("");
		JSONObject json;
		String line;

		try {
			while ((line = br.readLine()) != null) {
				file_contents.append(line);
			}

			br.close();
		} catch (IOException e) {
			file_contents = null;
		}

		if (file_contents == null) {
			//If there was a problem reading from the file, this is a critical error
			//There is no way to connect to the database since we were not able to
			//load a JDBC connection string.
			
			dbURL = "";
			System.err.println("CRITICAL ERROR: unable to load database configuration from file.");
		} else {
			try {
				//Config parameters loaded from the file, so we will attempt to parse them
				//and build the JDBC connection string.
				
				json = new JSONObject(file_contents.toString());
				dbURL = "jdbc:mysql://" + json.getString("hostname") + ":"
						+ json.getInt("port") + "/" + json.getString("schema")
						+ "?user=" + json.getString("user") + "&password="
						+ json.getString("pass");
			} catch (JSONException e) {
				System.err.println("CRITICAL ERROR: unable to parse database configuration");
				dbURL = "";
			}
		}
	}

	/*
	 * Load general configuration parameters from config/general.json
	 */
	private void loadGeneralConfig() {
		InputStream in = ClassLoader.getSystemResourceAsStream("general.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer file_contents = new StringBuffer("");
		JSONObject json;
		String line;

		try {
			while ((line = br.readLine()) != null) {
				file_contents.append(line);
			}

			br.close();
		} catch (IOException e) {
			file_contents = null;
		}

		if (file_contents == null) {
			//Unable to read the config file. The server can still run, but will be using
			//default values.
			System.err.println("WARNING: unable to load general configuration from file, using defaults");
			setDefaultConfig();
		} else {
			try {
				//Attempt to set the config based on the values read from the file
				json = new JSONObject(file_contents.toString());
				CSC301_HTTP_PORT = json.getInt("CSC301_HTTP_PORT");
				REPLIES_PER_PAGE = json.getInt("REPLIES_PER_PAGE");
				THREADS_PER_PAGE = json.getInt("THREADS_PER_PAGE");
				SESSION_DURATION_MINUTES = json.getInt("SESSION_DURATION_MINUTES");
				MAX_REPLY_LENGTH = json.getInt("MAX_REPLY_LENGTH");
				MAX_THREAD_BODY_LENGTH = json.getInt("MAX_THREAD_BODY_LENGTH");
			} catch (JSONException e) {
				//Since there was an error parsing the config, we set the configuration
				//to use default values. The server may still be able to run.
				System.err.println("WARNING: unable to parse general configuration, using defaults");
				setDefaultConfig();
			}
		}
	}

	/*
	 * Set all configurable values to their defaults.
	 * This is the fallback if there is an error reading the config
	 * from the file config/general.json
	 */
	private void setDefaultConfig() {
		SESSION_DURATION_MINUTES = 180;
		REPLIES_PER_PAGE = 20;
		THREADS_PER_PAGE = 20;
		CSC301_HTTP_PORT = 8080;
		MAX_REPLY_LENGTH = 5000;
		MAX_THREAD_BODY_LENGTH = 5000;
	}

	public String getJDBCURL() {
		return dbURL;
	}

	public int getHTTPPort() {
		return CSC301_HTTP_PORT;
	}

	public int getSessionDurationMinutes() {
		return SESSION_DURATION_MINUTES;
	}

	public int getRepliesPerPage() {
		return REPLIES_PER_PAGE;
	}

	public int getThreadsPerPage() {
		return THREADS_PER_PAGE;
	}

	public int getMaxReplyLength() {
		return MAX_REPLY_LENGTH;
	}

	public int getMaxThreadBodyLength() {
		return MAX_THREAD_BODY_LENGTH;
	}

	public static ConfigManager getInstance() {
		if (mgr == null) {
			mgr = new ConfigManager();
		}
		return mgr;
	}
}
