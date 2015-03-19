package boardem.server.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.json.User;
import boardem.server.logic.UserContactsLogic;
import boardem.server.logic.UserLogic;
import boardem.server.logic.UserBadgesLogic;
import boardem.server.logic.UserAttributesLogic;
import boardem.server.logic.UserShelfLogic;


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
	public Response listUsers()
	{
		//ultimately returns hashmap or array of all users, probably not just a response
		return Response.ok(UserLogic.getAllUsers()).build();
	}
	
	/**
	 * gets details for user with uid
	 * @param uid The Facebook ID of the user
	 * @return user information
	 */
	@GET
	@Path("{uid}")
	public Response getUser(@PathParam("uid") String userId){

		return Response.ok(UserLogic.getUserFromId(userId)).build();
	}
	
	/**
	 * Returns list of friends that the user has
	 * @param uid The Facebook ID of the user
	 * @return The user's friends
	 */
	@GET
	@Path("{uid}/contacts")
	public Response getContacts(@PathParam("uid") String userId)
	{
		return Response.ok(UserContactsLogic.getUserContacts(userId)).build();
	}

	/**
	 * Adds a user ID to list of friends that the user has
	 * @param uid The Facebook ID of the user
	 * @param fid The Facebook ID of the contact
	 * @return Boardem response (success or no)
	 */

	@POST
	@Path("{uid}/contacts")
	public Response addContact(@PathParam("uid") String userId, @QueryParam("fid") String friendId)
	{
		return Response.ok(UserContactsLogic.addUserContact(userId, friendId)).build();
	}

	/**
	 * Removes a user ID from the list of friends that the user has
	 * @param uid The Facebook ID of the user
	 * @param fid The Facebook ID of the contact
	 * @return Boardem response (success or no)
	 */

	@DELETE
	@Path("{uid}/contacts")
	public Response removeContact(@PathParam("uid") String userId, @QueryParam("fid") String friendId)
	{
		return Response.ok(UserContactsLogic.removeUserContact(userId, friendId)).build();
	}

	/**
	 * Returns a list of all badges for the given user
	 * @param uid The Facebook ID of the user
	 * @return The user's badges
	 */

	@GET
	@Path("{uid}/badges")
	public Response listBadges(@PathParam("uid") String userId)
	{
		return Response.ok(UserBadgesLogic.getUserBadges(userId)).build();
	}

	/**
	 * Adds a badge to a user
	 * @param uid The Facebook ID of the user
	 * @param bid The ID of the badge to add
	 * @return Boardem response (200 for success)
	 */

	@POST
	@Path("{uid}/badges")
	public Response addBadge(@PathParam("uid") String userId, @QueryParam("bid") String badgeId)
	{
		return Response.ok(UserBadgesLogic.addUserBadge(userId, badgeId)).build();
	}

	/**
	 * Removes a badge from a user
	 * @param uid The Facebook ID of the user
	 * @param bid The ID of the badge to remove
	 * @return Boardem response (200 for success)
	 */

	//Maybe necessary? Can't think of a use for it, but might be nice to have

	@DELETE
	@Path("{uid}/badges")
	public Response removeBadge(@PathParam("uid") String userId, @QueryParam("bid") String badgeId)
	{
		return Response.ok(UserBadgesLogic.removeUserBadge(userId, badgeId)).build();
	}


	/**
	 * Returns the attributes of a certain user
	 * @return The user's attributes
	 */
	@GET
	@Path("{uid}/attributes")
	public Response getAttributes(@PathParam("uid") String userId)
	{
		return Response.ok(UserAttributesLogic.getUserAttributes(userId)).build();
	}

	/**
	 * Adds an attribute ID to a user
	 * @param uid Facebook ID of user
	 * @param aid ID of attribute
	 * @return Boardem response (ok or no)
	 */
	@POST
	@Path("{uid}/attributes")
	public Response addAttribute(@PathParam("uid") String userId, @QueryParam("aid") String attributeId)
	{
		return Response.ok(UserAttributesLogic.addUserAttribute(userId, attributeId)).build();
	}

	/**
	 * Deletes an attribute ID from a user
	 * @param uid Facebook ID of user
	 * @param aid ID of attribute
	 * @return Boardem response (ok or no)
	 */
	@DELETE
	@Path("{uid}/attributes")
	public Response deleteAttribute(@PathParam("uid") String userId, @QueryParam("aid") String attributeId)
	{
		return Response.ok(UserAttributesLogic.deleteUserAttribute(userId, attributeId)).build();
	}

	/**
	 * Lists all itens in a user's shelf
	 * @return The user's shelf
	 */
	@GET
	@Path("{uid}/shelf")
	public Response getShelf(@PathParam("uid") String userId)
	{
		return Response.ok(UserShelfLogic.getUserShelf(userId)).build();
	}

	/**
	 * Adds a shelfItem ID to a user
	 * @param uid Facebook ID of user
	 * @param sid ID of shelfItem
	 * @return Boardem response (ok or no)
	 */
	@POST
	@Path("{uid}/shelf")
	public Response addShelfItem(@PathParam("uid") String userId, @QueryParam("sid") String shelfItemId)
	{
		return Response.ok(UserShelfLogic.addUserShelfID(userId, shelfItemId)).build();
	}

	/**
	 * Deletes an shelfItem ID from a user
	 * @param uid Facebook ID of user
	 * @param sid ID of shelfItem
	 * @return Boardem response (ok or no)
	 */
	@DELETE
	@Path("{uid}/shelf")
	public Response deleteShelfItem(@PathParam("uid") String userId, @QueryParam("sid") String shelfItemId)
	{
		return Response.ok(UserShelfLogic.removeUserShelfID(userId, shelfItemId)).build();
	}


	/**
	 * Lists all items in a user's roster
	 * @return The user's roster
	 */
	@GET
	@Path("{uid}/roster")
	public Response listRoster(@PathParam("uid") String userId)
	{
		return Response.ok(UserRosterLogic.getUserRoster(userId)).build();
	}

	/**
	 * Adds an event ID to a user
	 * @param uid Facebook ID of user
	 * @param eid ID of event
	 * @return Boardem response (ok or no)
	 */
	@POST
	@Path("{uid}/roster")
	public Response addRosterItem(@PathParam("uid") String userId, @QueryParam("eid") String eventId)
	{
		return Response.ok(UserRosterLogic.addUserRoster(userId, eventId)).build();
	}

	/**
	 * Deletes an event ID from a user
	 * @param uid Facebook ID of user
	 * @param eid ID of event
	 * @return Boardem response (ok or no)
	 */
	@DELETE
	@Path("{uid}/roster")
	public Response deleteRosterItem(@PathParam("uid") String userId, @QueryParam("eid") String eventId)
	{
		return Response.ok(UserRosterLogic.removeUserRoster(userId, eventId)).build();
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
