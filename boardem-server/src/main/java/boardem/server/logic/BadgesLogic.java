package boardem.server.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.Badge;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for the badges endpoint.
 */
public class BadgesLogic
{
	/**
	 * Gets all of the badges from Firebase 
	 */
	public static BoardemResponse getBadges()
	{
		//Get the badge information from Firebase
		Firebase ref = new Firebase("https://boardem.firebaseio.com/badges");
		DataSnapshot snap = FirebaseHelper.readData(ref);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Badge> map = (Map<String, Badge>) FirebaseHelper.convertToObjectMap((Map<String, HashMap>) snap.getValue(), Badge.class);
		System.out.println(map);
		//Put the badges in a sorted list
		List<String> badgesList = new ArrayList<String>();
		badgesList.addAll(map.keySet());
		Collections.sort(badgesList);
		
		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();
		response.setExtra(badgesList);
		return response;
	}
	
	/**
	 * Gets information about a single badge 
	 */
	public static BoardemResponse getBadge(String badgeId)
	{
		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();
		response.setExtra(Badge.getBadgeFromFirebase(badgeId));
		return response;
	}
}
