package com.episode.random.randomepisodegenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import model.Show;
import tools.ReadWriteShows;

public class MainActivity extends AppCompatActivity
{
	private FragmentManager fm;
	private Fragment fragment;
	private static MainActivity mainActivity;

	private static final String DIALOG_EXIT = "DIALOG_EXIT";

	private static final int REQUEST_EXIT = 0;

	public static MainActivity getInstance()
	{
		return mainActivity;
	}

	/**
	 * load shows, then open fragment
	 *
	 * @param savedInstanceState bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainActivity = this;
		ReadWriteShows.loadShows(this);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);

		setSupportActionBar(myToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		fm = getSupportFragmentManager();
		getFragment();
		if (fragment == null)
		{
			fragment = new MainFragment();
			fm.beginTransaction().add(R.id.mainFragmentContainer, fragment).commit();
		}
	}

	private void getFragment()
	{
		fragment = fm.findFragmentById(R.id.mainFragmentContainer);
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

	public void update()
	{
		getFragment();
		if (fragment instanceof MainFragment)
		{
			((MainFragment) fragment).update();
		}
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		getFragment();
		if (fragment instanceof MainFragment)
		{
			ExitDialog dialog = new ExitDialog();
			dialog.setTargetFragment(fragment, REQUEST_EXIT);
			dialog.show(fm, DIALOG_EXIT);
		}
		else
			onBackPressed();
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case REQUEST_EXIT:
				switch (resultCode)
				{
					case Activity.RESULT_CANCELED:

						break;
					case Activity.RESULT_OK:
						onBackPressed();
						break;
				}
		}
	}
}