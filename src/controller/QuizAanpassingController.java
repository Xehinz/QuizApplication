package controller;

import javax.swing.JFrame;

import persistency.DBHandler;
import view.QuizAanpassingView;
import model.Leraar;
import model.Quiz;

public class QuizAanpassingController {
	
	private QuizAanpassingView view;
	private DBHandler dbHandler;
	private Quiz quiz;
	private Leraar leraar;
	
	public QuizAanpassingController(Quiz quiz, Leraar leraar, DBHandler dbHandler) {
		view = new QuizAanpassingView();
		this.quiz = quiz;
		this.leraar = leraar;
		this.dbHandler = dbHandler;
		
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}

}
