package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import view.OpdrachtAanpassingView;
import view.viewInterfaces.IOpdrachtOpsommingBeheerView;

@SuppressWarnings("serial")
public class OpdrachtOpsommingBeheerView extends OpdrachtAanpassingView implements IOpdrachtOpsommingBeheerView {

	protected JLabel lblInJuisteVolgorde;
	protected JCheckBox chbInJuisteVolgorde;
	
	public OpdrachtOpsommingBeheerView() {
		super();
		this.setTitle("Opsomming opdracht");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 3;
		c.gridx = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		lblInJuisteVolgorde = new JLabel("In juiste volgorde: ");
		this.add(lblInJuisteVolgorde, c);
		c.gridx = 2;
		c.weightx = 10;
		c.anchor = GridBagConstraints.WEST;
		chbInJuisteVolgorde = new JCheckBox();
		this.add(chbInJuisteVolgorde, c);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setInJuisteVolgorde(boolean b){
		chbInJuisteVolgorde.setSelected(b);
	}
	
	public boolean getInJuisteVolgorde(){
		return chbInJuisteVolgorde.isSelected();
	}

}
