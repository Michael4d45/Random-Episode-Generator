package showDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.Vector;

import model.Episode;
import model.Season;
import model.Show;
import model.Shows;

public class ShowGenerator
{
	private static Shows shows = Shows.get();

	/**
	 * this is probably the worst case scenario of nesting, but
	 * I don't really care
	 * @param args no purpose
	 */
	public static void main(String[] args)
	{
		String input = "";

		while (!input.equals("end"))
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try
			{
				input = "";
				System.out.println("would you like to...");
				System.out.println("\tadd a show? (add)");
				System.out.println("\tlist shows? (list)");
				System.out.println("\tedit a show? (edit)");
				System.out.println("\tremove a show? (remove)");
				System.out.println("\tget random? (random)");
				System.out.println("\tleave? (end)");
				input = br.readLine();

				Show show = null;
				switch (input)
				{
					case "add":
						System.out.println("which show?");
						input = br.readLine();
						if (!shows.add(input))
						{
							System.out.println("show already exists");
							break;
						}
						show = shows.getShow(input);
						System.out.println("add a description");
						show.setDescription(br.readLine());
						System.out.println("how many seasons?");
						input = getNumber(br);
						if (input.equals("end"))
							break;

						Vector<Season> seasons = show.createSeasons(Integer.parseInt(input));
						for (Season season : seasons)
							addSeason(season, br);
						break;
					case "edit":
						System.out.println("you have these shows:");
						System.out.println(shows.getShows().toString());
						System.out.println("which show?");
						input = br.readLine();
						show = shows.getShow(input);
						if (show != null)
						{
							while (!input.equals("done") && !input.equals("end") && !input.equals("finish"))
							{
								System.out.println(show.verboseString());
								System.out.println("would you like to...");
								System.out.println("\tadd a season? (add)");
								System.out.println("\tlist season? (list)");
								System.out.println("\tedit a season? (edit)");
								System.out.println("\tremove a season? (remove)");
								System.out.println("\tchange show title? (title)");
								System.out.println("\tchange show description? (description)");
								System.out.println("\tfinish editing? (done)");
								System.out.println("\tleave? (end)");
								input = br.readLine();
								Season season = null;
								switch (input)
								{
									case "add":
										System.out.println("which season?");
										input = getNumber(br);
										if (input.equals("end"))
											break;
										season = new Season(Integer.parseInt(input));
										if (show.addSeason(season))
											addSeason(season, br);
										else
											System.out.println("couldn\'t add season");
										break;
									case "edit":
										System.out.println("you have these seasons:");
										System.out.println(show.getSeasons().toString());
										System.out.println("which season?");
										input = getNumber(br);
										if (input.equals("end"))
											break;
										season = show.getSeason(Integer.parseInt(input));
										if (season == null)
										{
											System.out.println("not a season");
											break;
										}
										System.out.println(season.verboseString());
										while (!input.equals("done") && !input.equals("end") && !input.equals("finish"))
										{
											System.out.println(season.verboseString());
											System.out.println("would you like to...");
											System.out.println("\tadd a episode? (add)");
											System.out.println("\tlist episodes? (list)");
											System.out.println("\tedit a episode? (edit)");
											System.out.println("\tremove a episode? (remove)");
											System.out.println("\tchange season number? (number)");
											System.out.println("\tfinish editing? (done)");
											System.out.println("\tleave? (end)");
											input = br.readLine();
											Episode episode = null;
											switch (input)
											{
												case "add":
													System.out.println("which episode?");
													input = getNumber(br);
													if (input.equals("end"))
														break;
													episode = new Episode(Integer.parseInt(input));
													if (season.addEpisode(episode))
														addEpisode(episode, br);
													else
														System.out.println("couldn\'t add episode");
													break;
												case "edit":
													System.out.println("which episode?");
													input = getNumber(br);
													if (input.equals("end"))
														break;
													episode = season.getEpisode(Integer.parseInt(input));
													if (episode != null)
													{
														while (!input.equals("done") && !input.equals("end") && !input.equals("finish"))
														{
															System.out.println(episode.toString());
															System.out.println("would you like to...");
															System.out.println("\tchange episode title? (title)");
															System.out.println("\tchange episode description? (description)");
															System.out.println("\tchange episode number? (number)");
															System.out.println("\tfinish editing? (done)");
															System.out.println("\tleave? (end)");
															input = br.readLine();
															switch (input)
															{
																case "title":
																	System.out.println("change title to...");
																	input = br.readLine();
																	episode.setTitle(input);
																	break;
																case "description":
																	System.out.println("change description to...");
																	input = br.readLine();
																	episode.setDescription(input);
																	break;
																case "number":
																	System.out.println("change number to...");
																	input = getNumber(br);
																	if (input.equals("end"))
																		break;
																	int newNumber = Integer.parseInt(input);
																	if (!season.changeNumber(episode, newNumber))
																		System.out.println("could not do that");
																	break;
																case "done":
																	System.out.println("\n");
																	break;
																default:
																	System.out.println("not a valid input");
																	break;
															}
														}
													}
													else
														System.out.println("not an episode");
													break;
												case "list":
													System.out.println("you have these episodes:");
													System.out.println(season.getEpisodes().toString());
													break;
												case "number":
													System.out.println("change number to...");
													input = getNumber(br);
													if (input.equals("end"))
														break;
													int newNumber = Integer.parseInt(input);
													if (!show.changeNumber(season, newNumber))
														System.out.println("could not do that");
													break;
												case "remove":
													System.out.println("which episode?:");
													System.out.println(shows.getShows().toString());
													input = getNumber(br);
													if (input.equals("end"))
														break;
													if(!season.remove(Integer.parseInt(input)))
														System.out.println("could not remove that");
													break;
												case "end":
													System.out.println("goodbye");
													break;
												case "done":
													System.out.println("\n");
													break;
												default:
													System.out.println("not a valid input");
													break;
											}
										}
										break;
									case "list":
										System.out.println("you have these seasons:");
										System.out.println(show.getSeasons().toString());
										break;
									case "title":
										System.out.println("change title to...");
										input = br.readLine();
										if(!shows.changeShowTitle(show,input))
										{
											System.out.println("could not change the name");
										}
										break;
									case "description":
										System.out.println("change description to...");
										input = br.readLine();
										show.setDescription(input);
										break;
									case "remove":
										System.out.println("which season?:");
										System.out.println(shows.getShows().toString());
										input = getNumber(br);
										if (input.equals("end"))
											break;
										if(!show.remove(Integer.parseInt(input)))
											System.out.println("could not remove that");
										break;
									case "end":
										System.out.println("goodbye");
										break;
									case "done":
										System.out.println("\n");
										break;
									default:
										System.out.println("not a valid input");
										break;
								}
							}
						}
						else
						{
							System.out.println("not a show");
						}
						break;
					case "list":
						System.out.println("you have these shows:");
						System.out.println(shows.getShows().toString());
						break;
					case "remove":
						System.out.println("which show?:");
						System.out.println(shows.getShows().toString());
						if(!shows.remove(br.readLine()))
							System.out.println("could not remove that");
						break;
					case "random":
						System.out.println();
						show = shows.getRandomShow();
						System.out.println(show.toString());
						Season season = show.getRandomSeason();
						System.out.println(season.toString());
						Episode episode = season.getRandomEpisode();
						System.out.println(episode.toString());
						break;
					case "end":
						System.out.println("goodbye");
						break;
					default:
						System.out.println("not a valid input");
						break;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}

	private static String getNumber(BufferedReader br) throws IOException
	{
		String input = "";
		while (!input.matches("[0-9]+") && !input.equals("end"))
			input = br.readLine();
		return input;
	}

	private static void addSeason(Season season, BufferedReader br) throws IOException
	{
		String input = "";
		System.out.println("how many episodes for season " + season.getSeasonNum() + "?");
		input = getNumber(br);
		if (input.equals("end"))
			return;
		Vector<Episode> episodes = season.createEpisodes(Integer.parseInt(input));
		for (Episode episode : episodes)
			addEpisode(episode, br);
	}

	private static void addEpisode(Episode episode, BufferedReader br) throws IOException
	{
		String input = "";

		System.out.println("episode " + episode.getEpisodeNum());
		System.out.println("add a title");
		episode.setTitle(br.readLine());
		System.out.println("add a description");
		episode.setDescription(br.readLine());

	}

	private ShowGenerator()
	{

	}
}
