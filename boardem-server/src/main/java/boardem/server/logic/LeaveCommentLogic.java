package boardem.server.logic;

import java.util.HashMap;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Comment;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for leaving a comment on an event
 */
public class LeaveCommentLogic
{
	public static BoardemResponse leaveComment(String eventId, Comment comment)
	{
		BoardemResponse response = null;
		
		Firebase eventRef = new Firebase("https://boardem.firebaseio.com/events/" + eventId);
		DataSnapshot snap = FirebaseHelper.readData(eventRef);
		
		//Check if the event exists
		if(snap == null || snap.getValue() == null)
		{
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else
		{
			//Get the event and store the comment in the comment list
			Event event = Event.getEventFromSnapshot(snap);
			event.getComments().add(comment);
			
			//Put the comment list in a map to write to Firebase
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("comments", event.getComments());
			
			//Write the data to Firebase
			FirebaseHelper.writeData(eventRef, dataMap);
			
			response = ResponseList.RESPONSE_SUCCESS;
		}
		
		return response;
	}
}
