package model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Represents a season of a TV show
 * It maintains a collection of Episodes
 */
public class Season implements Serializable
{
	private int seasonNum = 0;

	//TODO: may be removed if ending up never used
	private String description = "";

	private Map<Integer, Episode> episodesMap = new TreeMap<>();

	public Season(int i)
	{
		seasonNum = i;
	}


	public Vector<Episode> getEpisodes()
	{
		return new Vector<Episode>(episodesMap.values());
	}

	public int getSeasonNum()
	{
		return seasonNum;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("Season ");
		s.append(seasonNum);
		s.append("\n");
		return s.toString();
	}

	public String verboseString()
	{
		StringBuilder s = new StringBuilder();
		s.append("Season ");
		s.append(seasonNum);
		s.append("\n");
		for (Episode episode : getEpisodes())
			s.append(episode.toString());
		return s.toString();
	}

	/**
	 * generate a number of Episodes
	 *
	 * @param numEpisodes the number of episodes to be created
	 * @return the generated episodes and any current episodes
	 */
	public Vector<Episode> createEpisodes(int numEpisodes)
	{
		for (int i = 1; i <= numEpisodes; i++)
			addNew();
		return getEpisodes();
	}

	/**
	 * a safe method of adding an episode
	 * We don't want any duplicate episode numbers
	 * as that is the key to the episode map
	 *
	 * @param episode the episode to add to the collection
	 * @return if the episode was added
	 */
	public boolean addEpisode(Episode episode)
	{
		if (episode == null || episodesMap.containsKey(episode.getEpisodeNum()))
			return false;
		episodesMap.put(episode.getEpisodeNum(), episode);
		return true;
	}

	public Episode getEpisode(int i)
	{
		return episodesMap.get(i);
	}

	/**
	 * a safe method of manipulating the Episode's number
	 *
	 * @param episode   what episode to edit
	 * @param newNumber what number to change to
	 * @return whether the number was changed
	 */
	public boolean changeNumber(Episode episode, int newNumber)
	{
		if (episode == null || episodesMap.containsKey(newNumber) || !episodesMap.containsKey(episode.getEpisodeNum()))
			return false;
		episodesMap.remove(episode.getEpisodeNum());
		episode.setNumber(newNumber);
		episodesMap.put(newNumber, episode);

		return true;
	}

	public void setNumber(int number)
	{
		this.seasonNum = number;
	}

	/**
	 * get a random episode from the Episode collection
	 *
	 * @return the randomly generated episode, null if the collection is empty
	 */
	public Episode getRandomEpisode()
	{
		Vector<Episode> episodes = getEpisodes();
		if (episodes.isEmpty())
			return null;
		return episodes.elementAt((int) (Math.random() * episodes.size()));
	}

	/**
	 * remove the episode with the number <i>i</i>
	 *
	 * @param i the episode's number
	 * @return whether the episode was removed
	 */
	public boolean remove(int i)
	{
		if (!episodesMap.containsKey(i))
			return false;
		episodesMap.remove(i);

		return true;
	}

	/**
	 * remove an episode from the Episode Collection
	 *
	 * @param episode which episode to remove
	 * @return whether the episode was removed
	 */
	public boolean remove(Episode episode)
	{
		return remove(episode.getEpisodeNum());
	}

	public int getNumEpisodes()
	{
		return episodesMap.size();
	}

	/**
	 * add a new episode to the Episode Collection by increasing the Episode's number
	 */
	public void addNew()
	{
		int i = 1;
		//noinspection StatementWithEmptyBody
		while (!addEpisode(new Episode(i++))) ;
	}
}
