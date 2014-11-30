package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Ben Vandenberk
 *
 */
@SuppressWarnings("serial")
public class MainLeraarView extends JFrame {
	
	private JLabel lblLeraar;
	private JButton btnLogout, btnOpdrachtBeheer, btnQuizBeheer, btnLeerlingbeheer, btnOverzichtScores, btnAfsluiten;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public MainLeraarView() {
		super("Quiz applicatie (Leraar)");
		this.setSize(450, 300);		
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		lblLeraar = new JLabel("Leraar:");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 20, 0, 0);
		this.add(lblLeraar, constraints);
		
		btnLogout = new JButton("Logout");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.weightx = 0.1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.insets = new Insets(10, 0, 0, 20);
		this.add(btnLogout, constraints);
		
		btnOpdrachtBeheer = new JButton("Opdrachtbeheer");
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 20, 10, 20);
		this.add(btnOpdrachtBeheer, constraints);
		
		btnQuizBeheer = new JButton("Quizbeheer");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 10, 20);
		this.add(btnQuizBeheer, constraints);
		
		btnLeerlingbeheer = new JButton("Leerlingbeheer");
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 10, 20);
		this.add(btnLeerlingbeheer, constraints);
		
		btnOverzichtScores = new JButton("Overzicht Scores");
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 0, 20);
		this.add(btnOverzichtScores, constraints);
		
		btnAfsluiten = new JButton("Afsluiten");
		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.gridx = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.1;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 20);
		this.add(btnAfsluiten, constraints);
	}
	
	public void setLeraar(String leraar) {
		lblLeraar.setText(String.format("Leraar: %s", leraar));
	}
	
	public void addLogoutKnopActionListener(ActionListener listener) {
		btnLogout.addActionListener(listener);
	}
	
	public void addAfsluitenKnopActionListener(ActionListener listener) {
		btnAfsluiten.addActionListener(listener);
	}
	
	public void addOpdrachtBeheerKnopActionListener(ActionListener listener) {
		btnOpdrachtBeheer.addActionListener(listener);
	}
	
	public void addQuizBeheerKnopActionListener(ActionListener listener) {
		btnQuizBeheer.addActionListener(listener);
	}
	
	public void addLeerlingBeheerKnopActionListener(ActionListener listener) {
		btnLeerlingbeheer.addActionListener(listener);
	}
	
	public void addOverzichtScoresKnopActionListener(ActionListener listener) {
		btnOverzichtScores.addActionListener(listener);
	}

}
