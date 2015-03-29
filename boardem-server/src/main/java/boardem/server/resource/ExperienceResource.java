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
import boardem.server.logic.ExperienceLogic;


@Path("/exp")
//@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExperienceResource
{
	/**
	 * Lists the experience points of a user in the database
	 * @param uid The Facebook ID of a user
	 * @return The experience points of a user
	 */
	@GET
	@Path ("{uid}")
	public Response getExperience(@PathParam("uid") String userId)
	{
		return Response.ok(ExperienceLogic.getUserExperience(userId)).build();
	}


	/**
	 * Adds a specified amount of experience points to a user
	 * @param uid The Facebook ID of a user
	 * @param exp The amount of experience points to give to a user
	 * @return Boardem Response (Success or not)
	 */
	@POST
	public Response setExperience(@QueryParam("uid") String userId, @QueryParam("exp") Integer experience)
	{
		return Response.ok(ExperienceLogic.setUserExperience(userId, experience)).build();
	}

}