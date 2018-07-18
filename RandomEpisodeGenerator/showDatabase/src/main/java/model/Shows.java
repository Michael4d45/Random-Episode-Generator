package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

/**
 * the collection of shows is maintained here
 */
public class Shows
{
	private static final String SHOW_FILE = "showDatabase/shows/shows.txt";
	private static Shows sShows;
	private Map<String, Show> showsMap;

	private Shows()
	{
		showsMap = new TreeMap<>();

		//get shows
		//generateRandom();
	}

	/**
	 * this is a singleton getter
	 *
	 * @return shows singleton reference
	 */
	public static Shows get()
	{
		if (sShows == null)
			sShows = new Shows();
		return sShows;
	}

	/**
	 * for testing, can't use this inside the app, since you need to do android
	 * magic to do file things there, look at the tools.ReadWriteShows class in the
	 * app module
	 */
	private void getShowsFromSavedData()
	{
		try
		{
			InputStream is = new FileInputStream(new File(SHOW_FILE));

			String result = new Scanner(is).useDelimiter("\\A").next();

			loadFromJson(result);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * this turns a json string to the show collection object
	 * if it's not in the right format, it may not work
	 *
	 * @param json the string to load
	 */
	public void loadFromJson(String json)
	{
		if (json.isEmpty())
			return;
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Show>>()
		{
		}.getType();
		showsMap = gson.fromJson(json, type);
	}

	public void saveShowsToFile()
	{
		String json = getSaveShowsToFile();
		try
		{
			File file = new File(SHOW_FILE);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * for testing, when I didn't want to write out a bunch of shows
	 */
	private void generateRandom()
	{
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

	/**
	 * a safe method of changing the show title
	 * we don't want shows with the same title
	 *
	 * @param show    the show to manipulate
	 * @param newName the new name of the show
	 * @return whether the title was changed
	 */
	public boolean changeShowTitle(Show show, String newName)
	{
		if (newName == null || show == null || newName.equals("") || showsMap.containsKey(newName))
			return false;
		showsMap.remove(show.getTitle());
		showsMap.put(newName, show);
		show.setTitle(newName);
		return true;
	}

	/**
	 * a safe method of adding shows
	 *
	 * @param title the title of the new show
	 * @return whether the show was created and added
	 */
	public boolean add(String title)
	{
		if (title == null || title.equals("") || showsMap.containsKey(title))
			return false;
		Show show = new Show(title);
		showsMap.put(title, show);
		return true;
	}

	/**
	 * get show by title
	 *
	 * @param title the title of the show you want
	 * @return the Show
	 */
	public Show getShow(String title)
	{
		return showsMap.get(title);
	}

	/**
	 * get a randomly generated show
	 *
	 * @return the randomly generated show
	 */
	public Show getRandomShow()
	{
		Vector<Show> shows = getShows();
		if (shows.isEmpty())
			return null;
		return shows.elementAt((int) (shows.size() * Math.random()));
	}

	/**
	 * get a Vector of shows if they are to be <i>included</i>
	 *
	 * @return the show Vector
	 */
	public Vector<Show> getShows()
	{
		Vector<Show> shows = new Vector<>();
		for (Show show : showsMap.values())
			if (show.isIncluded())
				shows.add(show);

		return shows;
	}

	/**
	 * @return the entire collection of shows as a Vector
	 */
	public Vector<Show> getAllShows()
	{
		return new Vector<>(showsMap.values());
	}

	/**
	 * remove a show by title
	 *
	 * @param show title of show
	 * @return whether the show was removed
	 */
	public boolean remove(String show)
	{
		if (show == null || show.equals("") || !showsMap.containsKey(show))
			return false;
		showsMap.remove(show);
		return true;
	}

	/**
	 * add new shows with "Untitled" + increasing number to keep them unique
	 */
	public void addNew()
	{
		int num = 0;
		//noinspection StatementWithEmptyBody
		while (!add("Untitled" + num++)) ;
	}

	/**
	 * remove a show
	 *
	 * @param show show to be removed
	 * @return whether the show was removed
	 */
	public boolean remove(Show show)
	{
		return remove(show.getTitle());
	}

	/**
	 * turn the data into a json string
	 *
	 * @return json data
	 */
	public String getSaveShowsToFile()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(showsMap);
	}
}
