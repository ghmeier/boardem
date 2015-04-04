package boardem.server.logic.event;

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
 * Contains logic to add a user to an event's participation list.
 */
public class JoinEventLogic
{
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
}
