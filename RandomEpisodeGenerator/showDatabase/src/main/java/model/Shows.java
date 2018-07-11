package model;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class Shows
{
	private Vector<Show> shows;
	private Map<String, Show> showsMap;
	private static Shows sShows;

	private Shows()
	{
		shows = new Vector<>();
		showsMap = new TreeMap<>();
	}

	public static Shows get()
	{
		if(sShows == null)
			sShows = new Shows();
		return sShows;
	}

	public Vector<Show> getShow()
	{
		return shows;
	}

	public boolean add(String title)
	{
		if(title == null || title.equals("") || showsMap.containsKey(title))
			return false;
		Show show = new Show(title);
		shows.add(show);
		showsMap.put(title,show);
		return true;
	}

	public Show getShow(String input)
	{
		return showsMap.get(input);
	}

	public Show getRandomShow()
	{
		return shows.elementAt((int) (shows.size() * Math.random()));
	}

	public Vector<Show> getShows()
	{
		return shows;
	}

	public boolean remove(String show)
	{
		if(show == null || show.equals("") || !showsMap.containsKey(show))
			return false;
		return shows.remove(showsMap.remove(show));
	}
}
