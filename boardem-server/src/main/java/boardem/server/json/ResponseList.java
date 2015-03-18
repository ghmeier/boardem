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
	public static final BoardemResponse RESPONSE_FB_ID_USED = new BoardemResponse(7, "Facebook ID is already in use");
	public static final BoardemResponse RESPONSE_USER_OWNS_EVENT = new BoardemResponse(8, "User is the owner of this event");
	public static final BoardemResponse RESPONSE_USER_IN_CONTACTS = new BoardemResponse(9,"User is already in contacts.");
	public static final BoardemResponse RESPONSE_NO_BADGES = new BoardemResponse(10,"User has no badges.");
	public static final BoardemResponse RESPONSE_GAME_DOESNT_EXIST = new BoardemResponse(11, "Game does not exist");
	public static final BoardemResponse RESPONSE_BADGE_DOES_NOT_EXIST = new BoardemResponse(12,"User does not have this badge.");
	public static final BoardemResponse RESPONSE_NO_ATTRIBUTES = new BoardemResponse(13,"User has no attributes.");
	public static final BoardemResponse RESPONSE_ATTRIBUTE_DOES_NOT_EXIST = new BoardemResponse(14,"User does not have this attribute.");
}
