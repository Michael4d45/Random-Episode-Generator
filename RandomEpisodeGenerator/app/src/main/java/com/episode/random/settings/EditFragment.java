package com.episode.random.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.episode.random.randomepisodegenerator.R;

import java.util.Vector;

import custom_views.ExpandableHeightGridView;
import model.Episode;
import model.Season;
import model.Show;
import model.Shows;

public class EditFragment extends Fragment
{
	private static final String SHOW = "com.episode.random.randomepisodegenerator.SHOW";
	private static final String EPISODE = "com.episode.random.randomepisodegenerator.EPISODE";
	private static final String SEASON = "com.episode.random.randomepisodegenerator.SEASON";

	private int episode;
	private int season;
	private EditText description;
	private EditText title;
	private EditText seasonNumber;
	private EditText episodeNumber;
	private EditText episodeTitle;
	private EditText episodeDescription;

	private Show show;
	private boolean updating;

	public EditFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param show the show.
	 * @return A new instance of fragment EditFragment.
	 */
	public static EditFragment newInstance(Show show)
	{
		EditFragment fragment = new EditFragment();
		Bundle args = new Bundle();
		args.putSerializable(SHOW, show);
		fragment.setArguments(args);
		return fragment;
	}

	public static Fragment newRandomInstance()
	{
		Show show = Shows.get().getRandomShow();
		EditFragment fragment = new EditFragment();
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
		View v = inflater.inflate(R.layout.fragment_edit, container, false);

		title = (EditText) v.findViewById(R.id.show_title);
		description = (EditText) v.findViewById(R.id.show_description);

		ExtendShowListener(title);
		ExtendShowListener(description);

		if (show != null)
		{
			title.setText(show.getTitle());
			description.setText(show.getDescription());
		}

		RecyclerView seasonRecyclerView = (RecyclerView) v.findViewById(R.id.season_recycler_container);
		seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		if (show != null)
		{
			Vector<Season> seasons = show.getSeasons();
			ShowAdapter showAdapter = new ShowAdapter(seasons);
			seasonRecyclerView.setAdapter(showAdapter);
		}

		seasonNumber = (EditText) v.findViewById(R.id.season_number);
		episodeNumber = (EditText) v.findViewById(R.id.episode_number);
		episodeTitle = (EditText) v.findViewById(R.id.episode_title);
		episodeDescription = (EditText) v.findViewById(R.id.episode_description);


		ExtendSeasonListener(seasonNumber);
		ExtendEpisodeListener(episodeNumber);
		ExtendEpisodeListener(episodeTitle);
		ExtendEpisodeListener(episodeDescription);

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

	private void update(Season season, Episode episode)
	{
		updating = true;
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
			seasonNumber.setText(Integer.toString(this.season));
			episodeNumber.setText(Integer.toString(this.episode));
		}

		updating = false;
	}

	private void updateShow()
	{
		if (show != null)
		{
			if (!show.getTitle().equals(title.getText().toString()))
				if (!Shows.get().changeShowTitle(show, title.getText().toString()))
				{
					Toast.makeText(getContext(), "couldn\'t update show title", Toast.LENGTH_SHORT).show();
				}
			show.setDescription(description.getText().toString());
		}
	}

	private void updateSeason()
	{
		String num = seasonNumber.getText().toString();
		if (show != null && !num.equals(""))
		{
			Season tempSeason = show.getSeason(season);
			if (tempSeason != null)
			{
				int newNumber = Integer.parseInt(num);
				if (tempSeason.getSeasonNum() != newNumber)
					if (!show.changeNumber(tempSeason, newNumber))
					{
						Toast.makeText(getContext(), "couldn\'t update season", Toast.LENGTH_SHORT).show();
					}

			}
		}
	}

	private void updateEpisode()
	{
		if ( show != null)
		{
			Season tempSeason = show.getSeason(season);
			if (tempSeason != null)
			{
				Episode tempEpisode = tempSeason.getEpisode(episode);
				if (tempEpisode != null)
				{
					String num = episodeNumber.getText().toString();
					if(!num.equals(""))
					{
						int newNumber = (Integer.parseInt(num));
						if (tempEpisode.getEpisodeNum() != newNumber)
						{
							if (!tempSeason.changeNumber(tempEpisode, newNumber))
							{
								//Toast.makeText(getContext(), "couldn\'t update episode number", Toast.LENGTH_SHORT).show();
							}
						}
					}
					tempEpisode.setTitle(episodeTitle.getText().toString());
					tempEpisode.setDescription(episodeDescription.getText().toString());
				}
			}
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
			super(inflater.inflate(R.layout.recycler_season_edit, parent, false));
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
			String text = "Season " + Integer.toString(season.getSeasonNum());
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

	/**
	 * method for assigning listeners to views
	 *
	 * @param editText the view to update
	 */
	private void ExtendShowListener(EditText editText)
	{
		editText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable s)
			{
				updateShow();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}
		});
	}

	/**
	 * method for assigning listeners to views
	 *
	 * @param editText the view to update
	 */
	private void ExtendEpisodeListener(EditText editText)
	{
		editText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable s)
			{
				if (!updating)
					updateEpisode();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}
		});
	}

	/**
	 * method for assigning listeners to views
	 *
	 * @param editText the view to update
	 */
	private void ExtendSeasonListener(final EditText editText)
	{
		editText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable s)
			{
				updateSeason();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}
		});
	}
}