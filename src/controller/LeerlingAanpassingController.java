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
			DBHandler dbHandler, LeerlingAanpassingView aView) {
		this.aLeraar = aLeraar;
		this.aLeerling = aLeerling;
		this.aDBHandler = dbHandler;
		this.aView = aView;
		
		aView.addLeerlingBewarenListener(new LeerlingBewarenListener());
		aView.openQuizDeelnamesListener(new QuizDeelnamesListener());

		aView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			aDBHandler.getLeerlingContainer().addLeerling(aLeerling);
		}
	}

	class QuizDeelnamesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			/**
			 * verder uit te werken --> openen quiz deelnames deze leerling
			 */
		}
	}

}
