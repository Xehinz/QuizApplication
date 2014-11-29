package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import model.Quiz;
import model.QuizDeelname;
import view.OverzichtScoresLeerlingenView;

public class OverzichtScoresLeerlingenController {

	private LeerlingScoreTableModel leerlingScoreTableModel;
	private OverzichtScoresLeerlingenView leerlingenView;
	private Quiz quiz;
	private OverzichtScoresQuizzenController startScoreController;

	public OverzichtScoresLeerlingenController(Quiz quiz, OverzichtScoresQuizzenController startScoreController) {		
		leerlingenView = new OverzichtScoresLeerlingenView();
		leerlingScoreTableModel = new LeerlingScoreTableModel(quiz.getQuizDeelnames());
		this.quiz = quiz;
		this.startScoreController = startScoreController;
		
		openView();
		addStartScoreClosedHandler();
	}
	
	private void openView() {
		leerlingenView.setTableModel(leerlingScoreTableModel);
		leerlingenView.setQuizOnderwerp(quiz.getOnderwerp());	
		
		leerlingenView.addDetailKnopListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				QuizDeelname geselecteerd = leerlingScoreTableModel.getQuizDeelname(leerlingenView.getGeselecteerdeRijIndex());
				if (geselecteerd == null) {
					leerlingenView.toonInformationDialog("Selecteer een deelname om details te zien", "Geen deelname geselecteerd");
				} else {
					@SuppressWarnings("unused")
					OverzichtScoresAntwoordenController overzichtScoresAntwoordController = new OverzichtScoresAntwoordenController(geselecteerd, startScoreController);
				}
			}
		});		
		
		leerlingenView.setVisible(true);
	}
	
	private void addStartScoreClosedHandler() {
		startScoreController.addStartScoreViewClosedListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				leerlingenView.dispose();
			}
		});
	}

	@SuppressWarnings("serial")
	class LeerlingScoreTableModel extends AbstractTableModel {

		private String[] headers = new String[]{"Naam", "Leerjaar", "Datum deelname", "Behaalde score"};
		ArrayList<QuizDeelname> quizDeelnames = new ArrayList<QuizDeelname>();

		public LeerlingScoreTableModel(Collection<QuizDeelname> quizDeelnames) {
			this.quizDeelnames = new ArrayList<QuizDeelname>(quizDeelnames);
		}
		
		public QuizDeelname getQuizDeelname(int row) {
			return quizDeelnames.get(row);
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
			return quizDeelnames.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (quizDeelnames.isEmpty()) {
				return Object.class;
			}
			return getValueAt(0, columnIndex).getClass();
		}

		@Override
		public Object getValueAt(int row, int col) {
			QuizDeelname quizDeelname = quizDeelnames.get(row);
			switch(col) {
			case 0:
				return quizDeelname.getLeerling().getNaam();
			case 1:
				return quizDeelname.getLeerling().getLeerjaar();
			case 2:
				return quizDeelname.getDatum();
			case 3:
				return quizDeelname.getBehaaldeScore();
			}
			return "";
		}

	}

}
