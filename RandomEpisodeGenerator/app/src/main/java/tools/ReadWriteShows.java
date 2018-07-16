package tools;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import model.Shows;

public class ReadWriteShows
{

	private static final String SHOW_FILE = "shows";


	public static void saveShows(Context c)
	{
		writeToFile(Shows.get().getSaveShowsToFile(), c);
	}

	public static void loadShows(Context c)
	{
		Shows.get().loadFromJson(readFromFile(c));
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
			outputStreamWriter.close();
		}
		catch (IOException e)
		{
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}
}