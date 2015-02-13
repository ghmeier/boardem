package boardem.server.logic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

public class CreateEventLogic
{
	public BoardemResponse createEvent(Event event)
	{
		//Give the event a unique id
		event.setId(RandomStringUtils.randomAlphanumeric(20));
		
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase eventsRef = rootRef.child("events");
		
		DataSnapshot eventData = FirebaseHelper.readData(eventsRef);
		
		@SuppressWarnings({ "unchecked", "rawtypes"})
		Map<String, HashMap> dataMap = (Map<String, HashMap>) eventData.getValue();
		
		if(dataMap == null)
		{
			//No events are in the database, create one
			Firebase newEventRef = eventsRef.child(event.getId());
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("event_id", event.getId());
			data.put("lat", event.getLatitude());
			data.put("lng", event.getLongitude());
			data.put("date", event.getDate());
			data.put("owner", event.getOwner());
			data.put("participants", event.getParticipants());
			data.put("games", event.getGames());
			
			FirebaseHelper.writeData(newEventRef, data);
		}
		else
		{
			Map<String, Event> events = FirebaseHelper.convertToObjectMap(dataMap, Event.class);
			
			//Check if the event ID is already in use and change it if necessary
			while(events.containsKey(event.getId()))
			{
				event.setId(RandomStringUtils.randomAlphanumeric(20));
			}
			
			Firebase newEventRef = eventsRef.child(event.getId());
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("event_id", event.getId());
			data.put("lat", event.getLatitude());
			data.put("lng", event.getLongitude());
			data.put("date", event.getDate());
			data.put("owner", event.getOwner());
			data.put("participants", event.getParticipants());
			data.put("games", event.getGames());
			
			FirebaseHelper.writeData(newEventRef, data);
		}
		
		return ResponseList.RESPONSE_SUCCESS;
	}
}
