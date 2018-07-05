package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	FragmentManager fm;
	Fragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.mainFragmentContainer);
		if (fragment == null)
		{
			fragment = new MainFragment();
			fm.beginTransaction().add(R.id.mainFragmentContainer,fragment).commit();
		}
	}

	public void SwitchToEpisodeChoice()
	{
		fragment = new EpisodeChoiceFragment();
		fm.beginTransaction().replace(R.id.mainFragmentContainer,fragment).commit();
	}

	public void SwitchToShowChoice()
	{
		fragment = new ShowChoiceFragment();
		fm.beginTransaction().replace(R.id.mainFragmentContainer,fragment).commit();
	}
}
