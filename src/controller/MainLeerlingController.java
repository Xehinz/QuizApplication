package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Leerling;
import persistency.DBHandler;
import view.QuizScoresRapportView;
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.IMainLeerlingView;
import view.viewInterfaces.IQuizScoresRapportView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class MainLeerlingController {
	
	private boolean quizDeelnameStaatOpen;
	
	private DBHandler dbHandler;
	private Leerling leerling;	
	private OpstartController opstartController;
	private ViewFactory viewFactory;
	
	private QuizDeelnameController quizDeelnameController;
	
	private IMainLeerlingView mainView;
	private IQuizScoresRapportView quizScoresRapportView;
	
	public MainLeerlingController(DBHandler dbHandler, Leerling leerling, OpstartController opstartController, ViewFactory viewFactory) {
		this.dbHandler = dbHandler;
		this.leerling = leerling;
		this.opstartController = opstartController;
		this.viewFactory = viewFactory;
		mainView = (IMainLeerlingView)viewFactory.maakView(ViewType.MainLeerlingView);
		quizDeelnameStaatOpen = false;
		
		mainView.setLeerling(leerling.getNaam());
		mainView.addAfsluitenKnopActionListener(ae -> opstartController.saveEnSluitAf());
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
	
	class MainWindowClosingListener extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent event) {
			opstartController.saveEnSluitAf();
		}
	}
	
	class DeelnemenKnopListener implements ActionListener {		

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!quizDeelnameStaatOpen) {
				quizDeelnameStaatOpen = true;				
				quizDeelnameController = new QuizDeelnameController(dbHandler, leerling, viewFactory);
				quizDeelnameController.getView().addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						quizDeelnameStaatOpen = false;
					}
				});
			} else {
				quizDeelnameController.getView().toFront();
			}
		}
		
	}
	
	class QuizRapportKnopListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			quizScoresRapportView = new QuizScoresRapportView();
			QuizScoresRapportController controller = new QuizScoresRapportController(dbHandler, leerling, quizScoresRapportView);
			
		}
		
	}
}
