package com.episode.random.showdatabase.object;

public class EpisodeObject implements Comparable
{
	String Description;
	int episodeNum;

	@Override
	public int compareTo(Object o)
	{
		if (!(o instanceof EpisodeObject))
			return -1;

		EpisodeObject compareTo = (EpisodeObject) o;

		return Integer.compare(episodeNum, compareTo.episodeNum);
	}
}
