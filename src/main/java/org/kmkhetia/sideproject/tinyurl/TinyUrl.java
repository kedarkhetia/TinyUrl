package org.kmkhetia.sideproject.tinyurl;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.kmkhetia.sideproject.model.ResponseMessage;
import org.kmkhetia.sideproject.model.URL;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/api")
public class TinyUrl {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws UnsupportedEncodingException 
     * @throws SQLException 
     * @throws UnknownHostException 
     */
    
    @POST
    @Path("/url")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage setUrl(URL url) throws UnsupportedEncodingException, SQLException {
    	url.encodeUrl();
    	return URLCollection.setUrlToMap(url);
    }
    
    @GET
    @Path("/url")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage getUrl(@QueryParam("bigUrl") URL bigUrl) throws UnknownHostException, UnsupportedEncodingException, SQLException {
    	bigUrl.encodeUrl();
    	return URLCollection.getSortendUrl(bigUrl);
    }

}
