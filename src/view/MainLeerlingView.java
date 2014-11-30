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
public class MainLeerlingView extends JFrame {
	
	private JLabel lblLeerling;
	private JButton btnLogout, btnDeelnemen, btnQuizRapport, btnAfsluiten;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public MainLeerlingView() {
		super("Quiz applicatie (Leerling)");
		this.setSize(450, 250);		
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		lblLeerling = new JLabel("Leerling:");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 20, 0, 0);
		this.add(lblLeerling, constraints);
		
		btnLogout = new JButton("Logout");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.weightx = 0.1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.insets = new Insets(10, 0, 0, 20);
		this.add(btnLogout, constraints);
		
		btnDeelnemen = new JButton("Deelnemen aan een Quiz");
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 20, 10, 20);
		this.add(btnDeelnemen, constraints);
		
		btnQuizRapport = new JButton("Quiz Rapporten");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 0, 20);
		this.add(btnQuizRapport, constraints);
		
		btnAfsluiten = new JButton("Afsluiten");
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.1;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 20);
		this.add(btnAfsluiten, constraints);
	}
	
	public void setLeerling(String leerling) {
		lblLeerling.setText(String.format("Leerling: %s", leerling));
	}
	
	public void addLogoutKnopActionListener(ActionListener listener) {
		btnLogout.addActionListener(listener);
	}
	
	public void addAfsluitenKnopActionListener(ActionListener listener) {
		btnAfsluiten.addActionListener(listener);
	}
	
	public void addDeelnemenKnopActionListener(ActionListener listener) {
		btnDeelnemen.addActionListener(listener);
	}
	
	public void addQuizRapportKnopActionListener(ActionListener listener) {
		btnQuizRapport.addActionListener(listener);
	}

}
