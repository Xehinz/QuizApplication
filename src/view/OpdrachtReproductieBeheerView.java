package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.OpdrachtAanpassingView;

public class OpdrachtReproductieBeheerView extends OpdrachtAanpassingView {

	protected JLabel lblMinimumAantalTrefwoorden;
	protected JTextField txtMinimumAantalTrefwoorden;
	
	public OpdrachtReproductieBeheerView() {
		super();
		this.setTitle("Reproductie opdracht");
		middenPanel = new JPanel();
		middenPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		lblMinimumAantalTrefwoorden = new JLabel("Minimum aantal trefwoorden: ");
		middenPanel.add(lblMinimumAantalTrefwoorden, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMinimumAantalTrefwoorden = new JTextField();
		txtMinimumAantalTrefwoorden.setPreferredSize(new Dimension(200, 25));
		middenPanel.add(txtMinimumAantalTrefwoorden, c);

		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 1;
		b.gridy = 2;
		b.insets = new Insets(10, 10, 10, 10);
		b.fill = GridBagConstraints.BOTH;
		grootPanel.add(middenPanel, b);
	}
	
	public void setMinimumAantalTrefwoorden(int min){
		txtMinimumAantalTrefwoorden.setText(Integer.toString(min));
	}
	
	public int getMogelijkeAntwoordenMeerkeuze(){
		return Integer.parseInt(txtMinimumAantalTrefwoorden.getText());
	}

}
