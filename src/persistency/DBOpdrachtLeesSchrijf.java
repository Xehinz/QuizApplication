package persistency;

/**
 * Abstracte klasse om het wegschrijven of inlezen van 
 * databaseformaat
 *
 * @author Adriaan Kuipers
 * @version 10/12/2014
 *
 */

import java.io.IOException;

import model.KlassiekeOpdracht;
import model.Meerkeuze;
import model.Opdracht;
import model.Opsomming;
import model.Reproductie;

abstract class DBOpdrachtLeesSchrijf extends DBTemplate {

	DBOpdrachtLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected abstract String getLeesQuery();

	@Override
	protected abstract <T> T maakObject(Object[] rij);

	@Override
	protected abstract <T> String getSchrijfStatement(T object) throws IOException;

	@Override
	protected abstract String getDeleteStatement();
	
	protected void voegHintsToe(Opdracht opdracht, String hintsLijst) {
		String[] hints = hintsLijst.split("[\\x7C]");
		for (String hint : hints) {
			if (!hint.equals("")) {
			opdracht.addHint(hint);
			}
		}
	}
	
	protected String opdrachtSchrijfStatement (Opdracht opdracht) {		
		String hints = new String();
		for (int i = 0; i < opdracht.getHints().size(); i++) {
			if (i < opdracht.getHints().size() - 1) {
			hints += String.format("%s|", opdracht.getHints().get(i));	
			}
			else {
				hints += opdracht.getHints().get(i);
			}
		}
		String tabel = new String();
		if (opdracht instanceof KlassiekeOpdracht) {
			tabel = "klassieke_opdracht";
		}
		if (opdracht instanceof Meerkeuze) {
			tabel = "meerkeuze_opdracht";
		}
		if (opdracht instanceof Opsomming) {
			tabel = "opsomming_opdrachten";
		}
		if (opdracht instanceof Reproductie) {
			tabel = "reproductie_opdracht";
		}
		return String.format("INSERT INTO %s VALUES('%d', '%s', '%s', %d, %d, '%s', '%s', '%s', ", tabel, opdracht.getID(),(opdracht.getAanmaakDatum()).getDatumInMySQLFormaat(),opdracht.getVraag(),opdracht.getMaxAantalPogingen(), opdracht.getMaxAntwoordTijd(),(opdracht.getOpdrachtCategorie()).name(),(opdracht.getAuteur()).name(),hints);
	}

}
