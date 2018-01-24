package net.mishna.utils;

import static net.mishna.utils.AppUtils.PREF_BOOKMARKS;
import static net.mishna.utils.AppUtils.PREF_LAST_READING_POSITION;
import static net.mishna.utils.AppUtils.Separator;
import static net.mishna.utils.AppUtils.Space;
import static net.mishna.utils.AppUtils.getPreference;
import static net.mishna.utils.AppUtils.getStringArrayFromRes;
import static net.mishna.utils.AppUtils.isNullOrEmpty;
import static net.mishna.utils.AppUtils.setPreference;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.mishna.R;
import net.mishna.api.Bookmark;
import net.mishna.api.SederEnum;
import net.mishna.api.Tractate;
import net.mishna.api.TractateEnum;
import android.content.SharedPreferences;
import android.util.Log;

/** 
 * Utility methods related to saving, removing, validating of bookmarks  .
 * 
 * Application {@link Bookmark}s are persisted in the application {@link SharedPreferences} framework,
 * Using the preferences key {@link PREF_BOOKMARKS} as a {@link Set} of strings .
 * 
 * <h2>Persist pattern </h2>
 * The enumerated bookmark fields are persisted as a one String separated with "#" sign .
 * where {@link SederEnum} is translated to it's name  {@link SederEnum#name() } 
 * where {@link TractateEnum} is translated to it's name  {@link TractateEnum#name() } 
 * and chapter & Mishna number are converted to strings .
 * 
 * E.g : NEZIKIM#AVOT#4#7 .
 * 
 * @author Mistriel
 *
 */
public class BookmarkUtils
{

    private static String TAG = BookmarkUtils.class.getSimpleName();
    
    private static Set<String> bookmarks;
    private static Map<Bookmark, String> bookmarksMap;

    // static constructor for static class .
    static
    {
	
	bookmarks = getPreference(PREF_BOOKMARKS, new HashSet<String>(10));
	
	bookmarksMap = new LinkedHashMap<Bookmark, String>(bookmarks.size());
	
	for (String bookmark : bookmarks)
	{
	    if(!isNullOrEmpty(bookmark))
	    {
		bookmarksMap.put(bookmarkStringToBookmark(bookmark), bookmark);
	    }
	}
    }
    
    
    
    /**
     *  Validates if a specific {@link Bookmark} is stored in Application Bookmarks . 
     * @param bookmark
     * @return true if bookmark was found 
     */
    public static boolean isBookmarked(Bookmark bookmark)
    {
	    return bookmarksMap.containsKey(bookmark);
    }
    
    /**
     * 
     * @return true if there is no bookmarks saved to app preferences  .
     */
    public static boolean isBookmarksEmpty()
    {
	    return bookmarksMap.isEmpty();
    }
    
    
    /**
     * 
     * @return all application bookmarks saved in {@link SharedPreferences} .
     */
    public static Set<Bookmark> getBookmarks()
    {
	if(isNullOrEmpty(bookmarks))
	    return null ;
	
	return bookmarksMap.keySet();
    }
    
    
    /**
     * Save a  specific {@link Bookmark} to Application Bookmarks . 
     * @param bookmark
     * @return
     */
    public static boolean saveBookmark(Bookmark bookmark)
    {
	Bookmark clone = (Bookmark) bookmark.clone(); // Using clone in order to prevent messing up HashMap when changing the pointer of the key value
	if (isBookmarked(clone))
	{
	    return true ;
	}
	
	String bookmarkStr = bookmarkToStringFromat(clone) ;
	
	boolean saveSuccessful = bookmarks.add(bookmarkStr);
	bookmarksMap.put(clone, bookmarkStr);
	
	saveSuccessful &=  setPreference(PREF_BOOKMARKS, bookmarks);
	
	if(!saveSuccessful)
	{
	    Log.e(TAG, "Unable to save bookmark into SharedPreferences ."); 
	    return false ;
	}
	
	Log.d(TAG, "Bookmark saved : " + bookmarkStr); 
	
	AppUtils.popShortToast(AppUtils.getStringRes(R.string.bookmarkAdded));
	
	return saveSuccessful ; 
    }
    
    /**
     * Remove a  specific {@link Bookmark} from Application Bookmarks .
     * @param bookmark
     * @return
     */
    public static boolean removeBookmark(Bookmark bookmark)
    {
	if (!isBookmarked(bookmark))
	{
	    return true ;
	}
	
	String bookmarkStr = bookmarkToStringFromat(bookmark) ;
	
	boolean saveSuccessful = bookmarks.remove(bookmarkStr);
	bookmarksMap.remove(bookmark);
	
	saveSuccessful &=  setPreference(PREF_BOOKMARKS, bookmarks);
	
	if(!saveSuccessful)
	{
	    Log.e(TAG, "Unable to remove bookmark from SharedPreferences ."); 
	    return false ;
	}
	
	Log.d(TAG, "Bookmark removed : " + bookmarkStr); 
	AppUtils.popShortToast(AppUtils.getStringRes(R.string.bookmarkRemoved));
	
	return saveSuccessful ; 
    }
    
    
    /**
     * 
     * @return the last reading position {@link Bookmark}, null if none found .
     */
    public static Bookmark getLastReadingPosition()
    {
	String lastPos = getPreference(PREF_LAST_READING_POSITION, "") ;
	
	if(isNullOrEmpty(lastPos))
	{
	    return null ;
	}
	
	return bookmarkStringToBookmark(lastPos);
    }
    
    
    	/**
	 * This method return the {@link TractateEnum} by it's Value input
	 * @param value
	 */
	public static Bookmark getBookmarkByTractateValue(String value)
	{
	    int counter = 0 ;
	    
	    for (String tractateVal : getStringArrayFromRes(R.array.tractate_array))
	    {
		if(tractateVal.equals(value))
		    break ;
		counter++;
	    }
	    
	    TractateEnum tractate = TractateEnum.values()[counter];
	    SederEnum seder = tractate.getTractateSeder() ;
	    
	    Bookmark bookmark = new Bookmark(seder, tractate);
	    return bookmark ;
	}
    
    
    /**
     * 
     * @return the last reading position {@link Bookmark}, null if none found .
     */
    public static boolean setLastReadingPosition(Bookmark bookmark)
    {
	String lastPos = bookmarkToStringFromat(bookmark) ;
	return setPreference(PREF_LAST_READING_POSITION, lastPos) ;
    }
    
    
    public static String getBookmarkTractateLabel(Bookmark bookmark)
    {
	return getStringArrayFromRes(R.array.tractate_array)[bookmark.getTractate().ordinal()] ;
    }
    
    public static String getBookmarkTractateLabel(TractateEnum tractate)
    {
	return getStringArrayFromRes(R.array.tractate_array)[tractate.ordinal()] ;
    }
    
    
    
    public static String getBookmarkSederLabel(Bookmark bookmark)
    {
	return getStringArrayFromRes(R.array.sdarim_array)[bookmark.getSeder().ordinal()] ;
    }
    
    /**
     * 
     * @param bookmark
     * @return returns a Local language representation of the bookmark input  . 
     */
    public static String getBookmarkLabel(Bookmark bookmark)
    {
	
	StringBuilder bookmarkStr = new StringBuilder();
	
	bookmarkStr.append(getBookmarkTractateLabel(bookmark));	
	bookmarkStr.append(Space);
	bookmarkStr.append(AppUtils.getStringRes(R.string.chapter));	
	bookmarkStr.append(Space);
	bookmarkStr.append(bookmark.getChapter());
	bookmarkStr.append(Space);
	bookmarkStr.append(AppUtils.getStringRes(R.string.mishna));
	bookmarkStr.append(Space);
	bookmarkStr.append(bookmark.getMishna());
	
	return bookmarkStr.toString() ;
    }
    
    
    
    // Convert a Bookmark formatted as String to the Boookmark enum form .
    private static Bookmark bookmarkStringToBookmark(String bookmark)
    {
	String params[] = bookmark.split(Separator);
	
	SederEnum seder = SederEnum.valueOf(params[0]);
	TractateEnum tractate = TractateEnum.valueOf(params[1]);
	int chapter = Integer.valueOf(params[2]);
	int mishna = Integer.valueOf(params[3]);
	
	return new Bookmark(seder, tractate, chapter, mishna);
    }

    
    private static String bookmarkToStringFromat(Bookmark bookmark)
    {
	StringBuilder bookmarkStr = new StringBuilder();
	
	bookmarkStr.append(bookmark.getSeder().name());	
	bookmarkStr.append(Separator);
	bookmarkStr.append(bookmark.getTractate().name());	
	bookmarkStr.append(Separator);
	bookmarkStr.append(String.valueOf(bookmark.getChapter()));
	bookmarkStr.append(Separator);
	bookmarkStr.append(String.valueOf(bookmark.getMishna()));
	
	return bookmarkStr.toString() ;
    }
    
    

    
   
    
}
