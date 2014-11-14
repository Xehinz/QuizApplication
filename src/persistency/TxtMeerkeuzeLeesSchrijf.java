package persistency;

import java.io.IOException;

import model.Meerkeuze;

/**
 * Klasse om Meerkeuze objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 14/11/2014
 *
 */

public class TxtMeerkeuzeLeesSchrijf extends TxtTemplate {
	
	private TxtOpdrachtLeesSchrijf txtOpdrachtLeesSchrijf = new TxtOpdrachtLeesSchrijf();

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/meerkeuzeOpdrachten.csv" : "resources/meerkeuzeOpdrachten.txt";
	}

	@Override
	protected <T> T maakObject(String[] fields) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> String maakStringRecord(T object) throws IOException {
		Meerkeuze meerkeuze = null;
		if (object instanceof Meerkeuze) {
			meerkeuze = (Meerkeuze) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Meerkeuze");
		}
		String opties = new String();
		for (String s : meerkeuze.getOpties()) {
			opties += String.format("%s|", s);
		}
		return String.format("%s\t%s\t%s", txtOpdrachtLeesSchrijf.maakStringRecord(object), opties, meerkeuze.getJuisteAntwoord());
	}

	@Override
	protected String getHeaderCSV() {
		return (txtOpdrachtLeesSchrijf.getHeaderCSV() + "\tOpties\tJuiste Optie");
	}

}
