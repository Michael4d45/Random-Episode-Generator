package model;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class Shows
{
	private Map<String, Show> showsMap;
	private static Shows sShows;

	private Shows()
	{
		showsMap = new TreeMap<>();
		for(int i = 0; i < 1000; i++)
		{
			Show show =  new Show("bob " + i);
			show.setDescription("a bob description" + i);
			Vector<Season> seasons = show.createSeasons(2);
			for(Season season:seasons)
			{
				Vector<Episode> episodes = season.createEpisodes(2);
				for(Episode episode: episodes)
				{
					episode.setDescription("some description"+i);
					episode.setTitle("some title"+i);
				}
			}
			showsMap.put(("bob" + i),show);
		}
	}

	public static Shows get()
	{
		if(sShows == null)
			sShows = new Shows();
		return sShows;
	}

	public boolean add(String title)
	{
		if(title == null || title.equals("") || showsMap.containsKey(title))
			return false;
		Show show = new Show(title);
		showsMap.put(title,show);
		return true;
	}

	public Show getShow(String input)
	{
		return showsMap.get(input);
	}

	public Show getRandomShow()
	{
		Vector<Show> shows = getShows();
		if(shows.isEmpty())
			return null;
		return shows.elementAt((int) (shows.size() * Math.random()));
	}

	public Vector<Show> getShows()
	{
		return new Vector<Show>(showsMap.values());
	}

	public boolean remove(String show)
	{
		if(show == null || show.equals("") || !showsMap.containsKey(show))
			return false;
		showsMap.remove(show);
		return true;
	}
}
