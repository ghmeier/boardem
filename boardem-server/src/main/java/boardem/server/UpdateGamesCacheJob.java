package boardem.server;

import java.io.File;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Job to update the cache of games
 */
public class UpdateGamesCacheJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		File gamesMap = new File("games.dat");
		File gamesList = new File("games_list.dat");
		
		//Remove the old cache files
		gamesMap.delete();
		gamesList.delete();
		
		//Re-cache the games
		GameCache.cacheGames();
	}
	
}
