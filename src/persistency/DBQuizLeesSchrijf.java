package persistency;

import java.io.IOException;

import util.datumWrapper.Datum;
import model.Leraar;
import model.Quiz;
import model.quizStatus.QuizStatus;

public class DBQuizLeesSchrijf extends DBTemplate {

	DBQuizLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * from Quiz";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Quiz maakObject(Object[] rij) {
		int ID = (Integer) rij[0];
		Datum aanmaakDatum = (Datum) rij [1];
		QuizStatus status = (QuizStatus) rij [2];
		Leraar auteurNaam = (Leraar) rij [3];
		String onderwerp = (String) rij [4];
		Boolean isuniekeDeelname = (Boolean) rij [5];
//		Boolean isTest = (Boolean) rij [6];
		return new Quiz (ID, aanmaakDatum, status, auteurNaam, onderwerp, isuniekeDeelname);
	}

	@Override
	protected String getSchrijfStatement(Object object) throws IOException {
		Quiz quiz = null;
		if (object instanceof Quiz) {
			quiz = (Quiz) object;
		} else {
			throw new IOException(
					"Het object om weg te schrijven is geen quiz");
		}
		return String.format(
		"INSERT INTO quiz VALUES('%d', '%tF', %s, %s, %s, %s)",
		quiz.getID(),
		quiz.getAanmaakDatum().getDatumInEuropeesFormaat(),
		quiz.getQuizStatus().toString(),
		quiz.getAuteur(),
		quiz.getOnderwerp(),
		quiz.getIsUniekeDeelname());
	}

	@Override
	protected String getDeleteStatement() {

		return "TRUNCATE quiz";
	}

}
