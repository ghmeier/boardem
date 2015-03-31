package boardem.server.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import boardem.server.json.BoardemResponse;
import boardem.server.json.Comment;
import boardem.server.json.Event;
import boardem.server.logic.EventLogic;


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
		BoardemResponse response = EventLogic.createEvent(event).clone();
		response.setExtra(event.getId());
		return Response.ok(response).build();
	}
	
	/**
	 * Gets the list of events
	 */
	@GET
	public Response getEvents()
	{
		return Response.ok(EventLogic.getEvents()).build();
	}
	
	/**
	 * A user joins an event
	 */
	@POST
	@Path("{eid}/join")
	public Response joinEvent(@PathParam("eid") String eventId, @QueryParam("user_id") String userId)
	{
		return Response.ok(EventLogic.joinEvent(eventId, userId)).build();
	}
	
	/**
	 * A user leaves an event
	 */
	@POST
	@Path("{eid}/leave")
	public Response leaveEvent(@PathParam("eid") String eventId, @QueryParam("user_id") String userId)
	{
		return Response.ok(EventLogic.leaveEvent(eventId, userId)).build();
	}
	
	/**
	 * Gets information about an event
	 */
	@GET
	@Path("{eid}")
	public Response getEvent(@PathParam("eid") String eventId)
	{
		return Response.ok(EventLogic.getEvent(eventId)).build();
	}
	
	/**
	 * Updates information about the event
	 */
	@PUT
	@Path("{eid}")
	public Response updateEvent(@PathParam("eid") String eventId, @QueryParam("name") String name,
			@QueryParam("lat") Double lat, @QueryParam("lng") Double lng,
			@QueryParam("date") String date, @QueryParam("owner") String owner,
			@QueryParam("games") List<String> games)
	{
		return Response.ok(EventLogic.updateEvent(eventId, name, lat, lng, date, owner, games)).build();
	}
	
	/**
	 * Leaves a comment
	 */
	@POST
	@Path("{eid}/comment")
	public Response leaveComment(@PathParam("eid") String eventId, Comment comment)
	{
		return Response.ok(EventLogic.leaveComment(eventId, comment)).build();
	}
	
	/**
	 * Gets the comments of an event
	 */
	@GET
	@Path("{eid}/comments")
	public Response getComments(@PathParam("eid") String eventId)
	{
		return Response.ok(EventLogic.getComments(eventId)).build();
	}
}
