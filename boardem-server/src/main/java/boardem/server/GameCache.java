package boardem.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import boardem.server.json.Game;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Stores the map of cached game information
 */
public class GameCache
{
	/**
	 * Contains information about the games that are stored in Firebase
	 */
	public static Map<String, Game> games;

	/**
	 * Loads the cache. Creates the cache if it doesn't exist
	 */
	@SuppressWarnings("unchecked")
	public static void load()
	{
		File cacheFile = new File("games.dat");
		
		//Check if the file exists
		if(cacheFile.exists())
		{
			try
			{
				FileInputStream fileIn = new FileInputStream("games.dat");
				ObjectInputStream objIn = new ObjectInputStream(fileIn);
				games = (Map<String, Game>) objIn.readObject();
				objIn.close();
				fileIn.close();
			}
			catch(IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			} 
		}
		else
		{
			cacheGames();
		}
	}

	/**
	 * Downloads and caches game information from Firebase
	 */
	public static void cacheGames()
	{
		Firebase fb = new Firebase("https://boardem.firebaseio.com/games");
		DataSnapshot snap = FirebaseHelper.readData(fb);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Game> gameMap = (Map<String, Game>) FirebaseHelper.convertToObjectMap((Map<String, HashMap>) snap.getValue(), Game.class);
		GameCache.games = gameMap;

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
