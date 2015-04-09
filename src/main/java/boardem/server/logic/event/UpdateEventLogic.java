package boardem.server.logic.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

/**
 * Contains logic for updating an event's attributes.
 */
public class UpdateEventLogic
{

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
