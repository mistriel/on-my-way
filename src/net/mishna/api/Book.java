package net.mishna.api;

public class Book {

	protected String bookName ;
	
	protected int numberOfChapters ; 
	
	protected String[] chaptersNames ;

	
	
	
	public String getBookName()
	{
	    return bookName;
	}

	public void setBookName(String bookName)
	{
	    this.bookName = bookName;
	}

	public int getNumberOfChapters()
	{
	    return numberOfChapters;
	}

	public void setNumberOfChapters(int numberOfChapters)
	{
	    this.numberOfChapters = numberOfChapters;
	}

	public String[] getChaptersNames()
	{
	    return chaptersNames;
	}

	public void setChaptersNames(String[] chaptersNames)
	{
	    this.chaptersNames = chaptersNames;
	} 
	
	
}
