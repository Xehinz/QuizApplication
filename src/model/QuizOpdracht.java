package model;

public class QuizOpdracht {

	private Quiz quiz;
	private Opdracht opdracht;
	private int maxScore;

	public QuizOpdracht(Quiz quiz, Opdracht opdracht, int maxScore) {
		this.quiz = quiz;
		this.opdracht = opdracht;
		this.maxScore = maxScore;
	}

	public static void attachOpdrachtToQuiz(Quiz quiz, Opdracht opdracht,
			int maxScore) {
		QuizOpdracht quizOpdracht = new QuizOpdracht(quiz, opdracht, maxScore);
		quiz.addQuizOpdracht(quizOpdracht);
		opdracht.addQuizOpdracht(quizOpdracht);
	}

	public void detachOpdrachtFromQuiz() {
		quiz.removeQuizOpdracht(this);
		opdracht.removeQuizOpdracht(this);
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public Opdracht getOpdracht() {
		return opdracht;
	}

}
