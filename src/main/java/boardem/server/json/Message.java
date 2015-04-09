package boardem.server.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON representation of a message
 */
public class Message implements Comparable<Message>
{
	//Format used for dates and time
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	//Contains the ID of the user, not their username
	private String from;
	private String content;
	private Date timestamp;
	
	@JsonProperty("from")
	public void setFrom(String from)
	{
		this.from = from;
	}
	
	@JsonProperty("from")
	public String getFrom()
	{
		return from;
	}
	
	@JsonProperty("content")
	public void setContent(String content)
	{
		this.content = content;
	}
	
	@JsonProperty("content")
	public String getContent()
	{
		return content;
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
	public int compareTo(Message other)
	{
		return timestamp.compareTo(other.timestamp);
	}
}
