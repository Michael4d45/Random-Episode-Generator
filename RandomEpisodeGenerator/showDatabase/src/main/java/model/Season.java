package model;

import java.util.Set;
import java.util.TreeSet;

public class Season implements Comparable
{
	private int seasonNum = 0;
	private String description = "";

	private Set<Episode> episodes = new TreeSet<>();


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

	@Override
	public int compareTo(Object o)
	{
		if (!(o instanceof Season))
			return -1;

		Season compareTo = (Season) o;

		return Integer.compare(seasonNum, compareTo.seasonNum);
	}
}
