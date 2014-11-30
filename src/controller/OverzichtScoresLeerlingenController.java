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
import view.viewInterfaces.IOverzichtScoresLeerlingenView;
import view.viewInterfaces.IOverzichtScoresViewFactory;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class OverzichtScoresLeerlingenController {

	private LeerlingScoreTableModel leerlingScoreTableModel;
	private IOverzichtScoresLeerlingenView leerlingenView;
	private IOverzichtScoresViewFactory overzichtScoresViewFactory;
	private OverzichtScoresQuizzenController startScoreController;

	public OverzichtScoresLeerlingenController(Quiz quiz, OverzichtScoresQuizzenController startScoreController, IOverzichtScoresViewFactory overzichtScoresViewFactory) {		
		
		this.overzichtScoresViewFactory = overzichtScoresViewFactory;
		this.leerlingenView = (IOverzichtScoresLeerlingenView)overzichtScoresViewFactory.maakOverzichtScoresView("leerling");
		this.startScoreController = startScoreController;
		
		leerlingScoreTableModel = new LeerlingScoreTableModel(quiz.getQuizDeelnames());
		
		leerlingenView.setTableModel(leerlingScoreTableModel);
		leerlingenView.setQuizOnderwerp(quiz.getOnderwerp());		
		leerlingenView.addDetailKnopListener(new DetailKnopListener());			
		leerlingenView.setVisible(true);
		
		// Als het hoofd-score scherm sluit, moeten alle subvenstertjes ook sluiten
		startScoreController.addStartScoreViewClosedListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				leerlingenView.dispose();
			}
		});
	}
	
	class DetailKnopListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			QuizDeelname geselecteerd = leerlingScoreTableModel.getQuizDeelname(leerlingenView.getGeselecteerdeRijIndex());
			if (geselecteerd == null) {
				leerlingenView.toonInformationDialog("Selecteer een deelname om details te zien", "Geen deelname geselecteerd");
			} else {
				@SuppressWarnings("unused")
				OverzichtScoresAntwoordenController overzichtScoresAntwoordController = new OverzichtScoresAntwoordenController(geselecteerd, startScoreController, overzichtScoresViewFactory);
			}
		}
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
