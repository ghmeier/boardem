package boardem.server.json;

/**
JSON representation of a user.
*/
public class User
{
	private String username;
	private String facebookId;
	private String displayName;
	private String pictureUrl;
	
	public User(String username,String facebookId,String displayName, String pictureUrl){
		this.username = username;
		this.facebookId = facebookId;
		this.displayName = displayName;
		this.pictureUrl = pictureUrl;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void setFacebookId(String id)
	{
		facebookId = id;
	}

	public String getFacebookId()
	{
		return facebookId;
	}

	public void setDisplayName(String name)
	{
		displayName = name;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setPictureUrl(String url)
	{
		pictureUrl = url;
	}

	public String getPictureUrl()
	{
		return pictureUrl;
	}
}