package boardem.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import boardem.server.BoardemApplication;
import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

public class UserShelfIDsLogic {

	/**
	* Gets all of the shelfIDs that a user has
	* @param uid User ID to get a list of all the shelfIDs from
	* @return All shelfIDs (by shelfID ID numbers) that the user has
 	*/
	public static BoardemResponse getUserShelfIDs(String user_id)
	{
		BoardemResponse response = null;
		
		return response;
	}

	/**
	* Adds a shelfID (specified by an ID) to a user
	* @param user_id The Facebook ID of the user to add the shelfID to
	* @param shelf_id The ID of the shelfID to add to the user
	* @return RESPONSE_SUCCESS if completed without errors
	*/

	public static BoardemResponse addUserShelfID(String user_id, String shelf_id)
	{
		return ResponseList.RESPONSE_SUCCESS;
	}
	
	/**
	* Removes an shelfID (specified by an ID) from a user if it exists
	* @param user_id The Facebook ID of the user to remove the shelfID from
	* @param shelf_id The ID of the shelfID to remove from the user
	* @return RESPONSE_shelfID_DOES_NOT_EXIST if the user does not have that shelfID, RESPONSE_SUCCESS if the user did have that shelfID
	*/


	public static BoardemResponse removeUsershelfID(String user_id, String shelf_id)
	{
		return ResponseList.RESPONSE_SUCCESS;
	}

}
