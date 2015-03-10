package boardem.server.logic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

public class CreateEventLogic
{
	public static BoardemResponse createEvent(Event event)
	{
		//Give the event a unique id
		event.setId(RandomStringUtils.randomAlphanumeric(20));
		
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase eventsRef = rootRef.child("events");
		
		GeoFire geoFire = new GeoFire(rootRef.child("geofire"));
		FirebaseHelper.writeLocation(geoFire, new GeoLocation(event.getLatitude(), event.getLongitude()), event.getId());
		
		DataSnapshot eventData = FirebaseHelper.readData(eventsRef);
		
		@SuppressWarnings({ "unchecked", "rawtypes"})
		Map<String, HashMap> dataMap = (Map<String, HashMap>) eventData.getValue();

		if(dataMap == null)
		{
			//No events are in the database, create one
			Firebase newEventRef = eventsRef.child(event.getId());
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("event_id", event.getId());
			data.put("name", event.getName());
			data.put("lat", event.getLatitude());
			data.put("lng", event.getLongitude());
			data.put("date", event.getDate());
			data.put("owner", event.getOwner());
			data.put("participants", event.getParticipants());
			data.put("games", event.getGames());
			
			FirebaseHelper.writeData(newEventRef, data);
			
			updateUser(event.getOwner());
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
			data.put("name", event.getName());
			data.put("lat", event.getLatitude());
			data.put("lng", event.getLongitude());
			data.put("date", event.getDate());
			data.put("owner", event.getOwner());
			data.put("participants", event.getParticipants());
			data.put("games", event.getGames());
			
			FirebaseHelper.writeData(newEventRef, data);
			
			updateUser(event.getOwner());
		}
		
		return ResponseList.RESPONSE_SUCCESS;
	}
	
	/**
	 * Updates the users gamification attributes for creating an event
	 * @param userId ID of user to update
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void updateUser(String userId)
	{
		//Get the username from the user ID
		Firebase idRef = new Firebase("https://boardem.firebaseio.com/facebook_id/" + userId);
		DataSnapshot idSnap = FirebaseHelper.readData(idRef);
		
		//Get the user and increment the number of games created
		Firebase userRef = new Firebase("https://boardem.firebaseio.com/users/" + ((Map<String, HashMap>) idSnap.getValue()).get("username"));
		DataSnapshot userSnap = FirebaseHelper.readData(userRef);
		User user = User.getUserFromSnapshot(userSnap);
		user.incrementEventsCreated();
		
		//Write the data to Firebase
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("events_created", user.getEventsCreated());
		FirebaseHelper.writeData(userRef, data);
	}
}
