package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * De QuizOpdracht klasse koppelt een Quiz aan een Opdracht. Een zelfde opdracht
 * kan verschillende maximum scores hebben in verschillende quizzen.
 * QuizOpdracht houdt naast de Quiz en de Opdracht deze maxScore bij.
 *
 * @author Bert Neyt
 * 
 */
public class QuizOpdracht implements Comparable<QuizOpdracht> {

	private String ID;
	private Quiz quiz;
	private Opdracht opdracht;
	private int maxScore;
	private ArrayList<OpdrachtAntwoord> opdrachtAntwoorden;

	/**
	 * Constructor QuizOpdracht met 4 parameters
	 *
	 * @param ID
	 *            de String met de ID (java.util.UUID)
	 * @param quiz
	 *            de Quiz
	 * @param opdracht
	 *            de Opdracht
	 * @param maxScore
	 *            de maximum te behalen score
	 */
	private QuizOpdracht(String ID, Quiz quiz, Opdracht opdracht, int maxScore) {
		this.quiz = quiz;
		this.opdracht = opdracht;
		this.maxScore = maxScore;
		opdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		this.ID = ID;
	}

	/**
	 * Deze method koppelt een Quiz aan een Opdracht en kent aan de associatie
	 * een maximum score toe
	 * 
	 * @param ID
	 *            de String met de ID (java.util.UUID)
	 * @param quiz
	 *            de Quiz om te koppelen
	 * @param opdracht
	 *            de Opdracht om te koppelen
	 * @param maxScore
	 *            de maximum score van de opdracht in de quiz
	 * @param uitStorage
	 *            zet op true om een koppeling vanuit storage (tekst / DB) te
	 *            maken
	 * @return de resulterende QuizOpdracht
	 * @throws IllegalStateException
	 *             als de status van de Quiz het toevoegen van een Opdracht niet
	 *             toelaat
	 * @throws IllegalArgumentException
	 *             als de opdracht al is opgenomen in de quiz
	 */
	public static QuizOpdracht koppelOpdrachtAanQuiz(String ID, Quiz quiz,
			Opdracht opdracht, int maxScore, boolean uitStorage)
			throws IllegalStateException {

		if (!uitStorage) {
			if (!quiz.isAanpasbaar()) {
				throw new IllegalStateException(
						"De Quiz bevindt zich in een status die toevoegen van opdrachten onmogelijk maakt");
			}
			if (quiz.getOpdrachten().contains(opdracht)) {
				throw new IllegalArgumentException(
						"Deze opdracht zit al in de quiz");
			}
		}

		QuizOpdracht quizOpdracht = new QuizOpdracht(ID, quiz, opdracht,
				maxScore);
		quiz.addQuizOpdracht(quizOpdracht);
		opdracht.addQuizOpdracht(quizOpdracht);
		return quizOpdracht;
	}

	/**
	 * Deze method koppelt een Quiz aan een Opdracht en kent aan de associatie
	 * een maximum score toe
	 * 
	 * @param quiz
	 *            de Quiz om te koppelen
	 * @param opdracht
	 *            de Opdracht om te koppelen
	 * @param maxScore
	 *            de maximum score van de opdracht in de quiz
	 * @return de resulterende QuizOpdracht
	 * @throws IllegalStateException
	 *             als de status van de Quiz het toevoegen van een Opdracht niet
	 *             toelaat
	 */
	public static QuizOpdracht koppelOpdrachtAanQuiz(Quiz quiz,
			Opdracht opdracht, int maxScore) {
		return koppelOpdrachtAanQuiz(UUID.randomUUID().toString(), quiz,
				opdracht, maxScore, false);
	}

	/**
	 * Deze method doet de koppeling voorzien door deze QuizOpdracht teniet.
	 * 
	 * @throws IllegalStateException
	 *             als de status van de Quiz de ontkoppeling niet toelaat
	 */
	public void ontkoppelOpdrachtVanQuiz() throws IllegalStateException {
		if (quiz.isAanpasbaar()) {
			quiz.removeQuizOpdracht(this);
			opdracht.removeQuizOpdracht(this);
		} else {
			throw new IllegalStateException(
					"De Quiz bevindt zich in een status die ontkoppelingen onmogelijk maakt");
		}
	}

	/**
	 * Voegt een OpdrachtAntwoord toe aan de interne lijst van
	 * OpdrachtAntwoorden
	 * 
	 * @param opdrachtAntwoord
	 *            het toe te voegen OpdrachtAntwoord
	 */
	protected void addOpdrachtAntwoord(OpdrachtAntwoord opdrachtAntwoord) {
		this.opdrachtAntwoorden.add(opdrachtAntwoord);
	}

	/**
	 * Geeft een kopie van de lijst met OpdrachtAntwoorden terug
	 *
	 * @return een kopie van de lijst met OpdrachtAntwoorden
	 */
	public ArrayList<OpdrachtAntwoord> getOpdrachtAntwoorden() {
		ArrayList<OpdrachtAntwoord> kopie = new ArrayList<OpdrachtAntwoord>();
		for (OpdrachtAntwoord opdrachtAntwoord : this.opdrachtAntwoorden) {
			kopie.add(opdrachtAntwoord);
		}
		return kopie;
	}

	/**
	 * Haalt de Quiz geassocieerd met deze QuizOpdracht op
	 * 
	 * @return de Quiz geassocieerd met deze QuizOpdracht
	 */
	public Quiz getQuiz() {
		return quiz.clone();
	}

	/**
	 * Haalt de Opdracht geassocieerd met deze QuizOpdracht op
	 * 
	 * @return de Opdracht geassocieerd met deze QuizOpdracht
	 */
	public Opdracht getOpdracht() {
		return opdracht.clone();
	}

	/**
	 * Geeft de maximum score terug van deze QuizOpdracht
	 * 
	 * @return de maximum score
	 */
	public int getMaxScore() {
		return maxScore;
	}

	/**
	 * verandert de maximum score van deze QuizOpdracht
	 * 
	 * @param int de maximum score
	 */
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	/**
	 * Haalt de ID van de QuizOpdracht op
	 * 
	 * @return de ID van de QuizOpdracht op
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Geeft de gemiddelde score van alle leerlingen voor deze QuizOpdracht
	 * terug
	 * 
	 * @return de gemiddelde score van alle leerlingen voor deze QuizOpdracht
	 *         (double)
	 */
	public double getGemiddeldeScore() {
		double somScores = 0;
		for (OpdrachtAntwoord opdrachtAntwoord : this.opdrachtAntwoorden) {
			somScores += opdrachtAntwoord.getBehaaldeScore();
		}
		return somScores / this.opdrachtAntwoorden.size();
	}

	/**
	 * Vergelijkt deze QuizOpdracht met een andere QuizOpdracht, eerst op basis
	 * van de Quiz, dan op basis van de Opdracht
	 *
	 * @param quizOpdracht
	 *            de QuizOpdracht waarmee vergeleken wordt
	 * @return -1, 0 of 1 als deze QuizOpdracht voor, op dezelfde plaats of na
	 *         het argument-QuizOpdracht komt
	 */
	@Override
	public int compareTo(QuizOpdracht quizOpdracht) {
		if (this.quiz.compareTo(quizOpdracht.getQuiz()) == 0) {
			return this.opdracht.compareTo(quizOpdracht.getOpdracht());
		} else {
			return this.quiz.compareTo(quizOpdracht.getQuiz());
		}
	}

	@Override
	public QuizOpdracht clone() {
		QuizOpdracht clone = null;
		try {
			clone = (QuizOpdracht) super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof QuizOpdracht)) {
			return false;
		}
		QuizOpdracht other = (QuizOpdracht) obj;
		if (!this.quiz.equals(other.quiz)) {
			return false;
		}
		if (!this.opdracht.equals(other.opdracht)) {
			return false;
		}
		if (!(this.maxScore == other.maxScore)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		long hash = 1;
		hash = hash * 19 + maxScore;
		hash = hash * 13 + opdracht.hashCode();
		hash = hash * 7 + quiz.hashCode();
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@Override
	public String toString() {
		String result = "QuizOpdracht [ID=" + ID + "] - Maximale score: "
				+ maxScore;
		result += "\nDIE\n" + opdracht.toString().replaceAll("(?m)^", "\t");
		;
		result += "\nKOPPELT AAN\n" + quiz.toString().replaceAll("(?m)^", "\t");
		;
		return result;
	}

}
