package persistency;

import java.io.IOException;

import model.Leerling;

/**
 * Klasse om Leerling objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Ben Vandenberk
 * @version 04/11/2014
 *
 */
public class TxtLeerlingLeesSchrijf extends TxtTemplate {

	public TxtLeerlingLeesSchrijf() {
	}

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/leerlingen.csv" : "resources/leerlingen.txt";
	}

	@Override
	protected Leerling maakObject(String[] fields) throws IOException {
		try {
			return new Leerling(fields[0], fields[1], Integer.parseInt(fields[2]));
		} catch (NumberFormatException Nex) {
			throw new IOException("Fout bij het parsen van het leerjaar", Nex);
		} catch (IndexOutOfBoundsException Iex) {
			throw new IOException("Een record bevatte te weinig velden om een Leerling aan te maken", Iex);
		} catch (Exception ex) {
			throw new IOException(ex.getMessage());
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		Leerling leerling = null;
		if (object instanceof Leerling) {
			leerling = (Leerling) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Leerling");
		}
		return String.format("%s;%s;%d", leerling.getLeerlingVoornaam(), leerling.getLeerlingFamilienaam(),
				leerling.getLeerjaar());
	}

	@Override
	protected String getHeaderCSV() {
		return "Voornaam;Familienaam;Leerjaar";
	}

}
