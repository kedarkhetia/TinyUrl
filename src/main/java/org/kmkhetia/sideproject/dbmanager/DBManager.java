package org.kmkhetia.sideproject.dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.kmkhetia.sideproject.model.Config;
import org.kmkhetia.sideproject.model.URL;

public class DBManager {
	private static DBManager dbm;
	private Config config;
	private Connection con;
	
	private DBManager() throws SQLException {
		this.config = Config.getInstance();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlString = "jdbc:mysql://" + this.config.getDbHost() + 
				":" + this.config.getDbPort() + 
				"/" + this.config.getDbName();
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		this.con = DriverManager.getConnection(urlString+timeZoneSettings, config.getDbUsername(), config.getDbPassword());
	}
	
	public static DBManager getInstance() throws SQLException {
		if(dbm == null) {
			dbm = new DBManager();
		}
		return dbm;
	}
	
	public synchronized boolean insert(URL url, String hash) throws SQLException {
		PreparedStatement insert = con.prepareStatement("INSERT INTO TinyUrl (LONGURL, HASH) VALUES (?, ?)");
		insert.setString(1, url.getUrl());
		insert.setString(2, hash);
		return insert.execute();
	}
	
	public String getShort(URL url) throws SQLException {
		PreparedStatement getShort = con.prepareStatement("SELECT HASH FROM TinyUrl WHERE LONGURL=?");
		getShort.setString(1, url.getUrl());
		ResultSet result = getShort.executeQuery();
		if(!result.next()) {
			return null;
		}
		return result.getString("HASH");
	}
	
	public URL getLong(String hash) throws SQLException {
		PreparedStatement getLong = con.prepareStatement("SELECT LONGURL FROM TinyUrl WHERE HASH=?");
		getLong.setString(1, hash);
		ResultSet result = getLong.executeQuery();
		if(!result.next()) {
			return null;
		}
		return new URL(result.getString("LONGURL"));
	}
}
