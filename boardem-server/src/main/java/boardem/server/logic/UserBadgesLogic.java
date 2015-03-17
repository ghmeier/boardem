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

	public static BoardemResponse getUserBadges(String uid)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase badgesRef = rootRef.child("users").child(UserLogic.getStringNameFromId(uid)).child("badges");

		ArrayList<String> badgeIds = new ArrayList<String>();
		
		DataSnapshot badgesData = FirebaseHelper.readData(badgesRef);
		
		if (badgesData == null) {
			return ResponseList.RESPONSE_NO_BADGES;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, HashMap> badgeIdMap = (Map<String, HashMap>) badgesData.getValue();

		if (badgeIdMap != null) {
			Map<String, Object> realBadgeIdMap = FirebaseHelper.convertToObjectMap(badgeIdMap, Object.class);
			badgeIds.addAll(realBadgeIdMap.keySet());
		}


		response = ResponseList.RESPONSE_SUCCESS;

		//change once everything works
		response.setExtra(badgeIds);
		return response;
		
	}

	public static BoardemResponse addUserBadge(String user_id, String badge_id)
	{
		BoardemResponse response = null;

		String username = UserLogic.getStringNameFromId(user_id);

		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase badgesRef = rootRef.child("users").child(username).child("badges");

		DataSnapshot badgesData = FirebaseHelper.readData(badgesRef);

		@SuppressWarnings({"rawtypes", "unchecked"})
		Map<String,Object> badgesMap = (Map<String,Object>) badgesData.getValue();

		if (badgesMap == null) {
			//Firebase initialRef = rootRef.child("users").child(username);

			//badgesData = FirebaseHelper.readData(initialRef);

			Map<String,String> initialVal = new HashMap<String,String>();

			initialVal.put(badge_id, "0");

			FirebaseHelper.writeData(badgesRef, initialVal);
		} else {


			Map<String,String> contactMap = new HashMap<String,String>();

			int size = badgesMap.size();

			contactMap.put(badge_id, Integer.toString(size));

			FirebaseHelper.writeData(badgesRef, contactMap);

		}

		response = ResponseList.RESPONSE_SUCCESS;

		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static BoardemResponse removeUserBadge(String user_id, String friend_id)
	{
		BoardemResponse response = null;
	
		return response;
	}

}
