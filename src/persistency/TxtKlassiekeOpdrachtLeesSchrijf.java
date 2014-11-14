package persistency;

import java.io.IOException;
import model.KlassiekeOpdracht;

/**
 * Klasse om KlassiekeOpdracht objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 14/11/2014
 *
 */

public class TxtKlassiekeOpdrachtLeesSchrijf extends TxtTemplate {
	
	private TxtOpdrachtLeesSchrijf txtOpdrachtLeesSchrijf = new TxtOpdrachtLeesSchrijf();
	
	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/klassiekeOpdrachten.csv" : "resources/klassiekeOpdrachten.txt";
	}

	@Override
	protected <T> T maakObject(String[] fields) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected <T> String maakStringRecord(T object) throws IOException {
		KlassiekeOpdracht klassiekeOpdracht = null;
		if (object instanceof KlassiekeOpdracht) {
			klassiekeOpdracht = (KlassiekeOpdracht) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen KlassiekeOpdracht");
		}
		return String.format("%s\t%s", txtOpdrachtLeesSchrijf.maakStringRecord(object), klassiekeOpdracht.getJuisteAntwoord());		
	}

	@Override
	protected String getHeaderCSV() {
		return (txtOpdrachtLeesSchrijf.getHeaderCSV() + "\tAntwoord");
	}

}
