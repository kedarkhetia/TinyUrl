package org.kmkhetia.sideproject.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URL {
	private String url;
	
	public URL() {}
	
	public URL(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void encodeUrl() throws UnsupportedEncodingException {
		this.url = URLEncoder.encode(this.getUrl(), "UTF-8");
	}
	
	public URL decodeUrl() throws UnsupportedEncodingException {
		return new URL(URLDecoder.decode(this.getUrl(), "UTF-8"));
	}
	
	@Override
	public boolean equals(Object url) {
		if(url == this) {
			return true;
		}
		if(!(url instanceof URL)) {
			return false;
		}
		
		URL newUrl = (URL) url;
		if(this.url == newUrl.url) {
			return true;
		}
		return false;
	}
}
