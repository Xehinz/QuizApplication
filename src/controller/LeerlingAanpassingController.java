package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.OpdrachtBeheerController.NieuweKlassiekeKnopListener;
import controller.OpdrachtBeheerController.NieuweMeerkeuzeKnopListener;
import model.Leerling;
import model.Leraar;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Opsomming;
import model.Reproductie;
import persistency.DBHandler;
import view.LeerlingAanpassingView;
import view.OpdrachtMeerkeuzeBeheerView;
import view.OpdrachtOpsommingBeheerView;
import view.OpdrachtReproductieBeheerView;

public class LeerlingAanpassingController {

	private LeerlingAanpassingView aView;
	private DBHandler aDBHandler;
	private Leerling aLeerling;
	private Leraar aLeraar;

	
	public LeerlingAanpassingController(Leerling aLeerling, Leraar aLeraar,
			DBHandler dbHandler) {
		this.aLeraar = aLeraar;
		this.aLeerling = aLeerling;
		this.aDBHandler = dbHandler;
		this.aView = new LeerlingAanpassingView(aLeerling, aLeraar);
		
		aView.setID(aLeerling.getID());
		aView.setVoornaam(aLeerling.getLeerlingVoornaam());
		aView.setFamilienaam(aLeerling.getLeerlingFamilienaam());
		aView.setLeerjaar(aLeerling.getLeerjaar());
	
		aView.addLeerlingBewarenListener(new LeerlingBewarenListener());

		aView.setVisible(true);
	}
	
	public Leerling getLeerling() {
		return aLeerling;
	}

	public void setLeerling(String voornaam, String familienaam, int leerjaar) {
		aLeerling.setLeerlingVoornaam(voornaam);
		aLeerling.setLeerlingFamilienaam(familienaam);
		aLeerling.setLeerjaar(leerjaar);		
	}

	class LeerlingBewarenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			setLeerling(aView.getVoornaam(),aView.getFamilienaam(), aView.getLeerjaar());
			if (Integer.parseInt(aView.getID()) == 0) {aDBHandler.getLeerlingContainer().addLeerling(getLeerling());}
			else {aLeerling = aDBHandler.getLeerlingContainer().getLeerling(Integer.parseInt(aView.getID()));}
			
			aView.setID(aLeerling.getID());
		}
	}

}
