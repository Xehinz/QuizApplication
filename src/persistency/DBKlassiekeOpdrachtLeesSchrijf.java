package persistency;

/**
 * Klasse om Klassieke Opdracht objecten weg te schrijven of in te lezen in
 * databaseformaat
 *
 * @author Adriaan Kuipers
 * @version 11/12/2014
 *
 */

import java.io.IOException;

import util.datumWrapper.Datum;
import model.KlassiekeOpdracht;
import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;

class DBKlassiekeOpdrachtLeesSchrijf extends DBOpdrachtLeesSchrijf {

	DBKlassiekeOpdrachtLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM klassieke_opdracht"; 
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Opdracht maakObject(Object[] rij) {
		int ID = (Integer) rij[0];
		Datum datum = new Datum(rij[1].toString());
		String vraag = (String) rij[2];
		int maxPogingen = (Integer) rij[3];
		int maxTijd	= (Integer) rij[4];
		OpdrachtCategorie categorie = OpdrachtCategorie.valueOf((String)rij[5]);
		Leraar auteur = Leraar.valueOf((String)rij[6]);
		String hints = (String) rij[7];
		String antwoord = (String) rij[8];
		KlassiekeOpdracht opdracht =  new KlassiekeOpdracht(ID, datum, vraag, antwoord, maxPogingen, maxTijd, categorie, auteur);
		voegHintsToe(opdracht, hints);
		return opdracht;
	}

	@Override
	protected <T> String getSchrijfStatement(T object) throws IOException {
		KlassiekeOpdracht opdracht = null;
		if (object instanceof KlassiekeOpdracht) {
			opdracht = (KlassiekeOpdracht) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen KlassiekeOpdracht");
		}
		return String.format("%s '%s')", this.opdrachtSchrijfStatement(opdracht), opdracht.getJuisteAntwoord());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE klassieke_opdracht";
	}

}
