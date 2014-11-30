package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	
	private DBHandler dbHandler;
	private LoginView loginView;

	private Leraar leraar;
	private Leerling leerling;

	public OpstartController(DBHandler dbHandler) {		
		this.dbHandler = dbHandler;
		loginView = new LoginView();
		
		loginView.addWindowListener(new LoginViewClosedHandler());

		try {
			dbHandler.vulCatalogi();
		} catch (IOException Iex) {
			loginView.toonErrorMessage(
					"Fout bij het inlezen van data:\n" + Iex.getMessage(),
					"Fout");
		}
		
		login();
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
		dbHandler.setUseCSV(true);

		@SuppressWarnings("unused")
		OpstartController opstartController = new OpstartController(dbHandler);
	}
	
	public void login() {
		loginView = new LoginView();
		loginView.addWindowListener(new LoginViewClosedHandler());
		loginView.addLoginActionListener(new LoginKnopListener());

		loginView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginView.setVisible(true);
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
	
	class LoginKnopListener implements ActionListener {
		
		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent event) {
			if (valideerLeerlingOfLeraar(loginView.getVolledigeNaam())) {
				if (leraar == null) {
					 MainLeerlingController mainLeerlingController = new
					 MainLeerlingController(dbHandler, leerling, OpstartController.this);
				} else {
					 MainLeraarController mainLeraarController = new
					 MainLeraarController(dbHandler, leraar, OpstartController.this);
				}
				loginView.dispose();
			} else {
				loginView.toonInformationMessage("Ongeldige login",
						"Login mislukt");
			}
			leraar = null;
			leerling = null;
		}
	}
	
	class LoginViewClosedHandler extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent event) {
			try {
			dbHandler.saveCatalogi();
			} catch (IOException iEx) {
				loginView.toonErrorMessage(String.format("Fout bij het opslaan van de data:\n%s", iEx.getMessage()), "Fout bij Opslaan");
			}
		}
	}
	
}
