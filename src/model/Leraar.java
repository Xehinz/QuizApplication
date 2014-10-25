package model;

public enum Leraar {
	MARIA_AERTS("Maria", "Aerts"), JOS_VERBEEK("Jos", "Verbeek"), STEVEN_OPDEBEEK("Steven", "Opdebeek"), CHARLOTTE_NEVEN(
			"Charlotte", "Neven"), MIEKE_WITTEMANS("Mieke", "Wittemans");

	private String voornaam;
	private String familienaam;

	private Leraar(String voornaam, String familienaam) {
		this.voornaam = voornaam;
		this.familienaam = familienaam;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

}
