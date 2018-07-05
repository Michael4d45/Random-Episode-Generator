package com.episode.random.showdatabase.object;

import java.util.Set;

public class SeasonObject implements Comparable
{
	int seasonNum;
	String description;

	Set<EpisodeObject> episodes;

	@Override
	public int compareTo(Object o)
	{
		if (!(o instanceof SeasonObject))
			return -1;

		SeasonObject compareTo = (SeasonObject) o;

		return Integer.compare(seasonNum, compareTo.seasonNum);
	}
}
