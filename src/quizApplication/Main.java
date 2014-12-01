/**
 *
 */
package quizApplication;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import util.datumWrapper.Datum;
import controller.OpstartController;
import controller.OverzichtScoresQuizzenController;
import controller.QuizAanpassingController;
import controller.QuizBeheerController;
//import view.MainView;
import view.OverzichtScoresQuizzenView;
import view.QuizBeheerView;
import persistency.DBHandler;

/**
 * @author Cool Tim
 *
 */
public class Main {


	
	public static void main(String[] args) {		
		
		/*
		DBHandler dbHandler = new DBHandler();
		dbHandler.setUseCSV(true);
		try {
			dbHandler.vulCatalogi();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		Quiz quiz = dbHandler.getQuizCatalogus().getQuiz(1);
		Leraar leraar = Leraar.CHARLOTTE_NEVEN;
		QuizBeheerController qbc = new QuizBeheerController(dbHandler, leraar);
		*/
		
		OpstartController boot = new OpstartController();
		
	}
}
