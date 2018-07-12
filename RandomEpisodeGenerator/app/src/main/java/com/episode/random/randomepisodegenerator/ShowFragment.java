package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShowFragment extends Fragment
{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String SHOW_TITLE = "com.episode.random.randomepisodegenerator.SHOW_TITLE";
	private static final String IS_RANDOM = "com.episode.random.randomepisodegenerator.IS_RANDOM";

	// TODO: Rename and change types of parameters
	private String title;
	private boolean isRandom;

	public ShowFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param title Parameter 1.
	 * @return A new instance of fragment ShowFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ShowFragment newInstance(String title)
	{
		ShowFragment fragment = new ShowFragment();
		Bundle args = new Bundle();
		args.putString(SHOW_TITLE, title);
		args.putBoolean(IS_RANDOM, false);
		fragment.setArguments(args);
		return fragment;
	}

	public static Fragment newRandomInstance(String title)
	{
		ShowFragment fragment = new ShowFragment();
		Bundle args = new Bundle();
		args.putString(SHOW_TITLE, title);
		args.putBoolean(IS_RANDOM, true);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			title = getArguments().getString(SHOW_TITLE);
			isRandom = getArguments().getBoolean(IS_RANDOM);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_show, container, false);
	}
}