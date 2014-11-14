package persistency;

import java.io.IOException;
import model.Reproductie;

/**
 * Klasse om Reproductie objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 14/11/2014
 *
 */

public class TxtReproductieLeesSchrijf extends TxtTemplate {
	
	private TxtOpdrachtLeesSchrijf txtOpdrachtLeesSchrijf = new TxtOpdrachtLeesSchrijf();

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/reproductieOpdrachten.csv" : "resources/reproductieOpdrachten.txt";
	}

	@Override
	protected <T> T maakObject(String[] fields) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> String maakStringRecord(T object) throws IOException {
		Reproductie reproductie = null;
		if (object instanceof Reproductie) {
			reproductie = (Reproductie) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Reproductie");
		}		
		return String.format("%s\t%s\t%d", txtOpdrachtLeesSchrijf.maakStringRecord(object), reproductie.getJuisteTrefwoorden(), reproductie.getMinimumAantalTrefwoorden());	
	}

	@Override
	protected String getHeaderCSV() {
		return (txtOpdrachtLeesSchrijf.getHeaderCSV() + "\tTrefwoorden\tJuiste");
	}

}
