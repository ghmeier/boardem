package boardem.server.json;

/**
 * Contains pre-defined responses and response codes
 */
public class ResponseList
{
	//Responses
	public static final BoardemResponse RESPONSE_SUCCESS = new BoardemResponse(0, "Operation completed successfully");
	public static final BoardemResponse RESPONSE_FIREBASE_ERROR = new BoardemResponse(1, "Firebase error");
	public static final BoardemResponse RESPONSE_USERNAME_USED = new BoardemResponse(2, "This username is already used");
}
