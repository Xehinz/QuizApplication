package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Leerling;
import model.Leraar;
import model.score.OpdrachtScoreRegelsFactory;
import model.score.ScoreStrategyType;
import persistency.DBHandler;
import persistency.StorageStrategy;
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.ILoginView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class OpstartController {

	private DBHandler dbHandler;
	private ILoginView loginView;

	private Leraar leraar;
	private Leerling leerling;

	private Properties settings;
	private ViewFactory viewFactory;

	public OpstartController() {
		loadSettings();

		OpdrachtScoreRegelsFactory.getEnigeInstantie().setScoreStrategyType(
				ScoreStrategyType.valueOf(settings.getProperty("scoreregel")));

		this.dbHandler = new DBHandler();
		dbHandler.setDBStrategy(StorageStrategy.valueOf(settings
				.getProperty("dbstrategy")));

		viewFactory = new ViewFactory(settings);
		loginView = (ILoginView) viewFactory.maakView(ViewType.LoginView);

		loginView.addWindowListener(new LoginViewClosedHandler());

		try {
			dbHandler.vulCatalogi();
		} catch (IOException Iex) {
			loginView.toonErrorMessage(
					"Fout bij het inlezen van data:\n" + Iex.getMessage(),
					"Fout");
		} catch (Exception ex) {
			loginView.toonErrorMessage("Fout:\n" + ex.getMessage(), "Fout");
		}

		login();
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		OpstartController opstartController = new OpstartController();
	}

	public void login() {
		loginView = (ILoginView) viewFactory.maakView(ViewType.LoginView);
		loginView.addWindowListener(new LoginViewClosedHandler());
		loginView.addLoginActionListener(new LoginKnopListener());

		loginView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginView.setVisible(true);
	}

	public Properties getSettings() {
		return settings;
	}

	protected void saveEnSluitAf() {
		if (JOptionPane.showConfirmDialog(null,
				"Weet je zeker dat je het programma wil afsluiten?",
				"Programma Afsluiten?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			try {
				dbHandler.saveCatalogi();
				settings.store(new FileOutputStream("resources/settings.ini"),
						null);
			} catch (FileNotFoundException fEx) {
				JOptionPane.showConfirmDialog(null,
						"setting.ini niet gevonden:\n" + fEx.getMessage(),
						"Fout", JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException iEx) {
				JOptionPane.showConfirmDialog(null,
						"Fout bij het wegschrijven van data of de settings:\n"
								+ iEx.getMessage(), "Fout",
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showConfirmDialog(null,
						"Fout:\n" + ex.getMessage(), "Fout",
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			} finally {
				System.exit(0);
			}
		}
	}

	private void loadSettings() {
		settings = new Properties();
		try {
			settings.load(new FileInputStream("resources/settings.ini"));
		} catch (FileNotFoundException fEx) {
			JOptionPane.showConfirmDialog(null, "setting.ini niet gevonden:\n"
					+ fEx.getMessage(), "Fout", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException iEx) {
			JOptionPane.showConfirmDialog(null,
					"Inladen van instellingen uit settings.ini mislukt:\n"
							+ iEx.getMessage(), "Fout", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
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
					MainLeerlingController mainLeerlingController = new MainLeerlingController(
							dbHandler, leerling, OpstartController.this,
							viewFactory);
				} else {
					MainLeraarController mainLeraarController = new MainLeraarController(
							dbHandler, leraar, OpstartController.this,
							viewFactory);
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
			saveEnSluitAf();
		}
		
	}

}
