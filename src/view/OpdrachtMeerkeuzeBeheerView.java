package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.OpdrachtAanpassingView;

public class OpdrachtMeerkeuzeBeheerView extends OpdrachtAanpassingView {

	protected JLabel lblMogelijkeAntwoordenMeerkeuze;
	protected JTextField txtMogelijkeAntwoordenMeerkeuze;

	public OpdrachtMeerkeuzeBeheerView() {
		super();
		this.setTitle("Meerkeuze opdracht");
		middenPanel = new JPanel();
		middenPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		lblMogelijkeAntwoordenMeerkeuze = new JLabel("Mogelijke antwoorden: ");
		middenPanel.add(lblMogelijkeAntwoordenMeerkeuze, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMogelijkeAntwoordenMeerkeuze = new JTextField();
		txtMogelijkeAntwoordenMeerkeuze
				.setPreferredSize(new Dimension(600, 75));
		middenPanel.add(txtMogelijkeAntwoordenMeerkeuze, c);

		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 1;
		b.gridy = 2;
		b.insets = new Insets(10, 10, 10, 10);
		b.fill = GridBagConstraints.BOTH;
		grootPanel.add(middenPanel, b);

	}
	
	public void setMogelijkeAntwoordenMeerkeuze(String mogelijkeAntwoorden){
		txtMogelijkeAntwoordenMeerkeuze.setText(mogelijkeAntwoorden);
	}
	
	public String getMogelijkeAntwoordenMeerkeuze(){
		return txtMogelijkeAntwoordenMeerkeuze.getText();
	}
}
