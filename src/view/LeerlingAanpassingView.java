package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.tableModels.QuizAanpassingTableModel;
import model.Leerling;
import model.Leraar;
import model.OpdrachtCategorie;
import model.quizStatus.Afgesloten;
import model.quizStatus.Afgewerkt;
import model.quizStatus.InConstructie;
import model.quizStatus.LaatsteKans;
import model.quizStatus.Opengesteld;
import model.quizStatus.QuizStatus;

@SuppressWarnings("serial")
public class LeerlingAanpassingView extends JFrame  {

	private Leerling aLeerling;
	private Leraar aLeraar;

	//SWINGFIELDS	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel lblID, lblVoornaam, lblFamilienaam, lblLeerjaar;
	private JButton btnLeerlingBewaren, btnQuizDeelnames;
	private JTextField txtID, txtVoornaam, txtFamilienaam, txtLeerjaar;

	public LeerlingAanpassingView(Leerling aLeerling, Leraar aLeraar) {
		super("Leerling");
		this.setSize(400, 300);		
		this.setLocationRelativeTo(null);
		
		layout = new GridBagLayout();
		this.setLayout(layout);
				
		this.aLeerling = aLeerling;
		this.aLeraar = aLeraar;
		
		//INIT BUTTONS
		btnLeerlingBewaren = new JButton("Leerling bewaren");
		btnQuizDeelnames = new JButton("Quiz deelnames");
		
		//INIT TEXTFIELDS
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setPreferredSize(new Dimension(100, 20));
		txtVoornaam = new JTextField();
		txtFamilienaam = new JTextField();
		txtLeerjaar = new JTextField();
		txtLeerjaar.setPreferredSize(new Dimension(100, 20));
		
		//INIT LABELS
		lblID = new JLabel("ID");
		lblVoornaam = new JLabel("Voornaam");
		lblFamilienaam = new JLabel("Familienaam");
		lblLeerjaar = new JLabel ("Leerjaar");
		
		//ADD LABELS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.2;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(lblID, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 10, 0, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.weightx = 0.2;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(lblVoornaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 10, 0, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.weightx = 0.2;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(lblFamilienaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 10, 10, 10);
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.weightx = 0.2;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(lblLeerjaar, constraints);
		
		//ADD BUTTON
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 5;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(btnLeerlingBewaren, constraints);

		/*
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 5;
		constraints.gridx = 3;
		constraints.anchor = GridBagConstraints.WEST;
		leerlingInfoVeld.add(btnQuizDeelnames, constraints);
		*/
				
		//ADD TEXTFIELDS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.weightx = 0.4;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(txtID, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 10, 0, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.weightx = 0.8;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(txtVoornaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 10, 0, 10);
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.weightx = 0.8;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(txtFamilienaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 10, 10, 10);
		constraints.gridy = 3;
		constraints.gridx = 1;
		constraints.weightx = 0.4;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(txtLeerjaar, constraints);
		
//		//BUILD WINDOW
//		constraints = new GridBagConstraints();
//		constraints.insets = new Insets(10, 10, 10, 10);
//		constraints.gridy = 0;
//		constraints.gridx = 0;
//		constraints.weightx = 1;
//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		constraints.anchor = GridBagConstraints.NORTH;
//		this.add(leerlingInfoVeld, constraints);
//		
//		constraints = new GridBagConstraints();
//		constraints.insets = new Insets(10, 10, 10, 10);
//		constraints.gridy = 5;
//		constraints.gridx = 0;
//		this.add(opdrachtKnoppenVeld, constraints);
	}

	/**
	 * zelf in te stellen door de controller: als het een nieuwe leerling is, 
	 * dan kunnen er nog geen quiz-deelnames opgezocht worden, als het een 
	 * bestaande leerling is, dan kan dat wel
	 */
	public void disableQuizDeelnames() {
		btnQuizDeelnames.setEnabled(false);
	}
	
	public void addLeerlingBewarenListener(ActionListener listener) {
		btnLeerlingBewaren.addActionListener(listener);
	}

	public void openQuizDeelnamesListener(ActionListener listener) {
		btnQuizDeelnames.addActionListener(listener);
	}
	
	/**
	 * getters & setters (textboxen)
	 */
	public String getID() {
		return txtID.getText();
	}
	public void setID(int id) {
		 txtID.setText(Integer.toString(id));
	}
	
	public String getVoornaam() {
		return txtVoornaam.getText();
	}
	public void setVoornaam(String voornaam) {
		 txtVoornaam.setText(voornaam);
	}
	
	public String getFamilienaam() {
		return txtFamilienaam.getText();
	}
	public void setFamilienaam(String familienaam) {
		 txtFamilienaam.setText(familienaam);
	}
	
	public int getLeerjaar() {
		return Integer.parseInt(txtLeerjaar.getText());
	}
	public void setLeerjaar(int leerjaar) {
		 txtLeerjaar.setText(Integer.toString(leerjaar));
	}
	
}
