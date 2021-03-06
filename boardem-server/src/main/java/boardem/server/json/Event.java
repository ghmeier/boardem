package boardem.server.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firebase.client.DataSnapshot;

/**
 * JSON representation for an event
 */
public class Event
{
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private String id;
	private String name;
	private double lat;
	private double lng;
	private Date date;
	private String ownerId;
	private List<String> participantIds;
	private List<String> gameIds;
	//private List<Comment> comments;
	
	public Event()
	{
		//Make sure the objects exist in case they don't in Firebase
		participantIds = new ArrayList<String>();
		gameIds = new ArrayList<String>();
		//comments = new ArrayList<Comment>();
	}
	
	@JsonProperty("event_id")
	public void setId(String id)
	{
		this.id = id;
	}
	
	@JsonProperty("event_id")
	public String getId()
	{
		return id;
	}
	
	@JsonProperty("name")
	public void setName(String name)
	{
		this.name = name;
	}
	
	@JsonProperty("name")
	public String getName()
	{
		return name;
	}
	
	@JsonProperty("lat")
	public void setLatitude(double lat)
	{
		this.lat = lat;
	}
	
	@JsonProperty("lat")
	public double getLatitude()
	{
		return lat;
	}
	
	@JsonProperty("lng")
	public void setLongitude(double lng)
	{
		this.lng = lng;
	}
	
	@JsonProperty("lng")
	public double getLongitude()
	{
		return lng;
	}
	
	@JsonProperty("date")
	public void setDate(String date)
	{
		Date d = null;
		try
		{
			d = new SimpleDateFormat(DATE_FORMAT).parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		} 
		
		this.date = d;
	}
	
	@JsonProperty("date")
	public String getDate()
	{
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}
	
	public Date getDateObject()
	{
		return date;
	}
	
	@JsonProperty("owner")
	public void setOwner(String owner)
	{
		this.ownerId = owner;
	}
	
	@JsonProperty("owner")
	public String getOwner()
	{
		return ownerId;
	}
	
	@JsonProperty("participants")
	public void setParticipants(List<String> participants)
	{
		this.participantIds = participants;
	}
	
	@JsonProperty("participants")
	public List<String> getParticipants()
	{
		return participantIds;
	}
	
	@JsonProperty("games")
	public void setGames(List<String> games)
	{
		this.gameIds = games;
	}
	
	@JsonProperty("games")
	public List<String> getGames()
	{
		return gameIds;
	}
	
/*	@JsonProperty("comments")
	public void setComments(List<Comment> comments)
	{
		this.comments = comments;
	}
	
	@JsonProperty("comments")
	public List<Comment> getComments()
	{
		return comments;
	}*/
	
	@SuppressWarnings("unchecked")
	public static Event getEventFromSnapshot(DataSnapshot snap)
	{
		Event event = new Event();
		HashMap<String, Object> map = (HashMap<String, Object>) snap.getValue();
		
		event.setId((String) map.get("event_id"));
		event.setName((String) map.get("name"));
		event.setLatitude((double) map.get("lat"));
		event.setLongitude((double) map.get("lng"));
		event.setDate((String) map.get("date"));
		event.setOwner((String) map.get("owner"));
		
		//Check if lists are null and set if they are not
		List<String> participants = (List<String>) map.get("participants");
		if(participants != null)
		{
			event.setParticipants(participants);
		}
		List<String> games = (List<String>) map.get("games");
		if(games != null)
		{
			event.setGames(games);
		}

//		if(map.get("comments") != null)
//		{
//			@SuppressWarnings("rawtypes")
//			HashMap<String, Comment> commentMap = (HashMap<String, Comment>) FirebaseHelper.convertToObjectMap((Map<String, HashMap>) ((HashMap<String, Object>) snap.getValue()).get("comments"), Comment.class);
//			event.getComments().addAll(commentMap.values());
//			Collections.sort(event.getComments());
//		}
		
		return event;
	}
}
