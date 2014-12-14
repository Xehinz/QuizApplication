package controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import model.Leerling;
import model.OpdrachtAntwoord;
import model.QuizDeelname;
import persistency.DBHandler;
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.IQuizScoresRapportView;
import view.viewInterfaces.IView;

public class QuizScoresRapportController {

	private IQuizScoresRapportView View;

	public QuizScoresRapportController(DBHandler dbHandler, Leerling leerling,
			ViewFactory viewFactory) {

		View = (IQuizScoresRapportView) viewFactory
				.maakView(ViewType.QuizScoresRapportView);
		View.setLeerling(leerling.getNaam());
		QuizDeelnameTableModel tableModel = new QuizDeelnameTableModel(
				leerling.getQuizDeelnames());
		View.setTableModel(tableModel);
		View.setVisible(true);
		View.addSelectionListener(new QuizDeelnameSelectionListener(View,
				leerling, tableModel));
	}

	public IView getView() {
		return View;
	}

	class QuizDeelnameSelectionListener implements ListSelectionListener {
		Leerling leerling;
		IQuizScoresRapportView View;
		QuizDeelnameTableModel model;

		public QuizDeelnameSelectionListener(IQuizScoresRapportView view,
				Leerling leerling, QuizDeelnameTableModel model) {
			super();
			this.leerling = leerling;
			this.View = view;
			this.model = model;
		}

		@Override
		public void valueChanged(ListSelectionEvent event) {
			View.setTableModel1(new QuizDeelnameQuizScoresTableModel(leerling
					.getQuizDeelname(
							model.getValueAt(View.getGeselecteerdeRij(), 3)
									.toString()).getOpdrachtAntwoorden()));
		}

	}

	@SuppressWarnings("serial")
	class QuizDeelnameTableModel extends AbstractTableModel {

		private ArrayList<QuizDeelname> deelnamenVanLeerling = new ArrayList<QuizDeelname>();
		private String[] headersDeelnames;

		public QuizDeelnameTableModel(Collection<QuizDeelname> deelnames) {
			super();

			this.deelnamenVanLeerling = new ArrayList<QuizDeelname>(deelnames);

			headersDeelnames = new String[] { "Quiz", "Datum deelname",
					"Behaalde Score" };
		}

		@Override
		public String getColumnName(int col) {
			return headersDeelnames[col];
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return deelnamenVanLeerling.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (deelnamenVanLeerling.isEmpty()) {
				return Object.class;
			}
			return getValueAt(0, columnIndex).getClass();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			QuizDeelname quizDeelnames = deelnamenVanLeerling.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return quizDeelnames.getQuiz().getOnderwerp();

			case 1:
				return quizDeelnames.getDatum();

			case 2:
				return quizDeelnames.getBehaaldeScore() + "/10";

			case 3:
				return quizDeelnames.getID();

			default:
				break;
			}
			return null;
		}

	}

	@SuppressWarnings("serial")
	class QuizDeelnameQuizScoresTableModel extends AbstractTableModel {
		private ArrayList<OpdrachtAntwoord> lijstOpdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		private String[] headersQuizScores;

		public QuizDeelnameQuizScoresTableModel(
				ArrayList<OpdrachtAntwoord> antwoorden) {
			super();

			headersQuizScores = new String[] { "Opdrachtvraag",
					"Juiste antwoord", "Laatste antwoord", "Behaalde Score" };
			lijstOpdrachtAntwoorden = antwoorden;
			fireTableDataChanged();
		}

		@Override
		public String getColumnName(int col) {
			return headersQuizScores[col];
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return lijstOpdrachtAntwoorden.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (lijstOpdrachtAntwoorden.isEmpty()) {
				return Object.class;
			}
			return getValueAt(0, columnIndex).getClass();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			OpdrachtAntwoord opdrachtAntwoord = lijstOpdrachtAntwoorden
					.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return opdrachtAntwoord.getOpdracht().getVraag();

			case 1:
				return opdrachtAntwoord.getOpdracht().getJuisteAntwoord();

			case 2:
				return opdrachtAntwoord.getLaatsteAntwoord();

			case 3:
				return opdrachtAntwoord.getBehaaldeScore() + "/"
						+ opdrachtAntwoord.getQuizOpdracht().getMaxScore();

			default:
				break;
			}
			return null;
		}

	}
}
