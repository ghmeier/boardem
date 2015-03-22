package boardem.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import boardem.server.json.Game;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Stores the map of cached game information.
 * Also maintains a list of sorted games.
 */
public class GameCache
{
	/**
	 * Contains information about the games that are stored in Firebase
	 */
	public static Map<String, Game> games;
	
	/**
	 * Contains the games in a sorted list. Games are sorted by name.
	 */
	public static ArrayList<Game> gamesList;
	
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
				
				fileIn = new FileInputStream("games_list.dat");
				objIn = new ObjectInputStream(fileIn);
				gamesList = (ArrayList<Game>) objIn.readObject();
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
		
		makeSortedList();
	}
	
	/**
	 * Creates a sorted list of the games, sorted by name
	 */
	private static void makeSortedList()
	{
		gamesList = new ArrayList<Game>();
		
		Iterator<Game> iter = games.values().iterator();
		while(iter.hasNext())
		{
			Game next = iter.next();
			if(next != null)
			{
				gamesList.add(next);
			}
		}
		
		Collections.sort(gamesList);
		
		try
		{
			FileOutputStream fileOut = new FileOutputStream("games_list.dat");
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(gamesList);
			objOut.close();
			fileOut.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
