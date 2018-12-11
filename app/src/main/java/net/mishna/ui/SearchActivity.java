package net.mishna.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import net.mishna.BaseApplication;
import net.mishna.R;
import net.mishna.api.Bookmark;
import net.mishna.api.SederEnum;
import net.mishna.model.SearchActivityService;
import net.mishna.model.SearchResult;
import net.mishna.utils.AppConstants;
import net.mishna.utils.BookmarkUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.mishna.utils.AppUtils.isNullOrEmpty;


public class SearchActivity extends ListActivity {
    private List<SearchResult> resultList;
    private SearchResultsListAdapter searchAdapter;

    private EditText searchView;

    Set<SederEnum> activeSederSetInSearch;

    private String query;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);

        searchView = (EditText) findViewById(R.id.inputSearch);
        searchView.setOnEditorActionListener(searchButtonListener);

        BaseApplication.getContext();

        activeSederSetInSearch = new HashSet<SederEnum>(Arrays.asList(SederEnum.values()));
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.SederCheckBox01:
                if (checked) {
                    activeSederSetInSearch.add(SederEnum.ZRAIIM);
                } else {
                    activeSederSetInSearch.remove(SederEnum.ZRAIIM);
                }
                break;
            case R.id.SederCheckBox02:
                if (checked) {
                    activeSederSetInSearch.add(SederEnum.MOEAD);
                } else {
                    activeSederSetInSearch.remove(SederEnum.MOEAD);
                }
                break;
            case R.id.SederCheckBox03:
                if (checked) {
                    activeSederSetInSearch.add(SederEnum.NASHIM);
                } else {
                    activeSederSetInSearch.remove(SederEnum.NASHIM);
                }
                break;
            case R.id.SederCheckBox04:
                if (checked) {
                    activeSederSetInSearch.add(SederEnum.NEZIKIM);
                } else {
                    activeSederSetInSearch.remove(SederEnum.NEZIKIM);
                }
                break;
            case R.id.SederCheckBox05:
                if (checked) {
                    activeSederSetInSearch.add(SederEnum.KODASHIM);
                } else {
                    activeSederSetInSearch.remove(SederEnum.KODASHIM);
                }
                break;
            case R.id.SederCheckBox06:
                if (checked) {
                    activeSederSetInSearch.add(SederEnum.TAHAROT);
                } else {
                    activeSederSetInSearch.remove(SederEnum.TAHAROT);
                }
        }
    }


    private void doQuery(String query) {
        SearchActivityService searchService = new SearchActivityService();
        searchService.setActiveSeders(activeSederSetInSearch);

        resultList = searchService.queryTractates(query);

        if (!isNullOrEmpty(resultList)) {
            searchAdapter.notifyDataSetChanged();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // On Activity resume, might be that some resultList ca
        if (isNullOrEmpty(resultList)) {
            resultList = new ArrayList<SearchResult>(10);
        }


        searchAdapter = new SearchResultsListAdapter();

        setListAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SearchResult result = resultList.get(position);

        Intent intent = new Intent(this, ChapterViewer.class);
        intent.putExtra(AppConstants.BOOKMARK_INTENT_KEY, result.getBookmark());

        startActivity(intent);
    }


    OnEditorActionListener searchButtonListener = new OnEditorActionListener() {


        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            query = searchView.getText().toString();

            if (query != null) {
                CheckBox chk = (CheckBox) findViewById(R.id.WholeWordCheckBox);
                boolean searchWholeWord = chk.isChecked();

                if (searchWholeWord) {
                    query = query + " ";
                }

                doQuery(query);
            }
            return true;
        }
    };

    /**
     * A pretty basic ViewHolder used to keep references on children
     * {@link View}s.
     */
    private class SearchResultView {
        public TextView resultTitle; // holds the title of the search result, E.g Bookmark
        public TextView resultContent; // holds the content of the search result, E.g Mishna
    }

    /**
     * The Adapter used in the demonstration.
     */
    private class SearchResultsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int position) {
            return resultList.get(position).toString();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SearchResultView holder = null;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.search_activity_item, parent, false);

                holder = new SearchResultView();
                holder.resultTitle = (TextView) convertView.findViewById(R.id.searchResultTitle);
                holder.resultContent = (TextView) convertView.findViewById(R.id.searchResultContent);

                convertView.setTag(holder);
            } else {
                holder = (SearchResultView) convertView.getTag();
            }
            SearchResult curResult = resultList.get(position);
            Bookmark curBmrk = curResult.getBookmark();

            holder.resultTitle.setText(BookmarkUtils.getBookmarkLabel(curBmrk));

            String queryContent = curResult.getBookmarkContent();
            queryContent = queryContent.replaceAll(query, "<font color='#FFFB08'>" + query + "</font>");

            holder.resultContent.setText(Html.fromHtml(queryContent));

            return convertView;
        }
    }


}
