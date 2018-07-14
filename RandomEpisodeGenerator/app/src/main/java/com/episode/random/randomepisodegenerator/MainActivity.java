package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import model.Episode;
import model.Show;
import model.Shows;

public class MainActivity extends AppCompatActivity
{
	FragmentManager fm;
	Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);

		setSupportActionBar(myToolbar);

		fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.mainFragmentContainer);
		if (fragment == null)
		{
			fragment = new MainFragment();
			fm.beginTransaction().add(R.id.mainFragmentContainer, fragment).commit();
		}
	}

	public void switchToShow(Show show)
	{
		fragment = ShowFragment.newInstance(show);
		fm.beginTransaction().replace(R.id.mainFragmentContainer, fragment).addToBackStack("show").commit();
	}

	public void switchToShowSelect()
	{
		fragment = new ShowSelectFragment();
		fm.beginTransaction().replace(R.id.mainFragmentContainer, fragment).addToBackStack("select").commit();
	}

	public void switchToRandomShow()
	{
		fragment = ShowFragment.newRandomInstance();
		fm.beginTransaction().replace(R.id.mainFragmentContainer, fragment).addToBackStack("tag").commit();
	}

	public void switchToEdit()
	{

	}

	public void switchToEpisode(Episode episode)
	{
		fragment = EpisodeFragment.newInstance(episode);
		fm.beginTransaction().replace(R.id.mainFragmentContainer, fragment).addToBackStack("tag").commit();
	}
}
