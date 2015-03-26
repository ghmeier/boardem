package boardem.server.logic;

import java.util.Calendar;

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
			comment.setDateObject(Calendar.getInstance().getTime());
			
			//Write the comment to Firebase
			eventRef.child("comments").push().setValue(comment);
			
			response = ResponseList.RESPONSE_SUCCESS;
		}
		
		return response;
	}
}
