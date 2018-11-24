package org.kmkhetia.sideproject.tinyurl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Main {
	
	@GET
    @Path("/{url}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUrl(@PathParam("url") String url) throws UnknownHostException, UnsupportedEncodingException, URISyntaxException, SQLException {
		return Response.seeOther(new URI(URLCollection.getUrlMappedTo(url).decodeUrl().getUrl())).build();
    }
}
