package model;

import java.util.ArrayList;

import util.datumWrapper.Datum;

/**
 *
 * @author Adriaan Kuipers, Ben Vandenberk
 * @version 27/10/2014
 */
public class QuizDeelname implements Comparable<QuizDeelname>, Cloneable {

	private final Leerling leerling;
	private final Quiz quiz;
	private final Datum datum;
	private ArrayList<OpdrachtAntwoord> opdrachtAntwoorden;

	/**
	 * Constructor QuizDeelname met 2 parameters
	 *
	 * @param quiz
	 *            de Quiz
	 * @param leerling
	 *            de Leerling
	 */
	private QuizDeelname(Quiz quiz, Leerling leerling) {
		this.datum = new Datum();
		this.quiz = quiz;
		this.leerling = leerling;
		opdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
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
		if (!quiz.isDeelnameMogelijk()) {
			throw new IllegalArgumentException(String.format("De status van de quiz(%s) laat niet toe aan de quiz deel te nemen",
					quiz.getQuizStatus()));
		}
		if (!quiz.isGeldigLeerjaar(leerling.getLeerjaar())) {
			throw new IllegalArgumentException(String.format(
					"De quiz is niet opengesteld voor het leerjaar waarin de leerling zich bevindt (%d)", leerling.getLeerjaar()));
		}
		QuizDeelname quizDeelname = new QuizDeelname(quiz, leerling);
		leerling.addQuizDeelname(quizDeelname);
		quiz.addQuizDeelname(quizDeelname);
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

	// Hier moet een keuze gemaakt worden
	// Ofwel is deep cloning van QuizDeelname mogelijk, maar dan mogen de fields niet final zijn
	// Ofwel maakt clone() een shallow copy en kunnen de fields final zijn
	@Override
	public QuizDeelname clone() throws CloneNotSupportedException {
		return (QuizDeelname) super.clone();
	}

}
