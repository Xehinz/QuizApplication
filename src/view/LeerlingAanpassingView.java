package view;

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
	private JPanel opdrachtKnoppenVeld, leerlingInfoVeld;

	public LeerlingAanpassingView(Leerling aLeerling, Leraar aLeraar) {
		super("Leerling");
		this.setSize(600, 400);		
		this.setLocationRelativeTo(null);
		
		layout = new GridBagLayout();
		this.setLayout(layout);
				
		this.aLeerling = aLeerling;
		this.aLeraar = aLeraar;
		
		//INIT BUTTONS
		btnLeerlingBewaren = new JButton("Leerling bewaren");
		btnQuizDeelnames = new JButton("Quiz deelnames");
		
		//INIT TEXTFIELDS
		txtID = new JTextField(aLeerling.getID());
		txtID.setEditable(false);
		txtVoornaam = new JTextField(aLeerling.getLeerlingVoornaam());
		txtFamilienaam = new JTextField(aLeerling.getLeerlingFamilienaam());
		txtLeerjaar = new JTextField(aLeerling.getLeerjaar());
		
		//INIT LABELS
		lblID = new JLabel("ID");
		lblVoornaam = new JLabel("Voornaam");
		lblFamilienaam = new JLabel("Familienaam");
		lblLeerjaar = new JLabel ("Leerjaar");
		
		//INIT KNOPPENVELD
		opdrachtKnoppenVeld = new JPanel();
		opdrachtKnoppenVeld.setLayout(layout);
		//INIT QUIZINFOVELD
		leerlingInfoVeld = new JPanel();
		leerlingInfoVeld.setLayout(layout);
				
		//ADD LABELS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		leerlingInfoVeld.add(lblID, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 4;
		leerlingInfoVeld.add(lblVoornaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		leerlingInfoVeld.add(lblFamilienaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 2;
		leerlingInfoVeld.add(lblLeerjaar, constraints);
		
		//ADD BUTTON
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		leerlingInfoVeld.add(btnLeerlingBewaren, constraints);

		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		leerlingInfoVeld.add(btnQuizDeelnames, constraints);
				
		//ADD TEXTFIELDS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		leerlingInfoVeld.add(txtID, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		leerlingInfoVeld.add(txtVoornaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		leerlingInfoVeld.add(txtFamilienaam, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		leerlingInfoVeld.add(txtLeerjaar, constraints);
		
		//BUILD WINDOW
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTH;
		this.add(leerlingInfoVeld, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 1;
		this.add(opdrachtKnoppenVeld, constraints);
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
	public void setID(String id) {
		 txtID.setText(id);
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
