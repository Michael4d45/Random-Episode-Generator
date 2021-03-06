package model;

import java.io.Serializable;

/**
 * Represents an episode of a TV show
 */
public class Episode implements Serializable
{
	private String title = "";
	private String description = "";
	private int episodeNum = 0;

	public Episode(int i)
	{
		episodeNum = i;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getEpisodeNum()
	{
		return episodeNum;
	}

	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("\tEpisode ");
		s.append(episodeNum);
		s.append("\n");
		s.append("\t\t");
		s.append("title ");
		s.append(title);
		s.append("\n");
		s.append("\t\t");
		s.append("description ");
		s.append(description);
		s.append("\n");
		return s.toString();
	}

	public void setNumber(int number)
	{
		this.episodeNum = number;
	}
}
