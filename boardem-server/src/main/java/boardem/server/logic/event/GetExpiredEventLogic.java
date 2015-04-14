package boardem.server.logic.event;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for getting information about an expired event
 */
public class GetExpiredEventLogic
{
	public static BoardemResponse getExpiredEvent(String eventId)
	{
		Firebase eventRef = new Firebase("https://boardem.firebaseio.com/expired_events/" + eventId);
		DataSnapshot snap = FirebaseHelper.readData(eventRef);
		
		BoardemResponse response = null;
		
		//Check if the event exists
		if(snap.getValue() == null)
		{
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else
		{
			Event event = Event.getEventFromSnapshot(snap);
			response = ResponseList.RESPONSE_SUCCESS.clone();
			response.setExtra(event);
		}
		
		return response;
	}
}
