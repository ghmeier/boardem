package boardem.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.logic.CreateEventLogic;
import boardem.server.logic.GetEventLogic;
import boardem.server.logic.GetEventsLogic;
import boardem.server.logic.JoinEventLogic;
import boardem.server.logic.LeaveEventLogic;


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
		BoardemResponse response = CreateEventLogic.createEvent(event).clone();
		response.setExtra(event.getId());
		return Response.ok(response).header("Access-Control-Allow-Origin","*").build();
	}
	
	/**
	 * Gets the list of events
	 */
	@GET
	public Response getEvents()
	{
		return Response.ok(GetEventsLogic.getEvents()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * A user joins an event
	 */
	@POST
	@Path("{eid}/join")
	public Response joinEvent(@PathParam("eid") String eventId, @QueryParam("user_id") String userId)
	{
		return Response.ok(JoinEventLogic.joinEvent(eventId, userId)).header("Access-Control-Allow-Origin","*").build();
	}
	
	/**
	 * A user leaves an event
	 */
	@POST
	@Path("{eid}/leave")
	public Response leaveEvent(@PathParam("eid") String eventId, @QueryParam("user_id") String userId)
	{
		return Response.ok(LeaveEventLogic.leaveEvent(eventId, userId)).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Gets information about an event
	 */
	@GET
	@Path("{eid}")
	public Response getEvent(@PathParam("eid") String eventId)
	{
		return Response.ok(GetEventLogic.getEvent(eventId)).header("Access-Control-Allow-Origin", "*").build();
	}
}
