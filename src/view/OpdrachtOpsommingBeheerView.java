package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.OpdrachtAanpassingView;

public class OpdrachtOpsommingBeheerView extends OpdrachtAanpassingView {

	protected JLabel lblInJuisteVolgorde;
	protected JCheckBox chbInJuisteVolgorde;
	
	public OpdrachtOpsommingBeheerView() {
		super();
		this.setTitle("Opsomming opdracht");
		middenPanel = new JPanel();
		middenPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		lblInJuisteVolgorde = new JLabel("In juiste volgorde: ");
		middenPanel.add(lblInJuisteVolgorde, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		chbInJuisteVolgorde = new JCheckBox();
		middenPanel.add(chbInJuisteVolgorde, c);

		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 1;
		b.gridy = 2;
		b.insets = new Insets(10, 10, 10, 10);
		b.fill = GridBagConstraints.BOTH;
		grootPanel.add(middenPanel, b);
	}
	
	public void setInJuisteVolgorde(boolean b){
		chbInJuisteVolgorde.setSelected(b);
	}
	
	public boolean getMogelijkeAntwoordenMeerkeuze(){
		return chbInJuisteVolgorde.isSelected();
	}

}
