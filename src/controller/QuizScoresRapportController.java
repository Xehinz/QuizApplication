package controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.Leerling;
import model.Quiz;
import model.QuizDeelname;
import persistency.DBHandler;
import view.QuizScoresRapportView;
import view.viewInterfaces.IQuizScoresRapportView;

public class QuizScoresRapportController {
	
	private DBHandler dBHandler;
	private Leerling leerling; 

	private IQuizScoresRapportView View;
	
	public QuizScoresRapportController(DBHandler dbHandler, Leerling leerling, IQuizScoresRapportView view ) {
		
		this.dBHandler = dbHandler;
		this.leerling = leerling;
		View = view;
		View.setLeerling(leerling);
		View.setTableModel(new QuizDeelnameTableModel(leerling.getQuizDeelnames()));
		View.setTableModel1(new QuizDeelnameQuizScoresTableModel(leerling.getDeelgenomenQuizzen()));
		View.setVisible(true);
	}
	
}

@SuppressWarnings("serial")
	class QuizDeelnameTableModel extends AbstractTableModel {
	
	private ArrayList<QuizDeelname> deelnamenVanLeerling;
	private String[] headersDeelnames;
	
	public QuizDeelnameTableModel(ArrayList<QuizDeelname> deelnames) {
		super();
		
		headersDeelnames = new String[] {"Quiz", "Leerling", "Datum deelname", "Tijdstip Deelname"};		
		deelnamenVanLeerling = new ArrayList<QuizDeelname>();
	}
	
	@Override   
	public String getColumnName(int col) {
	        return headersDeelnames[col];
	    }
	
	@Override
	public int getColumnCount() {
		return 4;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
//Afwerken!
@SuppressWarnings("serial")
class QuizDeelnameQuizScoresTableModel extends AbstractTableModel {
	
private ArrayList<Quiz> quizzenVanLeerling;
private String[] headersQuizScores;

public QuizDeelnameQuizScoresTableModel(ArrayList<Quiz> quizzen) {
	super();

	headersQuizScores = new String[] {"Opdracht", "Behaalde Score"};
	quizzenVanLeerling = quizzen;
}

@Override
public String getColumnName(int col) {
        return headersQuizScores[col];
    }

@Override
public int getColumnCount() {
	return 2;
}

@Override
public int getRowCount() {
	return quizzenVanLeerling.size();
}

@Override
public Class<?> getColumnClass(int columnIndex) {
	if (quizzenVanLeerling.isEmpty()) {
        return Object.class;
    }
    return getValueAt(0, columnIndex).getClass();
}

@Override
public Object getValueAt(int rowIndex, int columnIndex) {
	// TODO Auto-generated method stub
	return null;
}



}
