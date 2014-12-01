package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Leraar;
import model.Quiz;
import persistency.DBHandler;
import view.QuizBeheerView;

public class QuizBeheerController {

	private DBHandler dbHandler;
	private QuizBeheerView view;
	private Quiz quiz;
	private Leraar leraar;

	public QuizBeheerController(DBHandler dbHandler, Leraar leraar) {
		this.dbHandler = dbHandler;
		this.leraar = leraar;
		this.view = new QuizBeheerView();
		this.quiz = null;

		// Vul Tabel
		view.setQuizzen(dbHandler.getQuizCatalogus().getQuizzen());
		// Set Knoppen
		view.addNieuweQuizKnopActionListener(new NieuweQuizKnopListener());
		view.addAanpassenQuizKnopActionListener(new AanpassenQuizKnopListener());
		view.addVerwijderQuizKnopActionListener(new VerwijderQuizKnopListener());

		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}
	
	// Adriaan, dit had ik even nodig
	public QuizBeheerView getView() {
		return view;
	}

	private void openQuizAanpassing(Quiz quiz, Leraar leraar) {
		QuizAanpassingController qac = new QuizAanpassingController(quiz,
				leraar, dbHandler);
	}

	class NieuweQuizKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			quiz = new Quiz(leraar);
			openQuizAanpassing(quiz, leraar);
		}
	}

	class AanpassenQuizKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			quiz = view.getGeselecteerdeQuiz();
			if (quiz == null) {
				view.toonInformationDialog(
						"Selecteer een quiz om aan te passen", "Fout");
				return;
			}
			openQuizAanpassing(quiz, quiz.getAuteur());
		}
	}

	class VerwijderQuizKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			quiz = view.getGeselecteerdeQuiz();
			if (quiz == null) {
				view.toonInformationDialog(
						"Selecteer een quiz om te verwijderen", "Fout");
				return;
			}
			if (!quiz.isVerwijderbaar()) {
				view.toonInformationDialog(
						"Kan deze quiz niet verwijderen", "Fout");
				return;
			}			
			if (!quiz.isVerwijderbaar()) {
				view.toonInformationDialog(
						"Kan deze quiz niet verwijderen", "Fout");
				return;
			}
			int bevestig = JOptionPane.showConfirmDialog(null,"Weet u zeker dat u deze quiz wil verwijderen","Verwijder",2);
			if(bevestig == JOptionPane.YES_OPTION) {
				dbHandler.getQuizCatalogus().removeQuiz(quiz);
			}
		}
	}

}
