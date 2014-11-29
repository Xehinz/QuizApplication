package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class DeelnameReproductieView extends DeelnameKlassiekOpsommingView {
	
	private JTextArea txtAntwoordGroot;
	
	public DeelnameReproductieView() {
		super();
		this.setSize(500, 275);
		
		// Het middelste deel varieert naargelang type opdracht
		pnlCentrum = new JPanel(layout);
		add(pnlCentrum, BorderLayout.CENTER);
		
		txtAntwoordGroot = new JTextArea();
		txtAntwoordGroot.setRows(5);
		txtAntwoord.setMinimumSize(new Dimension(50,20));
		txtAntwoord.setMaximumSize(new Dimension(10000,10000));
		constraints = new GridBagConstraints();		
		constraints.insets = new Insets(0, 20, 0, 20);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.gridy = 0;				
		pnlCentrum.add(new JScrollPane(txtAntwoordGroot), constraints);
	}
	
	@Override
	public String getAntwoord() {
		return txtAntwoordGroot.getText();
	}

}
