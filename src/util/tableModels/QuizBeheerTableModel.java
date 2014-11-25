package util.tableModels;

import java.util.ArrayList;

import model.Quiz;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class QuizBeheerTableModel extends AbstractTableModel {

	private ArrayList<Quiz> quizzen;
	private String[] columns;
	
	
	public QuizBeheerTableModel (ArrayList<Quiz> quizzen) {
		this.quizzen = quizzen;
		columns = new String[]{"Auteur","Onderwerp","Klas","Vragen","Status","Test","Unieke Deelname"};
	}
	
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}
	@Override
	public int getRowCount() {
		return quizzen.size();
	}
	@Override
	public Object getValueAt(int row, int col) {
		Quiz quiz = quizzen.get(row);
	     switch (col) {
	     case 0: return quiz.getAuteur();
	     case 1: return quiz.getOnderwerp();
	     case 2: return quiz.getDoelLeerjaren();
	     case 3: return (quiz.getQuizOpdrachten()).size();
	     case 4: return quiz.getQuizStatus();
	     case 5: return quiz.getIsTest();
	     case 6: return quiz.getIsUniekeDeelname();
	     
	     default: return null;
	    }
	}

	
	
	
	
}
