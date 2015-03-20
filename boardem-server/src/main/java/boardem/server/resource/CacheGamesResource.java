package boardem.server.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import boardem.server.logic.CacheGamesLogic;

@Path("/cache-games")
/**
 * Downloads game information from Firebase and caches it
 */
public class CacheGamesResource
{
	@POST
	public Response cacheGames()
	{
		CacheGamesLogic.cacheGames();
		return Response.ok().build();
	}
}
