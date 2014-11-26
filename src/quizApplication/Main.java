/**
 *
 */
package quizApplication;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import util.datumWrapper.Datum;
import controller.OverzichtScoresQuizzenController;
import controller.QuizBeheerController;
import view.MainView;
import view.OverzichtScoresQuizzenView;
import view.QuizBeheerView;
import persistency.DBHandler;

/**
 * @author Cool Tim
 *
 */
public class Main {


	
	public static void main(String[] args) {		
		
		OverzichtScoresQuizzenView scores = new OverzichtScoresQuizzenView();
		DBHandler dbHandler = new DBHandler();
		try {
			dbHandler.vulCatalogi();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		OverzichtScoresQuizzenController contr = new OverzichtScoresQuizzenController(dbHandler);
		

		
		
	}
}
