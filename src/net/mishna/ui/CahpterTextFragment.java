package net.mishna.ui;

import net.mishna.BaseApplication;
import net.mishna.R;
import net.mishna.api.Bookmark;
import net.mishna.api.Mishna;
import net.mishna.api.Tractate;
import net.mishna.api.TractateEnum;
import net.mishna.utils.AppConstants;
import net.mishna.utils.AppUtils;
import net.mishna.utils.BookmarkUtils;
import net.mishna.utils.DBAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CahpterTextFragment extends Fragment
{

    private Bookmark bookmark ;
    private TextView textView;
    private String fontName ;
    private int fontSize = 24;

    protected String chapterText;

    @Override
    public void onAttach(Activity activity)
    {
	// This is the first callback received; here we can set the text for
	// the fragment as defined by the tag specified during the fragment
	// transaction
	super.onAttach(activity);

	if (getArguments() != null)
	{
	    bookmark = getArguments().getParcelable(AppConstants.BOOKMARK_INTENT_KEY);
	}
	
	// Save last reading position to preferences
	BookmarkUtils.setLastReadingPosition(bookmark);
	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	View view = inflater.inflate(R.layout.frag1, container, false);
	
	fontName = AppUtils.getPreference(AppUtils.PREF_FONT, AppConstants.DEFAULT_FONT_NAME);
	fontSize = Integer.valueOf(AppUtils.getPreference(AppUtils.PREF_FONT_SIZE, "24"));

	textView = (TextView) view.findViewById(R.id.chapterText);
	textView.setTextSize(fontSize);
	
	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + fontName);
	textView.setTypeface(tf);

	if (getArguments() != null)
	{
	    initData();
	}

	return view;
    }

    private void initData()
    {
	new LoadChapter().execute(bookmark.getTractate(), bookmark.getChapter());
    }

    class LoadChapter extends AsyncTask<Object, Integer, Tractate>
    { //
	// Called to initiate the background activity
	@Override
	protected Tractate doInBackground(Object... params)
	{ //

	    TractateEnum trac = (TractateEnum)params[0];
	    int chapter = (Integer)params[1];
	    
	    DBAdapter db = DBAdapter.getInstance();

	    Tractate tractate = db.getTractateChapter(trac, chapter);
	   
	    return tractate;
	}

	@Override
	protected void onPostExecute(Tractate tractate)
	{ //
	    StringBuffer string = new StringBuffer();
	    for (Mishna m : tractate.getMishnayot())
	    {
		string.append(getString(R.string.mishna) + " " + m.getMishnaNumber() );
		string.append("\n");
		string.append(m.getText());
		string.append("\n");
		string.append("\n");
	    }
	    chapterText = string.toString();
	    
	    textView.setText(chapterText);
	}

    }

}
