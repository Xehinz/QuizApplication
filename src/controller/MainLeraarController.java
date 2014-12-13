package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Properties;

import javax.swing.ButtonModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;

import model.Leraar;
import model.score.ScoreStrategyType;
import persistency.DBHandler;
import persistency.StorageStrategy;
import view.BeheerLeerlingView;
import view.OpdrachtBeheerView;
import view.QuizBeheerView;
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.IMainLeraarView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class MainLeraarController extends Observable {

	private boolean overzichtScoresStaatOpen, opdrachtBeheerStaatOpen,
			quizBeheerStaatOpen, leerlingBeheerStaatOpen;
	private DBHandler dbHandler;
	private Properties settings;

	private OverzichtScoresQuizzenController overzichtScoresController;
	private OpdrachtBeheerController opdrachtBeheerController;
	private OpstartController opstartController;
	private QuizBeheerController quizBeheerController;
	private BeheerLeerlingController beheerLeerlingController;

	private QuizBeheerView quizBeheerView;
	private OpdrachtBeheerView opdrachtBeheerView;
	private BeheerLeerlingView beheerLeerlingView;

	private ViewFactory viewFactory;

	private IMainLeraarView mainView;
	private Leraar leraar;

	public MainLeraarController(DBHandler dbHandler, Leraar leraar,
			OpstartController opstartController, ViewFactory viewFactory) {
		mainView = (IMainLeraarView) viewFactory
				.maakView(ViewType.MainLeraarView);
		overzichtScoresStaatOpen = false;
		opdrachtBeheerStaatOpen = false;
		quizBeheerStaatOpen = false;
		leerlingBeheerStaatOpen = false;
		this.dbHandler = dbHandler;
		this.opstartController = opstartController;
		this.leraar = leraar;
		this.viewFactory = viewFactory;
		this.settings = opstartController.getSettings();

		this.addObserver(dbHandler);

		mainView.setLeraar(leraar.toString());
		mainView.setRodeLoginSelected(settings.getProperty("login").equals(
				"LoginView2") ? true : false);
		mainView.setScoreBerekeningSelected(Enum.valueOf(
				ScoreStrategyType.class, settings.getProperty("scoreregel")));
		mainView.setOpslagStrategySelected(Enum.valueOf(StorageStrategy.class,
				settings.getProperty("dbstrategy")));

		mainView.addRodeLoginClickedListener(new RodeLoginClickedHandler());
		mainView.addScoreStrategieChangedListener(new ScoreRegelChangedHandler());
		mainView.addOpslagStrategyChangedListener(new StorageStrategyChangedHandler());
		mainView.addOverzichtScoresKnopActionListener(new OverzichtScoresKnopListener());
		mainView.addWindowListener(new MainWindowClosingListener());
		mainView.addAfsluitenKnopActionListener(ae -> opstartController
				.saveEnSluitAf());
		mainView.addLogoutKnopActionListener(new LogoutKnopListener());
		mainView.addOpdrachtBeheerKnopActionListener(new OpdrachtBeheerKnopListener());
		mainView.addQuizBeheerKnopActionListener(new QuizBeheerKnopListener());
		mainView.addLeerlingBeheerKnopActionListener(new BeheerLeerlingenKnopListener());

		mainView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainView.setVisible(true);
		mainView.setLocationRelativeTo(null);
	}

	class LogoutKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			mainView.dispose();
			opstartController.login();
		}

	}

	class MainWindowClosingListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent event) {
			opstartController.saveEnSluitAf();
		}
	}

	class OverzichtScoresKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!overzichtScoresStaatOpen) {
				overzichtScoresStaatOpen = true;
				overzichtScoresController = new OverzichtScoresQuizzenController(
						dbHandler, viewFactory);

				overzichtScoresController.getView().addWindowListener(
						new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent event) {
								overzichtScoresStaatOpen = false;
							}
						});
			} else {
				overzichtScoresController.getView().toFront();
			}
		}

	}

	class OpdrachtBeheerKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!opdrachtBeheerStaatOpen) {
				opdrachtBeheerStaatOpen = true;
				opdrachtBeheerView = new OpdrachtBeheerView();
				opdrachtBeheerController = new OpdrachtBeheerController(
						dbHandler, leraar, opdrachtBeheerView);
				opdrachtBeheerView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						opdrachtBeheerStaatOpen = false;
					}
				});
			} else {
				opdrachtBeheerView.toFront();
			}
		}

	}

	class QuizBeheerKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!quizBeheerStaatOpen) {
				quizBeheerStaatOpen = true;
				quizBeheerController = new QuizBeheerController(dbHandler,
						leraar);
				quizBeheerController.getView().addWindowListener(
						new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent event) {
								quizBeheerStaatOpen = false;
							}
						});
			} else {
				quizBeheerController.getView().toFront();
			}
		}

	}

	class BeheerLeerlingenKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!leerlingBeheerStaatOpen) {
				leerlingBeheerStaatOpen = true;
				beheerLeerlingView = new BeheerLeerlingView();
				beheerLeerlingController = new BeheerLeerlingController(
						dbHandler, leraar, beheerLeerlingView);
				beheerLeerlingView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						leerlingBeheerStaatOpen = false;
					}
				});
			} else {
				beheerLeerlingView.toFront();
			}
		}
	}

	class RodeLoginClickedHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() instanceof JCheckBoxMenuItem) {
				JCheckBoxMenuItem rodeLoginCheckBox = (JCheckBoxMenuItem) event
						.getSource();
				if (rodeLoginCheckBox.isSelected()) {
					settings.put("login", "LoginView2");
				} else {
					settings.put("login", "LoginView");
				}
			}
		}

	}

	class ScoreRegelChangedHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getSource() instanceof JRadioButtonMenuItem) {
				JRadioButtonMenuItem scoreRadioButton = (JRadioButtonMenuItem) event
						.getSource();
				if (event.getStateChange() == ItemEvent.SELECTED) {
					settings.put("scoreregel", scoreRadioButton.getText()
							.toUpperCase());
				}
			}
		}

	}

	class StorageStrategyChangedHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			
			if (event.getSource() instanceof JRadioButtonMenuItem) {
				JRadioButtonMenuItem opslagRadioButton = (JRadioButtonMenuItem) event
						.getSource();
				ButtonModel opslagRadioButtonModel = opslagRadioButton
						.getModel();
				
				if (event.getStateChange() == ItemEvent.SELECTED) {	
					StorageStrategy naar = Enum.valueOf(StorageStrategy.class,
							opslagRadioButton.getText().toUpperCase());
					
					settings.put("dbstrategy", opslagRadioButton.getText()
							.toUpperCase());
					setChanged();
					notifyObservers(naar);
				}
				if (opslagRadioButton.getText().equals(
						StorageStrategy.DATABASE.toString())) {
					mainView.setEnabledDBConnectieGegevens(opslagRadioButtonModel
							.isSelected());
				}
			}
		}

	}

}
