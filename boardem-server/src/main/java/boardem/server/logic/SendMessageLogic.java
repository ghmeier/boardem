package boardem.server.logic;

import java.util.Calendar;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Message;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for sending a message between users
 */
public class SendMessageLogic
{
	public static BoardemResponse sendMessage(String messageId, Message message)
	{
		BoardemResponse response = null;
		
		//Get the existing message thread
		Firebase msgRef = new Firebase("https://boardem.firebaseio.com/messages/" + messageId);
		DataSnapshot snap = FirebaseHelper.readData(msgRef);
		
		if(snap == null || snap.getValue() == null)
		{
			response = ResponseList.RESPONSE_CONVERSATION_DOES_NOT_EXIST;
		}
		else
		{
			message.setDateObject(Calendar.getInstance().getTime());
			
			//Write the message to Firebase
			msgRef.child("messages").push().setValue(message);
			
			response = ResponseList.RESPONSE_SUCCESS;
		}
		
		return response;
	}
}
