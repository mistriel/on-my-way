package net.mishna.api;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class representing a specific location ({@link Mishna} in the 6 Sedarim .
 * 
 * @author Mistriel
 * 
 */
public class Bookmark implements Parcelable
{
    private SederEnum seder;
    private TractateEnum tractate;
    private int chapter = 1;
    private int mishna = 1;

    public Bookmark(Parcel in)
    {
	readFromParcel(in);
    }
    
    public Bookmark()
    {
	seder = SederEnum.values()[0];
	tractate = TractateEnum.values()[0];
	chapter = 1;
	mishna = 1;
    }
    
    public Bookmark(SederEnum iSeder)
    {
	seder = iSeder ;
	tractate = seder.getSederTractates()[0];
	chapter = 1;
	mishna = 1;
    }
    
    public Bookmark(SederEnum iSeder, TractateEnum iTractate)
    {
	seder = iSeder ;
	tractate = iTractate ;
	chapter = 1;
	mishna = 1;
    }
    
    public Bookmark(SederEnum iSeder, TractateEnum iTractate, int iChapter)
    {
	seder = iSeder ;
	tractate = iTractate ;
	chapter = iChapter;
	mishna = 1;
    }
    
    public Bookmark(SederEnum iSeder, TractateEnum iTractate, int iChapter, int iMishna)
    {
	seder = iSeder ;
	tractate = iTractate ;
	chapter = iChapter;
	mishna = iMishna;
    }
    
    
    
    

    /**
     * @return the seder
     */
    public SederEnum getSeder()
    {
        return seder;
    }

    /**
     * @param seder the seder to set
     */
    public void setSeder(SederEnum seder)
    {
        this.seder = seder;
    }

    /**
     * @return the tractate
     */
    public TractateEnum getTractate()
    {
        return tractate;
    }

    /**
     * @param tractate the tractate to set
     */
    public void setTractate(TractateEnum tractate)
    {
        this.tractate = tractate;
    }

    /**
     * @return the chapter
     */
    public int getChapter()
    {
        return chapter;
    }

    /**
     * @param chapter the chapter to set
     */
    public void setChapter(int chapter)
    {
        this.chapter = chapter;
    }

    /**
     * @return the mishna
     */
    public int getMishna()
    {
        return mishna;
    }

    /**
     * @param mishna the mishna to set
     */
    public void setMishna(int mishna)
    {
        this.mishna = mishna;
    }

    @Override
    public int describeContents()
    {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
	dest.writeInt(seder.ordinal());
	dest.writeInt(tractate.ordinal());
	dest.writeInt(chapter);
	dest.writeInt(mishna);
    }

    /**
     * 
     * Called from the constructor to create this object from a parcel.
     * 
     * @param in
     *            parcel from which to re-create object
     */
    private void readFromParcel(Parcel in)
    {
	seder = SederEnum.values()[in.readInt()];
	tractate = TractateEnum.values()[in.readInt()];
	chapter = in.readInt();
	mishna = in.readInt();
    }

    /**
     * 
     * This field is needed for Android to be able to create new objects,
     * individually or as arrays.
     * 
     * This also means that you can use use the default constructor to create
     * the object and use another method to hyrdate it as necessary.
     * 
     * I just find it easier to use the constructor. It makes sense for the way
     * my brain thinks ;-)
     * 
     */
    public static final Parcelable.Creator<Bookmark> CREATOR = new Parcelable.Creator<Bookmark>() 
    {
	public Bookmark createFromParcel(Parcel in)
	{
	    return new Bookmark(in);
	}

	public Bookmark[] newArray(int size)
	{
	    return new Bookmark[size];
	}
    };

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + chapter;
	result = prime * result + mishna;
	result = prime * result + ((seder == null) ? 0 : seder.ordinal());
	result = prime * result + ((tractate == null) ? 0 : tractate.ordinal());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
	if (this == obj) return true;
	if (obj == null) return false;
	if (getClass() != obj.getClass()) return false;
	Bookmark other = (Bookmark) obj;
	if (chapter != other.chapter) return false;
	if (mishna != other.mishna) return false;
	if (seder != other.seder) return false;
	if (tractate != other.tractate) return false;
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return String.format("Bookmark [%s, %s, %s, %s]", seder.name(), tractate.name(), chapter, mishna);
    }
    
    
    public Object clone()
    {
       return new Bookmark(seder, tractate, chapter, mishna);
    }
   

}
