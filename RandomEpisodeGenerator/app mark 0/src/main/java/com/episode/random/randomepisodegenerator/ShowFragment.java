package com.episode.random.randomepisodegenerator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

import tools.ExpandableHeightGridView;
import model.Episode;
import model.Season;
import model.Show;
import model.Shows;

public class ShowFragment extends Fragment
{
	private static final String SHOW = "com.episode.random.randomepisodegenerator.ShowFragment.SHOW";
	private static final String EPISODE = "com.episode.random.randomepisodegenerator.ShowFragment.EPISODE";
	private static final String SEASON = "com.episode.random.randomepisodegenerator.ShowFragment.SEASON";

	private int episode;
	private int season;
	private TextView seasonEpisodeNumber;
	private TextView episodeTitle;
	private TextView episodeDescription;

	private Show show;

	public ShowFragment()
	{
		// Required empty public constructor
	}

	/**
	 * @param show the show for the fragment.
	 * @return A new instance of fragment ShowFragment.
	 */
	public static ShowFragment newInstance(Show show)
	{
		ShowFragment fragment = new ShowFragment();
		Bundle args = new Bundle();
		args.putSerializable(SHOW, show);
		fragment.setArguments(args);
		return fragment;
	}

	public static Fragment newRandomInstance()
	{
		Show show = Shows.get().getRandomShow();
		ShowFragment fragment = new ShowFragment();
		Bundle args = new Bundle();
		if (show != null)
			args.putSerializable(SHOW, show);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			if (getArguments().containsKey(SHOW))
				show = (Show) getArguments().getSerializable(SHOW);
			if (getArguments().containsKey(EPISODE))
				episode = getArguments().getInt(EPISODE);
			if (getArguments().containsKey(SEASON))
				season = getArguments().getInt(SEASON);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_show, container, false);

		TextView title = (TextView) v.findViewById(R.id.show_title);

		if (show != null)
			title.setText(show.getTitle());

		RecyclerView seasonRecyclerView = (RecyclerView) v.findViewById(R.id.season_recycler_container);
		seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		if (show != null)
		{
			Vector<Season> seasons = show.getSeasons();
			ShowAdapter showAdapter = new ShowAdapter(seasons);
			seasonRecyclerView.setAdapter(showAdapter);
		}

		Button random = (Button) v.findViewById(R.id.random_episode_button);

		random.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (show != null)
				{
					Season season = show.getRandomSeason();
					if(season != null)
					{
						Episode episode = season.getRandomEpisode();
						update(season, episode);
					}
				}
			}
		});

		seasonEpisodeNumber = (TextView) v.findViewById(R.id.season_episode_number);
		episodeTitle = (TextView) v.findViewById(R.id.episode_title);
		episodeDescription = (TextView) v.findViewById(R.id.episode_description);

		if (show != null)
		{
			Season tempSeason = show.getSeason(season);
			if (tempSeason != null)
			{
				Episode tempEpisode = tempSeason.getEpisode(episode);
				update(tempSeason, tempEpisode);
			}
		}
		return v;
	}

	/**
	 * update the information on the textViews
	 * @param season the season
	 * @param episode the episode
	 */
	private void update(Season season, Episode episode)
	{
		if (episode != null && getArguments() != null)
		{
			episodeTitle.setText(episode.getTitle());
			episodeDescription.setText(episode.getDescription());
			this.episode = episode.getEpisodeNum();
			getArguments().putSerializable(EPISODE, this.episode);
		}
		if (season != null && getArguments() != null)
		{
			this.season = season.getSeasonNum();
			getArguments().putSerializable(SEASON, this.season);
		}
		if (season != null && episode != null)
		{
			String seasonEpisodeText =  getString(R.string.season__number_edit) + season +  getString(R.string._episode_number_edit) + episode;
			seasonEpisodeNumber.setText(seasonEpisodeText);
		}
	}

	private class ShowAdapter extends RecyclerView.Adapter<SeasonHolder>
	{
		private Vector<Season> seasons;

		ShowAdapter(Vector<Season> seasons)
		{
			this.seasons = seasons;
		}

		@NonNull
		@Override
		public SeasonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			return new SeasonHolder(layoutInflater, viewGroup);
		}

		@Override
		public int getItemCount()
		{
			return seasons.size();
		}

		@Override
		public void onBindViewHolder(@NonNull SeasonHolder seasonHolder, int position)
		{
			seasonHolder.bind(seasons.elementAt(position));
		}
	}

	private class SeasonHolder extends RecyclerView.ViewHolder
	{
		TextView seasonNumber;
		ExpandableHeightGridView episodeGrid;
		Season season;
		Vector<Episode> episodes;

		SeasonHolder(LayoutInflater inflater, ViewGroup parent)
		{
			super(inflater.inflate(R.layout.recycler_season, parent, false));
			seasonNumber = (TextView) itemView.findViewById(R.id.season_number);
			episodeGrid = (ExpandableHeightGridView) itemView.findViewById(R.id.episode_grid_container);
			episodeGrid.setExpanded(true);
			episodeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				public void onItemClick(AdapterView<?> parent, View v,
										int position, long id)
				{
					update(season, episodes.elementAt(position));
				}
			});
		}

		public void bind(final Season season)
		{
			this.season = season;
			String text = getString(R.string.season) + season.getSeasonNum();
			seasonNumber.setText(text);
			episodes = season.getEpisodes();
			episodeGrid.setAdapter(new EpisodeAdapter(getContext(), episodes));
		}

		class EpisodeAdapter extends BaseAdapter
		{
			private Vector<Episode> episodes;
			private Context mContext;

			EpisodeAdapter(Context c, Vector<Episode> episodes)
			{
				this.episodes = episodes;
				this.mContext = c;
			}

			public int getCount()
			{
				return episodes.size();
			}

			public Object getItem(int position)
			{
				return null;
			}

			public long getItemId(int position)
			{
				return 0;
			}

			// create a new ImageView for each item referenced by the Adapter
			public View getView(int position, View convertView, ViewGroup parent)
			{
				TextView episodeLayout;
				if (convertView == null)
				{
					// if it's not recycled, initialize some attributes
					episodeLayout = new TextView(mContext);
					episodeLayout.setTextSize(30);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					params.gravity = Gravity.CENTER;
					episodeLayout.setPadding(40, 80, 40, 80);
					episodeLayout.setLayoutParams(params);
					episodeLayout.setBackgroundColor(getResources().getColor(R.color.episode_grid));
				}
				else
				{
					episodeLayout = (TextView) convertView;
				}
				String text = Integer.toString(episodes.elementAt(position).getEpisodeNum());
				episodeLayout.setText(text);
				return episodeLayout;
			}

		}
	}
}