package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Ben Vandenberk
 *
 */
@SuppressWarnings("serial")
public class DeelnameMeerkeuzeView extends DeelnameKlassiekOpsommingView {
	
	private ButtonGroup buttonGroup;
	private JRadioButton[] keuzes;
	
	public DeelnameMeerkeuzeView() {
		super();	
		
		// Het middelste deel varieert naargelang type opdracht
		pnlCentrum = new JPanel(layout);
		add(pnlCentrum, BorderLayout.CENTER);	
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public String getAntwoord() {
		for (int i = 0; i < keuzes.length; i++) {
			if (keuzes[i].isSelected()) {
				return keuzes[i].getText();
			}
		}
		return "";
	}
	
	@Override
	public void setAntwoordKeuzes(String[] antwoordKeuzes) {
		int extraHoogte = antwoordKeuzes.length > 3 ? (antwoordKeuzes.length - 3) * 25 : 0;
		this.setSize(500, 275 + extraHoogte);
		
		buttonGroup = new ButtonGroup();
		keuzes = new JRadioButton[antwoordKeuzes.length];
		for (int i = 0; i < keuzes.length; i++) {
			keuzes[i] = new JRadioButton(antwoordKeuzes[i]);
			buttonGroup.add(keuzes[i]);
			
			constraints = new GridBagConstraints();
			constraints.gridy = i;
			constraints.gridx = 0;
			constraints.anchor = GridBagConstraints.WEST;
			pnlCentrum.add(keuzes[i], constraints);
		}
	}
}
