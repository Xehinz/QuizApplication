package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Properties;

import javax.swing.ButtonModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import model.Leraar;
import model.score.ScoreStrategyType;
import persistency.DBHandler;
import persistency.StorageStrategy;
import view.DBConnectieGegevensView;
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
	private HashMap<String, String> lookAndFeelClassNames;

	private OverzichtScoresQuizzenController overzichtScoresController;
	private OpdrachtBeheerController opdrachtBeheerController;
	private OpstartController opstartController;
	private QuizBeheerController quizBeheerController;
	private BeheerLeerlingController beheerLeerlingController;

	private DBConnectieGegevensView connectieGegevensView;

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
		
		lookAndFeelClassNames = new HashMap<String, String>();
		for (LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
			lookAndFeelClassNames.put(lafInfo.getName(), lafInfo.getClassName());
		}

		this.addObserver(dbHandler);
	}
	
	protected void run() {
		mainView.setLeraar(leraar.toString());
		mainView.setRodeLoginSelected(settings.getProperty("login").equals(
				"LoginView2") ? true : false);
		mainView.setScoreBerekeningSelected(Enum.valueOf(
				ScoreStrategyType.class, settings.getProperty("scoreregel")));
		mainView.setOpslagStrategySelected(Enum.valueOf(StorageStrategy.class,
				settings.getProperty("dbstrategy")));
		mainView.setSelectedLookAndFeel(settings.getProperty("lookandfeel"));

		mainView.addRodeLoginClickedListener(new RodeLoginClickedHandler());
		mainView.addScoreStrategieChangedListener(new ScoreRegelChangedHandler());
		mainView.addOpslagStrategyChangedListener(new StorageStrategyChangedHandler());
		mainView.addConnectieGegevensKlikListener(new ConnectieGegevensKlikHandler());
		mainView.addLookAndFeelChangedListener(new LookAndFeelChangedListener());
		
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
			if (overzichtScoresStaatOpen) {
				overzichtScoresController.getView().dispose();
			}
			if (opdrachtBeheerStaatOpen) {
				opdrachtBeheerController.getView().dispose();
			}
			if (quizBeheerStaatOpen) {
				quizBeheerController.getView().dispose();
			}
			if (leerlingBeheerStaatOpen) {
				beheerLeerlingController.getView().dispose();
			}
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
				overzichtScoresController.run();

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
				opdrachtBeheerController = new OpdrachtBeheerController(
						dbHandler, leraar, viewFactory);
				opdrachtBeheerController.getView().addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						opdrachtBeheerStaatOpen = false;
					}
				});
			} else {
				opdrachtBeheerController.getView().toFront();
			}
		}

	}

	class QuizBeheerKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!quizBeheerStaatOpen) {
				quizBeheerStaatOpen = true;
				quizBeheerController = new QuizBeheerController(dbHandler,
						leraar, viewFactory);
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
				beheerLeerlingController = new BeheerLeerlingController(
						dbHandler, leraar, viewFactory);
				beheerLeerlingController.getView().addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						leerlingBeheerStaatOpen = false;
					}
				});
			} else {
				beheerLeerlingController.getView().toFront();
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
	
	class ConnectieGegevensOpslaanHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			settings.put("connectiestring", connectieGegevensView.getConnectieString());
			settings.put("gebruiker", connectieGegevensView.getGebruiker());
			settings.put("paswoord", new String(connectieGegevensView.getPaswoord()));
			connectieGegevensView.dispose();
		}
		
	}
	
	class ConnectieGegevensKlikHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			connectieGegevensView = new DBConnectieGegevensView((JFrame)mainView);
			connectieGegevensView.setConnectieString(settings.getProperty("connectiestring"));
			connectieGegevensView.setGebruiker(settings.getProperty("gebruiker"));
			connectieGegevensView.setPaswoord(settings.getProperty("paswoord"));
			connectieGegevensView.addOpslaanKlikListener(new ConnectieGegevensOpslaanHandler());
			connectieGegevensView.setVisible(true);
		}
		
	}
	
	class LookAndFeelChangedListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getSource() instanceof JRadioButtonMenuItem) {
				JRadioButtonMenuItem source = (JRadioButtonMenuItem)event.getSource();
				String simpleClassName = source.getText();
				
				try {
				UIManager.setLookAndFeel(lookAndFeelClassNames.get(simpleClassName));
				} catch (Exception ex) {
					JOptionPane.showConfirmDialog(null, "Fout bij het veranderen van look and feel", "Fout", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				SwingUtilities.updateComponentTreeUI((JFrame)mainView);
				
				settings.put("lookandfeel", lookAndFeelClassNames.get(simpleClassName));
			}
		}
		
	}

}
