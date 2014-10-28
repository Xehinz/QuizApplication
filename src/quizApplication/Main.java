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
		// System.out.println(testQuiz.isGeldigLeerjaar(1));
		QuizDeelname.koppelQuizAanLeerling(testQuiz, testLeerling);
		testQuiz.getQuizDeelname(1).getLeerling().setLeerjaar(3);
	}
}
