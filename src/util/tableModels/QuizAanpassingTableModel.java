package util.tableModels;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import model.Opdracht;


@SuppressWarnings("serial")
public class QuizAanpassingTableModel extends AbstractTableModel {
	
	
	private ArrayList<Opdracht> opdrachten;
	private String[] headers;
	
	
	public QuizAanpassingTableModel () {
		headers = new String[]{""};
		opdrachten = new ArrayList<Opdracht>();
	}
	
	
	@Override
	public int getColumnCount() {
		return headers.length;
	}
	@Override
	public int getRowCount() {
		return opdrachten.size();
	}
	
	@Override   
	public String getColumnName(int col) {
	        return headers[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Opdracht opdracht = opdrachten.get(row);
	     switch (col) {
	     case 0: return opdracht.getAuteur();
	     
	     default: return null;
	    }
	}

	public void setOpdrachten (Collection<Opdracht> opdrachten) {
		this.opdrachten = new ArrayList<Opdracht>(opdrachten);
	}
	
	public Opdracht getOpdracht(int row) {
		if (row < opdrachten.size() && row >= 0) {
			return opdrachten.get(row);
		}
		return null;
	}
	
	
}
