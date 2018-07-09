package model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class Show implements Serializable
{
	private Set<Season> seasons = new TreeSet<>();
	private Vector<Episode> episodes;

	public Set<Season> getSeasons()
	{
		return seasons;
	}

	public Vector<Episode> getEpisodes()
	{
		if(episodes == null)
		{
			episodes = new Vector<>();
			for (Season season: seasons)
				episodes.addAll(season.getEpisodes());
		}

		return episodes;
	}
}
