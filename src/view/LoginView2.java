package view;

import java.awt.Color;
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

import view.viewInterfaces.ILoginView;

@SuppressWarnings("serial")
public class LoginView2 extends JFrame implements ILoginView {
	
	private JLabel lblUitleg;
	private JLabel lblVoornaam, lblFamilienaam;
	private JTextField txtVoornaam, txtFamilienaam;
	private JButton btnLogin;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;	
	
	public LoginView2() {
		super("Login");
		this.setSize(320, 180);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.RED);
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		lblUitleg = new JLabel("Geef je gegevens in:");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblUitleg, constraints);
		
		lblVoornaam = new JLabel("Voornaam: ");
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(lblVoornaam, constraints);
		
		txtVoornaam = new JTextField();
		txtVoornaam.setMinimumSize(new Dimension(100,28));
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
		constraints.insets = new Insets(5, 10, 0, 0);
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(lblFamilienaam, constraints);
		
		txtFamilienaam = new JTextField();
		txtFamilienaam.setMinimumSize(new Dimension(100,28));
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 0, 0, 10);
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.weightx = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(txtFamilienaam, constraints);
		
		btnLogin = new JButton("Login");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 0, 10, 10);
		constraints.gridy = 3;
		constraints.gridx = 1;
		constraints.weighty = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		this.add(btnLogin, constraints);
		
		SwingUtilities.updateComponentTreeUI(this);
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
