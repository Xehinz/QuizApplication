package persistency;

import java.io.IOException;

import util.datumWrapper.Datum;
import model.Leraar;
import model.Quiz;
import model.QuizStatus;

/**
 * Klasse om Quiz objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Jef Bellens
 * @version 15/11/2014
 *
 */

public class TxtQuizLeesSchrijf extends TxtTemplate {

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/quizzen.csv" : "resources/quizzen.txt";
	}

	@Override
	protected Quiz maakObject(String[] fields) throws IOException {
		try {
			return new Quiz(Integer.parseInt(fields[0]), new Datum(fields[1]), QuizStatus.valueOf(fields[2]), Leraar.valueOf(fields[3]), fields[4], Boolean.parseBoolean(fields[5]));
		} catch (NumberFormatException Nex) {
			throw new IOException("Fout bij het parsen van de IsUniekeDeelname en/of ID", Nex);
		} catch (IndexOutOfBoundsException Iex) {
			throw new IOException("Een record bevatte te weinig velden om een Quiz aan te maken", Iex);
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		Quiz quiz = null;
		if (object instanceof Quiz) {
			quiz = (Quiz) object;
		}
		else {
			throw new IOException("Het object om weg te schrijven is geen quiz");
		}
		
		String id = new String();
		String s = String.valueOf(quiz.getID());
		id += String.format("%s|", s);
		
		String datum = new String();
		Datum d = quiz.getAanmaakDatum();
		datum += String.format("%s|", d);
		
		String quizstatus = new String();
		QuizStatus qs = quiz.getQuizStatus();
		datum += String.format("%s|", qs);
		
		String auteur = new String();
		Leraar a = quiz.getAuteur();
		auteur += String.format("%s|", a);
		
		String onderwerp = new String();
		String o = quiz.getOnderwerp(); {
		onderwerp += String.format("%s|", o);
		
		String isuniekedeelname = new String();
		boolean iud = quiz.getIsUniekeDeelname();
		datum += String.format("%s|", iud);
		}
		return String.format("%d\t%s\t%s\t%s\t%s\t%s", quiz.getID(), quiz.getAanmaakDatum(), quiz.getQuizStatus(), quiz.getAuteur(), quiz.getOnderwerp(), quiz.getIsUniekeDeelname());
	}

	@Override
	protected String getHeaderCSV() {
		
		return "ID\tDatum\tQuizStatus\tAuteur\tOnderwerp\tIsUniekeDeelname";
	}
}
