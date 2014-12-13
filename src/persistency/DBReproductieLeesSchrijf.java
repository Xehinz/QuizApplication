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

class DBReproductieLeesSchrijf extends DBOpdrachtLeesSchrijf {

	DBReproductieLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM reproductie_opdracht";
	}

	@Override
	protected Opdracht maakObject(Object[] rij) {
		int ID = Integer.parseInt(rij[0].toString());
		Datum datum = new Datum(rij[1].toString());
		String vraag = rij[2].toString();
		int maxPogingen = Integer.parseInt(rij[3].toString());
		int maxTijd	= Integer.parseInt(rij[4].toString());
		OpdrachtCategorie categorie = OpdrachtCategorie.valueOf(rij[5].toString());
		Leraar auteur = Leraar.valueOf(rij[6].toString());
		String hints = rij[7].toString();
		String antwoord = rij[8].toString();
		int minTrefwoorden = Integer.parseInt(rij[9].toString());
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
		return String.format("%s'%s', '%s')", this.opdrachtSchrijfStatement(opdracht), opdracht.getJuisteAntwoord(), opdracht.getMinimumAantalTrefwoorden());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE reproductie_opdracht";
	}

}
