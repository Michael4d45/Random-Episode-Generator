package model;

public class Episode implements Comparable
{
	private String title = "";
	private String Description = "";
	private int episodeNum = 0;

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return Description;
	}

	public int getEpisodeNum()
	{
		return episodeNum;
	}

	@Override
	public int compareTo(Object o)
	{
		if (!(o instanceof Episode))
			return -1;

		Episode compareTo = (Episode) o;

		return Integer.compare(episodeNum, compareTo.episodeNum);
	}
}
