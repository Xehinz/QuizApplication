package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Leerling;
import persistency.DBHandler;
import view.MainLeerlingView;
import view.OpdrachtDeelnameViewFactory;
import view.QuizDeelnameView;
import view.viewInterfaces.IQuizDeelnameView;

public class MainLeerlingController {
	
	private boolean quizDeelnameStaatOpen;
	
	private DBHandler dbHandler;
	private Leerling leerling;
	
	private OpstartController opstartController;
	
	private MainLeerlingView mainView;
	private IQuizDeelnameView quizDeelnameView;
	
	public MainLeerlingController(DBHandler dbHandler, Leerling leerling, OpstartController opstartController) {
		this.dbHandler = dbHandler;
		this.leerling = leerling;
		this.opstartController = opstartController;
		mainView = new MainLeerlingView();
		quizDeelnameStaatOpen = false;
		
		mainView.setLeerling(leerling.getNaam());
		mainView.addAfsluitenKnopActionListener(new AfsluitenKnopListener());
		mainView.addLogoutKnopActionListener(new LogoutKnopListener());
		mainView.addDeelnemenKnopActionListener(new DeelnemenKnopListener());
		mainView.addQuizRapportKnopActionListener(new QuizRapportKnopListener());
		mainView.addWindowListener(new MainWindowClosingListener());
		
		mainView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainView.setLocationRelativeTo(null);
		mainView.setVisible(true);
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
	
	class DeelnemenKnopListener implements ActionListener {
		
		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent event) {
			if (!quizDeelnameStaatOpen) {
				quizDeelnameStaatOpen = true;
				quizDeelnameView = new QuizDeelnameView();
				QuizDeelnameController quizDeelnameController = new QuizDeelnameController(dbHandler, leerling, quizDeelnameView, new OpdrachtDeelnameViewFactory());
				quizDeelnameView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						quizDeelnameStaatOpen = false;
					}
				});
			} else {
				quizDeelnameView.toFront();
			}
		}
		
	}
	
	class QuizRapportKnopListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
		}
		
	}
}
