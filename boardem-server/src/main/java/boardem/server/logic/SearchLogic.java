package boardem.server.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.google.common.base.Optional;

/**
 * Contains logic for searching for games
 */
public class SearchLogic
{
	/**
	 * Searches for games
	 * @param userLat Latitude of the user
	 * @param userLng Longitude of the user
	 * @param userId 
	 * @param dist Max distance an event can be from the user
	 * @return Response containing list of events IDs that meet the search criteria
	 */
	@SuppressWarnings("deprecation")
	public static BoardemResponse searchEvents(double userLat, double userLng, String userId, 
			Optional<Double> dist, Optional<String> dateString)
	{
		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();

		//Will store the IDs of matching events
		List<String> eventIds = new ArrayList<String>();

		//Get the data from Firebase
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase eventsRef = rootRef.child("events");

		DataSnapshot eventsSnap = FirebaseHelper.readData(eventsRef);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, HashMap> dataMap = (Map<String, HashMap>) eventsSnap.getValue();

		Map<String, Event> eventsMap = null;

		//If the data map is null it will return an empty list of events
		if(dataMap != null)
		{
			eventsMap = FirebaseHelper.convertToObjectMap(dataMap, Event.class);

			//Search through the events
			Iterator<String> iterator = eventsMap.keySet().iterator();
			while(iterator.hasNext())
			{
				String eventId = iterator.next();
				Event event = eventsMap.get(eventId);

				//Filter events that the user owns
				if(event.getOwner().equals(userId))
				{
					continue;
				}

				//Check the date of the event
				if(dateString.isPresent())
				{
					Date eventDate = null;

					try
					{
						eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString.get());
					} 
					catch (ParseException e)
					{
						e.printStackTrace();
						continue;
					}

					//System.out.printf("%s, %s, %s\n", eventId, dateString.get(), event.getDate());
					
					if(eventDate.getYear() != event.getDateObject().getYear() ||
							eventDate.getMonth() != event.getDateObject().getMonth() ||
							eventDate.getDate() != event.getDateObject().getDate())
					{
						continue;
					}
				}
				
				eventIds.add(eventId);
			}
		}

		response.setExtra(eventIds);
		return response;
	}
}
