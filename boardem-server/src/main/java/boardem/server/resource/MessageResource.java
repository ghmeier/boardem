package boardem.server.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import boardem.server.json.Message;
import boardem.server.logic.MessageLogic;

@Path("/messages")
public class MessageResource
{
	/**
	 * Gets the conversation of the specified message thread
	 */
	@GET
	@Path("{mid}")
	public Response getMessages(@PathParam("mid") String messageId)
	{
		System.out.println("Workds");
		return Response.ok(MessageLogic.getMessages(messageId)).build();
	}
	
	/**
	 * Adds a message to a conversation.
	 */
	@POST
	@Path("{mid}/send")
	public Response sendMessage(@PathParam("mid") String messageId, Message message)
	{
		return Response.ok(MessageLogic.sendMessage(messageId, message)).build();
	}
	
	/**
	 * Creates a conversation between two or more users 
	 */
	@POST
	@Path("create")
	public Response createConversation(List<String> users)
	{
		return Response.ok(MessageLogic.createConversation(users)).build();
	}
}
