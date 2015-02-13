package boardem.server.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.CountDownLatch;

import boardem.server.UserCreator;

import com.firebase.client.*;

import boardem.server.json.User;
import boardem.server.json.BoardemResponse;
import boardem.server.UserCreator;

@Path("/signup")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignUpResource
{
	/**
	Adds a new user using a username/password combo instead of Facebook login
	@return 
	*/
	@POST
	public Response addUser(User user)
	{
		
		return Response.ok(UserCreator.addUser(user)).header("Access-Control-Allow-Origin","http://localhost:8100").build();
	}
}