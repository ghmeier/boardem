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

public class UserattributesLogic {

	/**
	* Gets all of the attributes that a user has
	* @param uid User ID to get a list of all the attributes from
	* @return All attributes (by attribute ID numbers) that the user has
 	*/
	public static BoardemResponse getUserAttributes(String user_id)
	{
		BoardemResponse response = null;
		
		//Point attributesRef to the "attributes" table of the user
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase attributesRef = rootRef.child("users").child(UserLogic.getStringNameFromId(user_id)).child("attributes");

		//Create ArrayList used in line 42
		ArrayList<String> attributeIds = new ArrayList<String>();
		
		//Convert attributesRef into a DataSnapshot
		DataSnapshot attributesData = FirebaseHelper.readData(attributesRef);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		//Convert attributeIdMap into a HashMap
		Map<String, HashMap> attributeIdMap = (Map<String, HashMap>) attributesData.getValue();

		//Check if user has any attributes
		if (attributeIdMap == null) {

			return ResponseList.RESPONSE_NO_ATTRIBUTES;
		//If they do have attributes...
		} else {
			//Create a new HashMap with the data inside it
			Map<String, Object> realattributeIdMap = FirebaseHelper.convertToObjectMap(attributeIdMap, Object.class);
			attributeIds.addAll(realattributeIdMap.keySet());
		}

		//Return success
		response = ResponseList.RESPONSE_SUCCESS;

		//Add attribute IDs to the response
		response.setExtra(attributeIds);

		return response;
		
	}

	/**
	* Adds a attribute (specified by an ID) to a user
	* @param user_id The Facebook ID of the user to add the attribute to
	* @param attribute_id The ID of the attribute to add to the user
	* @return RESPONSE_SUCCESS if completed without errors
	*/

	public static BoardemResponse addUserAttribute(String user_id, String attribute_id)
	{
		//Put username getting in front so we don't have to potentially do it twice
		String username = UserLogic.getStringNameFromId(user_id);

		//Point attributesRef to the "attributes" table of username "user_id"
		Firebase rootRef = new Firebase(BoardemApplication.FIREBASE_URL);
		Firebase attributesRef = rootRef.child("users").child(username).child("attributes");

		//Convert attributesRef to a DataSnapshot
		DataSnapshot attributesData = FirebaseHelper.readData(attributesRef);

		@SuppressWarnings({"rawtypes", "unchecked"})
		//Convert attributesData to a HashMap
		Map<String,Object> attributesMap = (Map<String,Object>) attributesData.getValue();

		//If the user doesn't have any attributes
		if (attributesMap == null) {

			Map<String,String> initialVal = new HashMap<String,String>();

			//Add the first value
			initialVal.put(attribute_id, "0");

			//Write it to the Firebase in the "attributes" table
			FirebaseHelper.writeData(attributesRef, initialVal);

		} else {


			Map<String,String> contactMap = new HashMap<String,String>();

			//To count the number of attributes the user has already
			int size = attributesMap.size();

			contactMap.put(attribute_id, Integer.toString(size));

			//Write the HashMap to Firebase under the "attributes" table
			FirebaseHelper.writeData(attributesRef, contactMap);

		}

		return ResponseList.RESPONSE_SUCCESS;
	}
	
	/**
	* Removes an attribute (specified by an ID) from a user if it exists
	* @param user_id The Facebook ID of the user to remove the attribute from
	* @param attribute_id The ID of the attribute to remove from the user
	* @return RESPONSE_ATTRIBUTE_DOES_NOT_EXIST if the user does not have that attribute, RESPONSE_SUCCESS if the user did have that attribute
	*/


	public static BoardemResponse removeUserAttribute(String user_id, String attribute_id)
	{
		return ResponseList.RESPONSE_SUCCESS;
	}

}
