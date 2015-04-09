package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.logic.BadgesLogic;

@Path("/badges")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BadgeResource 
{
	/**
	 * Gets the list of all badges
	 */
	@GET
	public Response getBadges()
	{
		return Response.ok(BadgesLogic.getBadges()).build();
	}
	
	/**
	 * Gets information about a single badge
	 */
	@Path("{bid}")
	@GET
	public Response getBadge(@PathParam("bid") String badgeId)
	{
		return Response.ok(BadgesLogic.getBadge(badgeId)).build();
	}
}
