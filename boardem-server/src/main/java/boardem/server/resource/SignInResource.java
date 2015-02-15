package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.json.User;
import boardem.server.logic.SignInLogic;

@Path("/signin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignInResource {
	
	@GET
	/**
	 * Requests the FacebookID and checks it against the Firebase server
	 * @return response code "success" if ID exists, "error" if ID doesn't exist
	 */
	public Response signIn(@QueryParam("facebookId")String facebookId){
		
		//check if user exists, return success if exists, otherwise error
		return Response.ok(SignInLogic.signIn(facebookId)).header("Access-Control-Allow-Origin","http://localhost:8100").build();
	}

}
