package util.tableModels;

/**
 * 
 * @author Adriaan Kuipers
 * @version 26/11/2014
 * 
 */

import java.util.ArrayList;
import java.util.Collection;

import model.Quiz;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;


@SuppressWarnings("serial")
public class QuizBeheerTableModel extends AbstractTableModel {

	private ArrayList<Quiz> quizzen;
	private String[] headers;
	
	
	public QuizBeheerTableModel () {
		headers = new String[]{"Auteur","Onderwerp","Klas","Vragen","Status","Test","Unieke Deelname"};
		quizzen = new ArrayList<Quiz>();
	}
	
	
	@Override
	public int getColumnCount() {
		return headers.length;
	}
	@Override
	public int getRowCount() {
		return quizzen.size();
	}
	
	@Override   
	public String getColumnName(int col) {
	        return headers[col];
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

	public void setQuizzen (Collection<Quiz> quizzen) {
		this.quizzen = new ArrayList<Quiz>(quizzen);
	}
	
	public Quiz getQuiz(int row) {
		if (row < quizzen.size() && row >= 0) {
			return quizzen.get(row);
		}
		return null;
	}
		
}
