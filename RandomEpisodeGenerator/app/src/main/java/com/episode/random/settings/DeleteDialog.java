package com.episode.random.settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.episode.random.randomepisodegenerator.R;

public class DeleteDialog extends DialogFragment
{
	private Intent intent;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_delete_dialog, null);

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle("delete")
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								sendResult(Activity.RESULT_CANCELED);
							}
						})
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								sendResult(Activity.RESULT_OK);
							}
						})
				.create();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	public void setIntent(Intent intent)
	{
		this.intent = intent;
	}

	private void sendResult(int resultCode)
	{
		if (getTargetFragment() == null)
		{
			return;
		}

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
	}
}
