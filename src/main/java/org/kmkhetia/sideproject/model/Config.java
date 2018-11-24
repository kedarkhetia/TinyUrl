package org.kmkhetia.sideproject.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class Config {
	private String dbHost;
	private int dbPort;
	private String dbName;
	private String dbUsername;
	private String dbPassword;

	private static volatile Config config;
	private static final String DB_CONFIG = "/DBConfig.json";
	
	private Config() {}
	
	public static Config getInstance() {
		if(config == null) {
			Gson gson = new Gson();
			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(DB_CONFIG)));
				String line;
				while((line = in.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			config = gson.fromJson(sb.toString(), Config.class);
		}
		return config;
	}
	
	public String getDbHost() {
		return dbHost;
	}

	public int getDbPort() {
		return dbPort;
	}

	public String getDbName() {
		return dbName;
	}
	
	public String getDbUsername() {
		return dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}
}
