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

		//get shows
		for (int i = 0; i < 10; i++)
		{
			Show show = new Show("bob " + i);
			show.setDescription("a bob description" + i);
			Vector<Season> seasons = show.createSeasons(100);
			for (Season season : seasons)
			{
				Vector<Episode> episodes = season.createEpisodes(100);
				for (Episode episode : episodes)
				{
					episode.setDescription("some description" + episode.getEpisodeNum());
					episode.setTitle("some title" + episode.getEpisodeNum());
				}
			}
			showsMap.put(show.getTitle(), show);
		}
	}

	public static Shows get()
	{
		if (sShows == null)
			sShows = new Shows();
		return sShows;
	}

	public boolean changeShowTitle(Show show, String newName)
	{
		if (newName == null || show == null || newName.equals("") || showsMap.containsKey(newName))
			return false;
		showsMap.remove(show.getTitle());
		showsMap.put(newName, show);
		show.setTitle(newName);
		return true;
	}

	public boolean add(String title)
	{
		if (title == null || title.equals("") || showsMap.containsKey(title))
			return false;
		Show show = new Show(title);
		showsMap.put(title, show);
		return true;
	}

	public Show getShow(String input)
	{
		return showsMap.get(input);
	}

	public Show getRandomShow()
	{
		Vector<Show> shows = getShows();
		if (shows.isEmpty())
			return null;
		return shows.elementAt((int) (shows.size() * Math.random()));
	}

	public Vector<Show> getShows()
	{
		Vector<Show> shows = new Vector<>();
		for(Show show: showsMap.values())
			if(show.isIncluded())
				shows.add(show);

		return shows;
	}

	public Vector<Show> getAllShows()
	{
		return new Vector<>(showsMap.values());
	}

	public boolean remove(String show)
	{
		if (show == null || show.equals("") || !showsMap.containsKey(show))
			return false;
		showsMap.remove(show);
		return true;
	}
}
