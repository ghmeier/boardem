package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.logic.GetGameLogic;


@Path("/game")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameResource
{
	/**
	 * Gets information about a game
	 */
	@GET
	@Path("{gid}")
	public Response getGame(@PathParam("gid") String gameId)
	{
		return Response.ok(GetGameLogic.getGame(gameId)).header("Access-Control-Allow-Origin","*").build();
	}
}
