package persistency;

import java.io.IOException;

import model.QuizOpdracht;

class DBQuizOpdrachtLeesSchrijf extends DBTemplate {

	public DBQuizOpdrachtLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM quiz_opdrachten";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PseudoQuizOpdracht maakObject(Object[] rij) {
		String ID = rij[0].toString();
		int quizID = (Integer) rij[1];
		int opdrachtID = (Integer) rij[2];
		int maxScore = (Integer) rij[3];
		return new PseudoQuizOpdracht(ID, quizID, opdrachtID, maxScore);
	}

	@Override
	protected String getSchrijfStatement(Object object) throws IOException {
		QuizOpdracht quizOpdracht = null;
		if (object instanceof QuizOpdracht) {
			quizOpdracht = (QuizOpdracht) object;
		} else {
			throw new IOException(
					"Het object om weg te schrijven is geen QuizOpdracht");
		}
		return String.format(
				"INSERT INTO quiz_opdrachten VALUES('%s', %d, %d, %d)",
				quizOpdracht.getID(), quizOpdracht.getQuiz().getID(),
				quizOpdracht.getOpdracht().getID(), quizOpdracht.getMaxScore());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE quiz_opdrachten";
	}

}
