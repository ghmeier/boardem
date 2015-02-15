package boardem.server.logic;

import java.util.HashMap;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * SignInLogic signs a user in to the app given a facebook ID and password and checking these against the database 
 */
public class SignInLogic
{
	/**
	 * Logs a user in using just Facebook ID
	 * @return BoardemResponse indicating if the operation completed succesfully, or the reason it failed
	 */
	public static BoardemResponse signIn(String fbid)
	{
		BoardemResponse response = null;
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com/");
		
		Firebase facebookIdRef = rootRef.child("facebook_id");

		DataSnapshot facebookIdData = FirebaseHelper.readData(facebookIdRef);
		
		//Get the map of user data out of the data snapshot
		//stringValues holds the JSON string before it is converted to a Java object
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, HashMap> dataMap = (Map<String, HashMap>) facebookIdData.getValue();

		if(dataMap == null)
		{
			//No users in database, send to login screen
			response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		else
		{
			Map<String, User> users = FirebaseHelper.convertToObjectMap(dataMap, User.class);
			
			//Check if the Facebook ID exists in the database
			if(users.containsKey(fbid))
			{
				//If Faebook ID exists in database, send success message for verification
				response = ResponseList.RESPONSE_SUCCESS;
			}

			//If the Facebook ID does not exist in the database, send out response 
			else
			{	
				response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
			}
		}

		return response;
	}

	/**
	 * Logs a user in using a username and password (Must pass in "User" object)
	 * @return BoardemResponse indicating if the operation completed succesfully, or the reason it failed
	 */
	public static BoardemResponse signIn(User user)
	{
		BoardemResponse response = null;
		Firebase rootRef = new Firebase("https://boardem.firebaseio.com/");
		
		Firebase usersRef = rootRef.child("users");
		Firebase facebookIdRef = rootRef.child("facebook_id");

		DataSnapshot userData = FirebaseHelper.readData(usersRef);
		
		//Get the map of user data out of the data snapshot
		//stringValues holds the JSON string before it is converted to a Java object
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, HashMap> dataMap = (Map<String, HashMap>) userData.getValue();

		if(dataMap == null)
		{
			//No users are in the database, so need to sign up

			response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
		}
		else
		{
			Map<String, User> users = FirebaseHelper.convertToObjectMap(dataMap, User.class);
			
			//Check if the username is already used
			if(users.containsKey(user.getFacebookId()))
			{
				//If already used, success!
				response = ResponseList.RESPONSE_SUCCESS;
			}
			else
			{

				response = ResponseList.RESPONSE_USER_DOES_NOT_EXIST;
			}
		}

		return response;
	}
}