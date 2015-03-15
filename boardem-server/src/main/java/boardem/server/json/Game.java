package boardem.server.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON representation of a game
 */
public class Game
{
	private double averageRating;
	private double bggRating;
	private String description;
	public List<String> designers;
	private long gameId;
	private String image;
	private boolean isExpansion;
	private long maxPlayers;
	private List<String> mechanics;
	private long minPlayers;
	private String name;
	private List<Map<String, Object>> playerPollResults;
	private long playingTime;
	private List<String> publishers;
	private long rank;
	private String thumbnail;
	private long yearPublished;
	
	@JsonProperty("averageRating")
	public double getAverageRating()
	{
		return averageRating;
	}
	
	@JsonProperty("averageRating")
	public void setAverageRating(double averageRating)
	{
		this.averageRating = averageRating;
	}
	
	@JsonProperty("bggRating")
	public double getBggRating()
	{
		return bggRating;
	}
	
	@JsonProperty("bggRating")
	public void setBggRating(double bggRating)
	{
		this.bggRating = bggRating;
	}
	
	@JsonProperty("description")
	public String getDescription()
	{
		return description;
	}
	
	@JsonProperty("description")
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	@JsonProperty("designers")
	public List<String> getDesigners()
	{
		return designers;
	}
	
	@JsonProperty("designers")
	public void setDesigners(List<String> designers)
	{
		this.designers = designers;
	}
	
	@JsonProperty("gameId")
	public long getGameId()
	{
		return gameId;
	}
	
	@JsonProperty("gameId")
	public void setGameId(long gameId)
	{
		this.gameId = gameId;
	}
	
	@JsonProperty("image")
	public String getImage()
	{
		return image;
	}
	
	@JsonProperty("image")
	public void setImage(String image)
	{
		this.image = image;
	}
	
	@JsonProperty("isExpansion")
	public boolean isExpansion()
	{
		return isExpansion;
	}
	
	@JsonProperty("isExpansion")
	public void setExpansion(boolean isExpansion)
	{
		this.isExpansion = isExpansion;
	}
	
	@JsonProperty("maxPlayers")
	public long getMaxPlayers()
	{
		return maxPlayers;
	}
	
	@JsonProperty("maxPlayers")
	public void setMaxPlayers(long maxPlayers)
	{
		this.maxPlayers = maxPlayers;
	}
	
	@JsonProperty("mechanics")
	public List<String> getMechanics()
	{
		return mechanics;
	}
	
	@JsonProperty("mechanics")
	public void setMechanics(List<String> mechanics)
	{
		this.mechanics = mechanics;
	}
	
	@JsonProperty("minPlayers")
	public long getMinPlayers()
	{
		return minPlayers;
	}
	
	@JsonProperty("minPlayers")
	public void setMinPlayers(long minPlayers)
	{
		this.minPlayers = minPlayers;
	}
	
	@JsonProperty("name")
	public String getName()
	{
		return name;
	}
	
	@JsonProperty("name")
	public void setName(String name)
	{
		this.name = name;
	}
	
	@JsonProperty("playerPollResults")
	public List<Map<String, Object>> getPlayerPollResults()
	{
		return playerPollResults;
	}
	
	@JsonProperty("playerPollResults")
	public void setPlayerPollResults(List<Map<String, Object>> playerPollResults)
	{
		this.playerPollResults = playerPollResults;
	}
	
	@JsonProperty("playingTime")
	public long getPlayingTime()
	{
		return playingTime;
	}
	
	@JsonProperty("playingTime")
	public void setPlayingTime(long playingTime)
	{
		this.playingTime = playingTime;
	}
	
	@JsonProperty("publishers")
	public List<String> getPublishers()
	{
		return publishers;
	}
	
	@JsonProperty("publishers")
	public void setPublishers(List<String> publishers)
	{
		this.publishers = publishers;
	}
	
	@JsonProperty("rank")
	public long getRank()
	{
		return rank;
	}
	
	@JsonProperty("rank")
	public void setRank(long rank)
	{
		this.rank = rank;
	}
	
	@JsonProperty("thumbnail")
	public String getThumbnail()
	{
		return thumbnail;
	}
	
	@JsonProperty("thumbnail")
	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}
	
	@JsonProperty("yearPublished")
	public long getYearPublished()
	{
		return yearPublished;
	}
	
	@JsonProperty("yearPublished")
	public void setYearPublished(long yearPublished)
	{
		this.yearPublished = yearPublished;
	}
}
