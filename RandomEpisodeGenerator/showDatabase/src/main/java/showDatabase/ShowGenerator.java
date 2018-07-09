package showDatabase;

import java.util.Vector;

import model.Episode;
import model.Show;
import model.Shows;

public class ShowGenerator
{
	private static Shows shows = Shows.get();

	private ShowGenerator()
	{

	}

	public static Show getRandomShow()
	{
		Vector<Show> showsVec = shows.getShows();
		return showsVec.elementAt((int)(showsVec.size() * Math.random()));
	}

	public static Episode getRandomEpisode(Show show)
	{
		Vector<Episode> episodes = show.getEpisodes();
		return episodes.elementAt((int)(episodes.size() * Math.random()));
	}

	public static Episode getRandomEpisode()
	{
		Vector<Episode> episodes = getRandomShow().getEpisodes();
		return episodes.elementAt((int)(episodes.size() * Math.random()));
	}

	public static Vector<Show> getShows()
	{
		return shows.getShows();
	}

	public static Vector<Episode> getEpisodes(Show show)
	{
		return show.getEpisodes();
	}
}
