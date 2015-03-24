package boardem.server.logic;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for getting the comments for an event
 */
public class GetCommentsLogic
{
	public static BoardemResponse getComments(String eventId)
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
			Event event = Event.getEventFromSnapshot(snap);
			
			response = ResponseList.RESPONSE_SUCCESS.clone();
			response.setExtra(event.getComments());
		}
		
		return response;
	}
}
