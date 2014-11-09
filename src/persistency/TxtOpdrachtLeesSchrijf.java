package persistency;

import java.io.IOException;

import model.Leerling;
import model.Opdracht;

/**
 * Klasse om Opdracht objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 09/11/2014
 *
 */

public class TxtOpdrachtLeesSchrijf extends TxtTemplate {
	
	public TxtOpdrachtLeesSchrijf() {
	}

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/opdrachten.csv" : "resources/opdrachten.txt";
	}

	@Override
	protected Opdracht maakObject(String[] fields) throws IOException {
		try {
			//return new Opdracht();
			return null;
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		Opdracht opdracht = null;
		if (object instanceof Opdracht) {
			opdracht = (Opdracht) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen opdracht");
		}
		return String.format("%s;%s", "", "");
	}

	@Override
	protected String getHeaderCSV() {
		return "header";
	}
}
