package model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class Season implements Comparable
{
	private int seasonNum = 0;
	private String description = "";

	private Map<Integer,Episode> episodesMap = new TreeMap<>();

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

	public int compareTo(Object o)
	{
		if (!(o instanceof Season))
			return -1;

		Season compareTo = (Season) o;

		return Integer.compare(seasonNum, compareTo.seasonNum);
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

	public Vector<Episode> createEpisodes(int numEpisodes)
	{
		for(int i = 1; i <= numEpisodes; i++)
		{
			Episode episode = new Episode(i);
			episodesMap.put(i,episode);
		}
		return getEpisodes();
	}

	public boolean addEpisode(Episode episode)
	{
		if(episode == null || episodesMap.containsKey(episode.getEpisodeNum()))
			return false;
		episodesMap.put(episode.getEpisodeNum(),episode);
		return true;
	}

	public Episode getEpisode(int i)
	{
		return episodesMap.get(i);
	}

	public boolean changeNumber(Episode episode, int newNumber)
	{
		if(episode == null || episodesMap.containsKey(newNumber) || !episodesMap.containsKey(episode.getEpisodeNum()))
			return false;
		episodesMap.remove(episode.getEpisodeNum());
		episode.setNumber(newNumber);
		episodesMap.put(newNumber,episode);

		return true;
	}

	public void setNumber(int number)
	{
		this.seasonNum = number;
	}

	public Episode getRandomEpisode()
	{
		Vector<Episode> episodes = getEpisodes();
		return episodes.elementAt((int) (Math.random() * episodes.size()));
	}

	public boolean remove(int i)
	{
		if (!episodesMap.containsKey(i))
			return false;
		episodesMap.remove(i);

		return true;
	}
}
