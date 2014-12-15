package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Leerling;
import model.Leraar;

import persistency.DBHandler;
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.ILeerlingAanpassingView;

public class LeerlingAanpassingController {

	private ILeerlingAanpassingView aView;
	private DBHandler aDBHandler;
	private Leerling aLeerling;

	public LeerlingAanpassingController(Leerling aLeerling, Leraar aLeraar,
			DBHandler dbHandler, ViewFactory viewFactory) {
		this.aLeerling = aLeerling;
		this.aDBHandler = dbHandler;
		this.aView = (ILeerlingAanpassingView) viewFactory
				.maakView(ViewType.LeerlingAanpassingView);

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
			try {
				setLeerling(aView.getVoornaam(), aView.getFamilienaam(),
						aView.getLeerjaar());
				if (Integer.parseInt(aView.getID()) == 0) {
					aDBHandler.getLeerlingContainer()
							.addLeerling(getLeerling());
					aView.dispose();
				}
			} catch (Exception ex) {
				aView.toonInformationMessage(ex.getMessage(), "Fout");
			}

			aView.setID(aLeerling.getID());
		}
	}

}
