package boardem.server.logic;

import java.util.Calendar;

import boardem.server.json.BoardemResponse;
import boardem.server.json.Comment;
import boardem.server.json.ResponseList;

import com.firebase.client.Firebase;

/**
 * Contains logic for leaving a comment on an event
 */
public class LeaveCommentLogic
{
	public static BoardemResponse leaveComment(String eventId, Comment comment)
	{
		Firebase eventRef = new Firebase("https://boardem.firebaseio.com/comments/" + eventId + "/comments");
		
		//Set the time the comment was sent
		comment.setDateObject(Calendar.getInstance().getTime());

		//Write the comment to Firebase
		eventRef.push().setValue(comment);

		return ResponseList.RESPONSE_SUCCESS;
	}
}
