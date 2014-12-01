package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Leraar;
import persistency.DBHandler;
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
public class MainLeraarController {
	
	private boolean overzichtScoresStaatOpen, opdrachtBeheerStaatOpen, quizBeheerStaatOpen, leerlingBeheerStaatOpen;
	private DBHandler dbHandler;
	
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

	public MainLeraarController(DBHandler dbHandler, Leraar leraar, OpstartController opstartController, ViewFactory viewFactory) {
		mainView = (IMainLeraarView)viewFactory.maakView(ViewType.MainLeraarView);
		overzichtScoresStaatOpen = false;
		opdrachtBeheerStaatOpen = false;
		quizBeheerStaatOpen = false;
		leerlingBeheerStaatOpen = false;
		this.dbHandler = dbHandler;
		this.opstartController = opstartController;
		this.leraar = leraar;
		this.viewFactory = viewFactory;

		mainView.setLeraar(leraar.toString());
		mainView.addOverzichtScoresKnopActionListener(new OverzichtScoresKnopListener());	
		mainView.addWindowListener(new MainWindowClosingListener());
		mainView.addAfsluitenKnopActionListener(new AfsluitenKnopListener());
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
	
	class AfsluitenKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (JOptionPane.showConfirmDialog(null, "Weet je zeker dat je het programma wil afsluiten?", "Programma Afsluiten?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
				dbHandler.saveCatalogi();
				} catch (IOException iEx) {
					JOptionPane.showMessageDialog(null, "Fout bij het wegschrijven van data:\n" + iEx.getMessage());
				} finally {
				System.exit(0);
				}
			}
		}
		
	}
	
	class MainWindowClosingListener extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent event) {
			if (JOptionPane.showConfirmDialog(null, "Weet je zeker dat je het programma wil afsluiten?", "Programma Afsluiten?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
				dbHandler.saveCatalogi();
				} catch (IOException iEx) {
					JOptionPane.showMessageDialog(null, "Fout bij het wegschrijven van data:\n" + iEx.getMessage());
				} finally {
				System.exit(0);
				}
			}
		}
	}
	
	class OverzichtScoresKnopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!overzichtScoresStaatOpen) {
				overzichtScoresStaatOpen = true;
				overzichtScoresController = new OverzichtScoresQuizzenController(
						dbHandler, viewFactory);
				
				overzichtScoresController.getView().addWindowListener(new WindowAdapter() {
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
				opdrachtBeheerController = new OpdrachtBeheerController(dbHandler, leraar, opdrachtBeheerView);
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
				quizBeheerController = new QuizBeheerController(dbHandler, leraar);
				quizBeheerController.getView().addWindowListener(new WindowAdapter() {
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
				beheerLeerlingController = new BeheerLeerlingController(dbHandler, leraar, beheerLeerlingView);
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

}
