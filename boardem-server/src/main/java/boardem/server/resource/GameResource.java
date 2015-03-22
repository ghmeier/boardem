package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.logic.GetGameLogic;
import boardem.server.logic.GetGamesLogic;

@Path("/games")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameResource
{
	/**
	 * Gets the nth page of games
	 */
	@GET
	public Response getGames(@QueryParam("page_number") int pageNumber)
	{
		return Response.ok(GetGamesLogic.getGames(pageNumber)).build();
	}
	
	/**
	 * Gets information about a game
	 */
	@GET
	@Path("{gid}")
	public Response getGame(@PathParam("gid") String gameId)
	{
		return Response.ok(GetGameLogic.getGame(gameId)).build();
	}
}
