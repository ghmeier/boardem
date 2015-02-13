package boardem.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
The UserCreator class creates a user based off of the provided information and
stores the user in Firebase
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

		//Holds the DataSnapshot received by the anonymous inner class ValueEventListener
		final DataSnapshotHolder holder = new DataSnapshotHolder();

		//Used to wait for firebase to send the data before continuing
		final CountDownLatch readLatch = new CountDownLatch(1);

		//Connect to Firebase
		Firebase usersRef = new Firebase("https://boardem.firebaseio.com/users");
		usersRef.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot snapshot)
			{
				holder.setSnapshot(snapshot);
				readLatch.countDown(); //Indicate that the data was received
			}

			@Override
			public void onCancelled(FirebaseError firebaseError)
			{
				System.out.printf("FirebaseError: %s\n", firebaseError.getMessage());
			}
		});

		//Wait for the Firebase data to be received
		try
		{
			readLatch.await();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		//Get the map of user data out of the data snapshot
		//stringValues holds the JSON string before it is converted to a Java object
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, HashMap> dataMap = (Map<String, HashMap>) holder.getSnapshot().getValue();

		if(dataMap == null)
		{
			//No users are in the database, create one
			Firebase newUserRef = usersRef.child(user.getUsername());

			//Create a map of the properties and their values
			Map<String, String> data = new HashMap<String, String>();
			data.put("username", user.getUsername());
			data.put("facebook_id", user.getFacebookId());
			data.put("display_name", user.getDisplayName());
			data.put("picture_url", user.getPictureUrl());

			final CountDownLatch writeLatch = new CountDownLatch(1);

			newUserRef.setValue(data, new Firebase.CompletionListener()
			{
				@Override
				public void onComplete(FirebaseError arg0, Firebase arg1)
				{
					//Notify that the write completed
					writeLatch.countDown();
				}
			});

			//Wait for the write to complete
			try
			{
				writeLatch.await();
			} 
			catch (InterruptedException e)
			{

			}

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
			
			//Check if the username is already in use
			Iterator<User> iter = users.values().iterator();
			while(iter.hasNext())
			{
				User toCheck = iter.next();

				if(toCheck.getUsername().equals(user.getUsername()))
				{
					response = ResponseList.RESPONSE_USERNAME_USED;
					break;
				}
			}

			if(response == null)
			{
				//Write data to Firebase
				Firebase newUserRef = usersRef.child(user.getUsername());

				//Create a map of the properties and their values
				Map<String, String> data = new HashMap<String, String>();
				data.put("username", user.getUsername());
				data.put("facebook_id", user.getFacebookId());
				data.put("display_name", user.getDisplayName());
				data.put("picture_url", user.getPictureUrl());

				final CountDownLatch writeLatch = new CountDownLatch(1);

				newUserRef.push().setValue(data, new Firebase.CompletionListener()
				{
					@Override
					public void onComplete(FirebaseError arg0, Firebase arg1)
					{
						//Notify that the write completed
						writeLatch.countDown();
					}
				});

				//Wait for the write to complete
				try
				{
					writeLatch.await();
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				response = ResponseList.RESPONSE_SUCCESS;
			}
		}

		return response;
	}	
}