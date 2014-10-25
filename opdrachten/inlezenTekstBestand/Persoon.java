package inlezenTekstBestand;

import util.datumWrapper.Datum;

public class Persoon {

	private Datum geboorteDatum;
	private String geboorteDatumString;
	private String naam;

	public Persoon(String geboorteDatum, String naam) {

		try {
			this.geboorteDatum = new Datum(geboorteDatum);
		} catch (Exception ex) {
			this.geboorteDatum = null;
		}

		this.naam = naam;
		this.geboorteDatumString = geboorteDatum;
	}

	public Persoon(Datum geboorteDatum, String naam) {
		this.geboorteDatum = geboorteDatum;
		this.geboorteDatumString = geboorteDatum.toString();
		this.naam = naam;
	}

	public Datum getGeboorteDatum() {
		return geboorteDatum;
	}

	public String getGeboorteDatumString() {
		return geboorteDatumString;
	}

	public String getNaam() {
		return naam;
	}

	@Override
	public String toString() {
		return naam + "\t" + geboorteDatumString;
	}

}
