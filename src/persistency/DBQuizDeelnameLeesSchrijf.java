package persistency;

import java.io.IOException;

import model.QuizDeelname;
import util.datumWrapper.Datum;

class DBQuizDeelnameLeesSchrijf extends DBTemplate {

	DBQuizDeelnameLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM quiz_deelname";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PseudoQuizDeelname maakObject(Object[] rij) {
		String quizDeelnameID = rij[0].toString();
		int quizID = Integer.parseInt(rij[1].toString());
		int leerlingID = Integer.parseInt(rij[2].toString());
		Datum deelnameDatum = new Datum(rij[3].toString());
		
		return new PseudoQuizDeelname(quizDeelnameID, quizID, leerlingID, deelnameDatum);
	}

	@Override
	protected String getSchrijfStatement(Object object) throws IOException {
		QuizDeelname quizDeelname = null;
		if (object instanceof QuizDeelname) {
			quizDeelname = (QuizDeelname) object;
		}
		else {
			throw new IOException(
					"Het object om weg te schrijven is geen QuizDeelname");			
		}
		return String.format(
				"INSERT INTO quiz_deelname VALUES('%s', %d, %d, '%s')",
				quizDeelname.getID(),
				quizDeelname.getQuiz().getID(),
				quizDeelname.getLeerling().getID(),
				quizDeelname.getDatum().getDatumInMySQLFormaat()
				); 
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE quiz_deelname";
	}
}
