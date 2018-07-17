package tools;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.episode.random.randomepisodegenerator.MainActivity;
import com.episode.random.randomepisodegenerator.MainFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import model.Shows;

public class ReadWriteShows
{

	private static final String SHOW_FILE = "shows";
	private static final Object SHOW_FOLDER = "RandomShows";

	public static void saveShows(Context c)
	{
		new SaveShowTask().execute(c);
	}

	public static void loadShows(Context c)
	{
		new LoadShowTask().execute(c);
	}

	private static String readFromFile(Context context)
	{
		String ret = "";

		try
		{
			InputStream inputStream = context.openFileInput(SHOW_FILE + ".txt");

			if (inputStream != null)
			{
				ret = new Scanner(inputStream).useDelimiter("\\A").next();
			}
		}
		catch (FileNotFoundException e)
		{
			Log.e("login activity", "File not found: " + e.toString());
		}

		return ret;
	}

	private static void writeToFile(String data, Context context)
	{
		try
		{
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(SHOW_FILE + ".txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.flush();
			outputStreamWriter.close();
		}
		catch (IOException e)
		{
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	static class SaveShowTask  extends AsyncTask<Context, Void, Void>
	{
		@Override
		protected Void doInBackground(Context... contexts)
		{
			writeToFile(Shows.get().getSaveShowsToFile(), contexts[0]);
			return null;
		}


		@Override
		protected void onPostExecute(Void v)
		{
			update();
		}
	}

	static class LoadShowTask extends AsyncTask<Context, Void, Void>
	{
		@Override
		protected Void doInBackground(Context... contexts)
		{
			Shows.get().loadFromJson(readFromFile(contexts[0]));
			return null;
		}

		@Override
		protected void onPostExecute(Void v)
		{
			update();
		}
	}

	private static void update()
	{
		MainFragment mainFragment = MainFragment.get();
		if(mainFragment != null)
			mainFragment.update();
	}
}