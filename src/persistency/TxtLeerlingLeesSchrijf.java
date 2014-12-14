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
class TxtLeerlingLeesSchrijf extends TxtTemplate {

	public TxtLeerlingLeesSchrijf(boolean useCSV) {
		super(useCSV);
	}

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/leerlingen.csv" : "resources/leerlingen.txt";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Leerling maakObject(String[] fields) throws IOException {
		try {
			return new Leerling(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]));
		} catch (NumberFormatException Nex) {
			throw new IOException("Fout bij het parsen van het leerjaar en/of ID", Nex);
		} catch (IndexOutOfBoundsException Iex) {
			throw new IOException("Een record bevatte te weinig velden om een Leerling aan te maken", Iex);
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
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
		return String.format("%d\t%s\t%s\t%d", leerling.getID(), leerling.getLeerlingVoornaam(), leerling.getLeerlingFamilienaam(),
				leerling.getLeerjaar());
	}

	@Override
	protected String getHeaderCSV() {
		return "ID\tVoornaam\tFamilienaam\tLeerjaar";
	}

}
