/**
 *
 */
package quizApplication;

import model.Leerling;
import model.Quiz;
import model.QuizDeelname;

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
		QuizDeelname.koppelQuizAanLeerling(testQuiz, testLeerling);
		// testQuiz.getQuizDeelname(1).getLeerling()
	}
}
