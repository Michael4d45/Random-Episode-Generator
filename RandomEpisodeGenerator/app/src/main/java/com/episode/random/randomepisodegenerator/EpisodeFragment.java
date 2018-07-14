package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import model.Episode;


public class EpisodeFragment extends Fragment
{
	private static final String EPISODE = "com.episode.random.randomepisodegenerator.EPISODE";

	private Episode episode;

	public EpisodeFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param episode The episode.
	 * @return A new instance of fragment EpisodeFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static EpisodeFragment newInstance(Episode episode)
	{
		EpisodeFragment fragment = new EpisodeFragment();
		Bundle args = new Bundle();
		args.putSerializable(EPISODE, episode);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			episode = (Episode) getArguments().getSerializable(EPISODE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_episode, container, false);

		TextView title = (TextView) v.findViewById(R.id.episode_title);
		TextView description = (TextView) v.findViewById(R.id.episode_description);

		title.setText(episode.getTitle());
		description.setText(episode.getDescription());

		return v;
	}
}
