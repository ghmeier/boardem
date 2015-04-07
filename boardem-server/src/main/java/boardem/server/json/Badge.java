package boardem.server.json;

import java.util.Map;

import boardem.server.FirebaseHelper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * JSON representation of a badge.
 */
public class Badge
{
	private String congrats;
	private String description;
	private long exp;
	private String icon;
	private String name;
	private String id;
	
	public Badge()
	{
		
	}
	
	@JsonProperty("congrats")
	public void setCongrats(String c)
	{
		congrats = c;
	}
	
	@JsonProperty("congrats")
	public String getCongrats()
	{
		return congrats;
	}
	
	@JsonProperty("description")
	public void setDescription(String d)
	{
		description = d;
	}
	
	@JsonProperty("description")
	public String getDescription()
	{
		return description;
	}
	
	@JsonProperty("icon")
	public void setIcon(String url)
	{
		icon = url;
	}
	
	@JsonProperty("icon")
	public String getIcon()
	{
		return icon;
	}
	
	@JsonProperty("name")
	public void setName(String n)
	{
		name = n;
	}
	
	@JsonProperty("name")
	public String getName()
	{
		return name;
	}
	
	@JsonProperty("exp")
	public void setExp(long exp)
	{
		this.exp = exp;
	}
	
	@JsonProperty("exp")
	public long getExp()
	{
		return exp;
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
	
	/**
	 * Gets information about a badge from Firebase.
	 * @param badgeId ID of the badge to get.
	 */
	public static Badge getBadgeFromFirebase(String badgeId)
	{
		if(badgeId == null)
		{
			return null;
		}
		
		Firebase ref = new Firebase("https://boardem.firebaseio.com/badges/" + badgeId);
		DataSnapshot snap = FirebaseHelper.readData(ref);
		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) snap.getValue();
		
		Badge badge = new Badge();
		badge.exp = (Long) data.get("exp");
		badge.congrats = (String) data.get("congrats");
		badge.description = (String) data.get("description");
		badge.icon = (String) data.get("icon");
		badge.name = (String) data.get("name");
		badge.id = (String) data.get("id");
		
		return badge;
	}
}
