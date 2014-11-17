package persistency;

import java.io.IOException;

import model.OpdrachtAntwoord;
import model.QuizDeelname;

public class TxtQuizDeelnameLeesSchrijf extends TxtTemplate {

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/quizDeelnames.csv" : "resources/quizDeelnames.txt";
	}

	@Override
	protected PseudoQuizDeelname maakObject(String[] fields) throws IOException {
		try {
			return new PseudoQuizDeelname(Integer.parseInt(fields[0]),
					Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
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
		QuizDeelname quizdeelname = null;
		if (object instanceof QuizDeelname) {
			quizdeelname = (QuizDeelname) object;
		} else {
			throw new IOException(
					"Het object om weg te schrijven is geen QuizDeelname");
		}
		return String.format("%s\t%s\t%s", quizdeelname.getID(),
				quizdeelname.getQuiz().getID(), quizdeelname.getLeerling().getID());
	}

	@Override
	protected String getHeaderCSV() {
		return "QuizDeelnameID\tQuizID\tLeerlingID";
	}

}
