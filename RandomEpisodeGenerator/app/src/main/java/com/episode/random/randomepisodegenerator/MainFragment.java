package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment
{
	public MainFragment()
	{
		// Required empty public constructor
	}

	Button select;
	Button random;
	Button edit;

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
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		select = (Button) v.findViewById(R.id.select_show_button);
		random = (Button) v.findViewById(R.id.random_show_button);
		edit = (Button) v.findViewById(R.id.edit_show_button);

		select.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				((MainActivity) getActivity()).switchToShowSelect();
			}
		});

		random.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				((MainActivity) getActivity()).switchToRandomEpisode();
			}
		});

		edit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				((MainActivity) getActivity()).switchToEdit();
			}
		});

		return v;
	}
}