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

		//Point shelfIDsRef to the "shelfIDs" table of the user
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase shelfIDsRef = rootRef.child("users").child(UserLogic.getStringNameFromId(user_id)).child("shelf");

		//Create ArrayList used in line 42
		ArrayList<String> shelfIDs = new ArrayList<String>();
		
		//Convert shelfIDsRef into a DataSnapshot
		DataSnapshot shelfIDsData = FirebaseHelper.readData(shelfIDsRef);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		//Convert shelfIDMap into a HashMap
		Map<String, HashMap> shelfIDMap = (Map<String, HashMap>) shelfIDsData.getValue();

		//Check if user has any shelfIDs
		if (shelfIDMap == null) {

			return ResponseList.RESPONSE_NO_SHELFIDS;
		//If they do have shelfIDs...
		} else {
			//Create a new HashMap with the data inside it
			Map<String, Object> realshelfIDMap = FirebaseHelper.convertToObjectMap(shelfIDMap, Object.class);
			shelfIDs.addAll(realshelfIDMap.keySet());
		}

		//Return success
		response = ResponseList.RESPONSE_SUCCESS;

		//Add shelfID IDs to the response
		response.setExtra(shelfIDs);
		
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
		//Put username getting in front so we don't have to potentially do it twice
		String username = UserLogic.getStringNameFromId(user_id);

		//Point shelfIDsRef to the "shelfIDs" table of username "user_id"
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase shelfIDsRef = rootRef.child("users").child(username).child("shelf");

		//Convert shelfIDsRef to a DataSnapshot
		DataSnapshot shelfIDsData = FirebaseHelper.readData(shelfIDsRef);

		@SuppressWarnings({"rawtypes", "unchecked"})
		//Convert shelfIDsData to a HashMap
		Map<String,Object> shelfIDsMap = (Map<String,Object>) shelfIDsData.getValue();

		//If the user doesn't have any shelfIDs
		if (shelfIDsMap == null) {

			Map<String,String> initialVal = new HashMap<String,String>();

			//Add the first value
			initialVal.put(shelf_id, "0");

			//Write it to the Firebase in the "shelfIDs" table
			FirebaseHelper.writeData(shelfIDsRef, initialVal);

		} else {


			Map<String,String> contactMap = new HashMap<String,String>();

			//To count the number of shelfIDs the user has already
			int size = shelfIDsMap.size();

			contactMap.put(shelf_id, Integer.toString(size));

			//Write the HashMap to Firebase under the "shelfIDs" table
			FirebaseHelper.writeData(shelfIDsRef, contactMap);

		}

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
