package boardem.server.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import boardem.server.FirebaseHelper;
import boardem.server.json.Game;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for caching game information from Firebase
 */
public class CacheGamesLogic
{
	public static void cacheGames()
	{
		//Delete the old game information if it exists
		File cacheFile = new File("games.dat");
		cacheFile.delete();

		//Get the game information from Firebase
		Firebase fb = new Firebase("https://boardem.firebaseio.com/games");
		DataSnapshot snap = FirebaseHelper.readData(fb);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Game> gameMap = (Map<String, Game>) FirebaseHelper.convertToObjectMap((Map<String, HashMap>) snap.getValue(), Game.class);
		
		//Serialize the games
		try
		{
			FileOutputStream fileOut = new FileOutputStream("games.dat");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(gameMap);
			objectOut.close();
			fileOut.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
