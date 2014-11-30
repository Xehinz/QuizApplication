package controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import controller.MainLeerlingController.DeelnemenKnopListener;
import controller.QuizDeelnameController.QuizTableModel;
import model.Leerling;
import model.Quiz;
import model.QuizDeelname;
import persistency.DBHandler;
import view.OverzichtScoresLeerlingenView;
import view.QuizScoresRapportView;
import view.viewInterfaces.IOverzichtScoresLeerlingenView;
import view.viewInterfaces.IQuizScoresRapportView;

public class QuizScoresRapportController {
	
	private DBHandler dBHandler;
	private Leerling leerling; 

	private IQuizScoresRapportView quizScoresRapportView;

	
	public QuizScoresRapportController(DBHandler dbHandler, Leerling leerling, IQuizScoresRapportView view ) {
		
		this.dBHandler = dbHandler;
		this.leerling = leerling;
		this.quizScoresRapportView = view;
		this.quizScoresRapportView.setLeerling(leerling);
		this.quizScoresRapportView.setTableModel(new QuizDeelnameScoresTableModel(leerling.getQuizDeelnames()));
		this.quizScoresRapportView.setVisible(true);
	}
	
}

@SuppressWarnings("serial")
	class QuizDeelnameScoresTableModel extends AbstractTableModel {
	private ArrayList<QuizDeelname> deelnamenVanLeerling;
	private String[] headers = new String[] {"Quiz", "Datum deelname"};
	
	
	public QuizDeelnameScoresTableModel(ArrayList<QuizDeelname> deelnames) {
		super();
	
		deelnamenVanLeerling = deelnames;
	}
	
	@Override   
	public String getColumnName(int col) {
	        return headers[col];
	    }
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return deelnamenVanLeerling.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
