package boardem.server.logic;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Conversation;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for getting a conversation 
 */
public class GetMessagesLogic
{
	public static BoardemResponse getMessages(String mid)
	{
		//Get the conversation from Firebase
		Firebase msgRef = new Firebase("https://boardem.firebaseio.com/messages/" + mid);
		DataSnapshot snap = FirebaseHelper.readData(msgRef);
		Conversation convo = Conversation.getConversationFromSnapshot(snap);
		
		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();
		response.setExtra(convo);
		return response;
	}
}
