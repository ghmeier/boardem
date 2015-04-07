package boardem.server.logic;

import static boardem.server.BadgeActions.ACTION_ADD_GAME;
import static boardem.server.BadgeActions.ACTION_CREATE_EVENT;
import static boardem.server.BadgeActions.ACTION_FRIEND;
import static boardem.server.BadgeActions.ACTION_JOIN_EVENT;
import static boardem.server.BadgeActions.ACTION_LEVEL;
import static boardem.server.BadgeActions.ACTION_PLAY_GAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.Badge;
import boardem.server.json.BoardemResponse;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Updates a user's progress on badges. Not attached to any endpoints.
 */
public class BadgeLogic
{
	/**
	 * Updates a user's progress on a badge.
	 * @param userId The Facebook ID of the user.
	 * @param action The action for the badge to be updated. See boardem.server.BadgeActions.java
	 */
	public static List<Badge> updateBadge(String userId, String action)
	{
		List<Badge> earnedBadges = new ArrayList<Badge>();
		
		//Get the user's information from Firebase
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com");
		Firebase idRef = rootRef.child("facebook_id/" + userId);
		DataSnapshot idSnap = FirebaseHelper.readData(idRef);
		
		@SuppressWarnings("unchecked")
		Firebase userRef = rootRef.child("users/" + (String) ((Map<String, Object>) idSnap.getValue()).get("username"));
		DataSnapshot userSnap = FirebaseHelper.readData(userRef);
		User user = User.getUserFromSnapshot(userSnap);
		
		Map<String, Long> badgeProgress = user.getBadgeProgress();
		Long value = badgeProgress.get(action);
		if(value == null)
		{
			throw new IllegalArgumentException("action must be one of the actions from BadgeActions.java");
		}
		++value;
		badgeProgress.put(action, value);
		
		//Write the data back to Firebase
		FirebaseHelper.writeData(userRef.child("badge_progress"), badgeProgress);
		
		Badge badge = checkBadgeEarned(value, action);
		
		//Give the user experience if necessary
		if(badge != null)
		{
			earnedBadges.add(badge);
			BoardemResponse response = ExperienceLogic.setUserExperience(userId, badge.getExp());
			earnedBadges.addAll(response.getBadges());
		}
		
		return earnedBadges;
	}
	
	/**
	 * Checks whether the user earned a badge. Returns the earned badge if a badge was earned. 
	 */
	private static Badge checkBadgeEarned(long value, String action)
	{
		String badgeId = null;
		
		switch(action)
		{
		case ACTION_ADD_GAME:
			switch((int) value)
			{
			case 1:
				badgeId = "add_game_1";
				break;
			case 10:
				badgeId = "add_game_2";
				break;
			case 25:
				badgeId = "add_game_3";
				break;
			case 50:
				badgeId = "add_game_4";
				break;
			case 100:
				badgeId = "add_game_5";
				break;
			}
			break;
		case ACTION_CREATE_EVENT:
			switch((int) value)
			{
			case 1:
				badgeId = "create_event_1";
				break;
			case 10:
				badgeId = "create_event_2";
				break;
			case 25:
				badgeId = "create_event_3";
				break;
			case 50:
				badgeId = "create_event_4";
				break;
			case 100:
				badgeId = "create_event_5";
				break;
			}
			break;
		case ACTION_FRIEND:
			switch((int) value)
			{
			case 1:
				badgeId = "friend_1";
				break;
			case 10:
				badgeId = "friend_2";
				break;
			case 25:
				badgeId = "friend_3";
				break;
			case 50:
				badgeId = "friend_4";
				break;
			case 100:
				badgeId = "friend_5";
				break;
			}
			break;
		case ACTION_JOIN_EVENT:
			switch((int) value)
			{
			case 1:
				badgeId = "join_event_1";
				break;
			case 10:
				badgeId = "join_event_2";
				break;
			case 25:
				badgeId = "join_event_3";
				break;
			case 50:
				badgeId = "join_event_4";
				break;
			case 100:
				badgeId = "join_event_5";
				break;
			}
			break;
		case ACTION_LEVEL:
			switch((int) value)
			{
			case 1:
				badgeId = "level_1";
				break;
			case 10:
				badgeId = "level_2";
				break;
			case 25:
				badgeId = "level_3";
				break;
			case 50:
				badgeId = "level_4";
				break;
			case 100:
				badgeId = "level_5";
				break;
			}
			break;
		case ACTION_PLAY_GAME:
			switch((int) value)
			{
			case 1:
				badgeId = "play_game_1";
				break;
			case 10:
				badgeId = "play_game_2";
				break;
			case 25:
				badgeId = "play_game_3";
				break;
			case 50:
				badgeId = "play_game_4";
				break;
			case 100:
				badgeId = "play_game_5";
				break;
			}
			break;
		}
		
		return Badge.getBadgeFromFirebase(badgeId);
	}
}
