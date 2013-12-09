package net.mishna.ui;

import net.mishna.BaseApplication;
import net.mishna.R;
import net.mishna.api.Bookmark;
import net.mishna.api.SederEnum;
import net.mishna.api.TractateEnum;
import net.mishna.utils.AppConstants;
import net.mishna.utils.AppUtils;
import net.mishna.utils.BookmarkUtils;
import net.mishna.utils.DBAdapter;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * This class handles the following functionalities :
 * 
 * <ol>
 *  <li>View a chapter .
 *  <li>Browse between the chapters .
 *  <li>Save a bookmark of the specific location.
 * </ol>
 * 
 * @author Mistriel
 * 
 */
public class ChapterViewer extends Activity
{
    Bookmark bookmark ; 
    
    private String tractateName;

    private ActionBar actionBar;
    private OnNavigationListener mOnNavigationListener;
    private Menu activityMenu;

    
    
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	
	bookmark = getIntent().getExtras().getParcelable(AppConstants.BOOKMARK_INTENT_KEY);

	tractateName = bookmark.getTractate().getName();

	// in order to show the action bar activity title should be visible .
	// call to requestWindowFeature(Window.FEATURE_NO_TITLE) will cause null
	// return from getActionBar() http://stackoverflow.com/a/6989144
	actionBar = getActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

	actionBar.setIcon(ImageAdapter.sederIconsArray[bookmark.getSeder().ordinal()]);
	actionBar.setDisplayShowTitleEnabled(true);
	actionBar.setTitle(tractateName);

	setContentView(R.layout.chapter_view);
    }

    @Override
    protected void onResume()
    {
	super.onResume();

	setupSpinner(bookmark);
	invalidateOptionsMenu();
    }

    protected void setBookMarkIcon()
    {
	if(BookmarkUtils.isBookmarked(bookmark))
	{
	    activityMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.bookmark_diskette_active));
	}
	else
	{
	    activityMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.bookmark_diskette));
	}
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.chapter_view_menu, menu);
	
	activityMenu = menu ;
	
	menu.getItem(1).setTitle(tractateName).setEnabled(true); // set actionbar menu Textbokx with the tractate name
	return true;
    }

    
    @Override

    public boolean onPrepareOptionsMenu(Menu menu) 
    {

        if(BookmarkUtils.isBookmarked(bookmark))
	{
	    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.bookmark_diskette_active));
	}
        menu.getItem(1).setTitle(tractateName).setEnabled(true);
        return true ;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

	switch (item.getItemId()) 
	{
	case 0:
	    Toast.makeText(this, "Action Item 0 selected!", Toast.LENGTH_SHORT).show();
	    return true;
	    
	case R.id.menu_save_bookmark:
	    
	    if(BookmarkUtils.isBookmarked(bookmark))
	    {
		BookmarkUtils.removeBookmark(bookmark);
		item.setIcon(getResources().getDrawable(R.drawable.bookmark_diskette));
	    }
	    else
	    {
		BookmarkUtils.saveBookmark(bookmark);
		item.setIcon(getResources().getDrawable(R.drawable.bookmark_diskette_active));
	    }
	    
	    return true;
	    
	default:
	    return super.onOptionsItemSelected(item);
	}

    }

    private void setupSpinner(Bookmark iBookmrk)
    {
	
	mOnNavigationListener = new OnNavigationListener() {
	    
	    private boolean isFirstCall = true;

	    @Override
	    public boolean onNavigationItemSelected(int position, long itemId)
	    {
		if(isFirstCall)
		{
		    isFirstCall = false ;
		}
		else
		{
		    bookmark.setChapter(position + 1); // // because chapters start with 1, Navigation start with 0 . 
		}
		
		
		// Change bookmarkIcon 
		setBookMarkIcon();

		// Create new fragment from our own Fragment class
		CahpterTextFragment newFragment = new CahpterTextFragment();

		Bundle bundle = new Bundle();
		bundle.putParcelable(AppConstants.BOOKMARK_INTENT_KEY, bookmark);

		newFragment.setArguments(bundle);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment container with this
		// fragment
		// and give the fragment a tag name equal to the string at the
		// position selected
		ft.replace(R.id.myfragment, newFragment);
		// Apply changes
		ft.commit();
		return true;
	    }
	};
	
	actionBar.setListNavigationCallbacks(getSpinnerAdapter(), mOnNavigationListener);
	actionBar.setSelectedNavigationItem(iBookmrk.getChapter() - 1); // because chapters start with 1, Navigation start with 0 . 

    }

    private CharSequence[] getChaptersNames()
    {
	return DBAdapter.getInstance().getTractateChapterNames(bookmark.getTractate());
    }

    @Override
    protected void onPause()
    {
	// TODO handle on pause to save the text position .
	super.onPause();

    }

    private ArrayAdapter getSpinnerAdapter()
    {
	final ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, getChaptersNames());
	return spinnerArrayAdapter;
    }

    
    public void firstClicked(View v)
    {
	int val = bookmark.getSeder().ordinal();
	if (val == 0) return;

	refreshCurrentActivity(SederEnum.ZRAIIM);
    }

    public void previousClicked(View v)
    {
	int val = bookmark.getSeder().ordinal();
	if (val == 0) return;

	refreshCurrentActivity(SederEnum.values()[bookmark.getSeder().ordinal() - 1]);
    }

    public void nextClicked(View v)
    {
	int val = bookmark.getSeder().ordinal();
	if (val == 5) return;

	refreshCurrentActivity(SederEnum.values()[bookmark.getSeder().ordinal() + 1]);
    }

    public void lastClicked(View v)
    {
	int val = bookmark.getSeder().ordinal();
	if (val == 5) return;

	refreshCurrentActivity(SederEnum.TAHAROT);
    }

    private void refreshCurrentActivity(SederEnum seder)
    {
	bookmark.setSeder(seder) ;
    }
    
}
