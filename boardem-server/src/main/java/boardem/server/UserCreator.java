package boardem.server;

import java.util.concurrent.CountDownLatch;

import com.firebase.client.*;

/**
The UserCreator class creates a user based off of the provided information and
stores the user in Firebase
*/
public class UserCreator
{
	/**
	Adds a new user using a username/password combo instead of Facebook login
	@return 0 if the user was created successfully, error code if not
	*/
	public static int addUser()
	{
		//Holds the DataSnapshot received by the anonymous inner class ValueEventListener
		final DataSnapshotHolder holder = new DataSnapshotHolder();

		//Used to wait for firebase to send the data before continuing
		final CountDownLatch stopSignal = new CountDownLatch(1);

		Firebase fb = new Firebase("https://boardem.firebaseio.com/users");
		fb.addListenerForSingleValueEvent(new ValueEventListener()
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

		try
		{
			stopSignal.await();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		//TODO - Make error codes a thing
		//Need to check if the username is in use
		//Write data to Firebase
//		Firebase postRef = usersRef.push();
		return 0;
	}	
}