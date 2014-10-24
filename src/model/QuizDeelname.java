package model;

import util.datumWrapper.Datum;

/**
 *
 * @author Adriaan Kuipers
 * @version 24/10/2014
 */
public class QuizDeelname implements Comparable<QuizDeelname> {

	private Leerling leerling;
	private Quiz quiz;
	private Datum datum;

	/**
	 * Constructor QuizDeelname met 2 parameters
	 *
	 * @param quiz
	 *            de Quiz
	 * @param leerling
	 *            de Leerling
	 */
	public QuizDeelname(Quiz quiz, Leerling leerling) {
		if (!this.IsDeelnameMogelijk(quiz, leerling)) {
			throw new IllegalArgumentException("Deelname niet mogelijk");
		}
		this.datum = new Datum();
		this.quiz = quiz;
		this.leerling = leerling;
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
	// Quiz.geldigLeerjaar & Leerling.getLeerjaar bestaan niet
	public boolean IsDeelnameMogelijk(Quiz quiz, Leerling leerling) {
		if (!quiz.getQuizStatus().equals("") || quiz.geldigLeerjaar(leerling.getLeerjaar())) {
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
	// Leerling.getNaam bestaat niet
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
