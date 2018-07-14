package model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class Show implements Serializable
{
	private String title = "";
	private String description = "";
	private Map<Integer, Season> seasonsMap = new TreeMap<>();

	public Show(String title)
	{
		this.title = title;
	}

	public Vector<Season> getSeasons()
	{
		return new Vector<Season>(seasonsMap.values());
	}

	@Override
	public String toString()
	{
		return title;
	}

	public String verboseString()
	{
		StringBuilder s = new StringBuilder();
		s.append(title);
		s.append("\n");
		s.append(description);
		s.append("\n");
		for (Season season : getSeasons())
			s.append(season.verboseString());
		s.append("\n");
		return s.toString();
	}

	public Vector<Season> createSeasons(int numSeasons)
	{
		for (int i = 1; i <= numSeasons; i++)
		{
			Season season = new Season(i);
			seasonsMap.put(i, season);
		}

		return getSeasons();
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Season getSeason(int i)
	{
		return seasonsMap.get(i);
	}

	public boolean addSeason(Season season)
	{
		if (season == null || seasonsMap.containsKey(season.getSeasonNum()))
			return false;
		seasonsMap.put(season.getSeasonNum(), season);
		return true;
	}

	public boolean changeNumber(Season season, int newNumber)
	{
		if (season == null || seasonsMap.containsKey(newNumber) || !seasonsMap.containsKey(season.getSeasonNum()))
			return false;
		seasonsMap.remove(season.getSeasonNum());
		season.setNumber(newNumber);
		seasonsMap.put(newNumber, season);

		return true;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Season getRandomSeason()
	{
		Vector<Season> seasons = getSeasons();
		return seasons.elementAt((int) (Math.random() * seasons.size()));
	}

	public boolean remove(int i)
	{
		if (!seasonsMap.containsKey(i))
			return false;
		seasonsMap.remove(i);

		return true;
	}

	public String getTitle()
	{
		return title;
	}

	public String getInfo()
	{
		StringBuilder s = new StringBuilder();
		s.append(seasonsMap.keySet().size());
		s.append(" seasons; ");
		int numEpisodes = 0;
		for (Season season : getSeasons())
			numEpisodes += season.getNumEpisodes();
		s.append(numEpisodes);
		s.append(" episodes");

		return s.toString();
	}

	public String getDescription()
	{
		return description;
	}
}
