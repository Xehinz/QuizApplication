package util.tableModels;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import model.KlassiekeOpdracht;
import model.Meerkeuze;
import model.Opdracht;
import model.Opsomming;
import model.Reproductie;

@SuppressWarnings("serial")
public class QuizAanpassingTableModel extends AbstractTableModel {

	private ArrayList<Opdracht> opdrachten;
	private String[] headers;

	public QuizAanpassingTableModel() {
		headers = new String[] { "Cat.", "Type", "Vraag" };
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
		case 0: {
			String cat = new String(opdracht.getOpdrachtCategorie().toString());
			cat = cat.toUpperCase();
			return cat.substring(0, 3);
		}
		case 1: {
			String type = new String();
			if (opdracht instanceof Meerkeuze) {
				type = "MK";
			}
			if (opdracht instanceof Opsomming) {
				type = "OP";
			}
			if (opdracht instanceof Reproductie) {
				type = "RE";
			} 
			if (opdracht instanceof KlassiekeOpdracht) {
				type = "KL";
			}
			return type;
		}
		case 2:
			return opdracht.getVraag();
		default:
			return null;
		}
	}

	public void setOpdrachten(Collection<Opdracht> opdrachten) {
		this.opdrachten = new ArrayList<Opdracht>(opdrachten);
	}

	public Opdracht getOpdracht(int row) {
		if (row < opdrachten.size() && row >= 0) {
			return opdrachten.get(row);
		}
		return null;
	}

}
