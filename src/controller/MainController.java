package controller;

/**
 * 
 * @author Johan Boogers
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import persistency.DBHandler;
import view.MainView;

public class MainController {

	private MainView theView;
	private DBHandler theData;

	MainController() {
		try {
			theData.vulCatalogi();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			theView.displayErrorMessage(e.toString());
			e.printStackTrace();
		}
	}

	class BeheerLeerlingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BeheerLeerlingController aQuizController = new BeheerLeerlingController(theData);
		}
	}
	
	class BeheerQuizListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BeheerQuizController aQuizController = new BeheerQuizController(theData);
		}
	}
	
}
