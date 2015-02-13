package boardem.server.json;

/**
 * Represents a JSON response 
 */
public class BoardemResponse
{
	private int code;
	private String message;
	
	public BoardemResponse()
	{
		
	}
	
	public BoardemResponse(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public void setCode(int code)
	{
		this.code = code;
	}
	
	public int getCode()
	{
		return code;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
