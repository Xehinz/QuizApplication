package persistency;

/**
 * Klasse om Opsomming objecten weg te schrijven of in te lezen in
 * databaseformaat
 *
 * @author Adriaan Kuipers
 * @version 11/12/2014
 *
 */

import java.io.IOException;

import model.KlassiekeOpdracht;
import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Opsomming;
import util.datumWrapper.Datum;

class DBOpsommingLeesSchrijf extends DBOpdrachtLeesSchrijf {

	DBOpsommingLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM opsomming_opdrachten";
	}

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
		boolean inJuisteVolgorde = (Boolean) rij[9];
		Opsomming opdracht =  new Opsomming(ID, datum, vraag, antwoord, maxPogingen, maxTijd, categorie, auteur, inJuisteVolgorde);
		voegHintsToe(opdracht, hints);
		return opdracht;
	}

	@Override
	protected <T> String getSchrijfStatement(T object) throws IOException {
		Opsomming opdracht = null;
		if (object instanceof Opsomming) {
			opdracht = (Opsomming) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Opsomming");
		}
		return String.format("%s'%s', %s)", this.opdrachtSchrijfStatement(opdracht), opdracht.getJuisteAntwoord(), opdracht.getInJuisteVolgorde());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE opsomming_opdrachten";
	}

}
