package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

import model.Show;
import model.Shows;


public class ShowSelectFragment extends Fragment
{

	public ShowSelectFragment()
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
		View v = inflater.inflate(R.layout.fragment_show_select, container, false);


		RecyclerView showRecyclerView = (RecyclerView) v.findViewById(R.id.show_recycler_container);
		showRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		Vector<Show> shows = Shows.get().getShows();

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

}