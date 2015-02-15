package boardem.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a JSON response 
 */
public class BoardemResponse
{
	private int code;
	private String message;
	
	private Object extra;
	
	public BoardemResponse()
	{
		
	}
	
	public BoardemResponse(int code, String message)
	{
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
	public void setExtra(String message)
	{
		extra = message;
	}
	
	@JsonProperty("extra")
	public Object getExtra()
	{
		return extra;
	}
}
