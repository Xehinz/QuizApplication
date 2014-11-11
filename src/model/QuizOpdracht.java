package model;

import java.util.ArrayList;

import util.IDBeheerder;

/**
 *
 * @author Bert Neyt
 * @version 0.0
 */
public class QuizOpdracht implements Comparable<QuizOpdracht> {	

	private int ID;
	private Quiz quiz;
	private Opdracht opdracht;
	private final int maxScore;
	private ArrayList<OpdrachtAntwoord> opdrachtAntwoorden;

	/**
	 * Constructor QuizOpdracht met 3 parameters
	 *
	 * @param quiz
	 *            de Quiz
	 * @param opdracht
	 *            de Opdracht
	 * @param maxScore
	 *            de maximum te behalen score
	 */
	private QuizOpdracht(Quiz quiz, Opdracht opdracht, int maxScore) {
		this.quiz = quiz;
		this.opdracht = opdracht;
		this.maxScore = maxScore;
		opdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		ID = this.hashCode();
	}

	public static void attachOpdrachtToQuiz(Quiz quiz, Opdracht opdracht, int maxScore) {
		QuizOpdracht quizOpdracht = new QuizOpdracht(quiz, opdracht, maxScore);
		quiz.addQuizOpdracht(quizOpdracht);
		opdracht.addQuizOpdracht(quizOpdracht);
	}

	public void detachOpdrachtFromQuiz() {
		quiz.removeQuizOpdracht(this);
		opdracht.removeQuizOpdracht(this);
	}

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

	public Quiz getQuiz() {
		return quiz.clone();
	}

	public Opdracht getOpdracht() {
		return opdracht.clone();
	}

	public int getMaxScore() {
		return maxScore;
	}

	/**
	 * Haalt de ID van de QuizOpdracht op
	 * 
	 * @return de ID van de QuizOpdracht op
	 */
	public int getID() {
		return ID;
	}

	public double getGemiddeldeScore() {
		double somScores = 0;
		for (OpdrachtAntwoord opdrachtAntwoord : this.opdrachtAntwoorden) {
			somScores += opdrachtAntwoord.getBehaaldeScore();
		}
		return somScores / this.opdrachtAntwoorden.size();
	}

	/**
	 * Vergelijkt deze QuizOpdracht met een andere QuizOpdracht, eerst op basis van de Quiz, dan op basis van de
	 * Opdracht
	 *
	 * @param quizOpdracht
	 *            de QuizOpdracht waarmee vergeleken wordt
	 * @return -1, 0 of 1 als deze QuizOpdracht voor, op dezelfde plaats of na het argument-QuizOpdracht komt
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
		return "Quiz met onderwerp " + this.quiz.getOnderwerp() + " gemaakt door " + this.quiz.getAuteur() + " op "
				+ this.quiz.getAanmaakDatum() + "\nbevat\nOpdracht met als vraag " + this.opdracht.getVraag()
				+ " uit de opdrachtcategorie " + this.opdracht.getOpdrachtCategorie() + "\nmet maximale score " + this.maxScore;
	}

}
