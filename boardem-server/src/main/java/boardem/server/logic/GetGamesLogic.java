package boardem.server.logic;

import boardem.server.json.BoardemResponse;
import boardem.server.json.Game;
import boardem.server.json.ResponseList;

import static boardem.server.GameCache.gamesList;

/**
 * Contains logic for getting a list of games
 */
public class GetGamesLogic
{
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
