package com.episode.random.randomepisodegenerator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import model.Show;
import model.Shows;
import tools.ReadWriteShows;

public class MainActivity extends AppCompatActivity
{
	private FragmentManager fm;
	private Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ReadWriteShows.loadShows(getBaseContext());

		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);

		setSupportActionBar(myToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

	public void switchToRandomShow()
	{
		fragment = ShowFragment.newRandomInstance();
		fm.beginTransaction().replace(R.id.mainFragmentContainer, fragment).addToBackStack("random").commit();
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();
		return true;
	}
}