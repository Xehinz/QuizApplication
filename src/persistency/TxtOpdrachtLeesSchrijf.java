package persistency;

import java.io.IOException;
import model.Opdracht;

/**
 * Klasse om Opdracht objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 15/11/2014
 *
 */

public abstract class TxtOpdrachtLeesSchrijf extends TxtTemplate {
	
	@Override
	protected abstract String getBestandsnaam();

	@Override
	protected abstract Opdracht maakObject(String[] fields) throws IOException;

	@Override
	protected abstract String maakStringRecord(Object object) throws IOException;

	@Override
	protected String getHeaderCSV() {
		return "ID\tDatum\tVraag\tMax pogingen\tMax Tijd\tCategorie\tAuteur\tHints";
	}
	
	protected void voegHintsToe(Opdracht opdracht, String hintsLijst) {
		String[] hints = hintsLijst.split("|");
		for (String hint : hints) {
			opdracht.addHint(hint);
		}
	}
	
	protected String maakBasisOpdrachtString (Opdracht opdracht) {		
		String hints = new String();
		for (String s : opdracht.getHints()) {
			hints += String.format("%s|", s);			
		}
		return String.format("%d\t%s\t%s\t%d\t%d\t%s\t%s\t%s",opdracht.getID(),(opdracht.getAanmaakDatum()).getDatumInEuropeesFormaat(),opdracht.getVraag(),opdracht.getMaxAantalPogingen(), opdracht.getMaxAntwoordTijd(),(opdracht.getOpdrachtCategorie()).toString(),opdracht.getAuteur(),hints);
	}
	
}
