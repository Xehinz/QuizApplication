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
	private ArrayList<Opdracht> quizOpdrachten;
	
	
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
		
		loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz.getOpdrachten());
		

		view.setVisible(true);
	}
	
	/**
	 * geeft data aan tabellen & update
	 * @param quiz
	 * @param lijst met alle opdrachten
	 */
	public void loadTables(Collection<Opdracht> alleOpdrachten, Collection<Opdracht> quizOpdrachten) {		
		quizOpdrachtenTabelModel.setOpdrachten(quizOpdrachten);
		alleOpdrachten.removeAll(quizOpdrachten);
		alleOpdrachtenTabelModel.setOpdrachten(alleOpdrachten);
		view.setLblAantalOpdrachten();
	}
	
	/**
	 * geeft de geselecteerde opdracht op basis van een rijnummer
	 * @param geselecteerde rij
	 * @return overeenkomstige Opdracht
	 */
	public Opdracht getGeselecteerdeOpdrachtAlleOpdrachten() {		
		return alleOpdrachtenTabelModel.getOpdracht(view.getSelectieAlleOpdrachten());
	}
	public Opdracht getGeselecteerdeQuizOpdracht() {		
		return quizOpdrachtenTabelModel.getOpdracht(view.getSelectieGeselecteerdeOpdrachten());
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
	class OpdrachtToevoegenKnopListener implements ActionListener {    
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = getGeselecteerdeOpdrachtAlleOpdrachten();
			if (opdracht == null) {
				view.toonInformationDialog(
						"Selecteer een opdracht om toe te voegen", "Fout");
				return;
			}
			quizOpdrachtenTabelModel.addOpdracht(opdracht);
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quizOpdrachtenTabelModel.getOpdrachten());
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
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quizOpdrachtenTabelModel.getOpdrachten());
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
				foutboodschap += "Geef een klas in";
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
				foutboodschap += "Klassen in foutief formaat, gebruik [4] of [1, 2, 4]";
			}
			// ONDERWERP
			String onderwerp = view.getOnderwerpTxt();
			if (onderwerp.equals("")) {
				foutboodschap += "Geef een onderwerp in";
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
			quiz.setQuizStatus(status);	
			// SET QUIZOPDRACHTEN
			quizOpdrachten = new ArrayList<>(quizOpdrachtenTabelModel.getOpdrachten());
			
				
			//methode 1 : eerst alles weg, dan alles (in de nieuwe volgorde) toevoegen.  TODO remove comment
			
			
				// ONTKOPPEL ALLES
			for(QuizOpdracht qo : quiz.getQuizOpdrachten()) {
				qo.ontkoppelOpdrachtVanQuiz();				
			}
				// KOPPEL ALLES
			for (int i = 0; i<quizOpdrachten.size(); i++) {
				QuizOpdracht.koppelOpdrachtAanQuiz(quiz, quizOpdrachten.get(i), quizOpdrachtenTabelModel.getMaxScore(i));
			}
			
			
			//methode 2 : test of opdracht al in de quiz zit. indien wel, alleen maxScore updaten(zou ook nog eerst getest kunnen worden)  TODO remove comment
			
			/*
			for (int i = 0; i<quizOpdrachten.size(); i++) {
				Opdracht opdracht = quizOpdrachten.get(i);
				if(quizOpdrachtContainsopdracht(quiz.getQuizOpdrachten(), opdracht)) {
					final int row = i;
					quiz.getQuizOpdrachten().stream().filter(qo -> qo.getOpdracht().equals(opdracht)).forEach(qo -> {qo.setMaxScore(quizOpdrachtenTabelModel.getMaxScore(row));});
				} else {
					QuizOpdracht.koppelOpdrachtAanQuiz(quiz, opdracht, quizOpdrachtenTabelModel.getMaxScore(i));
				}				
			}			
			//TODO check/update volgorde
			*/
			
			
			// ADD QUIZ TO DB
			if(!(dbHandler.getQuizCatalogus().getQuizzen()).contains(quiz)) {  
				dbHandler.getQuizCatalogus().addQuiz(quiz); //Add quiz to DB
			}			
			view.toonInformationDialog("Quiz bewaard", "Ok");
			// SET VIEW WITH NEW QUIZ
			quiz = new Quiz(leraar);
			quizBeheerController.updateTabel();
			view.setViewToQuiz(quiz);
			loadTables(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz.getOpdrachten());
		}
		
		/*
		public boolean quizOpdrachtContainsopdracht(final List<QuizOpdracht> list, final Opdracht opdracht){
		    return list.stream().filter(qo -> qo.getOpdracht().equals(opdracht)).findFirst().isPresent();
		}
		*/
		
	}
	class SelecteerCategorieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String opdrachtCategorieString = view.getOpdrachtCategorie();
			if (opdrachtCategorieString.equals("Alle categorieën")) {
				loadTables(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(), quiz.getOpdrachten());
			} else {
				OpdrachtCategorie OC = OpdrachtCategorie
						.valueOf(opdrachtCategorieString.toUpperCase());
				loadTables(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(OC), quiz.getOpdrachten());
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
			quizOpdrachtenTabelModel.wijzigVolgorde(getGeselecteerdeQuizOpdracht());
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
			this.fireTableDataChanged();
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
		private ArrayList<Integer> maxScores;
		private String[] headers;

		public QuizOpdrachtenTableModel() {
			headers = new String[] { "Cat.", "Type", "Vraag", "Max. Score" };
			opdrachten = new ArrayList<>();
			maxScores = new ArrayList<>();
		}

		public void wijzigVolgorde(Opdracht opdracht) {
			int positie = opdrachten.indexOf(opdracht);
			opdrachten.remove(positie);
			if (positie == 0) {
				opdrachten.add(opdrachten.size(), opdracht);
			} else {
				opdrachten.add(positie-1, opdracht);
			}
			this.fireTableDataChanged();
		}

		public int getMaxScore(int row) {
			return maxScores.get(row);
		}

		public Collection<Opdracht> getOpdrachten() {
			return opdrachten;
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
			case 3:
				if(row>maxScores.size()-1) {
					maxScores.add(1);
				}
				return maxScores.get(row);
			default:
				return null;
			}
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
		     if(col == 3) {
		    	 return true;
		     } else {
		    	 return false;
		     }
		}

		public void setOpdrachten(Collection<Opdracht> opdrachten) {
			this.opdrachten = new ArrayList<Opdracht>(opdrachten);
			for (Opdracht o : opdrachten) {
				for (QuizOpdracht qo : o.getQuizOpdrachten()) {   //GET QUIZOPDRACHT MET DEZE OPDRACHT & QUIZ
					if ((qo.getQuiz()).equals(quiz)) {   //GET QUIZOPDRACHT MET DEZE OPDRACHT & QUIZ
						maxScores.add(qo.getMaxScore());
					}
				}
			}
			this.fireTableDataChanged();
		}

		public Opdracht getOpdracht(int row) {
			if (row < opdrachten.size() && row >= 0) {
				return opdrachten.get(row);
			}
			return null;
		}
		
		public void setMaxScoreVoorRij(int row, int maxScore) {
			maxScores.set(row, maxScore);
		}
		
		public void addOpdracht(Opdracht opdracht) {
			opdrachten.add(opdracht);
			this.fireTableDataChanged();
		}
		
		public void removeOpdracht(int row) {
			opdrachten.remove(row);
			maxScores.remove(row);
			this.fireTableDataChanged();
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
