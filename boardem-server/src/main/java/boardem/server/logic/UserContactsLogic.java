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

public class UserContactsLogic {

	public static BoardemResponse getUserContacts(String uid)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase contactsRef = rootRef.child("users").child(uid);

		ArrayList<String> contactIds = new ArrayList<String>();
		
		DataSnapshot contactData = FirebaseHelper.readData(contactsRef);
		
		if (contactData == null) {
			return ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}

		Map<String, HashMap> contactIdMap = (Map<String, HashMap>) contactData.getValue();

		if (contactIdMap != null) {
			Map<String, Object> realContactIdMap = FirebaseHelper.convertToObjectMap(contactIdMap, Object.class);
			contactIds.addAll(realContactIdMap.keySet());
		}


		response = ResponseList.RESPONSE_SUCCESS;

		//change once everything works
		response.setExtra(null);
		return response;
		
	}

}
