package boardem.server.json;

import static boardem.server.BadgeActions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private List<String> messageIds;
	private List<String> completedIds; //Completed event IDs
	private List<String> earnedBadges; //IDs of earned badges
	private Map<String, Long> badgeProgress; //Key is the action for a badge, value is the number of times that action has been completed
	
	public User()
	{
		eventIds = new ArrayList<String>();
		messageIds = new ArrayList<String>();
		earnedBadges = new ArrayList<String>();
		
		badgeProgress = new HashMap<String, Long>();
		badgeProgress.put(ACTION_ADD_GAME, 0L);
		badgeProgress.put(ACTION_CREATE_EVENT, 0L);
		badgeProgress.put(ACTION_FRIEND, 0L);
		badgeProgress.put(ACTION_JOIN_EVENT, 0L);
		badgeProgress.put(ACTION_LEVEL, 0L);
		badgeProgress.put(ACTION_PLAY_GAME, 0L);
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
	
	@JsonProperty("messages")
	public void setMessages(List<String> list)
	{
		messageIds = list;
	}
	
	@JsonProperty("messages")
	public List<String> getMessages()
	{
		return messageIds;
	}
	
	@JsonProperty("completed_event_list")
	public void setCompletedEventList(List<String> list)
	{
		completedIds = list;
	}
	
	@JsonProperty("completed_event_list")
	public List<String> getCompletedEventList()
	{
		return completedIds;
	}
	
	@JsonProperty("badge_progress")
	public void setBadgeProgress(Map<String, Long> map)
	{
		badgeProgress = map;
	}
	
	@JsonProperty("badge_progress")
	public Map<String, Long> getBadgeProgress()
	{
		return badgeProgress;
	}
	
	@JsonProperty("earned_badges")
	public List<String> getEarnedBadges()
	{
		return earnedBadges;
	}
	
	@JsonProperty("earned_badges")
	public void setEarnedBadges(List<String> list)
	{
		earnedBadges = list;
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
		
		List<String> eventList = (List<String>) map.get("events");
		if(eventList != null)
		{
			user.setEvents(eventList);
		}
		
		List<String> messagesList = (List<String>) map.get("messages");
		if(messagesList != null)
		{
			user.setMessages(messagesList);
		}
		
		List<String> completedList = (List<String>) map.get("completed_event_list");
		if(completedList != null)
		{
			user.setCompletedEventList(completedList);
		}
		
		List<String> badgesList = (List<String>) map.get("earned_badges");
		if(badgesList != null)
		{
			user.setEarnedBadges(badgesList);
		}
		
		Map<String, Long> badgeProgressMap = (Map<String, Long>) map.get("badge_progress");
		if(badgeProgressMap != null)
		{
			user.setBadgeProgress(badgeProgressMap);
		}
		
		return user;
	}
}