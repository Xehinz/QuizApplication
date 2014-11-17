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

abstract class TxtOpdrachtLeesSchrijf extends TxtTemplate {
	
	public TxtOpdrachtLeesSchrijf(boolean useCSV) {
		super(useCSV);
	}
	
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
		String[] hints = hintsLijst.split("[\\x7C]");
		for (String hint : hints) {
			if (!hint.equals("")) {
			opdracht.addHint(hint);
			}
		}
	}
	
	protected String maakBasisOpdrachtString (Opdracht opdracht) {		
		String hints = new String();
		for (int i = 0; i < opdracht.getHints().size(); i++) {
			if (i < opdracht.getHints().size() - 1) {
			hints += String.format("%s|", opdracht.getHints().get(i));	
			}
			else {
				hints += opdracht.getHints().get(i);
			}
		}
		return String.format("%d\t%s\t%s\t%d\t%d\t%s\t%s\t%s",opdracht.getID(),(opdracht.getAanmaakDatum()).getDatumInEuropeesFormaat(),opdracht.getVraag(),opdracht.getMaxAantalPogingen(), opdracht.getMaxAntwoordTijd(),(opdracht.getOpdrachtCategorie()).name(),(opdracht.getAuteur()).name(),hints);
	}
	
}
