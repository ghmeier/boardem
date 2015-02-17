package boardem.server.logic;

import java.util.HashMap;
import java.util.Map;

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
	public static BoardemResponse getEvent(String eventId)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase eventRef = rootRef.child("events");
		
		DataSnapshot snapshot = FirebaseHelper.readData(eventRef);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, HashMap> dataMap = (Map<String, HashMap>) snapshot.getValue();
		
		if(dataMap == null)
		{
			//No events
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else
		{
			Map<String, Event> eventMap = (Map<String, Event>) FirebaseHelper.convertToObjectMap(dataMap, Event.class);
			
			//Check if the event exists
			if(!eventMap.containsKey(eventId))
			{
				response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
			}
			else
			{
				response = ResponseList.RESPONSE_SUCCESS.clone();
				response.setExtra(eventMap.get(eventId));
			}
		}
		
		return response;
	}
}
