package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import model.Leerling;
import model.Leraar;
import persistency.DBHandler;
import persistency.StorageStrategy;
import view.LoginView;

public class OpstartController {

	private LoginView loginView;
	private DBHandler dbHandler;

	private Leraar leraar;
	private Leerling leerling;

	public OpstartController(DBHandler dbHandler, LoginView loginView) {
		this.loginView = loginView;
		this.dbHandler = dbHandler;

		try {
			dbHandler.vulCatalogi();
		} catch (IOException Iex) {
			loginView.toonErrorMessage(
					"Fout bij het inlezen van data:\n" + Iex.getMessage(),
					"Fout");
		}

		loginView.addLoginActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (valideerLeerlingOfLeraar(loginView.getVolledigeNaam())) {
					if (leraar == null) {
						// MainLeerlingController mainLeerlingController = new
						// MainLeerlingController(dbHandler, leerling.clone());
					} else {
						// MainLeraarController mainLeraarController = new
						// MainLeraarController(dbHandler, leraar);
					}
					loginView.dispose();
				} else {
					loginView.toonInformationMessage("Ongeldige login",
							"Login mislukt");
				}
				leraar = null;
				leerling = null;
			}
		});

		loginView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginView.setVisible(true);
	}

	public static void main(String[] args) {
		LoginView loginView = new LoginView();

		Properties settings = new Properties();
		try {
			settings.load(new FileInputStream("resources/settings.ini"));
		} catch (FileNotFoundException fEx) {
			loginView.toonErrorMessage(
					"setting.ini niet gevonden:\n" + fEx.getMessage(), "Fout");
		} catch (IOException iEx) {
			loginView.toonErrorMessage(
					"Inladen van instellingen uit settings.ini mislukt:\n"
							+ iEx.getMessage(), "Fout");
		}

		DBHandler dbHandler = new DBHandler();
		dbHandler.setDBStrategy(StorageStrategy.valueOf(settings
				.getProperty("dbstrategy")));

		OpstartController opstartController = new OpstartController(dbHandler,
				loginView);
	}

	private boolean valideerLeerlingOfLeraar(String volledigeNaam) {
		try {
			leraar = Leraar.getLeraar(volledigeNaam);
			return true;
		} catch (IllegalArgumentException ex) {

		}

		try {
			leerling = dbHandler.getLeerlingContainer().getLeerling(
					volledigeNaam);
			return true;
		} catch (IllegalArgumentException ex) {

		}

		return false;
	}
}
