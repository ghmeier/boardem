package boardem.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.lang.Math;	

import boardem.server.BoardemApplication;
import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;
import boardem.server.logic.UserLogic;
import boardem.server.BadgeActions;
import boardem.server.json.Badge;
import boardem.server.logic.BadgeLogic;	

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

public class ExperienceLogic {

	/**
	* Lists all data for messages of a specified user
	* @param user_id User ID to get a list of all the messages from
	* @return All message data for user as a HashMap
 	*/
	public static BoardemResponse getUserExperience(String user_id)
	{
		BoardemResponse response = null;

		long experience;

		//Point experienceRef to the "experience" table of the user
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase experienceRef = rootRef.child("users").child(UserLogic.getStringNameFromId(user_id)).child("experience");

		DataSnapshot experienceSnap = FirebaseHelper.readData(experienceRef);

		//If the user does not have an experience table...
		if (experienceSnap == null || experienceSnap.getValue() == null) {
			//Return response that says that they have no experience
			experience = 0;
		} else {
			//Get the experience value as a long
			experience = (long) experienceSnap.getValue();
		}

		response = ResponseList.RESPONSE_SUCCESS.clone();

		response.setExtra(experience);

		return response;
	}


	/**
	* Lists all data for messages of a specified user
	* @param user_id User ID to get a list of all the messages from
	* @return All message data for user as a HashMap
 	*/
	public static BoardemResponse setUserExperience(String user_id, Long exp)
	{
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);

		if (exp == null) {
			return ResponseList.RESPONSE_NULL_INPUT;
		}

		String userName = UserLogic.getStringNameFromId(user_id);

		Firebase levelRef = rootRef.child("users").child(userName).child("level");

		DataSnapshot levelSnap = FirebaseHelper.readData(levelRef);

		long prevLevel = 0;

		if (levelSnap != null && levelSnap.getValue() != null) {
			prevLevel = (long) levelSnap.getValue();
		}

		long experience;

		long level;

		//Point experienceRef to the "experience" table of the user

		Firebase experienceRef = rootRef.child("users").child(userName).child("experience");

		DataSnapshot experienceSnap = FirebaseHelper.readData(experienceRef);

		//If the user does not have an experience table...
		if (experienceSnap == null || experienceSnap.getValue() == null) {
			//Add it to the user's table

			Firebase newExpRef = rootRef.child("users").child(userName);

			Map<String,Long> initialVal = new HashMap<String,Long>();

			//Gives us an integer value for level based on D&D 3.5 leveling rules
			//Magic. Pure magic.
			level = (long) Math.floor((Math.sqrt(5*((double)exp+125))+25)/50);

			//Add the first value
			initialVal.put("experience", exp);
			initialVal.put("level", level);

			//Write it to the Firebase in the "experience" table
			FirebaseHelper.writeData(newExpRef, initialVal);


		//Otherwise, if the user already has a table, add the current value to what already exists
		} else {
			//Get the current experience value as a long primitive
			experience = (long) experienceSnap.getValue();

			//Add the primitive of our input value to the current experience
			experience += exp.longValue();

			//Create a new reference to the user's data
			Firebase newExpRef = rootRef.child("users").child(userName);

			Map<String,Long> updatedVal = new HashMap<String,Long>();

			//Magic happens
			level = (long) Math.floor((Math.sqrt(5*((double)experience+125))+25)/50);			

			//Update the value 
			updatedVal.put("experience", experience);
			updatedVal.put("level", level);

			//Write it to the Firebase in the "experience" table
			FirebaseHelper.writeData(newExpRef, updatedVal);

		}

		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();

		if (prevLevel != level) {
		//Update the user's badge progress
			List<Badge> earnedBadges = BadgeLogic.updateBadge(user_id, BadgeActions.ACTION_LEVEL);
			for(Badge b : earnedBadges)
			{
				response.addBadge(b);
			}
		}

		return response;
	}

}