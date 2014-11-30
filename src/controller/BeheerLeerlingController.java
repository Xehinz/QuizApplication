package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.QuizBeheerController.AanpassenQuizKnopListener;
import controller.QuizBeheerController.NieuweQuizKnopListener;
import controller.QuizBeheerController.VerwijderQuizKnopListener;
import model.Leerling;
import model.Leraar;
import model.Quiz;
import persistency.DBHandler;
import view.BeheerLeerlingView;
import view.QuizBeheerView;

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
	}
	
	/**
	 * Toevoegen van action listeners en loaden van tabel
	 */
	private void AddListeners(BeheerLeerlingView aView){
		// Set Tabel
		aView.setLeerlingen(getDBHandler().getLeerlingContainer().getLeerlingen());
		// Set Knoppen
		aView.addNieuweLeerlingListener(new NieuweLeerlingListener());
		aView.addAanpassenLeerlingListener(new AanpassenLeerlingListener());
		aView.addVerwijderLeerlingListener(new VerwijderLeerlingListener());

		aView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aView.setVisible(true);		
	}
	
	private void openLeerlingAanpassing(Leerling aLeerling, Leraar aLeraar) {
		LeerlingAanpassingController aController = new LeerlingAanpassingController(aLeerling,
				aLeraar, getDBHandler());
	}

	/**
	 * listener subclasses 
	 */
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
			aLeerling = aView.getGeselecteerdeLeerling();
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
			aLeerling = aView.getGeselecteerdeLeerling();
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
}
