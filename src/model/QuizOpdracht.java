package model;

import java.util.ArrayList;

/**
 *
 * @author Bert Neyt
 * @version 0.0
 */
public class QuizOpdracht implements Comparable<QuizOpdracht>{

	private Quiz quiz;
	private Opdracht opdracht;
	private int maxScore;
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

	public Quiz getQuiz() {
		return quiz;
	}

	public Opdracht getOpdracht() {
		return opdracht;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public double getGemiddeldeScore() {
		double somScores = 0;
		for (OpdrachtAntwoord opdrachtAntwoord : this.opdrachtAntwoorden) {
			somScores += opdrachtAntwoord.getBehaaldeScore();
		}
		return somScores / this.opdrachtAntwoorden.size();
	}
	
	/**
	 * Nog uitwerken
	 * @param quizOpdracht
	 * @return
	 */
	public int compareTo(QuizOpdracht quizOpdracht) {
		return 0;
	}
	
	/**
	 * Nog uitwerken
	 */
	public QuizOpdracht clone() {
		QuizOpdracht clone= null;
		try {
			clone = (QuizOpdracht)super.clone();
			//...
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return clone;
	}

}
