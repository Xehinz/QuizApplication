package persistency;

/**
 * Klasse om Reproductie objecten weg te schrijven of in te lezen in
 * databaseformaat
 *
 * @author Adriaan Kuipers
 * @version 11/12/2014
 *
 */

import java.io.IOException;

import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Reproductie;
import util.datumWrapper.Datum;

public class DBReproductieLeesSchrijf extends DBOpdrachtLeesSchrijf {

	DBReproductieLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM reproductie";
	}

	@Override
	protected Opdracht maakObject(Object[] rij) {
		int ID = (Integer) rij[0];
		Datum datum = new Datum((String) rij[1]);
		String vraag = (String) rij[2];
		int maxPogingen = (Integer) rij[3];
		int maxTijd	= (Integer) rij[4];
		OpdrachtCategorie categorie = OpdrachtCategorie.valueOf((String)rij[5]);
		Leraar auteur = Leraar.valueOf((String)rij[6]);
		String hints = (String) rij[7];
		String antwoord = (String) rij[8];
		int minTrefwoorden = (Integer) rij[9];
		Reproductie opdracht =  new Reproductie(ID, datum, vraag, antwoord, minTrefwoorden,maxPogingen, maxTijd, categorie, auteur);		
		voegHintsToe(opdracht, hints);
		return opdracht;
	}

	@Override
	protected <T> String getSchrijfStatement(T object) throws IOException {
		Reproductie opdracht = null;
		if (object instanceof Reproductie) {
			opdracht = (Reproductie) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Reproductie");
		}
		return String.format("%s%s, '%s')", this.opdrachtSchrijfStatement(opdracht), opdracht.getJuisteAntwoord(), opdracht.getMinimumAantalTrefwoorden());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE reproductie";
	}

}
