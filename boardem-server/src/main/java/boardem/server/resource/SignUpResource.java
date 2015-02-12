package boardem.server.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import boardem.server.json.User;
import boardem.server.json.Response;
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
		return UserCreator.addUser(user);
	}
}