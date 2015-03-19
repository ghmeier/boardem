package boardem.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import boardem.server.BoardemApplication;
import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

public class UserRosterLogic {

	/**
	* Gets all of the events that a user is a part of
	* @param user_id User ID to get a list of all the event IDs from
	* @return All event IDs (by event ID numbers) that the user has
 	*/
	public static BoardemResponse getUserRoster(String user_id)
	{
		BoardemResponse response = null;

		return response;
	}

	/**
	* Adds a event (specified by an ID) to a user
	* @param user_id The Facebook ID of the user to add the event to
	* @param event_id The ID of the event to add to the user
	* @return RESPONSE_SUCCESS if completed without errors
	*/

	public static BoardemResponse addUserRosterItem(String user_id, String event_id)
	{
		return ResponseList.RESPONSE_SUCCESS;
	}
	
	/**
	* Removes an event (specified by an ID) from a user if it exists
	* @param user_id The Facebook ID of the user to remove the event from
	* @param event_id The ID of the event to remove from the user
	* @return RESPONSE_EVENT_DOES_NOT_EXIST if the user does not have that event, RESPONSE_SUCCESS if the user did have that event
	*/


	public static BoardemResponse deleteUserRosterItem(String user_id, String event_id)
	{
		return ResponseList.RESPONSE_SUCCESS;
	}

}