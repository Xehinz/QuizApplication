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
		this.quizScoresRapportView.setTableModel(new QuizDeelnameScoresTableModel(null, dbHandler.getQuizCatalogus().getQuizzen()));
		this.quizScoresRapportView.setVisible(true);
		
	}
	
}

@SuppressWarnings("serial")
	class QuizDeelnameScoresTableModel extends AbstractTableModel {

	private String[] headers;
	private ArrayList<QuizDeelname> deelnamen;
	private ArrayList<Quiz> quizzen;
	
	public QuizDeelnameScoresTableModel(ArrayList<QuizDeelname> deelnamen, ArrayList<Quiz> quizzen) {
		super();
		headers = new String[] {"Leerling", "Aantal deelnames"};
		this.deelnamen = deelnamen;
		this.quizzen = quizzen;
		
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
