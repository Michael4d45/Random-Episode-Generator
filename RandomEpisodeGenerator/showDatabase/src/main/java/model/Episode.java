package model;

public class Episode implements Comparable
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

	public String getDescription()
	{
		return description;
	}

	public int getEpisodeNum()
	{
		return episodeNum;
	}


	public int compareTo(Object o)
	{
		if (!(o instanceof Episode))
			return -1;

		Episode compareTo = (Episode) o;

		return Integer.compare(episodeNum, compareTo.episodeNum);
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

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setNumber(int number)
	{
		this.episodeNum = number;
	}
}
