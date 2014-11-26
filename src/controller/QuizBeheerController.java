package controller;

import javax.swing.JFrame;
import persistency.DBHandler;
import view.QuizBeheerView;

public class QuizBeheerController {
	
	private DBHandler dbHandler;
	private QuizBeheerView view;
	
	public QuizBeheerController(DBHandler dbHandler, QuizBeheerView view) {
		this.dbHandler = dbHandler;
		this.view = view;
		
		view.setQuizzen(dbHandler.getQuizCatalogus().getQuizzen());
		
		
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}

}
