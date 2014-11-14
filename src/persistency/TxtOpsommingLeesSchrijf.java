package persistency;

import java.io.IOException;
import model.Opsomming;

/**
 * Klasse om Opsomming objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 14/11/2014
 *
 */

public class TxtOpsommingLeesSchrijf extends TxtTemplate {
	
	private TxtOpdrachtLeesSchrijf txtOpdrachtLeesSchrijf = new TxtOpdrachtLeesSchrijf();

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/opsommingOpdrachten.csv" : "resources/opsommingOpdrachten.txt";
	}

	@Override
	protected <T> T maakObject(String[] fields) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> String maakStringRecord(T object) throws IOException {
		Opsomming opsomming = null;
		if (object instanceof Opsomming) {
			opsomming = (Opsomming) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Opsomming");
		}
		return String.format("%s\t%s\t%s\t%d", txtOpdrachtLeesSchrijf.maakStringRecord(object), opsomming.getJuisteAntwoord(), opsomming.getInJuisteVolgorde(), opsomming.getAantalAntwoordenInOpsomming());		
	}

	@Override
	protected String getHeaderCSV() {
		return (txtOpdrachtLeesSchrijf.getHeaderCSV() + "\tAntwoorden\tIn Volgorde\tAantal Antwoorden");
	}

}
