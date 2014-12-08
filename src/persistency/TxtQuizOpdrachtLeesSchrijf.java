package persistency;

import java.io.IOException;

import model.QuizOpdracht;

class TxtQuizOpdrachtLeesSchrijf extends TxtTemplate {

	public TxtQuizOpdrachtLeesSchrijf(boolean useCSV) {
		super(useCSV);
	}
	
	@Override
	protected String getBestandsnaam() {
		return useCSV? "resources/quizOpdrachten.csv" : "resources/quizOpdrachten.txt";
	}

	@Override
	protected PseudoQuizOpdracht maakObject(String[] fields) throws IOException {
		try {
			return new PseudoQuizOpdracht(fields[0], Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
		} catch (NumberFormatException Nex) {
			throw new IOException("Fout bij het parsen van quizOpdrachtID / quizID / opdrachtID of maxScore", Nex);
		} catch (IndexOutOfBoundsException Iex) {
			throw new IOException("Een record bevatte te weinig velden om een QuizOpdracht aan te maken", Iex);
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		QuizOpdracht quizOpdracht = null;
		if (object instanceof QuizOpdracht) {
			quizOpdracht = (QuizOpdracht)object;			
		} else {
			throw new IOException("Het object om weg te schrijven is geen QuizOpdracht");
		}
		return String.format("%s\t%d\t%d\t%d", quizOpdracht.getID(), quizOpdracht.getQuiz().getID(), quizOpdracht.getOpdracht().getID(), quizOpdracht.getMaxScore());
 	}

	@Override
	protected String getHeaderCSV() {
		return "ID\tquizID\topdrachtID\tmaxScore";
	}

}
