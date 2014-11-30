package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import persistency.DBHandler;
import view.viewInterfaces.IOverzichtScoresQuizzenView;
import view.viewInterfaces.IOverzichtScoresViewFactory;
import model.Quiz;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class OverzichtScoresQuizzenController {	

	private IOverzichtScoresQuizzenView quizzenView;
	private IOverzichtScoresViewFactory overzichtScoresViewFactory;
	private QuizScoreTableModel quizScoreTableModel;
	
	public OverzichtScoresQuizzenController(DBHandler dbHandler, IOverzichtScoresViewFactory overzichtScoresViewFactory) {
		
		this.overzichtScoresViewFactory = overzichtScoresViewFactory;
		this.quizzenView = (IOverzichtScoresQuizzenView)overzichtScoresViewFactory.maakOverzichtScoresView("quiz");

		quizScoreTableModel = new QuizScoreTableModel(dbHandler.getQuizCatalogus().getReedsIngevuldeQuizzen());
		
		quizzenView.setTableModel(quizScoreTableModel);		
		quizzenView.addDetailKnopListener(new DetailKnopListener());				
		quizzenView.setVisible(true);	
	}
	
	public void addStartScoreViewClosedListener(WindowListener listener) {
		quizzenView.addWindowListener(listener);
	}
	
	public void roepStartScoreViewNaarVoren() {
		quizzenView.toFront();
	}
	
	class DetailKnopListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			Quiz geselecteerd = quizScoreTableModel.getQuiz(quizzenView.getGeselecteerdeRijIndex());
			if (geselecteerd == null) {
				quizzenView.toonInformationDialog("Selecteer een quiz om details te zien", "Geen quiz geselecteerd");
			}
			else {
				@SuppressWarnings("unused")
				OverzichtScoresLeerlingenController overzichtScoresLeerlingController = new OverzichtScoresLeerlingenController(geselecteerd, OverzichtScoresQuizzenController.this, overzichtScoresViewFactory);					
			}			
		}
		
	}

	@SuppressWarnings("serial")
	class QuizScoreTableModel extends AbstractTableModel {

		ArrayList<Quiz> quizzen = new ArrayList<Quiz>();
		String[] headers = new String[]{"Onderwerp", "Auteur", "Leerjaren", "Gemiddelde score"};
		
		public QuizScoreTableModel(Collection<Quiz> quizzen) {
			this.quizzen = new ArrayList<Quiz>(quizzen);
		}	
		
		public Quiz getQuiz(int row) {
			if (row < 0 || row > quizzen.size()) {
				return null;
			}
			return quizzen.get(row);
		}
	
		@Override   
		public String getColumnName(int col) {
		       return headers[col];
		    }
		
		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return quizzen.size();
		}
		
	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	        if (quizzen.isEmpty()) {
	            return Object.class;
	        }
	        return getValueAt(0, columnIndex).getClass();
	    }

		@Override
		public Object getValueAt(int row, int col) {
			Quiz quiz = quizzen.get(row);
			switch(col) {
			case 0:
				return quiz.getOnderwerp();
			case 1:
				return quiz.getAuteur();
			case 2:				
				ArrayList<Integer> doelLeerjaren = quiz.getDoelLeerjaren();
				if (doelLeerjaren.size() == 0) {
					return "geen";
				}
				String result = "";
				for (int i = 0; i < doelLeerjaren.size(); i++) {
					if (i < doelLeerjaren.size() - 1) {
						result += doelLeerjaren.get(i) + ", ";
					} else {
						result += doelLeerjaren.get(i);
					}
				}
				return result;
			case 3:
				return quiz.getGemiddeldeScore();
			}
			return "";
		}
		
	}
}
