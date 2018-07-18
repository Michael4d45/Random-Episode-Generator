package com.episode.random.randomepisodegenerator;

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
import android.widget.TextView;

public class ExitDialog extends DialogFragment
{
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog, null);

		TextView text = (TextView) v.findViewById(R.id.dialog_text);
		text.setText(R.string.exit_dialog);

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle(R.string.exit)
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

	private void sendResult(int resultCode)
	{
		if (getTargetFragment() == null)
			return;

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
	}
}
