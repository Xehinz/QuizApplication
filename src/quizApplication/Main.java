/**
 *
 */
package quizApplication;

import model.Leerling;
import model.Quiz;
import model.QuizDeelname;
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
		Leerling testLeerling = new Leerling("Ben", "Vandenberk", 1);
		Quiz testQuiz = new Quiz();
		testQuiz.setQuizStatus(QuizStatus.OPENGESTELD);
		QuizDeelname.koppelQuizAanLeerling(testQuiz, testLeerling);

		QuizDeelname quizDeelname = testLeerling.getQuizDeelnames().get(0);

		System.out.println(quizDeelname);

	}
}
