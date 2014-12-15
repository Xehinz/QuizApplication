package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import view.OpdrachtAanpassingView;
import view.viewInterfaces.IOpdrachtReproductieBeheerView;

@SuppressWarnings("serial")
public class OpdrachtReproductieBeheerView extends OpdrachtAanpassingView implements IOpdrachtReproductieBeheerView {

	protected JLabel lblMinimumAantalTrefwoorden;
	protected JTextField txtMinimumAantalTrefwoorden;
	
	public OpdrachtReproductieBeheerView() {
		super();		
		this.setTitle("Reproductie opdracht");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 3;
		c.gridx = 1;
		c.insets = new Insets(10, 0, 0, 10);
		c.anchor = GridBagConstraints.EAST;
		lblMinimumAantalTrefwoorden = new JLabel("Min aantal trefwoorden: ");
		this.add(lblMinimumAantalTrefwoorden, c);
		c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 0, 0);
		c.gridx = 2;
		c.gridwidth = 2;
		c.gridy = 3;
		c.weightx = 10;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		txtMinimumAantalTrefwoorden = new JTextField();
		Dimension korteTextFieldDim = txtMinimumAantalTrefwoorden.getPreferredSize();
		korteTextFieldDim.width = 100;
		txtMinimumAantalTrefwoorden.setPreferredSize(korteTextFieldDim);
		txtMinimumAantalTrefwoorden.setMinimumSize(korteTextFieldDim);
		this.add(txtMinimumAantalTrefwoorden, c);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setMinimumAantalTrefwoorden(int min){
		txtMinimumAantalTrefwoorden.setText(Integer.toString(min));
	}
	
	public int getMinimumAantalTrefwoorden(){
		try {
			Integer.parseInt(txtMinimumAantalTrefwoorden.getText());
		} catch (Exception e) {
			throw new NumberFormatException(
					"Gelieve een positief geheel getal in te geven voor minimum aantal trefwoorden");
		}
		return Integer.parseInt(txtMinimumAantalTrefwoorden.getText());
	}
	
	@Override
	public void disableAanpassen() {
		super.disableAanpassen();
		txtMinimumAantalTrefwoorden.setEnabled(false);
	}

}
