package net.mishna;

import static net.mishna.utils.AppUtils.commitPreferences;
import static net.mishna.utils.AppUtils.getStringRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mishna.api.Bookmark;
import net.mishna.ui.BookmarksListActivity;
import net.mishna.ui.ChapterViewer;
import net.mishna.ui.IndexedTractatesActivity;
import net.mishna.ui.SearchActivity;
import net.mishna.ui.SixButtonsActivity;
import net.mishna.ui.UserSettingActivity;
import net.mishna.utils.AppConstants;
import net.mishna.utils.BookmarkUtils;
import net.mishna.utils.DBAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * This class is responsible for displaying the application main functionalities : 
 * 
 * <ol>
 * 	<li>Seder & Tractates Browsing </li>
 * 	<li>Bookmarks </li>
 * 	<li>My last reading Position </li>
 * 	<li>Daily Mishna </li>
 * 	<li>Search books </li>
 * 	<li>Preferences </li>
 * </ol>
 * 
 * 
 * @author Mistriel
 *
 */
public class MainActivity extends Activity 
{

    private static String TAG = MainActivity.class.getSimpleName();
    
    private ListView mFunctionalitiesListView;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

	requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide activity title .
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	

	mFunctionalitiesListView = (ListView) findViewById(R.id.list);

	List<Map<String, Object>> data = setMainActivityFunctionalities();
	SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.main_item, new String[] { "functionIcon", "functionTitle", "functionText" }, new int[] { R.id.functionIcon, R.id.functionTitle , R.id.functionText });



	mFunctionalitiesListView.setAdapter(adapter);
	
	mFunctionalitiesListView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {
		switch (position)
		{
        		case 0 : // browse books
        		{
        		    startTractatesSelectionActivity();
        		    break;
        		}
        		case 1 : // Last Position
        		{
        		    startChapterViewActivity();
        		    break;
        		}
        		case 2 : // Bookmarks
        		{
        		    startBookmarksActivity();
        		    break;
        		}
        		case 4 : // Search
        		{
        		    startSearchActivity();
        		    break;
        		}
        		case 5 : // preferences
        		{
        		    startUserSettingActivity();
        		    break;
        		}
		}
	    }
	});
    }

    
    protected void startChapterViewActivity()
    {
	
	Bookmark lastPos = BookmarkUtils.getLastReadingPosition();
	
	if(lastPos == null)
	{
	    return ;
	}
	
	Intent intent = new Intent(this, ChapterViewer.class);
	intent.putExtra(AppConstants.BOOKMARK_INTENT_KEY, lastPos);

	startActivity(intent);
    }

    
    protected void startUserSettingActivity()
    {
	final Intent intentToStartUserSettingActivity = new Intent(this, UserSettingActivity.class);
	startActivity(intentToStartUserSettingActivity);
	
    }
    
    
    protected void startBookmarksActivity()
    {
	final Intent intentToStartBookmarksActivity = new Intent(this, BookmarksListActivity.class);
	startActivity(intentToStartBookmarksActivity);
    }
    
    protected void startSearchActivity()
    {
	final Intent intentToStartSearchActivity = new Intent(this, SearchActivity.class);
	startActivity(intentToStartSearchActivity);
    }
    

    protected void startTractatesSelectionActivity()
    {
	final Intent intentToStartTractatesSelectionActivity = new Intent(this, IndexedTractatesActivity.class);
	startActivity(intentToStartTractatesSelectionActivity);
    }

    
    
    
    
    private List<Map<String, Object>> setMainActivityFunctionalities()
    {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	Map<String, Object> 	  map  = new HashMap<String, Object>();
	
	map.put("functionIcon", R.drawable.book_scetch);
	map.put("functionTitle", getStringRes(R.string.pickMishnaBtnTxt));
	map.put("functionText",  getStringRes(R.string.pickMishnaBtnExplanation));
	list.add(map);
	
	map = new HashMap<String, Object>();
	map.put("functionIcon", R.drawable.actions_arrow_left);
	map.put("functionTitle", getStringRes(R.string.lastPosition));
	map.put("functionText", getStringRes(R.string.pickLastPositionBtnExplanation));
	list.add(map);
	
	map = new HashMap<String, Object>();
	map.put("functionIcon", R.drawable.bookmark);
	map.put("functionTitle", getStringRes(R.string.bookmarks));
	map.put("functionText",	 getStringRes(R.string.pickBookmarksBtnExplanation));
	list.add(map);
	
	
	map = new HashMap<String, Object>();
	map.put("functionIcon", R.drawable.actions_calendar);
	map.put("functionTitle", getStringRes(R.string.dailyLearning));
	map.put("functionText", getStringRes(R.string.pickDailyLearningBtnExplanation));
	list.add(map);
	
	map = new HashMap<String, Object>();
	map.put("functionIcon", R.drawable.search);
	map.put("functionTitle", getStringRes(R.string.search));
	map.put("functionText", getStringRes(R.string.pickSearchBtnExplanation));
	list.add(map);
	
	map = new HashMap<String, Object>();
	map.put("functionIcon", R.drawable.configuration_settings);
	map.put("functionTitle", getStringRes(R.string.configuration));
	map.put("functionText", getStringRes(R.string.pickConfigurationBtnExplanation));
	list.add(map);
	

	return list;
    }
    
    @Override
    protected void onDestroy()
    {
        commitPreferences() ;
        
        super.onDestroy();
    }
   
}
