/**
 *
 */
package quizApplication;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import util.datumWrapper.Datum;
import controller.QuizBeheerController;
import view.MainView;
import view.QuizBeheerView;
import persistency.DBHandler;

/**
 * @author Cool Tim
 *
 */
public class Main {


	
	public static void main(String[] args) {		
		
		DBHandler db = new DBHandler();
		QuizBeheerView view = new QuizBeheerView();
		
		try {
			db.vulCatalogi();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		QuizBeheerController test = new QuizBeheerController(db, view);
		
		
	}
}
