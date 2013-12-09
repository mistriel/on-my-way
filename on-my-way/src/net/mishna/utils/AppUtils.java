package net.mishna.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;

import net.mishna.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Class for common application operations .
 * 
 * @author Mistriel
 * 
 */
public class AppUtils
{
    public static final String Separator = "#"; // global application separator
    public static final String Space = " "; // global application separator
						// .

    public static final String PREF_LAST_READING_POSITION = "user_last_reading_position";
    public static final String PREF_FONT = "pref_Font";
    public static final String PREF_FONT_SIZE = "PREF_FONT_SIZE";
    public static final String PREF_DAILY_LEARNING_FREQUENCY = "pref_LearningFrequency";
    public static final String PREF_SEND_BUG_REPORT = "pref_SendReport";
    public static final String PREF_BOOKMARKS = "pref_Bookmarks";


    private static Resources appRes;
    private static Context context;

    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor editor;
   
    
    static
    {
	AppUtils.appRes = BaseApplication.getAppRes();
	AppUtils.context = BaseApplication.getContext();

	sharedPrefs = AppUtils.context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
	editor = sharedPrefs.edit();
    }

    /**
     * Convert String resource id to {@link String}
     * 
     * @param stringId .
     * @return {@link String} of resource id . 
     */
    public static String getStringRes(int stringId)
    {
	if (appRes == null)
	{
	    return "Resources was not set on AppUtils";
	}

	return appRes.getString(stringId);
    }

    
    public static String[] getStringArrayFromRes(int arrayResId)
    {
	if (appRes == null)
	{
	    return null ; 
	}

	return appRes.getStringArray(arrayResId);
    }
    
    
    
    public static void popShortToast(String txt)
    {
	Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }

    public static void popLongToast(String txt)
    {
	Toast.makeText(context, txt, Toast.LENGTH_LONG).show();
    }

    // Get App preferences 
    


    public static boolean getPreference(String preference, Boolean defaultVal)
    {
	return sharedPrefs.getBoolean(preference, defaultVal);
    }
    
    public static int getPreference(String preference, int defaultVal)
    {
	return sharedPrefs.getInt(preference, defaultVal);
    }
    
    public static String getPreference(String preference, String defaultVal)
    {
	return sharedPrefs.getString(preference, defaultVal);
    }

    public static Set<String> getPreference(String preference, Set<String> defaultVal)
    {
	return sharedPrefs.getStringSet(preference, defaultVal);
    }

    
    
    public static boolean setPreference(String preference, String val)
    {
	editor.putString(preference, val);
	return editor.commit();
    }

    public static boolean setPreference(String preference, int val)
    {
	editor.putInt(preference, val);
	return editor.commit();
    }

    public static boolean setPreference(String preference, Boolean val)
    {
	editor.putBoolean(preference, val);
	return editor.commit();
    }

    public static boolean setPreference(String preference, Set<String> val)
    {
	editor.putStringSet(preference, val);
	return editor.commit();
    }

    // General app utilities

    /**
     * This method is called when the app should be killed. If the method should
     * try to kill the app in a "safe" way. (i.e. finalize all running
     * activities etc.) then assign a "true" boolean to it. Otherwise assign a
     * false boolean, and the process simply terminates.
     * 
     * @param killSafely
     *            kill the application in a "safe" manner. (true/false)
     */
    public static void killApp(boolean killSafely)
    {

	if (killSafely)
	{
	    /*
	     * Finalize and collect all objects of the app on exit. This ensures
	     * the app can be killed by Android without issues.
	     */
	    System.runFinalizersOnExit(true);

	    /*
	     * Close the app down completely.
	     */
	    System.exit(0);
	}
	else
	{
	    /*
	     * Simply end the process. This is fast, but can perhaps cause some
	     * problems.
	     */
	    android.os.Process.killProcess(android.os.Process.myPid());
	    System.exit(0);
	}

    }

    public static boolean commitPreferences()
    {
	return editor.commit();
    }
    
    /**
     * Copy an InputStream to an OutputStream .
     * 
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException
    {
	// ---copy 1K bytes at a time---
	byte[] buffer = new byte[1024];
	int length;
	while ((length = inputStream.read(buffer)) > 0)
	{
	    outputStream.write(buffer, 0, length);
	}
	inputStream.close();
	outputStream.close();
    }

    // Logging facilities .
    
    
    
    // General Java utility methods .

    /**
     * check if a certain String is null or is empty .
     * 
     * @param string
     * @return true if null or empty .
     */
    public static boolean isNullOrEmpty(String string)
    {
	return (string == null || string.isEmpty());
    }

    /**
     * check if a certain String is null or is empty .
     * 
     * @param string
     * @return true if null or empty .
     */
    public static boolean isNullOrEmpty(Collection<?> collection)
    {
	return (collection == null || collection.isEmpty());
    }

}
