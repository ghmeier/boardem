package boardem.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON representation of a user.
 */
public class User
{
	private String username;
	private String facebook_id;
	private String display_name;
	private String picture_url;
	
	public User()
	{
		
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
}