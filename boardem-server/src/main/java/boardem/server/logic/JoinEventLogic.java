package boardem.server.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * The JoinEventLogic class contains the logic to add a user to an event
 */
public class JoinEventLogic
{
	public static BoardemResponse joinEvent(String eventId, String userId)
	{
		BoardemResponse response = null;
		
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase usersRef = rootRef.child("users");
		Firebase eventsRef = rootRef.child("events");
		
		DataSnapshot eventData = FirebaseHelper.readData(eventsRef);
		DataSnapshot userData = FirebaseHelper.readData(usersRef);
				
		@SuppressWarnings({"unchecked", "rawtypes"})
		Map<String, HashMap> eventDataMap = (Map<String, HashMap>) eventData.getValue();
		@SuppressWarnings({"unchecked", "rawtypes"})
		Map<String, HashMap> userDataMap = (Map<String, HashMap>) userData.getValue();

		//Check if the event exists
		if(eventDataMap == null)
		{
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else if(userDataMap == null)
		{
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
				Map<String, User> userMap = FirebaseHelper.convertToObjectMap(userDataMap, User.class);
								
				//Make sure the user exists
				if(!userMap.containsKey(userId))
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
					else
					{
						//Write the data to Firebase
						Map<String, List<String>> data = new HashMap<String, List<String>>();
						data.put("participants", toUpdate.getParticipants());
					
						FirebaseHelper.writeData(joinEventRef, data);
						
						response = ResponseList.RESPONSE_SUCCESS;
					}
				}
			}
		}
		
		return response;
	}
}
