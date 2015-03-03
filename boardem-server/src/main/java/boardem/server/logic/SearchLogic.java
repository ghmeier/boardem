package boardem.server.logic;

import com.google.common.base.Optional;

import boardem.server.json.BoardemResponse;

/**
 * Contains logic for searching for games
 */
public class SearchLogic
{
	/**
	 * Searches for games
	 * @param userLat Latitude of the user
	 * @param userLng Longitude of the user
	 * @param dist Max distance an event can be from the user
	 * @return
	 */
	public static BoardemResponse searchEvents(double userLat, double userLng, Optional<Double> dist)
	{
		BoardemResponse response = null;
		
		return response;
	}
}
