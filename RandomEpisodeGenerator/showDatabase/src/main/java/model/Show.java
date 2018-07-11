package model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class Show implements Serializable
{
	private String title = "";
	private String description = "";
	private Set<Season> seasons = new TreeSet<>();
	private Map<Integer,Season> seasonsMap = new TreeMap<>();

	public Show(String title)
	{
		this.title = title;
	}

	public Set<Season> getSeasons()
	{
		return seasons;
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
		for(Season season:seasons)
			s.append(season.verboseString());
		s.append("\n");
		return s.toString();
	}

	public Set<Season> createSeasons(int numSeasons)
	{
		for(int i = 1; i <= numSeasons; i++)
		{
			Season season = new Season(i);
			seasons.add(season);
			seasonsMap.put(i,season);
		}

		return seasons;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void addSeason(int seasonNum)
	{
		if(seasonsMap.containsKey(seasonNum))
			return;
		Season season = new Season(seasonNum);
		seasons.add(season);
		seasonsMap.put(seasonNum,season);
	}

	public Season getSeason(int i)
	{
		return seasonsMap.get(i);
	}

	public boolean addSeason(Season season)
	{
		if(season == null || seasonsMap.containsKey(season.getSeasonNum()))
			return false;
		seasons.add(season);
		seasonsMap.put(season.getSeasonNum(),season);
		return true;
	}

	public boolean changeNumber(Season season, int newNumber)
	{
		if(season == null || seasonsMap.containsKey(newNumber) || !seasonsMap.containsKey(season.getSeasonNum()))
			return false;
		seasons.remove(season);
		seasonsMap.remove(season.getSeasonNum());
		season.setNumber(newNumber);
		seasons.add(season);
		seasonsMap.put(newNumber,season);

		return true;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Season getRandomSeason()
	{
		Vector<Integer> keys = new Vector<>(seasonsMap.keySet());
		return seasonsMap.get(keys.elementAt((int) (Math.random() * keys.size())));
	}
}
