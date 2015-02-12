package boardem.server.json;

/**
 * Represents a JSON response 
 */
public class Response
{
	private int code;
	private String message;
	
	public Response()
	{
		
	}
	
	public Response(int code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
	public void setCode(int c)
	{
		code = c;
	}
	
	public int getCode()
	{
		return code;
	}
	
	public void setMessage(String m)
	{
		message = m;
	}
	
	public String getMessage()
	{
		return message;
	}
}
