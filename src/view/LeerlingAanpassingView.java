package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import view.viewInterfaces.ILeerlingAanpassingView;

@SuppressWarnings("serial")
public class LeerlingAanpassingView extends JFrame implements ILeerlingAanpassingView {

	//SWINGFIELDS	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel lblID, lblVoornaam, lblFamilienaam, lblLeerjaar;
	private JButton btnLeerlingBewaren, btnQuizDeelnames;
	private JTextField txtID, txtVoornaam, txtFamilienaam, txtLeerjaar;

	public LeerlingAanpassingView() {
		super("Leerling");
		this.setSize(400, 300);		
		this.setLocationRelativeTo(null);
		
		layout = new GridBagLayout();
		this.setLayout(layout);		
		
		//INIT BUTTONS
		btnLeerlingBewaren = new JButton("Leerling bewaren");
		btnQuizDeelnames = new JButton("Quiz deelnames");
		
		//INIT TEXTFIELDS
		txtID = new JTextField();
		txtID.setEditable(false);
		Dimension textFieldDimension = txtID.getPreferredSize();
		textFieldDimension.width = 100;
		txtID.setPreferredSize(textFieldDimension);
		txtVoornaam = new JTextField();
		txtFamilienaam = new JTextField();
		txtLeerjaar = new JTextField();
		txtLeerjaar.setPreferredSize(textFieldDimension);
		
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
		
		SwingUtilities.updateComponentTreeUI(this);
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
		try {
		return Integer.parseInt(txtLeerjaar.getText());
		} catch (NumberFormatException nEx) {
			throw new NumberFormatException("Geef en getal tussen 1 en 6 in voor het leerjaar");
		}
	}
	
	public void setLeerjaar(int leerjaar) {
		 txtLeerjaar.setText(Integer.toString(leerjaar));
	}
	
	public void toonInformationMessage(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.OK_OPTION);
	}
	
}
