package boardem.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.concurrent.CountDownLatch;

import com.firebase.client.*;

@Path("/signup")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignUpResource
{
	@POST
	/**
	Adds a new user using a username/password combo instead of Facebook login
	@return 
	*/
	public int addUser(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		return UserCreator.addUser(username, password);
	}
}