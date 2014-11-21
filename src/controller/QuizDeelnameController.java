package controller;

import javax.swing.JFrame;

import view.QuizDeelnameView;
import model.Leerling;
import model.Quiz;

public class QuizDeelnameController {
	
	private final Quiz quiz;
	private final Leerling leerling;
	private QuizDeelnameView quizDeelnameView;
	
	public QuizDeelnameController(Quiz quiz, Leerling leerling, QuizDeelnameView quizDeelnameView) {
		this.quiz = quiz;
		this.leerling = leerling;
		this.quizDeelnameView = quizDeelnameView;
		
		quizDeelnameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//quizDeelnameView.setSize(500, 200);
		quizDeelnameView.setVisible(true);		
	}

}
