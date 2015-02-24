package boardem.server.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firebase.client.DataSnapshot;

/**
 * JSON representation of a user.
 */
public class User
{
	private String username;
	private String facebook_id;
	private String display_name;
	private String picture_url;
	private List<String> eventIds;
	
	//Gamification stuff
	private int eventsCreated;
	
	public User()
	{
		eventIds = new ArrayList<String>();
	}
	
	public User(String username, String facebookId, String displayName, String pictureUrl)
	{
		this.username = username;
		this.facebook_id = facebookId;
		this.display_name = displayName;
		this.picture_url = pictureUrl;
	}

	@JsonProperty("username")
	public void setUsername(String username)
	{
		this.username = username;
	}

	@JsonProperty("username")
	public String getUsername()
	{
		return username;
	}

	@JsonProperty("facebook_id")
	public void setFacebookId(String id)
	{
		facebook_id = id;
	}

	@JsonProperty("facebook_id")
	public String getFacebookId()
	{
		return facebook_id;
	}

	@JsonProperty("display_name")
	public void setDisplayName(String name)
	{
		display_name = name;
	}

	@JsonProperty("display_name")
	public String getDisplayName()
	{
		return display_name;
	}

	@JsonProperty("picture_url")
	public void setPictureUrl(String url)
	{
		picture_url = url;
	}

	@JsonProperty("picture_url")
	public String getPictureUrl()
	{
		return picture_url;
	}
	
	@JsonProperty("events")
	public void setEvents(List<String> list)
	{
		eventIds = list;
	}
	
	@JsonProperty("events")
	public List<String> getEvents()
	{
		return eventIds;
	}

	@JsonProperty("events_created")
	public void setEventsCreated(int num)
	{
		eventsCreated = num;
	}
	
	@JsonProperty("events_created")
	public int getEventsCreated()
	{
		return eventsCreated;
	}
	
	public void incrementEventsCreated()
	{
		++eventsCreated;
	}
	
	@SuppressWarnings("unchecked")
	public static User getUserFromSnapshot(DataSnapshot snap)
	{
		User user = new User();
		HashMap<String, Object> map = (HashMap<String, Object>) snap.getValue();
		user.setUsername((String) map.get("username"));
		user.setFacebookId((String) map.get("facebook_id"));
		user.setDisplayName((String) map.get("display_name"));
		user.setPictureUrl((String) map.get("picture_url"));
		
		//Just in case the value is not present in the database
		Long tmp = (Long) map.get("events_created");
		user.setEventsCreated((int) (tmp == null ? 0 : tmp));
		
		List<String> eventList = (List<String>) map.get("events");
		if(eventList != null)
		{
			user.setEvents(eventList);
		}
		
		return user;
	}
}