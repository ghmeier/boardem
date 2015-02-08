package boardem.server;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import java.util.concurrent.atomic.AtomicLong;

@Path("/signup")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignUpResource
{
	//Used to generate unique IDs for the users
	private final AtomicLong counter;

	public SignUpResource()
	{
		counter = new AtomicLong();
	}	

	@POST
	@Path("username")
	/**
	Adds a new user using a username/password combo instead of Facebook login
	@return 
	*/
	public int addUser(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		return UserCreator.createUser(username, password, counter.incrementAndGet());
	}
}