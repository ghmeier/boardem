package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/signin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignInResource {
	
	@GET
	/*
	 * get request that takes in the facebook Id and hashed user password as params to check against firebase.
	 */
	public int signIn(@QueryParam("facebookId")String fbId, @QueryParam("auth")String auth){
		
		
		return 0;
	}
	

}
