/**
 *
 */
package quizApplication;

import model.KlassiekeOpdracht;
import model.Leerling;
import model.Leraar;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.OpdrachtCategorie;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;
import model.QuizStatus;

/**
 * @author Cool Tim
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Quiz quiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "TestQuiz");
		quiz.setQuizStatus(QuizStatus.OPENGESTELD);
		Opdracht opdracht = new KlassiekeOpdracht("Wat is de hoofdstad van Frankrijk?", "Parijs",
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		System.out.println(opdracht);
		Leerling leerling = new Leerling("Ben", "Vandenberk", 4);

		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdracht, 10);

		QuizDeelname quizDeelname = leerling.getQuizDeelnames().get(0);
		QuizOpdracht quizOpdracht = quiz.getQuizOpdracht(1);

		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 1, 10, "Parijs");

		// System.out.println(leerling.getQuizDeelnames().get(0).getOpdrachtAntwoorden().get(0));

	}
}
