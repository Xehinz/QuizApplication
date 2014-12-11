package controller;

/**
 * 
 * @author Adriaan Kuipers
 * @version 08/12/2014
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import persistency.DBHandler;
import view.QuizAanpassingView;
import model.KlassiekeOpdracht;
import model.Leraar;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Opsomming;
import model.Quiz;
import model.QuizOpdracht;
import model.Reproductie;
import model.quizStatus.QuizStatus;

public class QuizAanpassingController {
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private QuizAanpassingView view;
	private DBHandler dbHandler;
	private Quiz quiz, quizClone; //quizClone laat toe om opdrachten toe te voegen en te verwijderen zonder de quiz te veranderen (pas bij bewaren quiz aanpassen)
	private Leraar leraar;
	private Opdracht opdracht;
	private QuizBeheerController quizBeheerController;
	private AlleOpdrachtenTableModel alleOpdrachtenTabelModel;
	private GeselecteerdeOpdrachtenTableModel geselecteerdeOpdrachtenTabelModel;
	

	public QuizAanpassingController(Quiz quiz, Leraar leraar,
			DBHandler dbHandler, QuizBeheerController quizBeheerController) {		
		this.quiz = quiz;
		quizClone = quiz.clone();
		this.leraar = leraar;
		this.dbHandler = dbHandler;
		alleOpdrachtenTabelModel = new AlleOpdrachtenTableModel();
		geselecteerdeOpdrachtenTabelModel = new GeselecteerdeOpdrachtenTableModel();
		this.quizBeheerController = quizBeheerController;
		view = new QuizAanpassingView(quizClone, leraar,
				dbHandler);

		// Set buttonlisteners
		view.addOpdrachtToevoegenKnopActionListener(new OpdrachtToevoegenKnopListener());
		view.addOpdrachtVerwijderenKnopActionListener(new OpdrachtVerwijderenKnopListener());
		view.addQuizBewarenKnopActionListener(new QuizBewaarKnopListener());
		view.addWijzigVolgordeKnopActionListener(new WijzigVolgordeKnopListener());

		//Set comboboxlisteners
		view.addSelecteerCategorieActionlistener(new SelecteerCategorieListener());
		view.addSelecteerSorteringActionListener(new SelecteerSorteringListener());
		
		//Set TableModels
		view.setTableModels(alleOpdrachtenTabelModel, geselecteerdeOpdrachtenTabelModel);
		
		loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz);
		

		view.setVisible(true);
	}
	
	public void loadTables(Collection<Opdracht> alleOpdrachten, Quiz quiz) {		
		geselecteerdeOpdrachtenTabelModel.setOpdrachten(quiz.getOpdrachten());
		geselecteerdeOpdrachtenTabelModel.fireTableDataChanged();
		alleOpdrachten.removeAll(quiz.getOpdrachten());
		alleOpdrachtenTabelModel.setOpdrachten(alleOpdrachten);
		alleOpdrachtenTabelModel.fireTableDataChanged();
		view.setLblAantalOpdrachten();
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
	
	/*
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
	*/
	
	class WijzigVolgordeKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			opdracht = view.getGeselecteerdeOpdrachtQuizOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Geen opdracht geselecteerd", "Fout");
				return;
			}
			quizClone.verplaatsOpdrachtEenHoger(opdracht);
		}		
	}	
	
	//TABELMODELLEN
	class AlleOpdrachtenTableModel extends AbstractTableModel {
		private ArrayList<Opdracht> opdrachten;
		private String[] headers;

		public AlleOpdrachtenTableModel() {
			headers = new String[] { "Cat.", "Type", "Vraag" };
			opdrachten = new ArrayList<Opdracht>();
		}

		@Override
		public int getColumnCount() {
			return headers.length;
		}

		@Override
		public int getRowCount() {
			return opdrachten.size();
		}

		@Override
		public String getColumnName(int col) {
			return headers[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			Opdracht opdracht = opdrachten.get(row);
			switch (col) {
			case 0: {
				String cat = new String(opdracht.getOpdrachtCategorie().toString());
				cat = cat.toUpperCase();
				return cat.substring(0, 3);
			}
			case 1: {
				String type = new String();
				if (opdracht instanceof Meerkeuze) {
					type = "MK";
				}
				if (opdracht instanceof Opsomming) {
					type = "OP";
				}
				if (opdracht instanceof Reproductie) {
					type = "RE";
				} 
				if (opdracht instanceof KlassiekeOpdracht) {
					type = "KL";
				}
				return type;
			}
			case 2:
				return opdracht.getVraag();
			default:
				return null;
			}
		}

		public void setOpdrachten(Collection<Opdracht> opdrachten) {
			this.opdrachten = new ArrayList<Opdracht>(opdrachten);
		}

		public Opdracht getOpdracht(int row) {
			if (row < opdrachten.size() && row >= 0) {
				return opdrachten.get(row);
			}
			return null;
		}
	}
		
	class GeselecteerdeOpdrachtenTableModel extends AbstractTableModel {
		private ArrayList<Opdracht> opdrachten;
		private String[] headers;

		public GeselecteerdeOpdrachtenTableModel() {
			headers = new String[] { "Cat.", "Type", "Vraag", "Max. Score" };
			opdrachten = new ArrayList<Opdracht>();
		}

		@Override
		public int getColumnCount() {
			return headers.length;
		}

		@Override
		public int getRowCount() {
			return opdrachten.size();
		}

		@Override
		public String getColumnName(int col) {
			return headers[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			Opdracht opdracht = opdrachten.get(row);
			switch (col) {
			case 0: {
				String cat = new String(opdracht.getOpdrachtCategorie().toString());
				cat = cat.toUpperCase();
				return cat.substring(0, 3);
			}
			case 1: {
				String type = new String();
				if (opdracht instanceof Meerkeuze) {
					type = "MK";
				}
				if (opdracht instanceof Opsomming) {
					type = "OP";
				}
				if (opdracht instanceof Reproductie) {
					type = "RE";
				} 
				if (opdracht instanceof KlassiekeOpdracht) {
					type = "KL";
				}
				return type;
			}
			case 2:
				return opdracht.getVraag();
			case 3: //TODO return maxScore
				return null;
			default:
				return null;
			}
		}

		public void setOpdrachten(Collection<Opdracht> opdrachten) {
			this.opdrachten = new ArrayList<Opdracht>(opdrachten);
		}

		public Opdracht getOpdracht(int row) {
			if (row < opdrachten.size() && row >= 0) {
				return opdrachten.get(row);
			}
			return null;
		}
	}
		
		

}
