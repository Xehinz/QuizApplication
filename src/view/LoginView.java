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

@SuppressWarnings("serial")
public class LoginView extends JFrame {
	
	private JLabel lblUitleg;
	private JLabel lblVoornaam, lblFamilienaam;
	private JTextField txtVoornaam, txtFamilienaam;
	private JButton btnLogin;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public LoginView() {
		super("Login");
		this.setSize(320, 165);
		this.setLocationRelativeTo(null);		
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		lblUitleg = new JLabel("Geef je gegevens in:");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblUitleg, constraints);
		
		lblVoornaam = new JLabel("Voornaam: ");
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.weighty = 1.5;
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblVoornaam, constraints);
		
		txtVoornaam = new JTextField();
		txtVoornaam.setMinimumSize(new Dimension(100,20));
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.weightx = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 0, 0, 10);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(txtVoornaam, constraints);
		
		lblFamilienaam = new JLabel("Familienaam: ");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.weighty = 1.5;
		constraints.insets = new Insets(0, 10, 0, 0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblFamilienaam, constraints);
		
		txtFamilienaam = new JTextField();
		txtFamilienaam.setMinimumSize(new Dimension(100,20));
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 0, 10);
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.weightx = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(txtFamilienaam, constraints);
		
		btnLogin = new JButton("Login");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 10, 10);
		constraints.gridy = 3;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.EAST;
		this.add(btnLogin, constraints);
	}
	
	public String getVoornaam() {
		return txtVoornaam.getText().trim().toLowerCase();
	}
	
	public String getFamilienaam() {
		return txtFamilienaam.getText().trim().toLowerCase();
	}
	
	public String getVolledigeNaam() {
		return getVoornaam() + " " + getFamilienaam();
	}
	
	public void addLoginActionListener(ActionListener listener) {
		btnLogin.addActionListener(listener);
	}
	
	public void toonErrorMessage(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.ERROR_MESSAGE);
	}
	
	public void toonInformationMessage(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}

}
