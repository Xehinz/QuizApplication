package persistency;

import java.io.IOException;
import java.time.LocalTime;

import util.datumWrapper.Datum;
import model.OpdrachtAntwoord;
import model.QuizDeelname;

class TxtQuizDeelnameLeesSchrijf extends TxtTemplate {

	public TxtQuizDeelnameLeesSchrijf(boolean useCSV) {
		super(useCSV);
	}
	
	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/quizDeelnames.csv" : "resources/quizDeelnames.txt";
	}

	@Override
	protected PseudoQuizDeelname maakObject(String[] fields) throws IOException {
		try {
			return new PseudoQuizDeelname(Integer.parseInt(fields[0]),
					Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), new Datum(fields[3]), LocalTime.parse(fields[4]));
		} catch (NumberFormatException Nex) {
			throw new IOException("Fout bij het parsen vanuit tekstbestand",
					Nex);
		} catch (IndexOutOfBoundsException Iex) {
			throw new IOException(
					"Een record bevatte te weinig velden om een QuizDeelname aan te maken",
					Iex);
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		QuizDeelname quizDeelname = null;
		if (object instanceof QuizDeelname) {
			quizDeelname = (QuizDeelname) object;
		} else {
			throw new IOException(
					"Het object om weg te schrijven is geen QuizDeelname");
		}
		return String.format("%s\t%s\t%s\t%s\t%s", quizDeelname.getID(),
				quizDeelname.getQuiz().getID(), quizDeelname.getLeerling().getID(), quizDeelname.getDatum().getDatumInEuropeesFormaat(), quizDeelname.getTijdstipDeelname().toString());
	}

	@Override
	protected String getHeaderCSV() {
		return "QuizDeelnameID\tQuizID\tLeerlingID\tDeelnameDatum\tTijdstipDeelname";
	}

}
