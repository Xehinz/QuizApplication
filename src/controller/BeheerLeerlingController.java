package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import model.Leerling;
import model.Leraar;
import persistency.DBHandler;
import view.BeheerLeerlingView;

/**
 * 
 * @author Johan Boogers
 *
 */

public class BeheerLeerlingController {

	private DBHandler aDBHandler;
	private BeheerLeerlingView aView;
	private Leerling aLeerling;
	private Leraar aLeraar;
	private BeheerLeerlingTableModel aTabelModel = new BeheerLeerlingTableModel();
	
	/**
	 * Default constructor met parameters
	 * @param aHandler
	 * @param aLeraar
	 * @param aView
	 */
	public BeheerLeerlingController(DBHandler aHandler, Leraar aLeraar, BeheerLeerlingView aView) {
		this.setDBHandler(aHandler);
		this.setBeheerLeerlingView(aView);
		this.setLeerling(aLeerling);
		this.setLeraar(aLeraar);
		
		AddListeners(aView);

		aTabelModel.setLeerlingen(aDBHandler.getLeerlingContainer().getLeerlingen());

	}
		
	/**
	 * Toevoegen van action listeners en loaden van tabel
	 */
	private void AddListeners(BeheerLeerlingView aView){
		aView.setTableModel(aTabelModel);
		// Windows
		aView.addFrameWindowListener(new FrameWindowListener());
		// Set Knoppen
		aView.addNieuweLeerlingListener(new NieuweLeerlingListener());
		aView.addAanpassenLeerlingListener(new AanpassenLeerlingListener());
		aView.addVerwijderLeerlingListener(new VerwijderLeerlingListener());
		
		//aView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aView.setVisible(true);		
	}
	
	private void openLeerlingAanpassing(Leerling aLeerling, Leraar aLeraar) {
		new LeerlingAanpassingController(aLeerling, aLeraar, getDBHandler());
	}

	/**
	 * listener subclasses 
	 */
	class FrameWindowListener implements WindowFocusListener {

		@Override
		public void windowGainedFocus(WindowEvent arg0) {
			aTabelModel.fireTableDataChanged();
		}

		@Override
		public void windowLostFocus(WindowEvent arg0) {
		}

	}
	
	class NieuweLeerlingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			aLeerling = new Leerling();
			openLeerlingAanpassing(aLeerling, getLeraar());
		}
	}

	class AanpassenLeerlingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			aLeerling =  aTabelModel.getLeerling(aView.getGeselecteerdeRij());
			if (aLeerling == null) {
				aView.toonInformationDialog(
						"Selecteer een leerling om aan te passen", "Fout");
				return;
			}
			openLeerlingAanpassing(aLeerling, getLeraar());
		}
	}

	class VerwijderLeerlingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			aLeerling = aTabelModel.getLeerling(aView.getGeselecteerdeRij());
			if (aLeerling == null) {
				aView.toonInformationDialog(
						"Selecteer een leerling om te verwijderen", "Fout");
				return;
			}
			if (!aLeerling.isVerwijderbaar()) {
				aView.toonInformationDialog(
						"Kan deze leerling niet verwijderen", "Fout");
				return;
			}			
			int bevestig = JOptionPane.showConfirmDialog(null,"Weet u zeker dat u deze leerling wil verwijderen","Verwijder",2);
			if(bevestig == JOptionPane.YES_OPTION) {
				getDBHandler().getLeerlingContainer().removeLeerling(aLeerling);
				aTabelModel.fireTableDataChanged();
			}
		}
	}
	
	/**
	 * getters & setters
	 * 
	 */
	public DBHandler getDBHandler() {
		return aDBHandler;
	}
	private void setDBHandler(DBHandler aDBHandler) {
		this.aDBHandler = aDBHandler;
	}

	private BeheerLeerlingView getBeheerLeerlingView(){
		return this.aView;
	}
	private void setBeheerLeerlingView(BeheerLeerlingView aView) {
		this.aView = aView;
	}
	
	public Leerling getLeerling() {
		return this.aLeerling;
	}
	private void setLeerling(Leerling aLeerling) {
		this.aLeerling = aLeerling;
	}
	
	public Leraar getLeraar() {
		return this.aLeraar;
	}
	private void setLeraar(Leraar aLeraar) {
		this.aLeraar = aLeraar;
	}
	
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
	
}
