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

public class UserMessagesLogic {

	/**
	* Lists all data for messages of a specified user
	* @param user_id User ID to get a list of all the messages from
	* @return All message data for user as a HashMap
 	*/
	public static BoardemResponse listUserMessages(String user_id)
	{
		BoardemResponse response = null;

		

		return response;
	}

}