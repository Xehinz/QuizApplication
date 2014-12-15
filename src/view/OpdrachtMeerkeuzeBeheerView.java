package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import view.OpdrachtAanpassingView;
import view.viewInterfaces.IOpdrachtMeerkeuzeBeheerView;

@SuppressWarnings("serial")
public class OpdrachtMeerkeuzeBeheerView extends OpdrachtAanpassingView implements IOpdrachtMeerkeuzeBeheerView {

	protected JLabel lblMogelijkeAntwoordenMeerkeuze;
	protected JTextField txtMogelijkeAntwoordenMeerkeuze;

	public OpdrachtMeerkeuzeBeheerView() {
		super();
		this.setTitle("Meerkeuze opdracht");

		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 3;
		c.gridx = 1;
		c.insets = new Insets(10, 10, 0, 10);
		c.anchor = GridBagConstraints.EAST;
		lblMogelijkeAntwoordenMeerkeuze = new JLabel("Mogelijke antwoorden:");
		this.add(lblMogelijkeAntwoordenMeerkeuze, c);
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridwidth = 2;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 0, 10);
		c.weightx = 10;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		txtMogelijkeAntwoordenMeerkeuze = new JTextField();
		this.add(txtMogelijkeAntwoordenMeerkeuze, c);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setMogelijkeAntwoordenMeerkeuze(String mogelijkeAntwoorden){
		txtMogelijkeAntwoordenMeerkeuze.setText(mogelijkeAntwoorden);
	}
	
	public String getMogelijkeAntwoordenMeerkeuze(){
		if (txtMogelijkeAntwoordenMeerkeuze.getText().equals("")){throw new IllegalArgumentException("Gelieve mogelijke antwoordkeuzes in te geven");}
		return txtMogelijkeAntwoordenMeerkeuze.getText();
	}
	
	@Override
	public void disableAanpassen() {
		super.disableAanpassen();
		txtMogelijkeAntwoordenMeerkeuze.setEditable(false);
	}
}
