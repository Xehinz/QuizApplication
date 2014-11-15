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

	@Override
	public String toString() {
		return voornaam + " " + familienaam;
	}

}
