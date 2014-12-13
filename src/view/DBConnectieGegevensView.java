package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class DBConnectieGegevensView extends JDialog {
	
	private JLabel lblConnectieString, lblGebruiker, lblPaswoord;
	private JTextField txtConnectieString, txtGebruiker;
	private JPasswordField txtPaswoord;
	private JButton btnOpslaan, btnAnnuleren;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public DBConnectieGegevensView(JFrame owner) {
		super(owner, "Database Connectie Gegevens", true);
		setLocationRelativeTo(owner);
		setSize(400, 185);
		
		layout = new GridBagLayout();
		setLayout(layout);
		
		lblConnectieString = new JLabel("Connectie string:");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 10, 0, 0);
		add(lblConnectieString, constraints);
		
		txtConnectieString = new JTextField();
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 10;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 10, 0, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(txtConnectieString, constraints);
		
		lblGebruiker = new JLabel("Gebruiker:");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(5, 10, 0, 0);
		add(lblGebruiker, constraints);
		
		txtGebruiker = new JTextField();
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 10;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(5, 10, 0, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(txtGebruiker, constraints);
		
		lblPaswoord = new JLabel("Paswoord:");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(5, 10, 0, 0);
		add(lblPaswoord, constraints);
		
		txtPaswoord = new JPasswordField();
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 10;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(5, 10, 0, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(txtPaswoord, constraints);		
		
		btnAnnuleren = new JButton("Annuleren");
		btnAnnuleren.addActionListener(ae -> dispose());
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 5;
		constraints.weighty = 10;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(btnAnnuleren, constraints);		
		
		btnOpslaan = new JButton("Opslaan");
		constraints = new GridBagConstraints();
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.weightx = 5;
		constraints.weighty = 10;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 0, 10, 10);
		add(btnOpslaan, constraints);	
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setConnectieString(String connectieString) {
		txtConnectieString.setText(connectieString);
	}
	
	public String getConnectieString() {
		return txtConnectieString.getText();
	}
	
	public void setGebruiker(String gebruiker) {
		txtGebruiker.setText(gebruiker);
	}
	
	public String getGebruiker() {
		return txtGebruiker.getText();
	}
	
	public void setPaswoord(String paswoord) {
		txtPaswoord.setText(paswoord);
	}
	
	public char[] getPaswoord() {
		return txtPaswoord.getPassword();
	}
	
	public void addOpslaanKlikListener(ActionListener listener) {
		btnOpslaan.addActionListener(listener);
	}

}
