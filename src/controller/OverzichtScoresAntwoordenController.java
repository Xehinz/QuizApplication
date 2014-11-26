package controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import view.OverzichtScoresAntwoordenView;
import model.OpdrachtAntwoord;
import model.QuizDeelname;

public class OverzichtScoresAntwoordenController {

	private AntwoordTableModel antwoordTableModel;
	
	public OverzichtScoresAntwoordenController(QuizDeelname quizDeelname) {		
		
		OverzichtScoresAntwoordenView antwoordenView = new OverzichtScoresAntwoordenView();
		antwoordTableModel = new AntwoordTableModel(quizDeelname);
		antwoordenView.setAntwoordTableModel(antwoordTableModel);
		antwoordenView.setTitel(quizDeelname.getLeerling().getNaam(), quizDeelname.getQuiz().getOnderwerp());
		
		antwoordenView.addAntwoordenSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				OpdrachtAntwoord geselecteerd = antwoordTableModel.getOpdrachtAntwoord(antwoordenView.getGeselecteerdeRij());
				antwoordenView.setJuisteAntwoord(geselecteerd.getOpdracht().getJuisteAntwoord());
				antwoordenView.setGemiddeldeScore(geselecteerd.getQuizOpdracht().getGemiddeldeScore(), geselecteerd.getQuizOpdracht().getMaxScore());
			}
			
		});
		
		antwoordenView.setVisible(true);
	}	
	
	@SuppressWarnings("serial")
	class AntwoordTableModel extends AbstractTableModel {

		private QuizDeelname quizDeelname;
		private String[] headers = new String[] {"Vraag", "Gegeven antwoord", "Behaalde score"};
		
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
				return opdrachtAntwoord.getBehaaldeScore() + "/" + opdrachtAntwoord.getQuizOpdracht().getMaxScore();
			default: 
				return "";
			}
		}		
	}

}
