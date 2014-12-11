package controller;

/**
 * 
 * @author Adriaan Kuipers
 * @version 08/12/2014
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistency.DBHandler;
import view.QuizAanpassingView;
import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Quiz;
import model.QuizOpdracht;
import model.quizStatus.QuizStatus;

public class QuizAanpassingController {

	private QuizAanpassingView view;
	private DBHandler dbHandler;
	private Quiz quiz, quizClone; //quizClone laat toe om opdrachten toe te voegen en te verwijderen zonder de quiz te veranderen (pas bij bewaren quiz aanpassen)
	private Leraar leraar;
	private Opdracht opdracht;
	private QuizBeheerController quizBeheerController;

	public QuizAanpassingController(Quiz quiz, Leraar leraar,
			DBHandler dbHandler, QuizBeheerController quizBeheerController) {		
		this.quiz = quiz;
		this.quizClone = quiz.clone();
		this.leraar = leraar;
		this.dbHandler = dbHandler;	
		this.quizBeheerController = quizBeheerController;
		view = new QuizAanpassingView(quizClone, leraar,
				dbHandler);

		// Set buttonlisteners
		view.addOpdrachtToevoegenKnopActionListener(new OpdrachtToevoegenKnopListener());
		view.addOpdrachtVerwijderenKnopActionListener(new OpdrachtVerwijderenKnopListener());
		view.addQuizBewarenKnopActionListener(new QuizBewaarKnopListener());

		//Set comboboxlisteners
		view.addSelecteerCategorieActionlistener(new SelecteerCategorieListener());
		view.addSelecteerSorteringActionListener(new SelecteerSorteringListener());
		
		//Set tabellistener
		view.addGeselecteerdeOpdrachtenTabelSelectieListener(new GeselecteerdeOpdrachtenTabelListSelectionListener());

		view.setVisible(true);
	}
	
	public QuizAanpassingView getView() {
		return view;
	}	

	class OpdrachtToevoegenKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = view.getGeselecteerdeOpdrachtAlleOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om toe te voegen", "Fout");
				return;
			}
			int maxScore = 0;
			do{
				try {
					maxScore = Integer.parseInt(view.vraagMaxScore());
					if(maxScore < 1 || maxScore > 100) {
						throw new IllegalArgumentException();
					}
				}
				catch  (Exception ex) {
					view.toonInformationDialog("De maximum score moet een geheel getal tussen 1 & 100 zijn", "Error");
				}
			}while (maxScore < 1 || maxScore > 100);
			QuizOpdracht.koppelOpdrachtAanQuiz(view.getQuiz(), opdracht, maxScore);
			view.setOpdrachtTabellen(dbHandler.getOpdrachtCatalogus().getOpdrachten(), view.getQuiz());
		}
	}	

	class OpdrachtVerwijderenKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = view.getGeselecteerdeOpdrachtQuizOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om te verwijderen", "Fout");
				return;
			}
			for (QuizOpdracht qo : opdracht.getQuizOpdrachten()) {
				if ((qo.getQuiz()).equals(quiz)) {
					qo.ontkoppelOpdrachtVanQuiz();
				}
			}			
			view.setOpdrachtTabellen(dbHandler.getOpdrachtCatalogus().getOpdrachten(), view.getQuiz());
		}
	}

	class QuizBewaarKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			quiz = view.getQuiz();
			// STATUS
			QuizStatus status = view.getQuizStatuscmb();
			// TODO Checken of status geldig is.				
			// KLAS
			String klas = view.getKlasTxt();
			if (klas.equals("")) {
				view.toonInformationDialog("Geef een klas in", "Fout");
				return;
			}
			int[] klassenArray;
			try {
				klas = klas.substring(1, klas.length() - 1);
				String[] klassen = klas.split(", ");
				klassenArray = new int[klassen.length];			
				for (int i = 0; i < (klassen.length); i++) {					
					klassenArray[i] = Integer.parseInt(klassen[i]);
					if (klassenArray[i]<0 || klassenArray[i]>6) {
						throw new IllegalArgumentException();
					}
				}
			} catch (Exception ex) {
				view.toonInformationDialog("Klassen in foutief formaat, gebruik [4] of [1, 2, 4]", "Fout");
				return;
			}
			// ONDERWERP
			String onderwerp = view.getOnderwerpTxt();
			if (onderwerp.equals("")) {
				view.toonInformationDialog("Geef een onderwerp in", "Fout");
				return;
			}
			// TEST & UNIEKEDEELNAME
			boolean isTest = view.getIsTestckb();
			boolean isUniekeDeelname = view.getIsUniekeDeelnameckb();
			// SETQUIZCLONE			
			quizClone.setDoelLeerjaren(klassenArray);
			quizClone.setOnderwerp(onderwerp);
			quizClone.setIsTest(isTest);
			quizClone.setIsUniekeDeelname(isUniekeDeelname);
			quizClone.setQuizStatus(status);
			// SET QUIZ
			quiz = quizClone;			
			//ADD QUIZ TO DB
			if(!(dbHandler.getQuizCatalogus().getQuizzen()).contains(quiz)) {  //TODO loopt hier fout bij aanpassen quiz
				dbHandler.getQuizCatalogus().addQuiz(quiz);
			}
			view.toonInformationDialog("Quiz bewaard", "Ok");
			quiz = new Quiz(leraar);
			quizClone = quiz.clone();
			quizBeheerController.updateTabel();
			view.initViewForQuiz((dbHandler.getOpdrachtCatalogus()).getOpdrachten(), quizClone); // Geef nieuwe, lege quiz aan view
		}
	}
	
	class SelecteerCategorieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String opdrachtCategorieString = view.getOpdrachtCategorie();
			if (opdrachtCategorieString.equals("Alle categorieën")) {
				view.setOpdrachtTabellen(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(), view.getQuiz());
			} else {
				OpdrachtCategorie OC = OpdrachtCategorie
						.valueOf(opdrachtCategorieString.toUpperCase());
				view.setOpdrachtTabellen(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(OC), view.getQuiz());
			}
		}
	}
	
	class SelecteerSorteringListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String sorteerString = view.getSorteerString();
			switch (sorteerString) {
			case "geen":
				view.setRowSorter(1);
				break;
			case "vraag":
				view.setRowSorter(2);
				break;
			case "categorie":
				view.setRowSorter(0);
				break;
			default:
				break;
			}
		}
	}
	
	class WijzigMaxScoreKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Opdracht opdracht = view.getGeselecteerdeOpdrachtQuizOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om de maximum score  voor aan te passen", "Fout");
				return;
			}
			int maxScore = 0;
			do{
				try {
					maxScore = Integer.parseInt(view.vraagMaxScore());
					if(maxScore < 1 || maxScore > 100) {
						throw new IllegalArgumentException();
					}
				}
				catch  (Exception ex) {
					view.toonInformationDialog("De maximum score moet een geheel getal tussen 1 & 100 zijn", "Error");
				}
			}while (maxScore < 1 || maxScore > 100);
			
			for (QuizOpdracht qo : opdracht.getQuizOpdrachten()) {
				if ((qo.getQuiz()).equals(quiz)) {
					qo.setMaxScore(maxScore);
				}
			}
		}		
	}
	
	class GeselecteerdeOpdrachtenTabelListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Opdracht opdracht = view.getGeselecteerdeOpdrachtQuizOpdrachten();
			if (!(opdracht == null)) {
				for (QuizOpdracht qo : opdracht.getQuizOpdrachten()) {
					if ((qo.getQuiz()).equals(quiz)) {
						view.setLblMaxScore(qo.getMaxScore());
					}
				}				
			}			
		}		
	}

}
