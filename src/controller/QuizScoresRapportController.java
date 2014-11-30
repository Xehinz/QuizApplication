package controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.table.TableModel;

import view.viewInterfaces.IOverzichtScoresLeerlingenView;
import view.viewInterfaces.IQuizScoresRapportView;

public class QuizScoresRapportController implements IQuizScoresRapportView,
		IOverzichtScoresLeerlingenView {

	@Override
	public void setVisible(boolean isVisible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocationRelativeTo(Component component) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefaultCloseOperation(int defaultCloseOperation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addWindowListener(WindowListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toFront() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setQuizOnderwerp(String onderwerp) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getGeselecteerdeRijIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGeselecteerdeRij() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTableModel(TableModel quizScoreTableModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toonInformationDialog(String boodschap, String titel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDetailKnopListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

}
