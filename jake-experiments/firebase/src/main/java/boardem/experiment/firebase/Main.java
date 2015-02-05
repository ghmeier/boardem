package boardem.experiment.firebase;

import com.firebase.client.*;
import java.util.HashMap;
import java.util.Map;

public class Main
{
	private Firebase fb;

	public static void main(String[] args)
	{
		Firebase ref = new Firebase("https://jake-experiment.firebaseio.com/").child("users");
		ref.goOnline();
		ref.child("jakelong").setValue(new User("Jake Long", 1995),
			new Firebase.CompletionListener()
			{
				@Override
				public void onComplete(FirebaseError error, Firebase firebase)
				{
					if(error != null)
					{
						System.out.printf("Data could not be saved. %s\n", error.getMessage());
					}
					else
					{
						System.out.println("It worked");
					}
				}
			});
		
		while(true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				System.out.printf("It's rude to interrupt: %s\n", e.getMessage());
			}
		}
	}

	public static class User
	{
		private int birthYear;
		private String fullName;

		//Default constructor
		public User() { }

		//User method for easier data storage
		public User(String fullName, int birthYear)
		{
			this.fullName = fullName;
			this.birthYear = birthYear;
		}

		//Returns the birth year of a user
		public long getBirthYear()
		{
			return birthYear;
		}

		//Returns the first and last name of a user
		public String getFullName()
		{
			return fullName;
		}
	}
}