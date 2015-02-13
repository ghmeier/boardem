package boardem.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * The UserCreator class creates a user based off of the provided information and
 * stores the user in Firebase
 */
public class UserCreator
{
	/**
	Adds a new user using a username/password combo instead of Facebook login
	@return BoardemResponse indicating if the operation completed succesfully, or the reason it failed
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
			//Convert the JSON string to Java objects
			Map<String, User> users = new HashMap<String, User>();
			ObjectMapper mapper = new ObjectMapper();
			
			Iterator<String> keyIterator = dataMap.keySet().iterator();
			while(keyIterator.hasNext())
			{
				String key = keyIterator.next();
				User u = null;
				
				try
				{
					String json = mapper.writeValueAsString(dataMap.get(key));
					System.out.println("\n\n" + json + "\n");
					u = mapper.readValue(json, User.class);
				} 
				catch (JsonParseException e)
				{
					e.printStackTrace();
				}
				catch (JsonMappingException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				users.put(key, u);
			}
			
			if(users.containsKey(user.getUsername()))
			{
				response = ResponseList.RESPONSE_USERNAME_USED;
			}

			if(response == null)
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