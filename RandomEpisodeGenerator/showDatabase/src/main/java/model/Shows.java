package model;

import java.util.Set;
import java.util.Vector;

public class Shows
{
	private Vector<Show> shows;
	private static Shows sShows;

	private Shows()
	{
		shows = new Vector<>();
	}

	public static Shows get()
	{
		if(sShows == null)
			sShows = new Shows();
		return sShows;
	}

	public Vector<Show> getShows()
	{
		return shows;
	}
}
