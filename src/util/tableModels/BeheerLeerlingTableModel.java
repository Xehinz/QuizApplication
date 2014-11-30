package util.tableModels;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import model.Leerling;

@SuppressWarnings("serial")
public class BeheerLeerlingTableModel extends AbstractTableModel  {

	private ArrayList<Leerling> leerlingen;
	private String[] headers;

	/**
	 * Default constructor, instellen van de tabel-headers
	 */
	public BeheerLeerlingTableModel () {
		headers = new String[]{"ID","Voornaam","Familienaam","Leerjaar"};
		leerlingen = new ArrayList<Leerling>();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return leerlingen.size();
	}

	@Override   
	public String getColumnName(int col) {
	        return headers[col];
	}

	/**
	 * Ophalen van een eigenschap van een leerling uit de collectie
	 */
	@Override
	public Object getValueAt(int row, int col) {
		Leerling aLeerling = leerlingen.get(row);
	     switch (col) {
		     case 0: return aLeerling.getID();
		     case 1: return aLeerling.getLeerlingVoornaam();
		     case 2: return aLeerling.getLeerlingFamilienaam();
		     case 3: return aLeerling.getLeerjaar();
		     default: return null;
	    }
	}

	/**
	 * Ophalen van 1 leerling uit de collectie
	 * @param selectedRow
	 * @return
	 */
	public Leerling getLeerling(int selectedRow) {
		if (selectedRow < leerlingen.size() && selectedRow >= 0) {
			return leerlingen.get(selectedRow);
		}
		return null;
	}

	/**
	 * Instellen van de lijst met leerlingen
	 * @param leerlingen
	 */
	public void setLeerlingen(Collection<Leerling> leerlingen) {
		this.leerlingen = new ArrayList<Leerling>(leerlingen);
	}

}
