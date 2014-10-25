package model;

public class Leerling {

	private String leerlingVoornaam, leerlingFamilienaam;
	private int leerjaar;

	public Leerling(String voornaam, String familienaam, int leerjaar) {
		setLeerlingVoornaam(voornaam);
		setLeerlingFamilienaam(familienaam);
		setLeerjaar(leerjaar);
	}

	public String getLeerlingVoornaam() {
		return leerlingVoornaam;
	}

	public String getLeerlingFamilienaam() {
		return leerlingFamilienaam;
	}

	public int getLeerjaar() {
		return leerjaar;
	}

	public String getNaam() {
		return leerlingVoornaam + " " + leerlingFamilienaam;
	}

	public void setLeerjaar(int leerjaar) {
		if (leerjaar < 1 || leerjaar > 6) {
			throw new IllegalArgumentException("Leerjaar moet een waarde aannemen van 1 tot en met 6");
		}
		this.leerjaar = leerjaar;
	}

	public void setLeerlingVoornaam(String voornaam) {
		this.leerlingVoornaam = voornaam;
	}

	public void setLeerlingFamilienaam(String familienaam) {
		this.leerlingFamilienaam = familienaam;
	}

	@Override
	public String toString() {
		return "Leerling " + getNaam();
	}

}
