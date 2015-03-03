package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.logic.SearchLogic;

import com.google.common.base.Optional;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource 
{
	/**
	 * Searches for games meeting certain criteria
	 */
	@GET
	public Response searchEvents(@QueryParam("user_lat") double userLat, @QueryParam("user_lng") double userLng,
			@QueryParam("user_id") String userId, @QueryParam("dist") Optional<Double> dist, @QueryParam("date") Optional<String> dateString)
	{
		return Response.ok(SearchLogic.searchEvents(userLat, userLng, userId, dist, dateString)).header("Access-Control-Allow-Origin", "*").build();
	}
}
