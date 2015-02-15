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
	public static BoardemResponse signIn(String fbid, String auth)
	{
		BoardemResponse response = null;

		return response;
	}

	/**
	 * Logs a user in using a username and password
	 * @return BoardemResponse indicating if the operation completed succesfully, or the reason it failed
	 */
	public static BoardemResponse signIn(User user)
	{
		BoardemResponse response = null;

		return response;
	}
}