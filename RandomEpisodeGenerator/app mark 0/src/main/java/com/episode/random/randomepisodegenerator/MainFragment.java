package com.episode.random.randomepisodegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.episode.random.settings.SettingsActivity;

import java.util.Vector;

import model.Show;
import model.Shows;
import tools.MenuHelper;

public class MainFragment extends Fragment
{
	private RecyclerView showRecyclerView;

	public MainFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_main, container, false);

		Button random = (Button) v.findViewById(R.id.random_show_button);
		showRecyclerView = (RecyclerView) v.findViewById(R.id.show_recycler_container);
		showRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		random.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				((MainActivity) getActivity()).switchToRandomShow();
			}
		});
		return v;
	}

	/**
	 * update the recycler view to display the shows
	 */
	public void update()
	{
		Vector<Show> shows = Shows.get().getShows();

		ShowAdapter showAdapter = new ShowAdapter(shows);
		showRecyclerView.setAdapter(showAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main_menu, menu);

		MenuItem settings = (MenuItem) menu.findItem(R.id.action_settings);
		TextView textBtn = MenuHelper.getTextButton(getString(R.string.settings), getResources().getDrawable(R.drawable.ic_settings_black_24dp),getContext());
		settings.setActionView(textBtn);
		textBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(SettingsActivity.newIntent(getContext()));
			}
		});

	}

	@Override
	public void onResume()
	{
		super.onResume();
		update();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	/**
	 * recycler for a list of shows
	 */
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
		TextView title;
		TextView info;
		View view;
		Show show;

		ShowHolder(LayoutInflater inflater, ViewGroup parent)
		{
			super(inflater.inflate(R.layout.recycler_show, parent, false));
			title = (TextView) itemView.findViewById(R.id.show_title);
			info = (TextView) itemView.findViewById(R.id.show_info);
			view = (View) itemView.findViewById(R.id.show_info_view);
			view.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					((MainActivity) getActivity()).switchToShow(show);
				}
			});
		}

		public void bind(final Show show)
		{
			this.show = show;
			title.setText(show.getTitle());
			info.setText(show.getInfo());
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		((MainActivity) getActivity()).onActivityResult(requestCode, resultCode, data);
	}
}
