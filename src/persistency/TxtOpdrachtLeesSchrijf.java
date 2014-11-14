package persistency;

import java.io.IOException;

import model.Opdracht;
import model.QuizOpdracht;
import model.KlassiekeOpdracht;
import model.Reproductie;
import model.Opsomming;
import model.Meerkeuze;

/**
 * Klasse om Opdracht objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 09/11/2014
 *
 */

public class TxtOpdrachtLeesSchrijf extends TxtTemplate {
	
	public TxtOpdrachtLeesSchrijf() {
	}

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/opdrachten.csv" : "resources/opdrachten.txt";
	}

	@Override
	protected Opdracht maakObject(String[] fields) throws IOException {
		try {
			//return new Opdracht();
			return null;
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		Opdracht opdracht = null;
		if (object instanceof Opdracht) {
			opdracht = (Opdracht) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen opdracht");
		}
		String hints = new String();
		for (String s : opdracht.getHints()) {
			hints += String.format("%s|", s);			
		}
		String quizOpdrachten = new String();
		for (QuizOpdracht qo : opdracht.getQuizOpdrachten()) {
			quizOpdrachten += String.format("%s|", qo.getID());
		}
		return String.format("%d\t%s\t%s\t%s\t%s\t%d\t%d\t%s\t%s",opdracht.getID(),opdracht.getAuteur(),(opdracht.getAanmaakDatum()).getDatumInEuropeesFormaat(),opdracht.getVraag(),(opdracht.getOpdrachtCategorie()).toString(),opdracht.getMaxAantalPogingen(), opdracht.getMaxAntwoordTijd(),hints,quizOpdrachten);
	}

	@Override
	protected String getHeaderCSV() {
		return "ID\tAuteur\tDatum\tVraag\tCategorie\tMax pogingen\tMax Tijd\tHints\tQuizOpdrachten";
	}
}
