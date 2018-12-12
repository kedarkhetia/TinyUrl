package org.kmkhetia.sideproject.model;

/**
 * The class represents Response Message type for APIs.
 * 
 * @author Kedar M. Khetia
 *
 */
public class ResponseMessage {
	private boolean ok;
	private URL url;
	
	public ResponseMessage(boolean ok, URL url) {
		this.ok = ok;
		this.url = url;
	}
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}
}
