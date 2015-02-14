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
	public static final BoardemResponse RESPONSE_USER_DOES_NOT_EXIST = new BoardemResponse(3, "User does not exist");
	public static final BoardemResponse RESPONSE_EVENT_DOES_NOT_EXIST = new BoardemResponse(4, "Event does not exist");
	public static final BoardemResponse RESPONSE_USER_IN_EVENT = new BoardemResponse(5, "User is already in the event");
	public static final BoardemResponse RESPONSE_USER_NOT_IN_EVENT = new BoardemResponse(6, "User is not in the event");
}
