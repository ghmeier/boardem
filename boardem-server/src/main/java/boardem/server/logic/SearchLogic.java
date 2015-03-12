package boardem.server.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Event;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
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
			
			//Stores the list of ids that match the search criteria
			Collection<String> ids = null;

			//Filter by distance, if the distance was specified
			if(dist.isPresent())
			{
				ids = filterByDistance(dist.get(), userLat, userLng);
			}
			else
			{
				ids = eventsMap.keySet();
			}

			//Search through the events
			Iterator<String> iterator = ids.iterator();
			while(iterator.hasNext())
			{
				String eventId = iterator.next();
				Event event = eventsMap.get(eventId);

				//Filter events that the user owns
				if(event.getOwner().equals(userId))
				{
					iterator.remove();
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
						iterator.remove();
					}

					if(eventDate.getYear() != event.getDateObject().getYear() ||
							eventDate.getMonth() != event.getDateObject().getMonth() ||
							eventDate.getDate() != event.getDateObject().getDate())
					{
						iterator.remove();
					}
				}

				eventIds.add(eventId);
			}
		}

		//Add the list of matching events to the response
		response.setExtra(eventIds);

		return response;
	}
	
	/**
	 * Gets a list of IDs for events that are within the specified distance of the specified location
	 * @param d Max distance
	 * @param lat Latitude to search from
	 * @param lng Longitude to search from
	 */
	private static LinkedList<String> filterByDistance(double d, double lat, double lng)
	{
		final LinkedList<String> list = new LinkedList<String>();
		
		GeoFire geofire = new GeoFire(new Firebase("https://boardem.firebaseio.com/geofire"));
		
		//Perform a query centered at (lat, lng) of radius d (in km)
		GeoQuery query = geofire.queryAtLocation(new GeoLocation(lat, lng), d);
		
		final CountDownLatch latch = new CountDownLatch(1);
		
		query.addGeoQueryEventListener(new GeoQueryEventListener()
		{

			@Override
			public void onGeoQueryError(FirebaseError arg0)
			{
				//Do nothing
			}

			@Override
			public void onGeoQueryReady()
			{
				//Notify that the results have been retrieved
				latch.countDown();
			}

			@Override
			public void onKeyEntered(String arg0, GeoLocation arg1)
			{
				//Add the ID to the id list
				list.add(arg0);
			}

			@Override
			public void onKeyExited(String arg0)
			{
				//Not needed
			}

			@Override
			public void onKeyMoved(String arg0, GeoLocation arg1)
			{
				//Not needed
			}
			
		});
		
		//Wait for the results to return
		try
		{
			latch.await();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		//Don't listen for any more events for this query
		query.removeAllListeners();
		
		return list;
	}
}
