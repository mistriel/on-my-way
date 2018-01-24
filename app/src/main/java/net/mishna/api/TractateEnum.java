package net.mishna.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TractateEnum 
{
	BRAHOT("BRAHOT"),
	PEAA("PEAA"),
	DEMAY("DEMAY"),
	KILAIIM("KILAIIM"),
	SHVIIT("SHVIIT"),
	TRUMOT("TRUMOT"),
	MEASROT("MEASROT"),
	MEASER_SHENI("MEASER_SHENI"),
	HALA("HALA"),
	ORLA("ORLA"),
	BIKURIM("BIKURIM"),
	SHABAT("SHABAT"),
	ERUVIN("ERUVIN"),
	PSAIIM("PSAIIM"),
	SHKALIM("SHKALIM"),
	YOMA("YOMA"),
	SUCA("SUCA"),
	BEITSA("BEITSA"),
	ROSH_HASHANA("ROSH_HASHANA"),
	TAANIT("TAANIT"),
	MEGILA("MEGILA"),
	MOED_KATAN("MOED_KATAN"),
	HAGIGA("HAGIGA"),
	YEVAMOT("YEVAMOT"),
	KTUBOT("KTUBOT"),
	NEDARIM("NEDARIM"),
	NAZIR("NAZIR"),
	SOTA("SOTA"),
	GITIN("GITIN"),
	KIDUSHIN("KIDUSHIN"),
	BABA_KAMA("BABA_KAMA"),
	BABA_METSIA("BABA_METSIA"),
	BABA_BATRA("BABA_BATRA"),
	SANHEDRIN("SANHEDRIN"),
	MAKOT("MAKOT"),
	SHVUOT("SHVUOT"),
	EDUYOT("EDUYOT"),
	AVODA_ZARA("AVODA_ZARA"),
	AVOT("AVOT"),
	HORAYOT("HORAYOT"),
	ZVAHIM("ZVAHIM"),
	MENAHOT("MENAHOT"),
	HULIN("HULIN"),
	BECHOROT("BECHOROT"),
	ARACHIN("ARACHIN"),
	TMURA("TMURA"),
	KRETOT("KRETOT"),
	MEILA("MEILA"),
	TAMID("TAMID"),
	MIDOT("MIDOT"),
	KANIM("KANIM"),
	KELIM("KELIM"),
	AHALOT("AHALOT"),
	NEGAIIM("NEGAIIM"),
	PARA("PARA"),
	TAHAROT("TAHAROT"),
	MIKVAOT("MIKVAOT"),
	NIDA("NIDA"),
	MACHSHIRIN("MACHSHIRIN"),
	ZAVIM("ZAVIM"),
	TVULYOM("TVULYOM"),
	YADAIIM("YADAIIM"),
	OKATSIN("OKATSIN");
	
	private String name ;
	
	private TractateEnum(String name) 
	{
		this.name = name ;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * 
	 * @return returns the {@link SederEnum} that the tractate belongs to .
	 */
	public SederEnum getTractateSeder()
	{
	    SederEnum tractateSeder = null;
	    for (SederEnum seder : SederEnum.values())
	    {
		Set<TractateEnum> tractateSet = new HashSet<TractateEnum>(Arrays.asList(seder.getSederTractates()));
		
		if(tractateSet.contains(this))
		{
		    tractateSeder =  seder ;
		    break ;
		    
		}
	    }
	    return tractateSeder ;
	}
	
	
}

