package net.mishna.api;


/**
 * Enumeration of the "Seder" (Division of tractates into books) .
 *
 * @author Mistriel
 */
public enum SederEnum {

    ZRAIIM(
            TractateEnum.BRAHOT,
            TractateEnum.PEAA,
            TractateEnum.DEMAY,
            TractateEnum.KILAIIM,
            TractateEnum.SHVIIT,
            TractateEnum.TRUMOT,
            TractateEnum.MEASROT,
            TractateEnum.MEASER_SHENI,
            TractateEnum.HALA,
            TractateEnum.ORLA,
            TractateEnum.BIKURIM),

    MOEAD(
            TractateEnum.SHABAT,
            TractateEnum.ERUVIN,
            TractateEnum.PSAIIM,
            TractateEnum.SHKALIM,
            TractateEnum.YOMA,
            TractateEnum.SUCA,
            TractateEnum.BEITSA,
            TractateEnum.ROSH_HASHANA,
            TractateEnum.TAANIT,
            TractateEnum.MEGILA,
            TractateEnum.MOED_KATAN,
            TractateEnum.HAGIGA),

    NASHIM(
            TractateEnum.YEVAMOT,
            TractateEnum.KTUBOT,
            TractateEnum.NEDARIM,
            TractateEnum.NAZIR,
            TractateEnum.SOTA,
            TractateEnum.GITIN,
            TractateEnum.KIDUSHIN),

    NEZIKIM(
            TractateEnum.BABA_KAMA,
            TractateEnum.BABA_METSIA,
            TractateEnum.BABA_BATRA,
            TractateEnum.SANHEDRIN,
            TractateEnum.MAKOT,
            TractateEnum.SHVUOT,
            TractateEnum.EDUYOT,
            TractateEnum.AVODA_ZARA,
            TractateEnum.AVOT,
            TractateEnum.HORAYOT),

    KODASHIM(
            TractateEnum.ZVAHIM,
            TractateEnum.MENAHOT,
            TractateEnum.HULIN,
            TractateEnum.BECHOROT,
            TractateEnum.ARACHIN,
            TractateEnum.TMURA,
            TractateEnum.KRETOT,
            TractateEnum.MEILA,
            TractateEnum.TAMID,
            TractateEnum.MIDOT,
            TractateEnum.KANIM),

    TAHAROT(
            TractateEnum.KELIM,
            TractateEnum.AHALOT,
            TractateEnum.NEGAIIM,
            TractateEnum.PARA,
            TractateEnum.TAHAROT,
            TractateEnum.MIKVAOT,
            TractateEnum.NIDA,
            TractateEnum.MACHSHIRIN,
            TractateEnum.ZAVIM,
            TractateEnum.TVULYOM,
            TractateEnum.YADAIIM,
            TractateEnum.OKATSIN);


    private TractateEnum[] trac;

    private SederEnum(TractateEnum... tractates) {
        this.trac = tractates;
    }

    public TractateEnum[] getSederTractates() {
        return trac;
    }


}
