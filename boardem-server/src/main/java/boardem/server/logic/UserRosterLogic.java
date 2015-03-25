package boardem.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		Firebase rostersRef = rootRef.child("users").child(UserLogic.getStringNameFromId(user_id)).child("events");

		//Create ArrayList used in line 42
		ArrayList<String> rosterIds = new ArrayList<String>();
		
		//Convert rostersRef into a DataSnapshot
		DataSnapshot rostersData = FirebaseHelper.readData(rostersRef);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		//Convert rosterIdMap into a HashMap
		List<String> rosterIdMap = (ArrayList<String>) rostersData.getValue();

		//Check if user has any events
		if (rosterIdMap == null) {

			return ResponseList.RESPONSE_NO_EVENTS;
		//If they do have events...
		} else {
			//Create a new HashMap with the data inside it
			//Map<String, Object> realrosterIdMap = FirebaseHelper.convertToObjectMap(rosterIdMap, Object.class);
			rosterIds.addAll(rosterIdMap);
		}

		//Return success
		response = ResponseList.RESPONSE_SUCCESS;

		//Add event IDs to the response
		response.setExtra(rosterIds);

		return response;
	}
	
}