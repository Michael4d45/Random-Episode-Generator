package com.episode.random.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.episode.random.randomepisodegenerator.R;

import model.Show;
import tools.ReadWriteShows;

public class SettingsActivity extends AppCompatActivity
{
	private Fragment fragment;
	private FragmentManager fm;

	public static Intent newIntent(Context context)
	{
		return new Intent(context, SettingsActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		fm = getSupportFragmentManager();

		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);

		myToolbar.setTitle("Settings");

		setSupportActionBar(myToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		fragment = fm.findFragmentById(R.id.settings_container);
		if (fragment == null)
		{
			fragment = new SettingsFragment();
			fm.beginTransaction().add(R.id.settings_container, fragment).commit();
		}
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();
		return true;
	}

	public void switchToEditShow(Show show)
	{
		fragment = EditFragment.newInstance(show);
		fm.beginTransaction().replace(R.id.settings_container, fragment).addToBackStack("edit").commit();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		ReadWriteShows.saveShows(this);
	}
}
