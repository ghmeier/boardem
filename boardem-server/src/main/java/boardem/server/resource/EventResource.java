package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.json.Event;


@Path("/event")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventResource
{
	/**
	 * Adds a new event 
	 */
	@POST
	public Response addEvent(Event event)
	{
		return Response.ok().header("Access-Control-Allow-Origin","http://localhost:8100").build();
	}
}
