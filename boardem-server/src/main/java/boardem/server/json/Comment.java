package boardem.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON representation of a Comment for an event 
 */
public class Comment
{
	private String userId;
	private String comment;
	
	@JsonProperty("user_id")
	public void setUserId(String id)
	{
		userId = id;
	}
	
	@JsonProperty("user_id")
	public String getUserId()
	{
		return userId;
	}
	
	@JsonProperty("comment")
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	@JsonProperty("comment")
	public String getComment()
	{
		return comment;
	}
}
