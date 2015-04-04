package boardem.server.logic.game;

import boardem.server.FirebaseHelper;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Game;
import boardem.server.json.ResponseList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Contains logic for getting information about a game.
 */
public class GetGameLogic
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
}
