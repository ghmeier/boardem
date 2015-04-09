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

public class UserBadgesLogic {

	/**
	* Gets all of the badges that a user has
	* @param uid User ID to get a list of all the badges from
	* @return All badges (by badge ID numbers) that the user has
 	*/
	public static BoardemResponse getUserBadges(String uid)
	{
		BoardemResponse response = null;
		
		//Point badgesRef to the "badges" table of the user
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase badgesRef = rootRef.child("users").child(UserLogic.getStringNameFromId(uid)).child("badges");

		//Create ArrayList used in line 42
		ArrayList<String> badgeIds = new ArrayList<String>();
		
		//Convert badgesRef into a DataSnapshot
		DataSnapshot badgesData = FirebaseHelper.readData(badgesRef);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		//Convert badgeIdMap into a HashMap
		Map<String, HashMap> badgeIdMap = (Map<String, HashMap>) badgesData.getValue();

		//Check if user has any badges
		if (badgeIdMap == null) {

			return ResponseList.RESPONSE_NO_BADGES;
		//If they do have badges...
		} else {
			//Create a new HashMap with the data inside it
			Map<String, Object> realBadgeIdMap = FirebaseHelper.convertToObjectMap(badgeIdMap, Object.class);
			badgeIds.addAll(realBadgeIdMap.keySet());
		}

		//Return success
		response = ResponseList.RESPONSE_SUCCESS.clone();

		//Add badge IDs to the response
		response.setExtra(badgeIds);

		return response;
		
	}

	/**
	* Adds a badge (specified by an ID) to a user
	* @param user_id The Facebook ID of the user to add the badge to
	* @param badge_id The ID of the badge to add to the user
	* @return RESPONSE_SUCCESS if completed without errors
	*/

	public static BoardemResponse addUserBadge(String user_id, String badge_id)
	{
		//Put username getting in front so we don't have to potentially do it twice
		String username = UserLogic.getStringNameFromId(user_id);

		//Point badgesRef to the "badges" table of username "user_id"
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase badgesRef = rootRef.child("users").child(username).child("badges");

		//Convert badgesRef to a DataSnapshot
		DataSnapshot badgesData = FirebaseHelper.readData(badgesRef);

		@SuppressWarnings({"rawtypes", "unchecked"})
		//Convert badgesData to a HashMap
		Map<String,Object> badgesMap = (Map<String,Object>) badgesData.getValue();

		//If the user doesn't have any badges
		if (badgesMap == null) {

			Map<String,String> initialVal = new HashMap<String,String>();

			//Add the first value
			initialVal.put(badge_id, "0");

			//Write it to the Firebase in the "badges" table
			FirebaseHelper.writeData(badgesRef, initialVal);

		//If the user already has this badge
		} else if (badgesMap.containsKey(badge_id)) {

			return ResponseList.RESPONSE_USER_HAS_BADGE;

		} else {


			Map<String,String> contactMap = new HashMap<String,String>();

			//To count the number of badges the user has already
			int size = badgesMap.size();

			contactMap.put(badge_id, Integer.toString(size));

			//Write the HashMap to Firebase under the "badges" table
			FirebaseHelper.writeData(badgesRef, contactMap);

		}

		return ResponseList.RESPONSE_SUCCESS;
	}
	
	/**
	* Removes a badge (specified by an ID) from a user if it exists
	* @param user_id The Facebook ID of the user to remove the badge from
	* @param badge_id The ID of the badge to remove from the user
	* @return RESPONSE_BADGE_DOES_NOT_EXIST if the user does not have that badge, RESPONSE_SUCCESS if the user did have that badge
	*/


	@SuppressWarnings("unchecked")
	// ^^^ Necessary anymore? I'm not using Eclipse
	public static BoardemResponse removeUserBadge(String user_id, String badge_id)
	{
		//Point badgesRef to the badge_id of user "user_id"
		Firebase badgesRef = new Firebase(BoardemApplication.FIREBASE_URL).child("users").child(UserLogic.getStringNameFromId(user_id)).child("badges").child(badge_id);

		//Convert to DataSnapshot
		DataSnapshot badgesData = FirebaseHelper.readData(badgesRef);

		@SuppressWarnings({"rawtypes", "unchecked"})
		//Convert to string (will only have one value, so no HashMap this time)
		String badgesExists = (String) badgesData.getValue();

		//If badgesExists = null, no badge with the given badge_id exists in that user's list of badges
		if (badgesExists == null) {

			return ResponseList.RESPONSE_BADGE_DOES_NOT_EXIST;

		} 

		//If the badge_id does exist in the user, remove it
		FirebaseHelper.removeData(badgesRef);

		return ResponseList.RESPONSE_SUCCESS;
	}

}
