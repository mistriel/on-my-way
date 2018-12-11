package net.mishna.api;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single Tranctae logic .
 * Means the distribution of Tranctate into chapters and
 *
 * @author Mistriel
 */
public class Tractate extends Book {

    private int tranctateOrdinalNumber;


    public int getTranctateOrdinalNumber() {
        return tranctateOrdinalNumber;
    }


    public void setTranctateOrdinalNumber(int tranctateOrdinalNumber) {
        this.tranctateOrdinalNumber = tranctateOrdinalNumber;
    }


    List<Mishna> mishnayot = new ArrayList<Mishna>();


    public String getTranctateName() {
        return bookName;
    }


    public void setTranctateName(String tranctateName) {
        bookName = tranctateName;
    }


    public List<Mishna> getMishnayot() {
        return mishnayot;
    }


    public void setMishnayot(List<Mishna> mishnayot) {
        this.mishnayot = mishnayot;
    }


}
