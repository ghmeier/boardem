package boardem.server.logic;

import java.util.HashMap;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import boardem.server.BoardemApplication;
import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;

public class UserLogic {

	public static BoardemResponse getAllUsers()
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase userRef = rootRef.child("users");
		
		DataSnapshot userData = FirebaseHelper.readData(userRef);
		
		if (userData == null){
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		
		//DataSnapshot userData = FirebaseHelper.readData(userRef.child(username.get("username")));

		response = new BoardemResponse();
		response.setExtra(userData.getValue());
		return response;
		
	}
	
	public static BoardemResponse getUserFromId(String uId)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase userRef = rootRef.child("users");
		Firebase idRef = rootRef.child("facebook_id").child(uId);
		
		DataSnapshot idData = FirebaseHelper.readData(idRef);
		
		Map<String,String> username = (Map<String,String>)idData.getValue();
		if (username == null){
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		
		DataSnapshot userData = FirebaseHelper.readData(userRef.child(username.get("username")));

		response = new BoardemResponse();
		response.setExtra(userData.getValue());
		return response;
		
	}

}
