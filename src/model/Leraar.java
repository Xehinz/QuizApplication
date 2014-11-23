package model;

/**
 * Een enumeratie van Leraren met voornaam en familienaam.
 * 
 * @author Ben Vandenberk
 *
 */
public enum Leraar {
	MARIA_AERTS("Maria", "Aerts"), JOS_VERBEEK("Jos", "Verbeek"), STEVEN_OPDEBEEK("Steven", "Opdebeek"), CHARLOTTE_NEVEN(
			"Charlotte", "Neven"), MIEKE_WITTEMANS("Mieke", "Wittemans");

	private String voornaam;
	private String familienaam;

	private Leraar(String voornaam, String familienaam) {
		this.voornaam = voornaam;
		this.familienaam = familienaam;
	}

	/**
	 * Haalt de voornaam van de Leraar op
	 * 
	 * @return de String met de voornaam
	 */
	public String getVoornaam() {
		return voornaam;
	}

	/**
	 * Haalt de familienaam van de Leraar op
	 * 
	 * @return de String met de familienaam
	 */
	public String getFamilienaam() {
		return familienaam;
	}
	
	/**
	 * Geeft de Leraar op basis van de meegegeven naam
	 * 
	 * @param volledigeNaam de String met de naam ("voornaam familienaam")
	 * @return de gewenste Leraar
	 * @throws IllegalArgumentException als er geen Leraar bestaat voor de meegegeven naam
	 */
	public static Leraar getLeraar(String volledigeNaam) throws IllegalArgumentException {
		
		volledigeNaam = volledigeNaam.trim().toLowerCase();
		switch(volledigeNaam) {		
		case "maria aerts":
			return Leraar.MARIA_AERTS;
		case "jos verbeek":
			return Leraar.JOS_VERBEEK;
		case "steven opdebeek":
			return Leraar.STEVEN_OPDEBEEK;
		case "charlotte neven":
			return Leraar.CHARLOTTE_NEVEN;
		case "mieke wittemans":
			return Leraar.MIEKE_WITTEMANS;
		}
		throw new IllegalArgumentException("Er bestaat geen Leraar met als naam " + volledigeNaam);
	}

	@Override
	public String toString() {
		return voornaam + " " + familienaam;
	}

}
