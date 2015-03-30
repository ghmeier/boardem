package boardem.server.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.RandomStringUtils;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Comment;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

/**
 * Contains logic for endpoints in EventResource
 */
public class EventLogic
{
	/**
	 * Creates a new event
	 * @param event JSON data for the event to be created
	 */
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

			createEventUpdateUser(event.getOwner(), event.getId());
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

			createEventUpdateUser(event.getOwner(), event.getId());
		}

		return ResponseList.RESPONSE_SUCCESS;
	}

	/**
	 * Updates the users gamification attributes for creating an event.
	 * Used in event creation.
	 * @param userId ID of user to update
	 */
	private static void createEventUpdateUser(String userId, String eventId)
	{
		//Get the username from the user ID
		Firebase idRef = new Firebase("https://boardem.firebaseio.com/facebook_id/" + userId);
		DataSnapshot idSnap = FirebaseHelper.readData(idRef);

		//Get the user and update their information from the new event
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Firebase userRef = new Firebase("https://boardem.firebaseio.com/users/" + ((Map<String, HashMap>) idSnap.getValue()).get("username"));
		DataSnapshot userSnap = FirebaseHelper.readData(userRef);
		User user = User.getUserFromSnapshot(userSnap);
		user.incrementEventsCreated();
		user.getEvents().add(eventId);

		//Write the data to Firebase
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("events_created", user.getEventsCreated());
		data.put("events", user.getEvents());
		FirebaseHelper.writeData(userRef, data);
	}
	
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
		if(snap == null || snap.getValue() == null)
		{
			response.setExtra(null);
		}
		else
		{
			List<Comment> commentList = new ArrayList<Comment>();
			commentList.addAll(FirebaseHelper.convertToObjectMap((HashMap<String, HashMap>) snap.getValue(), Comment.class).values());
			Collections.sort(commentList);
			response.setExtra(commentList);
		}
		
		return response;
	}
	
	/**
	 * Gets information about an event
	 * @param eventId ID of event to get information about
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
	
	/**
	 * Marks that a user is joining an event
	 * @param eventId ID of the event
	 * @param userId ID of the user joining
	 */
	public static BoardemResponse joinEvent(String eventId, String userId)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase idRef = rootRef.child("facebook_id");
		Firebase eventsRef = rootRef.child("events");
		
		DataSnapshot eventData = FirebaseHelper.readData(eventsRef);
		DataSnapshot idData = FirebaseHelper.readData(idRef);
				
		@SuppressWarnings({"unchecked", "rawtypes"})
		Map<String, HashMap> eventDataMap = (Map<String, HashMap>) eventData.getValue();
		@SuppressWarnings({"unchecked", "rawtypes"})
		Map<String, HashMap> idDataMap = (Map<String, HashMap>) idData.getValue();

		//Check if the event exists
		if(eventDataMap == null)
		{
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else if(idDataMap == null)
		{
			System.out.println("Here");
			response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		else
		{
			Map<String, Event> eventMap = FirebaseHelper.convertToObjectMap(eventDataMap, Event.class);
			
			//Make sure the event exists
			if(!eventMap.containsKey(eventId))
			{
				response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
			}
			else
			{
				Map<String, User> idMap = FirebaseHelper.convertToObjectMap(idDataMap, User.class);
								
				//Make sure the user exists
				if(!idMap.containsKey(userId))
				{
					response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
				}
				else
				{
					Firebase joinEventRef = eventsRef.child(eventId);
					
					//Get the event from the list of events
					Event toUpdate = eventMap.get(eventId);
					
					//Check if the user is already in the event
					if(toUpdate.getParticipants().contains(userId))
					{
						response = ResponseList.RESPONSE_USER_IN_EVENT;
					}
					//Check if the user is the owner of the event
					else if(toUpdate.getOwner().equals(userId))
					{
						response = ResponseList.RESPONSE_USER_OWNS_EVENT;
					}
					else
					{
						//Write the data to Firebase
						Map<String, List<String>> data = new HashMap<String, List<String>>();
						toUpdate.getParticipants().add(userId);
						data.put("participants", toUpdate.getParticipants());
					
						//Add the event to the list of events the user is in
						Firebase userRef = rootRef.child("users/" + idMap.get(userId).getUsername());
						DataSnapshot userSnapshot = FirebaseHelper.readData(userRef);
						User user = User.getUserFromSnapshot(userSnapshot);
						user.getEvents().add(toUpdate.getId());
						
						Map<String, Object> userData = new HashMap<String, Object>();
						userData.put("events", user.getEvents());
						
						FirebaseHelper.writeData(joinEventRef, data);
						FirebaseHelper.writeData(userRef, userData);
						
						response = ResponseList.RESPONSE_SUCCESS;
					}
				}
			}
		}
		
		return response;
	}
	
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

	/**
	 * Removes a user from an event
	 * @param eventId ID of the event
	 * @param userId ID of the user
	 */
	public static BoardemResponse leaveEvent(String eventId, String userId)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase idRef = rootRef.child("facebook_id");
		Firebase eventsRef = rootRef.child("events");
		
		DataSnapshot eventData = FirebaseHelper.readData(eventsRef);
		DataSnapshot idData = FirebaseHelper.readData(idRef);
				
		@SuppressWarnings({"unchecked", "rawtypes"})
		Map<String, HashMap> eventDataMap = (Map<String, HashMap>) eventData.getValue();
		@SuppressWarnings({"unchecked", "rawtypes"})
		Map<String, HashMap> idDataMap = (Map<String, HashMap>) idData.getValue();

		//Check if the event exists
		if(eventDataMap == null)
		{
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else if(idDataMap == null)
		{
			response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		else
		{
			//Root of the problem
			//Converting from the array in FIreba
			Map<String, Event> eventMap = FirebaseHelper.convertToObjectMap(eventDataMap, Event.class);
			
			//Make sure the event exists
			if(!eventMap.containsKey(eventId))
			{
				response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
			}
			else
			{
				Map<String, User> idMap = FirebaseHelper.convertToObjectMap(idDataMap, User.class);
								
				//Make sure the user exists
				if(!idMap.containsKey(userId))
				{
					response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
				}
				else
				{
					//Get the event from the list of events
					Event toUpdate = eventMap.get(eventId);

					//Check if the user is in the event
					if(!toUpdate.getParticipants().contains(userId))
					{
						response = ResponseList.RESPONSE_USER_NOT_IN_EVENT;
					}
					else
					{
						Firebase ref = eventsRef.child(eventId);
						Firebase userRef = rootRef.child("users/" + idMap.get(userId).getUsername());

						//Have to get user first to avoid null pointer
						DataSnapshot userSnapshot = FirebaseHelper.readData(userRef);
						User user = User.getUserFromSnapshot(userSnapshot);
						
						final CountDownLatch removeLatch = new CountDownLatch(1);
						
						//Remove the existing list
						ref.child("participants").removeValue(new Firebase.CompletionListener() 
						{	
							@Override
							public void onComplete(FirebaseError arg0, Firebase arg1) 
							{
								//Notify that the removal was complete
								removeLatch.countDown();
							}
						});
						
						//Wait for the removal to complete
						try
						{
							removeLatch.await();
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
						
						final CountDownLatch userRemoveLatch = new CountDownLatch(1);
						userRef.child("events").removeValue(new Firebase.CompletionListener()
						{
							@Override
							public void onComplete(FirebaseError arg0, Firebase arg1)
							{
								userRemoveLatch.countDown();
							}
						});
						
						try
						{
							userRemoveLatch.await();
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
						 
						//Write the data to Firebase
						Map<String, List<String>> data = new HashMap<String, List<String>>();
						Map<String, List<String>> userData = new HashMap<String, List<String>>();
						
						toUpdate.getParticipants().remove(userId);
						data.put("participants", toUpdate.getParticipants());
					
						user.getEvents().remove(toUpdate.getId());
						userData.put("events", user.getEvents());
						
						FirebaseHelper.writeData(ref, data);
						FirebaseHelper.writeData(userRef, userData);
						
						response = ResponseList.RESPONSE_SUCCESS;
					}
				}
			}
		}
		
		return response;
	}
	
	/**
	 * Updates information about an event.
	 * <p>
	 * If an attribute is not going to be updated, have the parameter be null.
	 * @param eventId ID of the event to update. <b>Cannot be null</b>
	 * @param name New name of the event
	 * @param lat New latitude of the event
	 * @param lng New longitude of the event
	 * @param date New date for the event
	 * @param owner New owner of the event
	 * @param games New list of games for the event
	 */
	public static BoardemResponse updateEvent(String eventId,
			String name, Double lat, Double lng,
			String date, String owner,
			List<String> games)
	{
		BoardemResponse response = null;

		Firebase ref = new Firebase("https://boardem.firebaseio.com/events/" + eventId);
		DataSnapshot snapshot = FirebaseHelper.readData(ref);

		//Check if the event exists
		if(snapshot.getValue() == null)
		{
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else
		{
			Event event = Event.getEventFromSnapshot(snapshot);

			//Update the information if it is present
			if(name != null)
			{
				event.setName(name);
			}
			if(lat != null)
			{
				event.setLatitude(lat);
			}
			if(lng != null)
			{
				event.setLongitude(lng);
			}
			if(date != null)
			{
				event.setDate(date);
			}
			if(owner != null)
			{
				event.setOwner(owner);
			}
			if(games != null)
			{
				event.setGames(games);
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("name", event.getName());
			data.put("lat", event.getLatitude());
			data.put("lng", event.getLongitude());
			data.put("date", event.getDate());
			data.put("owner", event.getOwner());
			data.put("games", event.getGames());
			
			FirebaseHelper.writeData(ref, data);
			GeoFire geofire = new GeoFire(new Firebase("https://boardem.firebaseio.com/geofire"));
			
			//Write the updated location data
			GeoLocation loc = new GeoLocation(event.getLatitude(), event.getLongitude());
			FirebaseHelper.writeLocation(geofire, loc, eventId);
			
			response = ResponseList.RESPONSE_SUCCESS;
		}

		return response;
	}
}
