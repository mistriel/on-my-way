package net.mishna.api;

public class Mishna {

    private SederEnum seder;
    private TractateEnum tractate;
    private int chapter;
    private int mishnaNumber;
    private String text;


    public SederEnum getSeder() {
        return seder;
    }

    public void setSeder(SederEnum seder) {
        this.seder = seder;
    }

    public TractateEnum getTractate() {
        return tractate;
    }

    public void setTractate(TractateEnum tractate) {
        this.tractate = tractate;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getMishnaNumber() {
        return mishnaNumber;
    }

    public void setMishnaNumber(int mishnaNumber) {
        this.mishnaNumber = mishnaNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
