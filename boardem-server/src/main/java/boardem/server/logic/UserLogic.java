package boardem.server.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import boardem.server.BoardemApplication;
import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;

public class UserLogic {

	public static BoardemResponse getAllUsers()
	{

		//Set up new BordemResponse to be returned
		BoardemResponse response = null;
		
		//Reference the rood Firebase and its child "users"
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase userRef = rootRef.child("facebook_id");

		//Create an ArrayList to store the userIds in once we find them
		ArrayList<String> userIds = new ArrayList<String>();
		
		//Read the data from the "users" table
		DataSnapshot userData = FirebaseHelper.readData(userRef);
		
		//If no users exist in the database, return a response
		if (userData == null){
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		} else {

			//Create a new map to store the data from user so we can filter out the usernames later
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Map<String, HashMap> newPost = (Map<String, HashMap>) userData.getValue();

			//Filter out usernames into a new map
			Map<String, Object> usersMap = FirebaseHelper.convertToObjectMap(newPost, Object.class);

			//Add all keys (which in this case are usernames) to the userIds ArrayList
			userIds.addAll(usersMap.keySet());
		}

		//Return a successful response
		response = ResponseList.RESPONSE_SUCCESS.clone();

		//Return the list of usernames
		response.setExtra(userIds);

		//Return the response
		return response;
		
	}
	
	public static BoardemResponse getUserFromId(String uId)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase userRef = rootRef.child("users");
		
		DataSnapshot idData = getNameFromId(uId);
		
		@SuppressWarnings("unchecked")
		Map<String,String> username = (Map<String,String>)idData.getValue();
		if (username == null){
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		
		DataSnapshot userData = FirebaseHelper.readData(userRef.child(username.get("username")));

		response = new BoardemResponse();
		response.setExtra(userData.getValue());
		return response;
		
	}
	
	public static DataSnapshot getNameFromId(String uId){
		Firebase ref = new Firebase(BoardemApplication.FIREBASE_URL).child("facebook_id").child(uId);
		
		return FirebaseHelper.readData(ref);
	}

	/**
	* Gets the username of a user from a facebook id
	* @param uid Facebook ID of user
	* @return Username of user
	*/
	public static String getStringNameFromId (String uid) {
		//Create new firebase that points to the child
		Firebase ref = new Firebase("https://boardem.firebaseio.com").child("facebook_id").child(uid);

		//Convert the "username" table to a DataSnapshot
		DataSnapshot name = FirebaseHelper.readData(ref);

		@SuppressWarnings("unchecked")
		//Convert name into a HashMap
		Map<String, String> nameTable = (Map<String, String>) name.getValue();

		//Find and return the username of a user as a string
		return nameTable.get("username");
	}

}