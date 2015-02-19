package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.json.BoardemResponse;
import boardem.server.json.User;
import boardem.server.logic.UserLogic;



@Path("/users")
//@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource
{
	/**
	 * Lists all users in database
	 * @return All current users
	 */
	@GET
	public Response listUsers(User user)
	{
		//ultimately returns hashmap or array of all users, probably not just a response
		return null;
	}
	
	/**
	 * gets details for user with uid
	 * @return user information
	 */
	@GET
	@Path("{uid}")
	public Response getUser(@PathParam("uid") String userId){

		return Response.ok(UserLogic.getUserFromId(userId)).build();
	}
	
	/**
	 * Returns list of friends that the user has
	 * @return THe user's friends
	 */
	@GET
	@Path("{uid}/contacts")
	public Response listContacts(User user)
	{
		return null;
	}

	/**
	 * Returns a list of all badges for the given user
	 * @return The user's badges
	 */
	@GET
	@Path("{uid}/badges")
	public Response listBadges(User user)
	{
		return null;
	}

	/**
	 * Returns the attributes of a certain user
	 * @return The user's attributes
	 */
	@GET
	@Path("{uid}/attributes")
	public Response listAttributes(User user)
	{
		return null;
	}

	/**
	 * Lists all itens in a user's shelf
	 * @return The user's shelf
	 */
	@GET
	@Path("{uid}/shelf")
	public Response listShelf(User user)
	{
		return null;
	}

	/**
	 * Lists all items in a user's roster
	 * @return The user's roster
	 */
	@GET
	@Path("{uid}/roster")
	public Response listRoster(User user)
	{
		return null;
	}

	/**
	 * Lists messages to user from other users
	 * @return The user's messages
	 */
	 @GET
	 @Path("{uid}/message")
	 public Response listMessages(User user)
	 {
	 	return null;
	 }
}
