package boardem.server.logic.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Comment;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for getting all comments that have been posted on an event.
 */
public class GetCommentsLogic
{
	/**
	 * Gets the comments for an event
	 * @param eventId ID of the event
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BoardemResponse getComments(String eventId)
	{
		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();

		Firebase eventRef = new Firebase("https://boardem.firebaseio.com/comments/" + eventId + "/comments");
		DataSnapshot snap = FirebaseHelper.readData(eventRef);

		//Check if the event exists
		if(snap.getValue() == null)
		{
			response.setExtra(null);
		}
		else
		{
			//Get the list of comments from the DataSnapshot
			List<Comment> commentList = new ArrayList<Comment>();
			commentList.addAll(FirebaseHelper.convertToObjectMap((HashMap<String, HashMap>) snap.getValue(), Comment.class).values());
			//Sort the comments by date and time posted
			Collections.sort(commentList);
			response.setExtra(commentList);
		}

		return response;
	}
}
