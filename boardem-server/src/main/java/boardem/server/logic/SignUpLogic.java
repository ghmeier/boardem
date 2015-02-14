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
 * The SignUpLogic class creates a user based off of the provided information and
 * stores the user in Firebase
 */
public class SignUpLogic
{
	/**
	 * Adds a new user using a username/password combo instead of Facebook login
	 * @return BoardemResponse indicating if the operation completed succesfully, or the reason it failed
	 */
	public static BoardemResponse addUser(User user)
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
			//No users are in the database, create one
			Firebase newUserRef = usersRef.child(user.getUsername());
			Firebase newIdRef = facebookIdRef.child(user.getFacebookId());
			
			//Create a map of the properties and their values
			Map<String, String> data = new HashMap<String, String>();
			data.put("username", user.getUsername());
			data.put("facebook_id", user.getFacebookId());
			data.put("display_name", user.getDisplayName());
			data.put("picture_url", user.getPictureUrl());

			Map<String, String> facebookIdData = new HashMap<String, String>();
			facebookIdData.put("username", user.getUsername());
			
			FirebaseHelper.writeData(newUserRef, data);
			FirebaseHelper.writeData(newIdRef, facebookIdData);

			response = ResponseList.RESPONSE_SUCCESS;
		}
		else
		{
			Map<String, User> users = FirebaseHelper.convertToObjectMap(dataMap, User.class);
			
			//Check if the username is already used
			if(users.containsKey(user.getUsername()))
			{
				response = ResponseList.RESPONSE_USERNAME_USED;
			}
			else
			{
				Firebase newUserRef = usersRef.child(user.getUsername());
				Firebase newIdRef = facebookIdRef.child(user.getFacebookId());
				
				//Create a map of the properties and their values
				Map<String, String> data = new HashMap<String, String>();
				data.put("username", user.getUsername());
				data.put("facebook_id", user.getFacebookId());
				data.put("display_name", user.getDisplayName());
				data.put("picture_url", user.getPictureUrl());

				Map<String, String> facebookIdData = new HashMap<String, String>();
				facebookIdData.put("username", user.getUsername());
				
				FirebaseHelper.writeData(newUserRef, data);
				FirebaseHelper.writeData(newIdRef, facebookIdData);
				
				response = ResponseList.RESPONSE_SUCCESS;
			}
		}

		return response;
	}
}