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
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
	private Quiz quiz;
	private Leraar leraar;
	private Opdracht opdracht;
	private QuizBeheerController quizBeheerController;
	private AlleOpdrachtenTableModel alleOpdrachtenTabelModel;
	private QuizOpdrachtenTableModel quizOpdrachtenTabelModel;
	private TableRowSorter<TableModel> rowSorter;
	private List<RowSorter.SortKey> sortKeys;
	
	
	/**
	 * Default constructor met parameters
	 * @param quiz
	 * @param ingelogde leraar
	 * @param dbHandler
	 * @param Controller die deze controller heeft opgeroepen	 
	 */
	public QuizAanpassingController(Quiz quiz, Leraar leraar,
			DBHandler dbHandler, QuizBeheerController quizBeheerController) {		
		this.quiz = quiz;
		this.leraar = leraar;
		this.dbHandler = dbHandler;
		alleOpdrachtenTabelModel = new AlleOpdrachtenTableModel();
		quizOpdrachtenTabelModel = new QuizOpdrachtenTableModel();
		this.quizBeheerController = quizBeheerController;
		view = new QuizAanpassingView(quiz, leraar,
				dbHandler);		

		// Set buttonlisteners
		view.addOpdrachtToevoegenKnopActionListener(new OpdrachtToevoegenKnopListener());
		view.addOpdrachtVerwijderenKnopActionListener(new OpdrachtVerwijderenKnopListener());
		view.addQuizBewarenKnopActionListener(new QuizBewaarKnopListener());
		view.addWijzigVolgordeKnopActionListener(new WijzigVolgordeKnopListener());

		//Set comboboxlisteners
		view.addSelecteerCategorieActionlistener(new SelecteerCategorieListener());
		view.addSelecteerSorteringActionListener(new SelecteerSorteringListener());
		
		//Set TableModels & rowSorter
		view.setTableModels(alleOpdrachtenTabelModel, quizOpdrachtenTabelModel);
		rowSorter = new TableRowSorter<TableModel>(view.getAlleOpdrachtenTabel().getModel());		
		view.getAlleOpdrachtenTabel().setRowSorter(rowSorter);
		
		loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz);
		

		view.setVisible(true);
	}
	
	/**
	 * geeft data aan tabellen & update
	 * @param quiz
	 * @param lijst met alle opdrachten
	 */
	public void loadTables(Collection<Opdracht> alleOpdrachten, Quiz quiz) {		
		quizOpdrachtenTabelModel.setOpdrachten(quiz.getOpdrachten());
		quizOpdrachtenTabelModel.fireTableDataChanged();
		alleOpdrachten.removeAll(quiz.getOpdrachten());
		alleOpdrachtenTabelModel.setOpdrachten(alleOpdrachten);
		alleOpdrachtenTabelModel.fireTableDataChanged();
		view.setLblAantalOpdrachten();
	}
	
	/**
	 * geeft de geselecteerde opdracht op basis van een rijnummer
	 * @param geselecteerde rij
	 * @return overeenkomstige Opdracht
	 */
	public Opdracht getGeselecteerdeOpdrachtAlleOpdrachten(int rij) {		
		return alleOpdrachtenTabelModel.getOpdracht(rij);
	}
	public Opdracht getGeselecteerdeQuizOpdracht(int rij) {		
		return quizOpdrachtenTabelModel.getOpdracht(rij);
	}
	
	/**
	 * Sorteer tabel
	 * @param kolom om op te sorteren
	 */
	public void setRowSorter (int columnIndex) {		
		sortKeys = new ArrayList<>();
		rowSorter.setSortKeys(null);	
		if (!(columnIndex == 1)) {
			sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
			rowSorter.setSortKeys(sortKeys);
		}
		rowSorter.sort();
	}	
	
	//GETTER
	public QuizAanpassingView getView() {
		return view;
	}
	
	//LISTENERCLASSES
	class OpdrachtToevoegenKnopListener implements ActionListener {    //TODO redo (add to list)
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = getGeselecteerdeOpdrachtAlleOpdrachten(view.getSelectieAlleOpdrachten());
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om toe te voegen", "Fout");
				return;
			}
			QuizOpdracht.koppelOpdrachtAanQuiz(quiz, opdracht, 1); //TODO MaxScore
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz);
		}
	}
	class OpdrachtVerwijderenKnopListener implements ActionListener {     //TODO redo (remove from list)
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = getGeselecteerdeQuizOpdracht(view.getSelectieGeselecteerdeOpdrachten());
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
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz);
		}
	}
	class QuizBewaarKnopListener implements ActionListener {   //TODO add quizopdrachten from list to quiz
		@Override
		public void actionPerformed(ActionEvent event) {
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
			// SET		
			quiz.setDoelLeerjaren(klassenArray);
			quiz.setOnderwerp(onderwerp);
			quiz.setIsTest(isTest);
			quiz.setIsUniekeDeelname(isUniekeDeelname);
			quiz.setQuizStatus(status);						
			//ADD QUIZ TO DB
			if(!(dbHandler.getQuizCatalogus().getQuizzen()).contains(quiz)) {  
				dbHandler.getQuizCatalogus().addQuiz(quiz); //Add quiz to DB
			}			
			view.toonInformationDialog("Quiz bewaard", "Ok");
			//SET VIEW WITH NEW QUIZ
			quiz = new Quiz(leraar);
			quizBeheerController.updateTabel();
			view.setViewToQuiz(quiz);
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz);
		}
	}
	class SelecteerCategorieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String opdrachtCategorieString = view.getOpdrachtCategorie();
			if (opdrachtCategorieString.equals("Alle categorieën")) {
				loadTables(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(), quiz);
			} else {
				OpdrachtCategorie OC = OpdrachtCategorie
						.valueOf(opdrachtCategorieString.toUpperCase());
				loadTables(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(OC), quiz);
			}
		}
	}
	class SelecteerSorteringListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String sorteerString = view.getSorteerString();
			switch (sorteerString) {
			case "geen":
				setRowSorter(1);
				break;
			case "vraag":
				setRowSorter(2);
				break;
			case "categorie":
				setRowSorter(0);
				break;
			default:
				break;
			}
		}
	}
	class WijzigVolgordeKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			opdracht = getGeselecteerdeQuizOpdracht(view.getSelectieGeselecteerdeOpdrachten());
			if (opdracht == null) {
				view.toonInformationDialog(
						"Geen opdracht geselecteerd", "Fout");
				return;
			}
			quiz.verplaatsOpdrachtEenHoger(opdracht); //TODO verplaatsen binnen lijst
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz);
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
	class QuizOpdrachtenTableModel extends AbstractTableModel {
		private ArrayList<Opdracht> opdrachten;
		private String[] headers;

		public QuizOpdrachtenTableModel() {
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
	
		
	
	//TABELMODELLEN
	
		
		

}
