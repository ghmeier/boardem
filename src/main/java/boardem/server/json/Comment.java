package boardem.server.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON representation of a Comment for an event 
 */
public class Comment implements Comparable<Comment>
{
	//Format used for dates and time
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private String userId;	
	private String comment;
	private Date timestamp;
	
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
	
	@JsonProperty("timestamp")
	public void setDate(String time)
	{
		Date d = null;
		try
		{
			d = new SimpleDateFormat(DATE_FORMAT).parse(time);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		} 
		
		this.timestamp = d;
	}
	
	public void setDateObject(Date date)
	{
		timestamp = date;
	}
	
	@JsonProperty("timestamp")
	public String getDate()
	{
		return new SimpleDateFormat(DATE_FORMAT).format(timestamp);
	}

	@Override
	public int compareTo(Comment other)
	{
		return timestamp.compareTo(other.timestamp);
	}
}
