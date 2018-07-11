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

	private Set<Episode> episodes = new TreeSet<>();
	private Map<Integer,Episode> episodesMap = new TreeMap<>();

	public Season(int i)
	{
		seasonNum = i;
	}


	public Set<Episode> getEpisodes()
	{
		return episodes;
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
		for (Episode episode : episodes)
			s.append(episode.toString());
		return s.toString();
	}

	public Set<Episode> createEpisodes(int numEpisodes)
	{
		for(int i = 1; i <= numEpisodes; i++)
		{
			Episode episode = new Episode(i);
			episodes.add(episode);
			episodesMap.put(i,episode);
		}
		return episodes;
	}

	public boolean addEpisode(Episode episode)
	{
		if(episode == null || episodesMap.containsKey(episode.getEpisodeNum()))
			return false;
		episodes.add(episode);
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
		episodes.remove(episode);
		episodesMap.remove(episode.getEpisodeNum());
		episode.setNumber(newNumber);
		episodes.add(episode);
		episodesMap.put(newNumber,episode);

		return true;
	}

	public void setNumber(int number)
	{
		this.seasonNum = number;
	}

	public Episode getRandomEpisode()
	{
		Vector<Integer> keys = new Vector<>(episodesMap.keySet());
		return episodesMap.get(keys.elementAt((int) (Math.random() * keys.size())));
	}
}
