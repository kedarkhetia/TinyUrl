package org.kmkhetia.sideproject.tinyurl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import org.kmkhetia.sideproject.dbmanager.DBManager;
import org.kmkhetia.sideproject.model.ResponseMessage;
import org.kmkhetia.sideproject.model.URL;

import com.google.common.hash.Hashing;
/**
 * URLCollection implements the logic of the application.
 * 
 * @author Kedar M. Khetia
 *
 */
public class URLCollection {
	private static final int PORT = 8080;
	
	/**
	 * It returns the big url mapped to Short URL
	 * 
	 * @param sortenUrl
	 * @return
	 * @throws UnknownHostException
	 * @throws SQLException
	 */
	public static URL getUrlMappedTo(String shortenUrl) throws UnknownHostException, SQLException {
		URL url = DBManager.getInstance().getLong(shortenUrl);
		if(url != null && !url.getUrl().isEmpty()) {
			return url;
		}
		return new URL("http://" + InetAddress.getLocalHost().getHostName()+"/tinyurl/");
	}
	/**
	 * This method gets short URL from long url.
	 * 
	 * @param bigUrl
	 * @return
	 * @throws UnknownHostException
	 * @throws SQLException
	 */
	public static ResponseMessage getShortendUrl(URL bigUrl) throws UnknownHostException, SQLException {
		String urlHead = "http://" + getInstanceName() + ":" + PORT +"/tinyurl/";
		String hash = DBManager.getInstance().getShort(bigUrl);
		if(hash != null && !hash.isEmpty()) {
			return new ResponseMessage(true, new URL(urlHead + hash));
		}
		return new ResponseMessage(false, new URL(urlHead));
	}
	
	/**
	 * It generates the key value of long url and short url.
	 * 
	 * @param url
	 * @return
	 */
	public static ResponseMessage setUrlToMap(URL url) {
		try {
			String encodedUrl = Hashing.sha256()
					  .hashString(url.getUrl(), StandardCharsets.UTF_8)
					  .toString();
			String encodedUrl7char = encodedUrl.substring(encodedUrl.length() - 7);
			DBManager manager = DBManager.getInstance();
			String result = manager.getShort(url);
			boolean res = true;
			if(result == null || result.isEmpty()) {
				res = manager.insert(url, encodedUrl7char);
			}
			return new ResponseMessage(true, new URL("http://" + getInstanceName() + ":" + PORT + "/tinyurl/" + encodedUrl7char));
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseMessage(false, url);
		}
	}
	
	/**
	 * This method gets the meta data like public hostname for 
	 * URLCollection.
	 * 
	 * @return String
	 */
	public static String getInstanceName() {
		try {
			HttpURLConnection connection = (HttpURLConnection) new java.net.URL("http://169.254.169.254/latest/meta-data/public-hostname").openConnection();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String line;
			String hostname = "";
			while((line = reader.readLine()) != null) {
				hostname += line;
			} 
			return hostname;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
