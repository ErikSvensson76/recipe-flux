package se.smelly.eat.models;

import com.fasterxml.jackson.annotation.JsonGetter;

public enum Measurement {
	
	ML("ml", "milliliter"),
	CL("cl", "centiliter"),
	DL("dl", "deciliter"),
	L("l", "liter"),
	KRM("krm", "kryddmått"),
	TSK("tsk", "tesked"),
	MSK("msk", "matsked"),
	ST("st","styck"),
	G("g","gram"),
	HG("hg","hekto"),
	KG("kg", "kilo");
	
	private String shortName;
	private String longName;
	
	private Measurement(String shortName, String longName) {
		this.shortName = shortName;
		this.longName = longName;
	}
	
	@JsonGetter
	public String getShort() {
		return shortName;
	}
	
	public String getFull() {
		return longName;
	}
}
