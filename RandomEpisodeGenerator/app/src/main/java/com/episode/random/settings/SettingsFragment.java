package com.episode.random.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.episode.random.randomepisodegenerator.R;

import java.util.Vector;

import model.Show;
import model.Shows;
import tools.MenuHelper;

public class SettingsFragment extends Fragment
{
	private static final String SHOW = "com.episode.random.settings.SettingsFragment.SHOW";
	private static final String DIALOG_DELETE = "DIALOG_DELETE";
	private static final int REQUEST_DELETE = 0;
	private RecyclerView showRecyclerView;

	public SettingsFragment()
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
		View v = inflater.inflate(R.layout.fragment_settings, container, false);

		showRecyclerView = (RecyclerView) v.findViewById(R.id.show_settings_recycler_container);
		showRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		update();

		return v;
	}

	private void update()
	{
		Vector<Show> shows = Shows.get().getAllShows();

		ShowAdapter showAdapter = new ShowAdapter(shows);
		showRecyclerView.setAdapter(showAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.settings_menu, menu);

		MenuItem add = (MenuItem) menu.findItem(R.id.action_new_show);
		TextView textBtn = MenuHelper.getTextButton(getString(R.string.add), getResources().getDrawable(R.drawable.ic_add_black_24dp),getContext());
		add.setActionView(textBtn);
		textBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Shows.get().addNew();
				update();
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case REQUEST_DELETE:
				switch (resultCode)
				{
					case Activity.RESULT_CANCELED:
						break;
					case Activity.RESULT_OK:
						if (data != null && data.hasExtra(SHOW))
						{
							Show show = (Show) data.getSerializableExtra(SHOW);
							if (!Shows.get().remove(show))
							{
								Toast.makeText(getContext(), getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
							}
							else
								update();
						}
						break;
				}
				break;
		}
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
		ImageButton delete;
		Switch isIncluded;
		Show show;

		ShowHolder(final LayoutInflater inflater, ViewGroup parent)
		{
			super(inflater.inflate(R.layout.recycler_show_settings, parent, false));
			title = (Button) itemView.findViewById(R.id.show_title_button);
			isIncluded = (Switch) itemView.findViewById(R.id.show_included);
			delete = (ImageButton) itemView.findViewById(R.id.delete_button);
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

			delete.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					FragmentManager manager = getFragmentManager();
					DeleteDialog dialog = new DeleteDialog();
					Intent intent = new Intent();
					intent.putExtra(SHOW, show);
					dialog.setIntent(intent);
					dialog.setTargetFragment(SettingsFragment.this, REQUEST_DELETE);
					dialog.show(manager, DIALOG_DELETE);
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