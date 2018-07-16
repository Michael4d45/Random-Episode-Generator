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

public class Shows
{
	private Map<String, Show> showsMap;
	private static Shows sShows;
	private static final String SHOW_FILE = "showDatabase/shows/shows.txt";

	private Shows()
	{
		showsMap = new TreeMap<>();

		//get shows
		//generateRandom();
	}

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

	public void loadFromJson(String json)
	{
		if(json.isEmpty())
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
		for (Show show : showsMap.values())
			if (show.isIncluded())
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

	public void addNew()
	{
		int num = 0;
		while (!add("Untitled" + num++)) ;
	}

	public boolean remove(Show show)
	{
		return remove(show.getTitle());
	}

	public String getSaveShowsToFile()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(showsMap);
	}
}
