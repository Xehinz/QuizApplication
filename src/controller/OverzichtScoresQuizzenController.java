package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import persistency.DBHandler;
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.IOverzichtScoresQuizzenView;
import view.viewInterfaces.IView;
import model.Quiz;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class OverzichtScoresQuizzenController {	

	private IOverzichtScoresQuizzenView quizzenView;
	private ViewFactory viewFactory;	
	private QuizScoreTableModel quizScoreTableModel;
	
	public OverzichtScoresQuizzenController(DBHandler dbHandler, ViewFactory viewFactory) {
		
		this.viewFactory = viewFactory;
		this.quizzenView = (IOverzichtScoresQuizzenView)viewFactory.maakView(ViewType.OverzichtScoresQuizzen);
		quizScoreTableModel = new QuizScoreTableModel(dbHandler.getQuizCatalogus().getReedsIngevuldeQuizzen());	

	}
	
	public IView getView() {
		return quizzenView;
	}
	
	protected void run() {
		quizzenView.setTableModel(quizScoreTableModel, 3, 1, 1, 1);		
		quizzenView.addDetailKnopListener(new DetailKnopListener());				
		quizzenView.setVisible(true);	
	}
	
	class DetailKnopListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			Quiz geselecteerd = quizScoreTableModel.getQuiz(quizzenView.getGeselecteerdeRijIndex());
			if (geselecteerd == null) {
				quizzenView.toonInformationDialog("Selecteer een quiz om details te zien", "Geen quiz geselecteerd");
			}
			else {				
				OverzichtScoresLeerlingenController overzichtScoresLeerlingController = new OverzichtScoresLeerlingenController(geselecteerd, OverzichtScoresQuizzenController.this, viewFactory);	
				overzichtScoresLeerlingController.run();
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
