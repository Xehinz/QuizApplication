package controller;

/**
 * 
 * @author Adriaan Kuipers
 * @version 14/12/2014
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import model.Leraar;
import model.Quiz;
import persistency.DBHandler;
import view.QuizBeheerView;

public class QuizBeheerController {

	private DBHandler dbHandler;
	private QuizBeheerView view;
	private Quiz quiz;
	private Leraar leraar;
	private boolean quizAanpassingStaatOpen;
	private QuizAanpassingController quizAanpassingController;
	private QuizBeheerTableModel tabelModel;

	public QuizBeheerController(DBHandler dbHandler, Leraar leraar) {
		this.dbHandler = dbHandler;
		this.leraar = leraar;
		this.view = new QuizBeheerView();
		this.quiz = null;
		quizAanpassingStaatOpen = false;
		
		// Vul Tabel
		tabelModel = new QuizBeheerTableModel();
		view.setTabelModel(tabelModel);
		tabelModel.setQuizzen(dbHandler.getQuizCatalogus().getQuizzen());
		// Set Knoppen
		view.addNieuweQuizKnopActionListener(new NieuweQuizKnopListener());
		view.addAanpassenQuizKnopActionListener(new AanpassenQuizKnopListener());
		view.addVerwijderQuizKnopActionListener(new VerwijderQuizKnopListener());
		view.setVisible(true);
	}
		
	public QuizBeheerView getView() {
		return view;
	}

	private void openQuizAanpassing(Quiz quiz, Leraar leraar) {
		if(quizAanpassingStaatOpen) {
			quizAanpassingController.getView().toFront();
		}
		else {
			quizAanpassingStaatOpen = true;
			quizAanpassingController = new QuizAanpassingController(quiz,
					leraar, dbHandler, this);
			quizAanpassingController.getView().addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent event) {
					quizAanpassingStaatOpen = false;
				}
			});
		}
	}
	
	public void updateTabel() {
		tabelModel.setQuizzen(dbHandler.getQuizCatalogus().getQuizzen());
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
			quiz = tabelModel.getQuiz(view.getGeselecteerdeRij());
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
			if(quizAanpassingStaatOpen) {
				quizAanpassingController.getView().toFront();
			}
			else {
				quiz = tabelModel.getQuiz(view.getGeselecteerdeRij());
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
				int bevestig = JOptionPane.showConfirmDialog(null,"Weet u zeker dat u deze quiz wil verwijderen","Verwijder",2);
				if(bevestig == JOptionPane.YES_OPTION) {
					dbHandler.getQuizCatalogus().removeQuiz(quiz);
					updateTabel();
				}				
			}			
		}
	}	
	
	//TABELMODEL
	@SuppressWarnings("serial")
	public class QuizBeheerTableModel extends AbstractTableModel {

		private ArrayList<Quiz> quizzen;
		private String[] headers;
		
		
		public QuizBeheerTableModel () {
			headers = new String[]{"Auteur","Onderwerp","Klas","Vragen","Status","Test","Unieke Deelname"};
			quizzen = new ArrayList<Quiz>();
		}
		
		
		@Override
		public int getColumnCount() {
			return headers.length;
		}
		@Override
		public int getRowCount() {
			return quizzen.size();
		}
		
		@Override   
		public String getColumnName(int col) {
		        return headers[col];
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			Quiz quiz = quizzen.get(row);
		     switch (col) {
		     case 0: return quiz.getAuteur();
		     case 1: return quiz.getOnderwerp();
		     case 2: return quiz.getDoelLeerjaren();
		     case 3: return (quiz.getQuizOpdrachten()).size();
		     case 4: return quiz.getQuizStatus();
		     case 5: return quiz.getIsTest();
		     case 6: return quiz.getIsUniekeDeelname();
		     
		     default: return null;
		    }
		}

		public void setQuizzen (Collection<Quiz> quizzen) {
			this.quizzen = new ArrayList<Quiz>(quizzen);
			this.fireTableDataChanged();
		}
		
		public Quiz getQuiz(int row) {
			if (row < quizzen.size() && row >= 0) {
				return quizzen.get(row);
			}
			return null;
		}
			
	}

}
