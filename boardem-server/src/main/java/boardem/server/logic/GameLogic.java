package boardem.server.logic;

import static boardem.server.GameCache.gamesList;
import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Game;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for the endpoints in GameLogic 
 */
public class GameLogic
{
	/**
	 * Gets information about a game
	 * @param gameId
	 */
	public static BoardemResponse getGame(String gameId)
	{
		BoardemResponse response = null;
		
		Firebase fb = new Firebase("https://boardem.firebaseio.com/games/" + gameId);
		DataSnapshot snap = FirebaseHelper.readData(fb);
		
		if(snap == null || snap.getValue() == null)
		{
			response = ResponseList.RESPONSE_GAME_DOESNT_EXIST;
		}
		else
		{
			//Get the game information out of the snapshot
			Game game = Game.getGameFromSnapshot(snap);
			
			response = ResponseList.RESPONSE_SUCCESS.clone();
			response.setExtra(game);
		}
		
		return response;
	}
	
	/**
	 * Gets the nth list of 10 games
	 */
	public static BoardemResponse getGames(int pageNumber)
	{
		BoardemResponse response = ResponseList.RESPONSE_SUCCESS.clone();
		Game retVal[] = new Game[10];
		int listIndex = 10 * pageNumber;
		
		//Get the next 10 games. Fill in null values when the end of the list is reached
		for(int n = 0; n < 10; ++n)
		{
			if(listIndex >= gamesList.size())
			{
				retVal[n] = null;
			}
			else
			{
				retVal[n] = gamesList.get(listIndex);
			}
			
			++listIndex;
		}
		
		response.setExtra(retVal);
		return response;
	}
}
