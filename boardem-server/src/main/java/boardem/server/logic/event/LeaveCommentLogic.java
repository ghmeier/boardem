package boardem.server.logic.event;

import java.util.Calendar;

import boardem.server.json.BoardemResponse;
import boardem.server.json.Comment;
import boardem.server.json.ResponseList;

import com.firebase.client.Firebase;

/**
 * Contains logic for posting a comment to an event.
 */
public class LeaveCommentLogic
{
	/**
	 * Leaves a comment on an event
	 * @param eventId ID of the event to leave a comment on
	 * @param comment JSON data for the comment to be left
	 */
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
