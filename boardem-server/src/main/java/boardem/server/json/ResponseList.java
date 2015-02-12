package boardem.server.json;

/**
 * Contains pre-defined responses and response codes
 */
public class ResponseList
{
	//Responses
	public static final Response RESPONSE_SUCCESS = new Response(0, "Operation completed successfully");
	public static final Response RESPONSE_FIREBASE_ERROR = new Response(1, "Firebase error");
	public static final Response RESPONSE_USERNAME_USED = new Response(2, "This username is already used");
}
