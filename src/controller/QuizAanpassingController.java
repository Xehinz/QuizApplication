package controller;

/**
 * 
 * @author Adriaan Kuipers
 * @version 08/12/2014
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JFrame;

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
	private Quiz quiz;
	private Leraar leraar;
	private Opdracht opdracht;

	public QuizAanpassingController(Quiz quiz, Leraar leraar,
			DBHandler dbHandler) {
		view = new QuizAanpassingView(quiz, leraar,
				dbHandler);
		this.quiz = quiz;
		this.leraar = leraar;
		this.dbHandler = dbHandler;

		// Set buttonlisteners
		view.addOpdrachtToevoegenKnopActionListener(new OpdrachtToevoegenKnopListener());
		view.addopdrachtVerwijderenKnopActionListener(new OpdrachtVerwijderenKnopListener());
		view.addQuizBewarenKnopActionListener(new QuizBewaarKnopListener());

		//Set comboboxlisteners
		view.addCategorieLijstSelectieActionListener(new CategorieLijstSelectieListener());
		view.addSorteerLijstSelectieActionListener(new SorteerLijstSelectieListener());

		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
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
			QuizOpdracht.koppelOpdrachtAanQuiz(view.getQuiz(), view.getGeselecteerdeOpdrachtAlleOpdrachten(), view.getMaxScore());
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
			.ontkoppelOpdrachtVanQuiz()
		}
	}

	class QuizBewaarKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			quiz = view.getQuiz();
			// STATUS
			QuizStatus status = view.getQuizStatuscmb();
			if (status == null) {
				// TODO Checken of status geldig is.
				return;
			}
			// KLAS
			String klas = view.getKlasTxt();
			if (klas == null) {
				view.toonInformationDialog("Geef een klas in", "Fout");
				return;
			}
			klas = klas.substring(1, klas.length() - 1);
			String[] klassen = klas.split(", ");
			int[] klassenArray = new int[klassen.length];
			try {
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
			if (onderwerp == null) {
				view.toonInformationDialog("Geef een onderwerp in", "Fout");
				return;
			}
			// TEST & UNIEKEDEELNAME
			boolean isTest = view.getIsTestckb();
			boolean isUniekeDeelname = view.getIsUniekeDeelnameckb();
			// SETQUIZ
			quiz.setQuizStatus(status);
			quiz.setDoelLeerjaren(klassenArray);
			quiz.setOnderwerp(onderwerp);
			quiz.setIsTest(isTest);
			quiz.setIsUniekeDeelname(isUniekeDeelname);
			view.toonInformationDialog("Quiz bewaard", "Ok");
			//ADD QUIZ TO DB
			if(!(dbHandler.getQuizCatalogus().getQuizzen()).contains(quiz)) {
				dbHandler.getQuizCatalogus().addQuiz(quiz);
			}
			view.initViewForQuiz(new Quiz(leraar),
					(dbHandler.getOpdrachtCatalogus()).getOpdrachten()); // Geef nieuwe quiz aan view
		}
	}
	
	class CategorieLijstSelectieListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
		       if (event.getStateChange() == ItemEvent.SELECTED) {
		          OpdrachtCategorie cat = (OpdrachtCategorie) event.getItem();
		          view.filterOpCategorie(cat);
		       }
		    }       
	}
	
	class SorteerLijstSelectieListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
		       if (event.getStateChange() == ItemEvent.SELECTED) {
		          String selectie = (String)event.getItem();
		          view.sorteerAlleOpdrachten(selectie);
		       }
		    } 
	}

}
