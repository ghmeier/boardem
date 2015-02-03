package boardem.experiment.firebase;

import com.firebase.client.*;

public class Main
{
	private Firebase fb;

	public static void main(String[] args)
	{
		new Main().run();
	}

	public Main()
	{
		fb = new Firebase("https://jake-experiment.firebaseio.com/");
	}

	public void run()
	{
		User alanisawesome = new User("Alan Turing", 1912);
		User gracehop = new User("Grace Hopper", 1906);

		Firebase usersRef = fb.child("users");

		Map<String, User> users = new HashMap<String, User>();
		users.put("alanisawesome", alanisawesome);
		users.put("gracehop", gracehop);

		usersRef.setValues(users);
	}

	public class User
	{
		private int birthYear;
		private String fullName;

		public User { }

		public User(String fullName, int birthYear)
		{
			this.fullName = fullName;
			this.birthYear = birthYear;
		}

		public long getBirthYear()
		{
			return birthYear;
		}

		public String getFullName()
		{
			return fullName;
		}
	}
}