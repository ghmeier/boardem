package boardem.server.logic;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic to get information about events
 */
public class GetEventLogic
{
	/**
	 * Gets information about an event
	 * @param eventId ID of event to get information about
	 * @return BoardemResponse containing event information, if the event exists
	 */
	public static BoardemResponse getEvent(String eventId)
	{
		BoardemResponse response = null;
		
		Firebase fb = new Firebase("https://boardem.firebaseio.com/events/" + eventId);
		DataSnapshot snap = FirebaseHelper.readData(fb);
		
		if(snap == null || snap.getValue() == null)
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
