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

public class UserContactsLogic {

	public static BoardemResponse getUserContacts(String uid)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase contactsRef = rootRef.child("users").child(UserLogic.getStringNameFromId(uid)).child("contacts");

		ArrayList<String> contactIds = new ArrayList<String>();
		
		DataSnapshot contactData = FirebaseHelper.readData(contactsRef);
		
		if (contactData == null) {
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, HashMap> contactIdMap = (Map<String, HashMap>) contactData.getValue();

		if (contactIdMap != null) {
			Map<String, Object> realContactIdMap = FirebaseHelper.convertToObjectMap(contactIdMap, Object.class);
			contactIds.addAll(realContactIdMap.keySet());
		}

		response = ResponseList.RESPONSE_SUCCESS.clone();

		//Everything works now!
		response.setExtra(contactIds);
		return response;
		
	}

	public static BoardemResponse addUserContact(String user_id, String friend_id)
	{
		BoardemResponse response = null;

		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		
		DataSnapshot friendName = UserLogic.getNameFromId(friend_id);
		@SuppressWarnings("unchecked")
		Map<String,String> fName = (Map<String,String>)friendName.getValue();
		
		if (fName == null || fName.get("username") == null){
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		
		DataSnapshot userName = UserLogic.getNameFromId(user_id);
		@SuppressWarnings("unchecked")
		Map<String,String> name = (Map<String,String>)userName.getValue();
		
		if (name == null || name.get("username") == null){
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		
		Firebase contactsRef = rootRef.child("users").child(name.get("username")).child("contacts");

		DataSnapshot contactData = FirebaseHelper.readData(contactsRef);

		@SuppressWarnings({ "unchecked" })
		Map<String, Object> contactIdMap = (Map<String, Object>) contactData.getValue();

		if (contactIdMap != null && contactIdMap.containsKey(friend_id)) {
			//Definitely wrong response, because the friend_id for sure exists
			return ResponseList.RESPONSE_USER_IN_CONTACTS;
		}

		//Add friend_id to user
		Map<String,String> friend = new HashMap<String,String>();
		friend.put(friend_id, fName.get("username"));
		FirebaseHelper.writeData(contactsRef, friend);
		
		response = new BoardemResponse(200,"Successfully added contact.");
		response.setExtra(friend);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static BoardemResponse removeUserContact(String user_id, String friend_id)
	{
		BoardemResponse response = null;
		
		DataSnapshot usernameRaw = UserLogic.getNameFromId(user_id);	
		Map<String,String> username = (Map<String,String>)usernameRaw.getValue();
		
		if (username == null || username.get("username") == null){
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		
		String name = username.get("username");
		Firebase ref = new Firebase(BoardemApplication.FIREBASE_URL).child("users").child(name).child("contacts").child(friend_id);
		
		FirebaseHelper.removeData(ref);		
		
		response = new BoardemResponse(200,"Successfully removed user.");
		return response;
	}

}
