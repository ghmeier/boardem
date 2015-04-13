package boardem.server.logic;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for getting the expired events that a user participated in 
 */
public class UserCompletedEventsLogic
{
	public static BoardemResponse getCompletedEvents(String userId)
	{
		//Get the user. Find their username first
		Firebase idRef = new Firebase("https://boardem.firebaseio.com/facebook_id/" + userId);
		DataSnapshot idSnap = FirebaseHelper.readData(idRef);
		User user = User.getUserFromSnapshot(idSnap);
		
		Firebase userRef = new Firebase("https://boardem.firebaseio.com/users/" + user.getUsername());
		DataSnapshot userSnap = FirebaseHelper.readData(userRef);
		user = User.getUserFromSnapshot(userSnap);
		
		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();
		response.setExtra(user.getCompletedEventList());
		return response;
	}
}
