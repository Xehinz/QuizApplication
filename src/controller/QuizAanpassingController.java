package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import persistency.DBHandler;
import view.QuizAanpassingView;
import model.Leraar;
import model.Opdracht;
import model.Quiz;
import model.quizStatus.QuizStatus;

public class QuizAanpassingController {
	
	private QuizAanpassingView view;
	private DBHandler dbHandler;
	private Quiz quiz;
	private Leraar leraar;
	private Opdracht opdracht;
	
	public QuizAanpassingController(Quiz quiz, Leraar leraar, DBHandler dbHandler) {
		view = new QuizAanpassingView(quiz, leraar, (dbHandler.getOpdrachtCatalogus()).getOpdrachten());
		this.quiz = quiz;
		this.leraar = leraar;
		this.dbHandler = dbHandler;
		
		
		//Set buttonlisteners
		view.addOpdrachtToevoegenKnopActionListener(new OpdrachtToevoegenKnopListener());
		view.addopdrachtVerwijderenKnopActionListener(new OpdrachtVerwijderenKnopListener());
		view.addQuizBewarenKnopActionListener(new QuizBewaarKnopListener());
		
		//Set comboboxlisteners
		
		
		//Set checkboxlisteners
		
		
		
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}
	
	//view.setOpdrachtTabellen((dbHandler.getOpdrachtCatalogus()).getOpdrachten(), quiz);
	
	class OpdrachtToevoegenKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = view.getGeselecteerdeOpdrachtAlleOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om toe te voegen", "Fout");
				return;
			}
			//TODO add opdracht to quizOpdrachten
		}
	}
	
	class OpdrachtVerwijderenKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = view.getGeselecteerdeOpdrachtQuizOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om toe te voegen", "Fout");
				return;
			}
			//TODO remove opdracht from quizOpdrachten
		}
	}
	
	class QuizBewaarKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			quiz = view.getQuiz();
			QuizStatus status = view.getQuizStatuscmb();
			if (status == null) {
				//TODO Checken of status geldig is.
				return;
			}
			String klas = view.getKlasTxt();
			if (klas == null) {
				//TODO Checken klas geldig is, en meerdere klassen in juiste formaat staan.
				return;
			}
			String onderwerp = view.getOnderwerpTxt();
			if (onderwerp == null) {
				view.toonInformationDialog(
						"Geef een onderwerp in", "Fout");
				return;
			}
			boolean isTest = view.getIsTestckb();
			boolean isUniekeDeelname = view.getIsUniekeDeelnameckb();
			quiz.setQuizStatus(status);
			//TODO setDoeljaren   quiz.setDoelLeerjaren(doelLeerjaar);
			quiz.setOnderwerp(onderwerp);
			quiz.setIsTest(isTest);
			quiz.setIsUniekeDeelname(isUniekeDeelname);
			view.toonInformationDialog(
					"Quiz", "Fout");
			//TODO quiz updaten / toevoegen in DB
			view.setViewToQuiz(new Quiz(leraar), (dbHandler.getOpdrachtCatalogus()).getOpdrachten());  //Geef nieuwe quiz aan view
		}
	}

}
