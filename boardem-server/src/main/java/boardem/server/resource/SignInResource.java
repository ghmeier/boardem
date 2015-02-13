package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.json.User;

@Path("/signin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignInResource {
	
	@GET
	/*
	 * get request that takes in the facebook Id and hashed user password as params to check against firebase.
	 */
	public Response signIn(@QueryParam("facebookId")String fbId, @QueryParam("auth")String auth){
		
		//get user data here and stuff
		User user = new User("default","test","Test User","test.png");
		
		//check if user exists, return success if exists, otherwise error
		return Response.ok(user).header("Access-Control-Allow-Origin","http://localhost:8100").build();
	}

}
