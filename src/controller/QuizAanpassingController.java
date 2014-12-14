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
import java.util.Collections;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
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
		
		loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz.getQuizOpdrachten());
		

		view.setVisible(true);
	}
	
	//GETTER
	public QuizAanpassingView getView() {
		return view;
	}
	
	private void loadTables(Collection<Opdracht> alleOpdrachten, Collection<QuizOpdracht> quizOpdrachten) {		
		quizOpdrachtenTabelModel.setOpdrachten(quiz);
		alleOpdrachten.removeAll(quizOpdrachten);
		alleOpdrachtenTabelModel.setOpdrachten(alleOpdrachten);
		view.setLblAantalOpdrachten();
	}	

	private Opdracht getGeselecteerdeOpdrachtAlleOpdrachten() {		
		return alleOpdrachtenTabelModel.getOpdracht(view.getSelectieAlleOpdrachten());
	}
	
	private Opdracht getGeselecteerdeQuizOpdracht() {		
		return quizOpdrachtenTabelModel.getOpdracht(view.getSelectieGeselecteerdeOpdrachten());
	}

	private void setRowSorter (int columnIndex) {		
		sortKeys = new ArrayList<>();
		rowSorter.setSortKeys(null);	
		if (!(columnIndex == 1)) {
			sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
			rowSorter.setSortKeys(sortKeys);
		}
		rowSorter.sort();
	}	
	
	//LISTENERCLASSES
	class OpdrachtToevoegenKnopListener implements ActionListener {    
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = getGeselecteerdeOpdrachtAlleOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om toe te voegen", "Fout");
				return;
			}
			if (quizOpdrachtenTabelModel.bevatReedsOpdracht(opdracht)) {
				view.toonInformationDialog("Deze opdracht is al opgenomen in de quiz", "Twee Dezelfde Opdrachten");
				return;
			}
			quizOpdrachtenTabelModel.addOpdracht(opdracht);
		}
	}
	
	class OpdrachtVerwijderenKnopListener implements ActionListener {     
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = getGeselecteerdeQuizOpdracht();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om te verwijderen", "Fout");
				return;
			}
			quizOpdrachtenTabelModel.removeOpdracht(view.getSelectieGeselecteerdeOpdrachten());
		}
	}
	
	class QuizBewaarKnopListener implements ActionListener {   
		@Override
		public void actionPerformed(ActionEvent event) {
			String foutboodschap = new String("");
			// STATUS
			QuizStatus status = view.getQuizStatuscmb();
			// TODO Checken of status geldig is.				
			// KLAS
			String klas = view.getKlasTxt();
			if (klas.equals("")) {
				foutboodschap += "Geef een klas in./n";
			}
			int[] klassenArray = new int[6];
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
				foutboodschap += "Klassen in foutief formaat, gebruik [4] of [1, 2, 4]./n";
			}
			// ONDERWERP
			String onderwerp = view.getOnderwerpTxt();
			if (onderwerp.equals("")) {
				foutboodschap += "Geef een onderwerp in. /n";
			}
			// TEST & UNIEKEDEELNAME
			boolean isTest = view.getIsTestckb();
			boolean isUniekeDeelname = view.getIsUniekeDeelnameckb();
			// TEST OP FOUT
			if (!foutboodschap.equals("")) {
				view.toonFoutBoodschap(foutboodschap);
				return;
			}
			// SET		
			quiz.setDoelLeerjaren(klassenArray);
			quiz.setOnderwerp(onderwerp);
			quiz.setIsTest(isTest);
			quiz.setIsUniekeDeelname(isUniekeDeelname);					
			
			
			if (quiz.isAanpasbaar()) {
			// ONTKOPPEL ALLES
			for(QuizOpdracht qo : quiz.getQuizOpdrachten()) {
				qo.ontkoppelOpdrachtVanQuiz();				
			}
				// KOPPEL ALLES
			for (int i = 0; i<quizOpdrachtenTabelModel.getQuizOpdrachten().size(); i++) {
				
				QuizOpdracht.koppelOpdrachtAanQuiz(quiz, quizOpdrachtenTabelModel.getOpdracht(i), quizOpdrachtenTabelModel.getMaxScore(i));
			}	
			}
			
			quiz.setQuizStatus(status);	
			
			// ADD QUIZ TO DB
			if(!(dbHandler.getQuizCatalogus().getQuizzen()).contains(quiz)) {  
				dbHandler.getQuizCatalogus().addQuiz(quiz); //Add quiz to DB
			}			
			view.toonInformationDialog("Quiz bewaard", "Ok");
			// SET VIEW WITH NEW QUIZ
			quiz = new Quiz(leraar);
			quizBeheerController.updateTabel();
			view.setViewToQuiz(quiz);
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz.getQuizOpdrachten());
		}		
	}
	
	class SelecteerCategorieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String opdrachtCategorieString = view.getOpdrachtCategorie();
			if (opdrachtCategorieString.equals("Alle categorieën")) {
				loadTables(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(), quiz.getQuizOpdrachten());
			} else {
				OpdrachtCategorie OC = OpdrachtCategorie
						.valueOf(opdrachtCategorieString.toUpperCase());
				loadTables(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(OC), quiz.getQuizOpdrachten());
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
		public void actionPerformed(ActionEvent event) {
			quizOpdrachtenTabelModel.zetGeselecteerdeOpdrachtEenHoger();
		}		
	}
	
	//TABELMODELLEN
	@SuppressWarnings("serial")
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
			this.fireTableDataChanged();
		}

		public Opdracht getOpdracht(int row) {
			if (row < opdrachten.size() && row >= 0) {
				return opdrachten.get(row);
			}
			return null;
		}
	}
	
	@SuppressWarnings("serial")
	class QuizOpdrachtenTableModel extends AbstractTableModel {
		private ArrayList<HulpQuizOpdracht> quizOpdrachten;
		private String[] headers;

		public QuizOpdrachtenTableModel() {
			headers = new String[] { "Cat.", "Type", "Vraag", "Max. Score" };
			quizOpdrachten = new ArrayList<>();
		}	

		public int getMaxScore(int row) {
			return quizOpdrachten.get(row).getMaxScore();
		}
		
		public ArrayList<HulpQuizOpdracht> getQuizOpdrachten() {
			return quizOpdrachten;
		}

		@Override
		public int getColumnCount() {
			return headers.length;
		}

		@Override
		public int getRowCount() {
			return quizOpdrachten.size();
		}

		@Override
		public String getColumnName(int col) {
			return headers[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			HulpQuizOpdracht quizOpdracht = quizOpdrachten.get(row);
			Opdracht opdracht = quizOpdracht.getOpdracht();
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
			case 3:	
				return quizOpdracht.getMaxScore();
			default:
				return null;
			}
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
		     if(col == 3 && quiz.isAanpasbaar()) {
		    	 return true;
		     } else {
		    	 return false;
		     }
		}
		
		@Override  
	    public Class<?> getColumnClass(int col) {  
	        if (col == 3)       //MaxScore moet int zijn
	            return Integer.class;  
	        else return String.class;
		}
		
		@Override
		   public void setValueAt(Object maxScore, int row, int col)
		   {	  
				if(col == 3) {
					quizOpdrachten.get(row).setMaxScore((Integer)maxScore);
				}	
		   }
		
		public void zetGeselecteerdeOpdrachtEenHoger() {
			HulpQuizOpdracht teVerplaatsen = null;
			int teVerplaatsenIndex = view.getSelectieGeselecteerdeOpdrachten();
			if (teVerplaatsenIndex < 0) {
				return;
			}
			teVerplaatsen = quizOpdrachten.get(teVerplaatsenIndex);
			if (teVerplaatsenIndex == 0) {
				quizOpdrachten.remove(0);
				quizOpdrachten.add(teVerplaatsen);			
			} else {
				Collections.swap(quizOpdrachten, teVerplaatsenIndex, teVerplaatsenIndex - 1);
			}
			this.fireTableDataChanged();
		}
		
		public boolean bevatReedsOpdracht(Opdracht opdracht) {
			for (HulpQuizOpdracht quizOpdracht : quizOpdrachten) {
				if (quizOpdracht.getOpdracht().equals(opdracht)) {
					return true;
				}
			}
			return false;
		}
		
		public void setOpdrachten(Quiz quiz) {
			this.quizOpdrachten = new ArrayList<HulpQuizOpdracht>();
			for (QuizOpdracht quizOpdracht : quiz.getQuizOpdrachten()) {
				this.quizOpdrachten.add(new HulpQuizOpdracht(quizOpdracht));
			}
			this.fireTableDataChanged();
		}

		public Opdracht getOpdracht(int row) {
			if (row < quizOpdrachten.size() && row >= 0) {
				return quizOpdrachten.get(row).getOpdracht();
			}
			return null;
		}		
		
		public void addOpdracht(Opdracht opdracht) {
			quizOpdrachten.add(new HulpQuizOpdracht(opdracht, 1));
			view.setLblAantalOpdrachten();
			this.fireTableDataChanged();
		}
		
		public void removeOpdracht(int row) {
			quizOpdrachten.remove(row);
			view.setLblAantalOpdrachten();
			this.fireTableDataChanged();
		}
		
	}
	
	class HulpQuizOpdracht {
		private Opdracht opdracht;
		private int maxScore;
		
		public HulpQuizOpdracht(QuizOpdracht quizOpdracht) {
			this.opdracht = quizOpdracht.getOpdracht();
			this.maxScore = quizOpdracht.getMaxScore();
		}
		
		public HulpQuizOpdracht(Opdracht opdracht, int maxScore) {
			this.opdracht = opdracht;
			this.maxScore = maxScore;
		}

		public Opdracht getOpdracht() {
			return opdracht;
		}

		public void setOpdracht(Opdracht opdracht) {
			this.opdracht = opdracht;
		}

		public int getMaxScore() {
			return maxScore;
		}

		public void setMaxScore(int maxScore) {
			this.maxScore = maxScore;
		}
		
	}		

}
