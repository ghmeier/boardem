package boardem.server.logic.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardem.server.BadgeActions;
import boardem.server.FirebaseHelper;
import boardem.server.json.Badge;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;
import boardem.server.json.User;
import boardem.server.logic.BadgeLogic;

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
			
			//Check if the user is already attending the event
			if(participants.contains(userId))
			{
				response = ResponseList.RESPONSE_USER_IN_EVENT;
			}
			//Check if the user owns the event
			else if(event.getOwner().equals(userId))
			{
				response = ResponseList.RESPONSE_USER_OWNS_EVENT;
			}
			else
			{
				//Add the user to the event's participant list
				participants.add(userId);
				
				//Add the event to the list of events the user is attending
				List<String> userEvents = user.getEvents();
				userEvents.add(eventId);
				
				//Write the data to Firebase
				Map<String, Object> eventData = new HashMap<String, Object>();
				eventData.put("participants", participants);
				FirebaseHelper.writeData(eventRef, eventData);
				
				Map<String, Object> userData = new HashMap<String, Object>();
				userData.put("events", userEvents);
				FirebaseHelper.writeData(userRef, userData);
				
				//Update the user's badge progress
				List<Badge> earnedBadges = BadgeLogic.updateBadge(userId, BadgeActions.ACTION_JOIN_EVENT);

				//Add any earned badges to the response
				response = ResponseList.RESPONSE_SUCCESS.clone();
				for(Badge b : earnedBadges)
				{
					response.addBadge(b);
				}
			}
		}

		return response;
	}
}
