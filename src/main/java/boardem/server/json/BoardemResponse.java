package boardem.server.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a JSON response 
 */
public class BoardemResponse
{
	private int code;
	private String message;
	
	private Object extra;
	
	private List<Badge> badgesEarned;
	
	public BoardemResponse()
	{
		badgesEarned = new ArrayList<Badge>();
	}
	
	public BoardemResponse(int code, String message)
	{
		this();
		this.code = code;
		this.message = message;
		this.extra = "none";
	}

	@JsonProperty("code")
	public void setCode(int code)
	{
		this.code = code;
	}
	
	@JsonProperty("code")
	public int getCode()
	{
		return code;
	}
	
	@JsonProperty("message")
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	@JsonProperty("message")
	public String getMessage()
	{
		return message;
	}
	
	@JsonProperty("extra")
	public void setExtra(Object message)
	{
		extra = message;
	}
	
	@JsonProperty("extra")
	public Object getExtra()
	{
		return extra;
	}
	
	@JsonProperty("badge")
	public List<Badge> getBadges()
	{
		return badgesEarned;
	}
	
	@JsonProperty("badge")
	public void setBadges(List<Badge> badges)
	{
		badgesEarned = badges;
	}
	
	public void addBadge(Badge b)
	{
		badgesEarned.add(b);
	}
	
	/**
	 * Clones this BoardemResponse
	 */
	@Override
	public BoardemResponse clone()
	{
		BoardemResponse r = new BoardemResponse(this.code, this.message);
		return r;
	}
}
