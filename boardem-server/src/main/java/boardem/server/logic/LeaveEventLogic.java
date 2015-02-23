package boardem.server.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * The LeaveEventLogic class contains logic to remove a user from an event
 */
public class LeaveEventLogic
{
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
}
