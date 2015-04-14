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
 * Contains logic to remove a user from an event's participant list.
 */
public class LeaveEventLogic
{
	/**
	 * Removes a user from an event
	 * @param eventId ID of the event
	 * @param userId ID of the user
	 */
	public static BoardemResponse leaveEvent(String eventId, String userId)
	{
		BoardemResponse response = null;

		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase idRef = rootRef.child("facebook_id/" + userId);
		Firebase eventRef = rootRef.child("events/" + eventId);
		DataSnapshot eventSnap = FirebaseHelper.readData(eventRef);
		
		//Get the user. Find their username first
		DataSnapshot idSnap = FirebaseHelper.readData(idRef);
		User user = User.getUserFromSnapshot(idSnap);
		
		Firebase userRef = rootRef.child("users/" + user.getUsername());
		DataSnapshot userSnap = FirebaseHelper.readData(userRef);
		user = User.getUserFromSnapshot(userSnap);
		
		//Check if the event exists
		if(eventSnap.getValue() == null)
		{
			response = ResponseList.RESPONSE_EVENT_DOES_NOT_EXIST;
		}
		else
		{
			Event event = Event.getEventFromSnapshot(eventSnap);
			List<String> participants = event.getParticipants();
			List<String> userEvents = user.getEvents();
			
			//Make sure the user is actually attending the event
			if(!participants.contains(userId))
			{
				response = ResponseList.RESPONSE_USER_NOT_IN_EVENT;
			}
			else
			{
				participants.remove(userId);
				userEvents.remove(eventId);
				
				//Remove the old data
				FirebaseHelper.removeData(eventRef.child("participants"));
				FirebaseHelper.removeData(userRef.child("events"));
				
				//Write the new data
				Map<String, Object> eventData = new HashMap<String, Object>();
				eventData.put("participants", participants);
				FirebaseHelper.writeData(eventRef, eventData);
				
				Map<String, Object> userData = new HashMap<String, Object>();
				userData.put("events", userEvents);
				FirebaseHelper.writeData(userRef, userData);
				
				response = ResponseList.RESPONSE_SUCCESS;
			}
		}

		return response;
	}
}
