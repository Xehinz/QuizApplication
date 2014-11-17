package view;

/**
 * 
 * @author Johan Boogers
 *
 * 
 */

import java.awt.event.ActionListener;
import javax.swing.*;

public class MainView extends JFrame {

	private JButton buttonBeheerLeerling = new JButton("Beheer Leerling");
	private JButton buttonBeheerQuiz = new JButton("Beheer Quiz");
	private JButton buttonBeheerOpdracht = new JButton("Beheer Opdracht");
	private JButton buttonNeemDeelAanQuiz = new JButton("Neem deel aan een Quiz");
	private JButton buttonOverzichtScores = new JButton("Overzicht scores");

	private JButton buttonStop = new JButton("Stop");
	
	MainView() {

		JPanel calcPanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 200);
		
		calcPanel.add(buttonBeheerLeerling);
		calcPanel.add(buttonBeheerQuiz);
		calcPanel.add(buttonBeheerOpdracht);
		calcPanel.add(buttonNeemDeelAanQuiz);
		calcPanel.add(buttonOverzichtScores);
		calcPanel.add(buttonStop);
		
		this.add(calcPanel);
		
	}
	
	void displayErrorMessage(String errorMessage){ 
		JOptionPane.showMessageDialog(this, errorMessage); 
	} 

	void addBeheerLeerlingListener(ActionListener listenForCalcButton){
		buttonBeheerLeerling.addActionListener(listenForCalcButton);
	}

	void addBeheerQuizListener(ActionListener listenForCalcButton){
		buttonBeheerQuiz.addActionListener(listenForCalcButton);
	}

	void addBeheerOpdrachtListener(ActionListener listenForCalcButton){
		buttonBeheerOpdracht.addActionListener(listenForCalcButton);
	}

	void addNeemDeelAanQuizListener(ActionListener listenForCalcButton){
		buttonNeemDeelAanQuiz.addActionListener(listenForCalcButton);
	}

	void addOverzichtScoresListener(ActionListener listenForCalcButton){
		buttonOverzichtScores.addActionListener(listenForCalcButton);
	}

	void addStopListener(ActionListener listenForCalcButton){
		buttonStop.addActionListener(listenForCalcButton);
	}

}