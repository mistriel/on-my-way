package net.mishna.model;

import static net.mishna.utils.AppUtils.Space;
import static net.mishna.utils.AppUtils.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.mishna.api.Bookmark;
import net.mishna.api.Mishna;
import net.mishna.api.SederEnum;
import net.mishna.api.TractateEnum;
import net.mishna.utils.DBAdapter;

/**
 * This class represents the pure data model of the Search Activity functionality .
 * It's meant to holds the data related to searching through all books in DB .
 * <p>
 * Searching functionality .
 * The searching is based on a specified query input, this query
 * <p>
 * The model is mutable through it's accessor methods, who manipulates model state .
 * Any assigned view is capable of accessing these methods and changing the model's state .
 *
 * @author Mistriel
 */
public class SearchActivityService {
    private String queryInput = null;
    private SearchResult choosenResult = null;
    private List<SearchResult> resultList = null;

    Set<SederEnum> activeSederSetInSearch = null;
    private ArrayList<TractateEnum> activeTractates;


    public SearchActivityService() {
        // TODO Auto-generated constructor stub
    }

    // mutator methods .

    public List<SearchResult> queryTractates(String query) {
        if (isNullOrEmpty(query) || query.length() < 2 || Space.equals(query)) // query is empty or too small .
        {
            return Collections.emptyList();
        }

        DBAdapter dbAdapter = DBAdapter.getInstance();

        List<Mishna> mishnayot = dbAdapter.queryTractates(findActiveTractates(), query);

        if (mishnayot.isEmpty()) {
            return Collections.emptyList();
        }

        resultList = new ArrayList<SearchResult>(mishnayot.size());


        for (Mishna mishna : mishnayot) {
            Bookmark bookmark = new Bookmark(mishna.getSeder(), mishna.getTractate(), mishna.getChapter(), mishna.getMishnaNumber());
            SearchResult sr = new SearchResult(query, bookmark, mishna.getText());

            resultList.add(sr);
        }

        return resultList;
    }

    private List<TractateEnum> findActiveTractates() {

        if (isNullOrEmpty(activeSederSetInSearch) | activeSederSetInSearch.size() == 6) {
            activeTractates = new ArrayList<TractateEnum>(Arrays.asList(TractateEnum.values()));
        } else {
            activeTractates = new ArrayList<TractateEnum>(56);
            for (SederEnum seder : activeSederSetInSearch) {
                activeTractates.addAll(new ArrayList<TractateEnum>(Arrays.asList(seder.getSederTractates())));
            }
        }
        return activeTractates;
    }


    /**
     * List the list of Seders that will participate in the search query .
     *
     * @param books
     */
    public void setActiveSeders(Set<SederEnum> activeSeders) {
        activeSederSetInSearch = activeSeders;
    }


}
