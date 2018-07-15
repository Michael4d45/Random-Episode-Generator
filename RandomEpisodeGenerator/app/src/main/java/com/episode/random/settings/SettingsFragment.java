package com.episode.random.settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.episode.random.randomepisodegenerator.MainActivity;
import com.episode.random.randomepisodegenerator.MainFragment;
import com.episode.random.randomepisodegenerator.R;

import java.util.Vector;

import model.Show;
import model.Shows;

public class SettingsFragment extends Fragment
{
	public SettingsFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_settings, container, false);

		RecyclerView showRecyclerView = (RecyclerView) v.findViewById(R.id.show_settings_recycler_container);
		showRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		Vector<Show> shows = Shows.get().getAllShows();

		ShowAdapter showAdapter = new ShowAdapter(shows);
		showRecyclerView.setAdapter(showAdapter);

		return v;
	}


	private class ShowAdapter extends RecyclerView.Adapter<ShowHolder>
	{
		private Vector<Show> shows;

		ShowAdapter(Vector<Show> shows)
		{
			this.shows = shows;
		}

		@NonNull
		@Override
		public ShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			return new ShowHolder(layoutInflater, viewGroup);
		}

		@Override
		public int getItemCount()
		{
			return shows.size();
		}

		@Override
		public void onBindViewHolder(@NonNull ShowHolder showHolder, int position)
		{
			showHolder.bind(shows.elementAt(position));
		}
	}

	private class ShowHolder extends RecyclerView.ViewHolder
	{
		Button title;
		Switch isIncluded;
		Show show;

		ShowHolder(LayoutInflater inflater, ViewGroup parent)
		{
			super(inflater.inflate(R.layout.recycler_show_settings, parent, false));
			title = (Button) itemView.findViewById(R.id.show_title_button);
			isIncluded = (Switch) itemView.findViewById(R.id.show_included);
			isIncluded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean b)
				{
					show.setIncluded(b);
				}
			});

			title.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					((SettingsActivity) getActivity()).switchToEditShow(show);
				}
			});
		}

		public void bind(final Show show)
		{
			this.show = show;
			title.setText(show.getTitle());
			isIncluded.setChecked(show.isIncluded());
		}
	}
}
