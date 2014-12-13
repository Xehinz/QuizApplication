package persistency;

import java.io.IOException;

import util.datumWrapper.Datum;

import model.Leraar;
import model.Quiz;
import model.quizStatus.QuizStatus;

/**
 * Klasse om Quiz objecten weg te schrijven of in te lezen in een Database
 *
 * @author Jef Bellens
 * @version 12/12/2014
 *
 */

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
		Datum aanmaakDatum = new Datum(rij[1].toString());
		QuizStatus status = QuizStatus.getInstance(rij[2].toString());
		Leraar auteurNaam = Leraar.getLeraar(rij[3].toString().replace("_"," "));
		String onderwerp = (String) rij[4];
		Boolean isuniekeDeelname = (Boolean) rij[5];
		// Boolean isTest = (Boolean) rij [6];
		return new Quiz(ID, aanmaakDatum, status, auteurNaam, onderwerp,
				isuniekeDeelname);
	}

	@Override
	protected String getSchrijfStatement(Object object) throws IOException {
		Quiz quiz = null;
		if (object instanceof Quiz) {
			quiz = (Quiz) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen quiz");
		}
		
		String doelLeerjaren = "";
		for (int i = 0; i < quiz.getDoelLeerjaren().size(); i++) {
			if(i != quiz.getDoelLeerjaren().size())
				doelLeerjaren += quiz.getDoelLeerjaren().get(i) + ",";
			else
				doelLeerjaren += quiz.getDoelLeerjaren().get(i).toString();
		}
				
		
		return String.format(
				"INSERT INTO quiz VALUES('%s', '%s', '%s', '%s', '%s', %s, %s, '%s')", 
					quiz.getID(), 
					quiz.getAanmaakDatum().getDatumInMySQLFormaat(), 
					quiz.getQuizStatus().toString(), 
					quiz.getAuteur().name(), 
					quiz.getOnderwerp(),
					quiz.getIsUniekeDeelname(), 
					quiz.getIsTest(), 
					doelLeerjaren);
	}

	@Override
	protected String getDeleteStatement() {

		return "TRUNCATE quiz";
	}

}
