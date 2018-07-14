package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Vector;

import model.Season;
import model.Show;
import model.Shows;

public class ShowFragment extends Fragment
{
	private static final String SHOW = "com.episode.random.randomepisodegenerator.SHOW";

	private Show show;

	public ShowFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param show the show.
	 * @return A new instance of fragment ShowFragment.
	 */
	// TODO: Rename and change types and number of parameters
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
			show = (Show) getArguments().getSerializable(SHOW);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_show, container, false);

		TextView title = (TextView) v.findViewById(R.id.show_title);
		TextView description = (TextView) v.findViewById(R.id.show_description);

		title.setText(show.getTitle());
		description.setText(show.getDescription());

		RecyclerView seasonRecyclerView = (RecyclerView) v.findViewById(R.id.season_recycler_container);
		seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		Vector<Season> seasons = show.getSeasons();

		ShowAdapter showAdapter = new ShowAdapter(seasons);
		seasonRecyclerView.setAdapter(showAdapter);

		return v;
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
		GridView episodeGrid;
		Season season;

		SeasonHolder(LayoutInflater inflater, ViewGroup parent)
		{
			super(inflater.inflate(R.layout.season_recycler, parent, false));
			seasonNumber = (TextView) itemView.findViewById(R.id.season_number);
			episodeGrid = (GridView) itemView.findViewById(R.id.episode_grid_container);

		}

		public void bind(final Season season)
		{
			this.season = season;
		}
	}
}