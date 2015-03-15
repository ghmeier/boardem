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
		Firebase badgesRef = rootRef.child("users").child(uid).child("badges");

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
		response.setExtra(null);
		return response;
		
	}

	public static BoardemResponse addUserBadge(String user_id, String friend_id)
	{
		BoardemResponse response = null;

		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static BoardemResponse removeUserBadge(String user_id, String friend_id)
	{
		BoardemResponse response = null;
	
		return response;
	}

}
