package boardem.server.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firebase.client.DataSnapshot;

/**
 * JSON representation of a conversation. A conversation is a series of messages between two or more
 * users.
 */
public class Conversation
{
	private String id;
	private List<String> users;
	private List<Message> messages;
	
	public Conversation()
	{
		users = new ArrayList<String>();
		messages = new ArrayList<Message>();
	}
	
	@JsonProperty("id")
	public void setId(String id)
	{
		this.id = id;
	}
	
	@JsonProperty("id")
	public String getId()
	{
		return id;
	}
	
	@JsonProperty("users")
	public void setUsers(List<String> users)
	{
		this.users = users;
	}
	
	@JsonProperty("users")
	public List<String> getUsers()
	{
		return users;
	}
	
	@JsonProperty("messages")
	public void setMessages(List<Message> messages)
	{
		this.messages = messages;
	}
	
	@JsonProperty("messages")
	public List<Message> getMessages()
	{
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public static Conversation getConversationFromSnapshot(DataSnapshot snap)
	{
		//Pull the data out of the snapshot
		Map<String, Object> map = (Map<String, Object>) snap.getValue();
		
		Conversation c = new Conversation();
		c.setId((String) map.get("id"));
		
		List<String> usersList = (List<String>) map.get("users");
		if(usersList != null)
		{
			c.setUsers(usersList);
		}
	
		if(map.get("messages") != null)
		{
			@SuppressWarnings("rawtypes")
			HashMap<String, Message> messageMap = (HashMap<String, Message>) FirebaseHelper.convertToObjectMap((Map<String, HashMap>) ((HashMap<String, Object>) snap.getValue()).get("messages"), Message.class);
			c.getMessages().addAll(messageMap.values());
			Collections.sort(c.getMessages());
		}
		
		return c;
	}
}
