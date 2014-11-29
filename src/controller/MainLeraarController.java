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
import view.MainLeraarView;
import view.OpdrachtBeheerView;
import view.OverzichtScoresQuizzenView;
import view.QuizBeheerView;

public class MainLeraarController {
	
	private boolean overzichtScoresStaatOpen, opdrachtBeheerStaatOpen, quizBeheerStaatOpen;
	private DBHandler dbHandler;
	
	private OverzichtScoresQuizzenController overzichtScoresController;
	private OpdrachtBeheerController opdrachtBeheerController;
	private OpstartController opstartController;
	private QuizBeheerController quizBeheerController;
	
	private QuizBeheerView quizBeheerView;
	private OpdrachtBeheerView opdrachtBeheerView;
	private OverzichtScoresQuizzenView overzichtScoresQuizzenView;
	
	private MainLeraarView mainView;	
	private Leraar leraar;

	public MainLeraarController(DBHandler dbHandler, Leraar leraar, OpstartController opstartController) {
		mainView = new MainLeraarView();
		overzichtScoresStaatOpen = false;
		opdrachtBeheerStaatOpen = false;
		quizBeheerStaatOpen = false;
		this.dbHandler = dbHandler;
		this.opstartController = opstartController;
		this.leraar = leraar;

		mainView.setLeraar(leraar.toString());
		mainView.addOverzichtScoresKnopActionListener(new OverzichtScoresKnopListener());	
		mainView.addWindowListener(new MainWindowClosingListener());
		mainView.addAfsluitenKnopActionListener(new AfsluitenKnopListener());
		mainView.addLogoutKnopActionListener(new LogoutKnopListener());
		mainView.addOpdrachtBeheerKnopActionListener(new OpdrachtBeheerKnopListener());
		mainView.addQuizBeheerKnopActionListener(new QuizBeheerKnopListener());

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
			if (JOptionPane.showConfirmDialog(mainView, "Weet je zeker dat je het programma wil afsluiten?", "Programma Afsluiten?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
				dbHandler.vulCatalogi();
				} catch (IOException iEx) {
					JOptionPane.showMessageDialog(mainView, "Fout bij het wegschrijven van data:\n" + iEx.getMessage());
				} finally {
				System.exit(0);
				}
			}
		}
		
	}
	
	class MainWindowClosingListener extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent event) {
			if (JOptionPane.showConfirmDialog(mainView, "Weet je zeker dat je het programma wil afsluiten?", "Programma Afsluiten?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
				dbHandler.vulCatalogi();
				} catch (IOException iEx) {
					JOptionPane.showMessageDialog(mainView, "Fout bij het wegschrijven van data:\n" + iEx.getMessage());
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
				overzichtScoresQuizzenView = new OverzichtScoresQuizzenView();
				overzichtScoresController = new OverzichtScoresQuizzenController(
						dbHandler, overzichtScoresQuizzenView);
				
				overzichtScoresQuizzenView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						overzichtScoresStaatOpen = false;
					}
				});
			} else {
				overzichtScoresQuizzenView.toFront();
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
				quizBeheerView = new QuizBeheerView();
				quizBeheerController = new QuizBeheerController(dbHandler, leraar, quizBeheerView);
				quizBeheerView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						quizBeheerStaatOpen = false;
					}
				});
			} else {
				quizBeheerView.toFront();
			}
		}
		
	}

}
