package persistency;

import java.io.IOException;

import model.Leerling;
import model.QuizOpdracht;

public class DBLeerlingLeesSchrijf extends DBTemplate {
	/**
	 * Klasse om Leerling objecten weg te schrijven of in te lezen in
	 * databaseformaat
	 *
	 * @author Bert Neyt
	 *
	 */

	public DBLeerlingLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM leerling";
	}

	@Override
	protected Leerling maakObject(Object[] rij) {
		int ID = (Integer) rij[0];
		String voorNaam = (String) rij[1];
		String familieNaam  = (String) rij[2];
		int leerjaar = (Integer) rij[3];
		return new Leerling(ID, voorNaam, familieNaam, leerjaar);
	}

	@Override
	protected String getSchrijfStatement(Object object) throws IOException {
		Leerling leerling = null;
		if (object instanceof Leerling) {
			leerling = (Leerling) object;
		} else {
			throw new IOException(
					"Het object om weg te schrijven is geen Leerling");
		}
		return String.format(
				"INSERT INTO leerling VALUES('%d', %s, %s, %d)",
				leerling.getID(), leerling.getLeerlingVoornaam(),
				leerling.getLeerlingFamilienaam(), leerling.getLeerjaar());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE leerling";
	}

}
