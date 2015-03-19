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

		//Point rostersRef to the "roster" table of the user
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase rostersRef = rootRef.child("users").child(UserLogic.getStringNameFromId(user_id)).child("roster");

		//Create ArrayList used in line 42
		ArrayList<String> rosterIds = new ArrayList<String>();
		
		//Convert rostersRef into a DataSnapshot
		DataSnapshot rostersData = FirebaseHelper.readData(rostersRef);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		//Convert rosterIdMap into a HashMap
		Map<String, HashMap> rosterIdMap = (Map<String, HashMap>) rostersData.getValue();

		//Check if user has any events
		if (rosterIdMap == null) {

			return ResponseList.RESPONSE_NO_ROSTERS;
		//If they do have events...
		} else {
			//Create a new HashMap with the data inside it
			Map<String, Object> realrosterIdMap = FirebaseHelper.convertToObjectMap(rosterIdMap, Object.class);
			rosterIds.addAll(realrosterIdMap.keySet());
		}

		//Return success
		response = ResponseList.RESPONSE_SUCCESS;

		//Add event IDs to the response
		response.setExtra(rosterIds);

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
		//Put username getting in front so we don't have to potentially do it twice
		String username = UserLogic.getStringNameFromId(user_id);

		//Point rostersRef to the "roster" table of username "user_id"
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase rostersRef = rootRef.child("users").child(username).child("roster");

		//Convert rostersRef to a DataSnapshot
		DataSnapshot rostersData = FirebaseHelper.readData(rostersRef);

		@SuppressWarnings({"rawtypes", "unchecked"})
		//Convert rostersData to a HashMap
		Map<String,Object> rostersMap = (Map<String,Object>) rostersData.getValue();

		//If the user doesn't have any events
		if (rostersMap == null) {

			Map<String,String> initialVal = new HashMap<String,String>();

			//Add the first value
			initialVal.put(event_id, "0");

			//Write it to the Firebase in the "roster" table
			FirebaseHelper.writeData(rostersRef, initialVal);

		//If the user already has this event
		} else if (rostersMap.containsKey(event_id)) {

			return ResponseList.RESPONSE_USER_IN_EVENT;

		} else {


			Map<String,String> contactMap = new HashMap<String,String>();

			//To count the number of events the user has already
			int size = rostersMap.size();

			contactMap.put(event_id, Integer.toString(size));

			//Write the HashMap to Firebase under the "roster" table
			FirebaseHelper.writeData(rostersRef, contactMap);

		}

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

		//Point rostersRef to the event_id of user "user_id"
		Firebase rostersRef = new Firebase(BoardemApplication.FIREBASE_URL).child("users").child(UserLogic.getStringNameFromId(user_id)).child("roster").child(event_id);

		//Convert to DataSnapshot
		DataSnapshot rostersData = FirebaseHelper.readData(rostersRef);

		@SuppressWarnings({"rawtypes", "unchecked"})
		//Convert to string (will only have one value, so no HashMap this time)
		String rostersExists = (String) rostersData.getValue();

		//If rostersExists = null, no roster with the given event_id exists in that user's list of rosters
		if (rostersExists == null) {

			return ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;

		} 

		//If the event_id does exist in the user, remove it
		FirebaseHelper.removeData(rostersRef);

		return ResponseList.RESPONSE_SUCCESS;
	}

}