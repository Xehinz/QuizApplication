package model;

/**
 * Een enum voor het voorstellen van een opdracht categorie. Bevat 4
 * categorie&euml;n: Aardrijkskunde, Nederlands, Wetenschappen en Wiskunde.
 * 
 * @author Bert Neyt
 *
 */
public enum OpdrachtCategorie {
	AARDRIJKSKUNDE, NEDERLANDS, WETENSCHAPPEN, WISKUNDE;
	
	public String toString() {
		switch(this) {
		case AARDRIJKSKUNDE:
			return "Aardrijkskunde";			
		case NEDERLANDS:
			return "Nederlands";			
		case WETENSCHAPPEN:
			return "Wetenschappen";			
		case WISKUNDE:
			return "Wiskunde";
			default:
				return "";
		}
	}
}
