package boardem.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import boardem.server.json.Event;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;

/**
 * Job to look through events and move finished events to a new table in Firebase
 */
public class UpdateExpiredEventsJob implements Job
{
	public UpdateExpiredEventsJob()
	{
		
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		GeoFire geofire = new GeoFire(rootRef.child("geofire"));
		Firebase eventRef = rootRef.child("events");
		Firebase expiredRef = rootRef.child("expired_events");
		
		//Get the events from Firebase
		DataSnapshot eventSnap = FirebaseHelper.readData(eventRef);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HashMap<String, Event> eventsMap = (HashMap<String, Event>) FirebaseHelper.convertToObjectMap((Map<String, HashMap>) eventSnap.getValue(), Event.class);
		
		Date today = Calendar.getInstance().getTime();
		
		//Iterate through each event
		Iterator<Event> iter = eventsMap.values().iterator();
		while(iter.hasNext())
		{
			Event event = iter.next();
			
			//Check if the event has expired
			if(event.getDateObject().before(today))
			{
				//Remove the event from the active events table
				eventRef.child(event.getId()).removeValue();
				
				//Add the event to the expired events table
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("event_id", event.getId());
				data.put("name", event.getName());
				data.put("lat", event.getLatitude());
				data.put("lng", event.getLongitude());
				data.put("date", event.getDate());
				data.put("owner", event.getOwner());
				data.put("participants", event.getParticipants());
				data.put("games", event.getGames());
				
				FirebaseHelper.writeData(expiredRef.child(event.getId()), data);
				
				//Remove the event from Geofire
				geofire.removeLocation(event.getId());
				
				//Update the users that were in the event
				List<String> participants = event.getParticipants();
				if(participants != null && !participants.isEmpty())
				{
					for(String userId : participants)
					{
						//Get the username of the user
						Firebase idRef = rootRef.child("facebook_id/" + userId);
						DataSnapshot idSnap = FirebaseHelper.readData(idRef);
						User user = User.getUserFromSnapshot(idSnap);
						
						//Get the users information from the main user table
						Firebase userRef = rootRef.child("users/" + user.getUsername());
						DataSnapshot userSnap = FirebaseHelper.readData(userRef);
						user = User.getUserFromSnapshot(userSnap);
						
						List<String> activeEvents = user.getEvents();
						List<String> finishedEvents = user.getCompletedEventList();
						activeEvents = activeEvents == null ? new ArrayList<String>() : activeEvents;
						finishedEvents = finishedEvents == null ? new ArrayList<String>() : finishedEvents;
						
						//Remove the event from active events and put it in finished events
						activeEvents.remove(event.getId());
						finishedEvents.add(event.getId());
						
						//Write the data to Firebase
						HashMap<String, Object> userData = new HashMap<String, Object>();
						userData.put("events", activeEvents);
						userData.put("completed_event_list", finishedEvents);
						
						FirebaseHelper.writeData(userRef, userData);
					}
				}
			}
		}
	}

}
