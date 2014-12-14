package persistency;

import java.io.IOException;
import java.util.ArrayList;

import util.datumWrapper.Datum;
import model.Leraar;
import model.Quiz;
import model.quizStatus.QuizStatus;

/**
 * Klasse om Quiz objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Jef Bellens
 * @version 15/11/2014
 *
 */

class TxtQuizLeesSchrijf extends TxtTemplate {

	public TxtQuizLeesSchrijf(boolean useCSV) {
		super(useCSV);
	}
	
	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/quizzen.csv" : "resources/quizzen.txt";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Quiz maakObject(String[] fields) throws IOException {
		try {
			Quiz quiz = new Quiz(Integer.parseInt(fields[0]), new Datum(fields[1]), QuizStatus.getInstance(fields[2]), Leraar.valueOf(fields[3]), fields[4], Boolean.parseBoolean(fields[5]));
			quiz.setIsTest(Boolean.parseBoolean(fields[6]));
			String[] doelLeerjarenStrings = fields[7].split(",");
			int[] doelLeerjaren = new int[doelLeerjarenStrings.length];
			for (int i = 0; i < doelLeerjarenStrings.length; i++) {
				doelLeerjaren[i] = Integer.parseInt(doelLeerjarenStrings[i]);
			}
			quiz.setDoelLeerjaren(doelLeerjaren);
			return quiz;
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

		ArrayList<Integer> doelLeerjaren = quiz.getDoelLeerjaren();
		String leerjaren = "";
		for (int i = 0; i < doelLeerjaren.size(); i++) {
			if (i < doelLeerjaren.size() - 1) {
				leerjaren += String.format("%d,", doelLeerjaren.get(i));
			} else {
				leerjaren += doelLeerjaren.get(i);
			}
		}
		return String.format("%d\t%s\t%s\t%s\t%s\t%s\t%s\t%s", quiz.getID(), quiz.getAanmaakDatum().getDatumInEuropeesFormaat(), quiz.getQuizStatus().toString(), quiz.getAuteur().name(), quiz.getOnderwerp(), quiz.getIsUniekeDeelname(), quiz.getIsTest(), leerjaren);
	}

	@Override
	protected String getHeaderCSV() {
		
		return "ID\tDatum\tQuizStatus\tAuteur\tOnderwerp\tIsUniekeDeelname\tIsTest\tDoelLeerjaren";
	}
}
