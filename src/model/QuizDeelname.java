package model;

import util.datumWrapper.Datum;

/**
 *
 * @author Adriaan Kuipers
 * @version 24/10/2014
 */
public class QuizDeelname implements Comparable<QuizDeelname> {

	private final Leerling leerling;
	private final Quiz quiz;
	private final Datum datum;

	/**
	 * Constructor QuizDeelname met 2 parameters
	 *
	 * @param quiz
	 *            de Quiz
	 * @param leerling
	 *            de Leerling
	 */
	private QuizDeelname(Quiz quiz, Leerling leerling) {
		if (!this.IsDeelnameMogelijk(quiz, leerling)) {
			throw new IllegalArgumentException("Deelname niet mogelijk");
		}
		this.datum = new Datum();
		this.quiz = quiz;
		this.leerling = leerling;
	}

	public Leerling getLeerling() {
		return leerling;
	}
	
	public Quiz getQuiz() {
		return quiz;
	}
	
	public Datum getDatum() {
		return datum;
	}

	public static void koppelQuizAanLeerling(Quiz quiz, Leerling leerling) {
		QuizDeelname quizDeelname = new QuizDeelname(quiz, leerling);
		leerling.addQuizDeelname(quizDeelname);
		quiz.addQuizDeelname(quizDeelname);
	}	

	/**
	 * Testen of leerling aan quiz deel mag nemen
	 *
	 * @param quiz
	 *            Quiz waaraan leerling wenst deel te nemen
	 * @param leerling
	 *            Leerling die wenst deel te nemen
	 * @return
	 */
	public boolean IsDeelnameMogelijk(Quiz quiz, Leerling leerling) {
		if (!quiz.getQuizStatus().equals("") || quiz.isGeldigLeerjaar(leerling.getLeerjaar())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * geeft ovezicht van gestelde vragen met juiste antwoorden, gegeven antwoorden & behaalde score
	 */
	public String feedback() {
		return "";
	}

	/**
	 * override toString --> return naam leerling, datum deelname & onderwerp ??? quiz
	 */
	@Override
	public String toString() {
		return String.format("Deelname van %s aan %s-quiz op %s", this.leerling.getNaam(), this.quiz.getOnderwerp(),
				this.datum.getDatumInEuropeesFormaat());
	}

	/**
	 * override equals --> controle of deze quizdeelname dezelfde is als de andere
	 *
	 * @param aQuizDeelname
	 *            een quizdeelname waarmee we gaan vergelijken
	 * @return
	 */
	@Override
	public boolean equals(Object aQuizDeelname) {
		return true;
	}

	/**
	 * Vergelijk deze quizdeelname met een andere quizdeelname
	 *
	 * @return een negatief nummer, een nul of een positief nummer als de quizdeelname ???
	 */
	@Override
	public int compareTo(QuizDeelname aQuizDeelname) {
		return 0;
	}

	/**
	 * ???
	 */
	@Override
	public int hashCode() {
		return 0;
	}

}
