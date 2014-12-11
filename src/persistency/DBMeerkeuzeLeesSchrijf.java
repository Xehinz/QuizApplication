package persistency;

/**
 * Klasse om Meerkeuze objecten weg te schrijven of in te lezen in
 * databaseformaat
 *
 * @author Adriaan Kuipers
 * @version 11/12/2014
 *
 */

import java.io.IOException;

import model.Leraar;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtCategorie;
import util.datumWrapper.Datum;

public class DBMeerkeuzeLeesSchrijf extends DBOpdrachtLeesSchrijf {

	DBMeerkeuzeLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM meerkeuze";
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
		String antwoord = (String) rij[9];
		String opties = (String) rij[8];
		Meerkeuze opdracht =  new Meerkeuze(ID, datum, vraag, antwoord, maxPogingen, maxTijd, categorie, auteur);		
		voegHintsToe(opdracht, hints);
		opdracht.setOpties(opties);
		return opdracht;
	}

	@Override
	protected <T> String getSchrijfStatement(T object) throws IOException {
		Meerkeuze opdracht = null;
		if (object instanceof Meerkeuze) {
			opdracht = (Meerkeuze) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Meerkeuze");
		}
		String opties = new String();
		for (int i = 0; i < opdracht.getOpties().size(); i++) {
			if (i < opdracht.getOpties().size() - 1) {
			opties += String.format("%s;", opdracht.getOpties().get(i));
			}
			else {
				opties += opdracht.getOpties().get(i);
			}
		}
		return String.format("%s%s, %s)", this.opdrachtSchrijfStatement(opdracht), opties, opdracht.getJuisteAntwoord());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE meerkeuze";
	}

}
