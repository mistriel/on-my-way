package net.mishna.model;

import net.mishna.api.Bookmark;

public class SearchResult
{

    private String query ; 
    private Bookmark bookmark ; 
    private String bookmarkContent ;

    
    
    public SearchResult(String query, Bookmark bookmark, String bookmarkContent)
    {
	this.query = query;
	this.bookmark = bookmark;
	this.bookmarkContent = bookmarkContent;
    }

    /**
     * @return the query
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query)
    {
        this.query = query;
    }

    /**
     * @return the bookmark
     */
    public Bookmark getBookmark()
    {
        return bookmark;
    }

    /**
     * @param bookmark the bookmark to set
     */
    public void setBookmark(Bookmark bookmark)
    {
        this.bookmark = bookmark;
    }

    /**
     * @return the bookmarkContent
     */
    public String getBookmarkContent()
    {
        return bookmarkContent;
    }

    /**
     * @param bookmarkContent the bookmarkContent to set
     */
    public void setBookmarkContent(String bookmarkContent)
    {
        this.bookmarkContent = bookmarkContent;
    } 
    
    

    
}
