package boardem.server.logic.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for getting the list of events.
 */
public class GetEventsLogic
{
	/**
	 * Gets the list of event IDs for active events 
	 */
	public static BoardemResponse getEvents()
	{
		BoardemResponse response = null;
		List<String> eventIds = new ArrayList<String>();

		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase eventsRef = rootRef.child("events");
		DataSnapshot eventsSnap = FirebaseHelper.readData(eventsRef);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, HashMap> dataMap = (Map<String, HashMap>) eventsSnap.getValue();

		//If the data map is null it will return an empty list of events
		if(dataMap != null)
		{
			Map<String, Object> eventsMap = FirebaseHelper.convertToObjectMap(dataMap, Object.class);
			eventIds.addAll(eventsMap.keySet());
		}

		//Add the list of event IDs to the extra part of the response
		response = ResponseList.RESPONSE_SUCCESS.clone();
		response.setExtra(eventIds);

		return response;
	}
}
