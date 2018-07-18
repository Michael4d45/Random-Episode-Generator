package model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * represents a show object
 * maintains a collection of Seasons
 */
public class Show implements Serializable
{
	//whether to include in main collection for app
	private boolean include = true;

	private String title;

	//TODO: may be removed if not being used
	private String description = "";
	private Map<Integer, Season> seasonsMap = new TreeMap<>();

	public Show(String title)
	{
		this.title = title;
	}

	public Vector<Season> getSeasons()
	{
		return new Vector<>(seasonsMap.values());
	}

	@Override
	public String toString()
	{
		return title;
	}

	public String verboseString()
	{
		StringBuilder s = new StringBuilder();
		s.append(title);
		s.append("\n");
		s.append(description);
		s.append("\n");
		for (Season season : getSeasons())
			s.append(season.verboseString());
		s.append("\n");
		return s.toString();
	}

	public Vector<Season> createSeasons(int numSeasons)
	{
		for (int i = 1; i <= numSeasons; i++)
		{
			Season season = new Season(i);
			seasonsMap.put(i, season);
		}

		return getSeasons();
	}

	public Season getSeason(int i)
	{
		return seasonsMap.get(i);
	}

	/**
	 * safely add a season to the Season collection
	 *
	 * @param season the season to add
	 * @return whether the season was added
	 */
	public boolean addSeason(Season season)
	{
		if (season == null || seasonsMap.containsKey(season.getSeasonNum()))
			return false;
		seasonsMap.put(season.getSeasonNum(), season);
		return true;
	}

	/**
	 * safely change the number of the Season
	 *
	 * @param season    the season to change
	 * @param newNumber the new number to change to
	 * @return whether the season's number was changed
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean changeNumber(Season season, int newNumber)
	{
		if (season == null || seasonsMap.containsKey(newNumber) || !seasonsMap.containsKey(season.getSeasonNum()))
			return false;
		seasonsMap.remove(season.getSeasonNum());
		season.setNumber(newNumber);
		seasonsMap.put(newNumber, season);

		return true;
	}

	/**
	 * @return a random season from the collection of Seasons
	 */
	public Season getRandomSeason()
	{
		Vector<Season> seasons = getSeasons();
		if (seasons.isEmpty())
			return null;
		return seasons.elementAt((int) (Math.random() * seasons.size()));
	}

	/**
	 * remove the <i>i</i>th season
	 *
	 * @param i the number assigned for the season
	 * @return whether the season was removed
	 */
	public boolean remove(int i)
	{
		if (!seasonsMap.containsKey(i))
			return false;
		seasonsMap.remove(i);

		return true;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return info about the show for the app
	 */
	public String getInfo()
	{
		StringBuilder s = new StringBuilder();
		s.append(seasonsMap.keySet().size());
		s.append(" seasons; ");
		int numEpisodes = 0;
		for (Season season : getSeasons())
			numEpisodes += season.getNumEpisodes();
		s.append(numEpisodes);
		s.append(" episodes");

		return s.toString();
	}

	public boolean isIncluded()
	{
		return include;
	}

	public void setIncluded(boolean included)
	{
		this.include = included;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public boolean remove(Season season)
	{
		return remove(season.getSeasonNum());
	}

	/**
	 * add a new Season to the collection
	 */
	public void addNew()
	{
		int i = 1;
		//noinspection StatementWithEmptyBody
		while (!addSeason(new Season(i++))) ;
	}
}
