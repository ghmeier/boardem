package boardem.server;

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
	public static int addUser(String username, String password)
	{
//		Firebase usersRef = new Firebase("https://boardem.firebaseio.com/users");
		//TODO - Make error codes a thing
		//Need to check if the username is in use
		//Write data to Firebase
//		Firebase postRef = usersRef.push();
		return 0;
	}	
}