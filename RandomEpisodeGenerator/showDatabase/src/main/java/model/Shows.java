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
