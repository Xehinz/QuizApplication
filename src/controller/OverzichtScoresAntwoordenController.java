package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.IOverzichtScoresAntwoordenView;
import model.OpdrachtAntwoord;
import model.QuizDeelname;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class OverzichtScoresAntwoordenController {

	private AntwoordTableModel antwoordTableModel;
	private IOverzichtScoresAntwoordenView antwoordenView;
	private QuizDeelname quizDeelname;
	private OverzichtScoresQuizzenController startScoreController;
	
	public OverzichtScoresAntwoordenController(QuizDeelname quizDeelname, OverzichtScoresQuizzenController startScoreController, ViewFactory viewFactory) {		
		
		this.antwoordenView = (IOverzichtScoresAntwoordenView)viewFactory.maakView(ViewType.OverzichtScoresAntwoorden);
		antwoordTableModel = new AntwoordTableModel(quizDeelname);
		this.quizDeelname = quizDeelname;
		this.startScoreController = startScoreController;
		
	}	
	
	protected void run() {
		antwoordenView.setAntwoordTableModel(antwoordTableModel, 3, 3, 1, 1, 1);
		antwoordenView.setTitel(quizDeelname.getLeerling().getNaam(), quizDeelname.getQuiz().getOnderwerp());		
		antwoordenView.addAntwoordenSelectionListener(new AntwoordSelectionListener());		
		antwoordenView.setVisible(true);
		
		// Als het hoofd-score scherm sluit, moeten alle subvenstertjes ook sluiten
		startScoreController.getView().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				antwoordenView.dispose();
			}
		});
	}
	
	class AntwoordSelectionListener implements ListSelectionListener {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			OpdrachtAntwoord geselecteerd = antwoordTableModel.getOpdrachtAntwoord(antwoordenView.getGeselecteerdeRij());
			antwoordenView.setJuisteAntwoord(geselecteerd.getOpdracht().getJuisteAntwoord());
			antwoordenView.setGemiddeldeScore(geselecteerd.getQuizOpdracht().getGemiddeldeScore(), geselecteerd.getQuizOpdracht().getMaxScore());
		}
		
	}
	
	@SuppressWarnings("serial")
	class AntwoordTableModel extends AbstractTableModel {

		private QuizDeelname quizDeelname;
		private String[] headers = new String[] {"Vraag", "Gegeven antwoord", "Antwoordtijd (s)", "Aantal pogingen", "Behaalde score"};
		
		public AntwoordTableModel(QuizDeelname quizDeelname) {
			this.quizDeelname = quizDeelname;			
		}
		
		public OpdrachtAntwoord getOpdrachtAntwoord(int row) {
			if (row > -1 && row < quizDeelname.getOpdrachtAntwoorden().size()) {
			return quizDeelname.getOpdrachtAntwoorden().get(row);
			}
			return null;
		}
		
		@Override
		public String getColumnName(int col) {
			return headers[col];
		}
		
		@Override
		public int getColumnCount() {
			return headers.length;
		}

		@Override
		public int getRowCount() {
			return quizDeelname.getOpdrachtAntwoorden().size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			OpdrachtAntwoord opdrachtAntwoord = quizDeelname.getOpdrachtAntwoorden().get(rowIndex);
			switch(columnIndex) {
			case 0:
				return opdrachtAntwoord.getOpdracht().getVraag();
			case 1:
				return opdrachtAntwoord.getLaatsteAntwoord();
			case 2:
				return opdrachtAntwoord.getAntwoordTijd();
			case 3:
				return opdrachtAntwoord.getAantalPogingen();
			case 4:
				return String.format("%.2f/%d", opdrachtAntwoord.getBehaaldeScore(), opdrachtAntwoord.getQuizOpdracht().getMaxScore());
			default: 
				return "";
			}
		}		
	}

}
