package net.mishna.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.mishna.R;
import net.mishna.api.Bookmark;
import net.mishna.utils.AppConstants;
import net.mishna.utils.AppUtils;
import net.mishna.utils.BookmarkUtils;

import java.util.ArrayList;
import java.util.List;

import static net.mishna.utils.AppUtils.isNullOrEmpty;

/**
 * This activity is responsible to display a list of all saved bookmarks . Each
 * list item is composed of 3 items :
 *
 * <ol>
 * <li>A bookmark icon (Active state) .
 * <li>A text representing the bookmark
 * <li>A delete button for removing the icon selected .
 * </ol>
 *
 * @author Mistriel
 */
public class BookmarksListActivity extends ListActivity {

    private List<Bookmark> bookmarks;
    private BookmarksListAdapter mAdapter;


    // Activity lifecycle .
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks_layout);

    }

    public void onSetEmpty(View v) {
        mAdapter.changeData(null);
    }

    public void onSetData(View v) {
        mAdapter.changeData(bookmarks);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // On Activity resume, might be that some bookmarks were canceled .
        if (!BookmarkUtils.isBookmarksEmpty()) {
            bookmarks = new ArrayList<Bookmark>(BookmarkUtils.getBookmarks());
        } else {
            bookmarks = new ArrayList<Bookmark>(10);
        }

        mAdapter = new BookmarksListAdapter();
        setListAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Bookmark bookmark = bookmarks.get(position);

        Intent intent = new Intent(this, ChapterViewer.class);
        intent.putExtra(AppConstants.BOOKMARK_INTENT_KEY, bookmark);

        startActivity(intent);
    }


    /**
     * A pretty basic ViewHolder used to keep references on children {@link View}s.
     */
    private static class BookmarkViewHolder {
        public TextView bookmarkContent;
        public ImageView deleteBtn;
        public ImageView sederIcon;
    }

    /**
     * The Adapter used in the demonstration.
     */
    private class BookmarksListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bookmarks.size();
        }

        @Override
        public String getItem(int position) {
            return bookmarks.get(position).toString(); // TODO - change the BookMark to test
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void changeData(List<Bookmark> bookmarks) {
            if (!isNullOrEmpty(bookmarks)) {
                notifyDataSetChanged();
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BookmarkViewHolder holder = null;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.bookmark_item, parent, false);

                holder = new BookmarkViewHolder();
                holder.bookmarkContent = (TextView) convertView.findViewById(R.id.bookmark_txt_label);

                holder.sederIcon = (ImageView) convertView.findViewById(R.id.btn_seder_icon);

                holder.deleteBtn = (ImageView) convertView.findViewById(R.id.btn_bookmark_delete);
                holder.deleteBtn.setOnClickListener(mDeleteButtonClickListener);

                convertView.setTag(holder);
            } else {
                holder = (BookmarkViewHolder) convertView.getTag();
            }
            Bookmark curBmrk = bookmarks.get(position);

            holder.bookmarkContent.setText(BookmarkUtils.getBookmarkTractateLabel(curBmrk) + " " + AppUtils.getStringRes(R.string.chapter) + " " + curBmrk.getChapter());
            holder.sederIcon.setImageResource(ImageAdapter.sederIconsArray[curBmrk.getSeder().ordinal()]);

            return convertView;
        }
    }


    private OnClickListener mDeleteButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = getListView().getPositionForView(v);
            if (position != ListView.INVALID_POSITION) {
                removeBookmark(position);
            }
        }
    };


    // Removes bookmarks from the bookmarks list in a specific position .
    private void removeBookmark(int position) {
        Bookmark toBeRemoved = bookmarks.get(position);

        bookmarks.remove(toBeRemoved);
        BookmarkUtils.removeBookmark(toBeRemoved);

        mAdapter.notifyDataSetChanged();
    }


}
