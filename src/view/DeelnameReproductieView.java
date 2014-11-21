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
	
	private JTextArea grootAntwoordVeld;
	
	public DeelnameReproductieView() {
		super();
		this.setSize(500, 275);
		
		// Het middelste deel varieert naargelang type opdracht
		panelCenter = new JPanel(layout);
		add(panelCenter, BorderLayout.CENTER);
		
		grootAntwoordVeld = new JTextArea();
		grootAntwoordVeld.setRows(5);
		antwoordVeld.setMinimumSize(new Dimension(50,20));
		antwoordVeld.setMaximumSize(new Dimension(10000,10000));
		constraints = new GridBagConstraints();		
		constraints.insets = new Insets(0, 20, 0, 20);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.gridy = 0;				
		panelCenter.add(new JScrollPane(grootAntwoordVeld), constraints);
	}
	
	@Override
	public String getAntwoord() {
		return grootAntwoordVeld.getText();
	}

}
