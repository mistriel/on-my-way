package net.mishna.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.mishna.BaseApplication;
import net.mishna.api.Mishna;
import net.mishna.api.Tractate;
import net.mishna.api.TractateEnum;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter
{
    private static String TAG = DBAdapter.class.getSimpleName();
    public static final String DATABASE_NAME = "books.db";

    private static DBAdapter dbAdapter = null;

    public static final String TRACTATES_INFO_TABLE_NAME = "TRACTATES_INFO";

    private static final String ID_FIELD = "_id";
    private static final String CHAPTER_NUMBER_FIELD = "CHAPTER_NUM";
    private static final String MISHNA_NUMBER_FIELD = "MISHNA_NUM";
    private static final String MISHNA_TEXT_SOURCE_FIELD = "SOURCE";
    private static final String MISHNA_TEXT_SOURCE_NO_ACCENT_FIELD = "SOURCE_NO_ACCENT";

    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    private List<String> rawBooksNames;

    /**
     * Singleton class managing all DB functionality and transcations .
     * 
     * @param ctx
     * @return
     */
    public static DBAdapter getInstance()
    {
	if (dbAdapter == null)
	{
	    dbAdapter = new DBAdapter();
	}
	return dbAdapter;
    }

    private DBAdapter()
    {
	DBHelper = new DatabaseHelper(BaseApplication.getContext());
	init();

    }

    private void init()
    {
	List<String> books = new ArrayList<String>(65);  
	open();

	// SELECT name FROM sqlite_master WHERE type = "table" - sqlite select
	// to retrieve all books names
	Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

	c.moveToFirst();
	while (c.isAfterLast() == false)
	{
	    books.add(c.getString(0));
	    c.moveToNext();
	}

	close();
	books.remove("android_metadata");

	rawBooksNames = books;

    }

    /**
     * This method is responsible to create the DB (if not exist), and if exist
     * it will read it from /data/data/net.mishna/databases folder .
     * 
     * @param dbFullPath
     *            - full path without DB file name .
     * 
     */
    public static void initilizeApplicationDatabase(String dbFullPath, AssetManager assets)
    {
	File destFile = new File(dbFullPath + "/" + DATABASE_NAME);
	if (!destFile.exists())
	{
	    try
	    {
		final File dir = new File(dbFullPath);
		dir.mkdirs(); // create folders where write files
		destFile = new File(dir, DATABASE_NAME);

		Log.i(TAG, "Books DB is copied to destination path ");
		AppUtils.copyStream(assets.open("db/" + DATABASE_NAME), new FileOutputStream(destFile));
	    }
	    catch (IOException e)
	    {
		e.printStackTrace();
		Log.e(TAG, e.getMessage());
	    }
	}
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
	DatabaseHelper(Context context)
	{
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
	    // TODO - move the db creation to here . 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	    Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            
	    for (TractateEnum tractate : TractateEnum.values())
	    {
		db.execSQL("DROP TABLE IF EXISTS " + tractate.getName());
	    }
            onCreate(db);

	}
    }

    // ---opens the database---
    public DBAdapter open() throws SQLException
    {
	db = DBHelper.getWritableDatabase();
	return this;
    }

    // ---closes the database---
    public void close()
    {
	DBHelper.close();
    }

    /**
     * This method return a whole Tractate or only one Chapter .
     * 
     * @param tractate
     * @param chapter
     *            0 designates for all Tractate, 1..n for a specific chapter in
     *            the Tractate .
     * 
     * @return Tractate with all the chapters or the specified one
     * @throws SQLException
     */
    public Tractate getTractateChapter(TractateEnum tractate, int chapter) throws SQLException
    {
	Tractate tractateHolder = new Tractate();
	List<Mishna> mishnayot = new ArrayList<Mishna>();
	String chapterWhereClause = null;
	Cursor mCursor;

	if (chapter > 0)
	{
	    StringBuffer chapterWhereClauseBuffer = new StringBuffer();

	    chapterWhereClauseBuffer.append(CHAPTER_NUMBER_FIELD);
	    chapterWhereClauseBuffer.append("=");
	    chapterWhereClauseBuffer.append("'");
	    chapterWhereClauseBuffer.append(chapter);
	    chapterWhereClauseBuffer.append("'");

	    chapterWhereClause = chapterWhereClauseBuffer.toString();
	}

	try
	{
	    this.open();
	    mCursor = db.query(tractate.name(), new String[] { ID_FIELD, CHAPTER_NUMBER_FIELD, MISHNA_NUMBER_FIELD, MISHNA_TEXT_SOURCE_FIELD }, chapterWhereClause, null, null, null, null, null);
	    mCursor.moveToFirst();

	    tractateHolder.setTranctateName(tractate.name());
	    tractateHolder.setTranctateOrdinalNumber(tractate.ordinal() + 1);

	    if (mCursor != null)
	    {
		do
		{
		    Mishna mishna = new Mishna();
		    mishna.setChapter(Integer.valueOf(mCursor.getString(1)));
		    mishna.setMishnaNumber(Integer.valueOf(mCursor.getString(2)));
		    mishna.setText(mCursor.getString(3));

		    mishnayot.add(mishna);
		} while ((mCursor.moveToNext()));
	    }
	}
	finally
	{
	    this.close();
	}

	tractateHolder.setMishnayot(mishnayot);
	tractateHolder.setChaptersNames(getTractateChapterNames(tractate));

	return tractateHolder;
    }

    // TODO : add doc, add method input for query progress update . 
    
    public List<Mishna> queryTractates(List<TractateEnum> tractates, String query) throws SQLException
    {
	List<Mishna> mishnayot = new ArrayList<Mishna>(10);
	String queryWhereClause = null ;
	queryWhereClause = MISHNA_TEXT_SOURCE_NO_ACCENT_FIELD + " LIKE '%" + query + "%'";
	Cursor mCursor;
	
	try
	{
	    this.open();
	    
	    for (TractateEnum tractate : tractates)
	    {
		mCursor = db.query(tractate.name(), new String[] { ID_FIELD, CHAPTER_NUMBER_FIELD, MISHNA_NUMBER_FIELD, MISHNA_TEXT_SOURCE_NO_ACCENT_FIELD }, queryWhereClause, null, null, null, null, null);
		
		if (mCursor != null && mCursor.moveToFirst() )
		{
		    do
		    {
			Mishna mishna = new Mishna();
			mishna.setChapter(Integer.valueOf(mCursor.getString(1)));
			mishna.setMishnaNumber(Integer.valueOf(mCursor.getString(2)));
			mishna.setText(mCursor.getString(3));
			mishna.setTractate(tractate);
			mishna.setSeder(tractate.getTractateSeder());
			
			mishnayot.add(mishna);
		    } 
		    while ((mCursor.moveToNext()));
		}
	    }
	}
	catch(SQLException ex)
	{
	    Log.e(TAG, ex.getMessage());
	}
	finally
	{
	    this.close();
	}

	return mishnayot;
    }
    
    
    /**
     * Return the chapter names inside a Tractate . 
     * @param tractate
     * @return
     * @throws SQLException
     */
    public String[] getTractateChapterNames(TractateEnum tractate) throws SQLException
    {
	String chapterWhereClause = "TRACTATE_NAME" + "='" + tractate.name() + "'";
	Cursor mCursor;

	String chapterNamesRaw = null;

	try
	{
	    this.open();
	    mCursor = db.query(TRACTATES_INFO_TABLE_NAME, new String[] { "TRACTATE_NAME", "NUMBER_OF_CHAPTERS", "CHAPTER_NAMES" }, chapterWhereClause, null, null, null, null, null);
	    mCursor.moveToFirst();

	    if (mCursor != null)
	    {
		chapterNamesRaw = mCursor.getString(2);
	    }

	}
	finally
	{
	    this.close();
	}

	return chapterNamesRaw.split("#");
    }

    /**
     * Return all books names in the DB, Where each table in db file represents
     * one book .
     * 
     * @return list of book (Table) names in the DB
     */
    public String[] getAllTractateBooks()
    {
	return rawBooksNames.toArray(new String[rawBooksNames.size()]);
    }

}
