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
