package boardem.server.logic.game;

import static boardem.server.GameCache.gamesList;
import boardem.server.json.BoardemResponse;
import boardem.server.json.Game;
import boardem.server.json.ResponseList;

/**
 * Contains logic for getting the nth page of 10 games.
 */
public class GetGamesLogic
{
	
	
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
