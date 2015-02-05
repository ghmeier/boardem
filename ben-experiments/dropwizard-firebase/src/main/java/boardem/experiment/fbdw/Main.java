package boardem.experiment.fbdw;

import com.firebase.client.Firebase;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Main
{
	private Firebase fb;

	// public static void main(String[] args)
	// {
	// 	//Run the "run" method of "Main" at program start
	// 	new Main().run("ten", 1500);
	// }

	public Main()
	{
		//Create new Firebase object to interface with later
		JOptionPane.showMessageDialog(null, "Main()", "Hello", JOptionPane.INFORMATION_MESSAGE);

		fb = new Firebase("https://glaring-heat-4154.firebaseio.com/");
	}

	public void runFB(String username, int year)
	{
		JOptionPane.showMessageDialog(null, "runFB()", "Hello", JOptionPane.INFORMATION_MESSAGE);
		fb = new Firebase("https://glaring-heat-4154.firebaseio.com/");
		//Create new example users
		User tempUser = new User(username, year);
		//User gracehop = new User("Grace Hopper", 1906);

		//create new Firebase object from "fb" child "users"
		Firebase usersRef = fb.child("users");

		//Create new HashMap for storing user data before sending to Firebase
		Map<String, User> users = new HashMap<String, User>();

		//Put the username (string) and the User (object) into the HashMap
		users.put(username, tempUser);
		//users.put("gracehop", gracehop);

		//Update Firebase with the HashMap data
		usersRef.setValue(users);
	}

	public class User
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