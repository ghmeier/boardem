package boardem.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import boardem.server.json.Response;
import boardem.server.json.ResponseList;
import boardem.server.json.User;

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
	@return Response indicating if the operation completed succesfully, or the reason it failed
	 */
	public static Response addUser(User user)
	{
		Response response = null;

		//Holds the DataSnapshot received by the anonymous inner class ValueEventListener
		final DataSnapshotHolder holder = new DataSnapshotHolder();

		//Used to wait for firebase to send the data before continuing
		final CountDownLatch stopSignal = new CountDownLatch(1);

		//Connect to Firebase
		Firebase usersRef = new Firebase("https://boardem.firebaseio.com/users");
		usersRef.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot snapshot)
			{
				holder.setSnapshot(snapshot);
				stopSignal.countDown(); //Indicate that the data was received
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
			stopSignal.await();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.println(holder.getSnapshot());

		//Get the map of user data out of the data snapshot
		@SuppressWarnings("unchecked")
		Map<String, User> users = (Map<String, User>) holder.getSnapshot().getValue();

		if(users == null)
		{
			//No users are in the database, create one
			Firebase newUserRef = usersRef.child(user.getUsername());

			//Create a map of the properties and their values
			Map<String, String> data = new HashMap<String, String>();
			data.put("username", user.getUsername());
			data.put("facebookId", user.getFacebookId());
			data.put("displayName", user.getDisplayName());
			data.put("pictureUrl", user.getPictureUrl());

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

			}

			response = ResponseList.RESPONSE_SUCCESS;
		}
		else
		{
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
				data.put("facebookId", user.getFacebookId());
				data.put("displayName", user.getDisplayName());
				data.put("pictureUrl", user.getPictureUrl());

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