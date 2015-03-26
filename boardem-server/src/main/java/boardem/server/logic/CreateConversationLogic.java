package boardem.server.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Conversation;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for creating a conversation. Randomly generates a unique message ID
 * and adds the message to the list of conversations each user is participating in
 */
public class CreateConversationLogic
{
	public static BoardemResponse createConversation(List<String> users)
	{		
		Firebase msgRef = new Firebase("https://boardem.firebaseio.com/messages");
		DataSnapshot snap = FirebaseHelper.readData(msgRef);

		//First, generate a unique ID
		//Get the list of existing message IDs
		String id = RandomStringUtils.randomAlphanumeric(20);

		//Check if there are converstations in Firebase
		if(snap.getValue() != null)
		{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Map<String, Conversation> messages = (Map<String, Conversation>) FirebaseHelper.convertToObjectMap((Map<String, HashMap>) snap.getValue(), Conversation.class);

			//Check if the conversation ID is already in use and change it if necessary
			while(messages.containsKey(id))
			{
				id = RandomStringUtils.randomAlphanumeric(20);
			}
		}

		//Write the conversation data to Firebase
		Firebase newMsgRef = msgRef.child(id);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		data.put("users", users);
		FirebaseHelper.writeData(newMsgRef, data);

		updateUserData(users, id);

		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();
		response.setExtra(id);
		return response;
	}

	/**
	 * Updates the messages list of each user in the conversation 
	 */
	private static void updateUserData(List<String> users, String mid)
	{
		for(String userId : users)
		{
			//Get the user's username from the facebook_id table
			Firebase idRef = new Firebase("https://boardem.firebaseio.com/facebook_id/" + userId);
			DataSnapshot idSnap = FirebaseHelper.readData(idRef);
			User user = User.getUserFromSnapshot(idSnap);
			
			//Get the user from Firebase
			Firebase userRef = new Firebase("https://boardem.firebaseio.com/users/" + user.getUsername());
			DataSnapshot snap = FirebaseHelper.readData(userRef);
			user = User.getUserFromSnapshot(snap);
			user.getMessages().add(mid);

			//Write the new data to Firebase
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("messages", user.getMessages());
			FirebaseHelper.writeData(userRef, data);
		}
	}
}
