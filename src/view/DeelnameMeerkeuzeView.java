package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class DeelnameMeerkeuzeView extends DeelnameKlassiekOpsommingView {
	
	private ButtonGroup buttonGroup;
	private JRadioButton[] keuzes;
	
	public DeelnameMeerkeuzeView(String[] antwoordKeuzes) {
		super();
		int extraHoogte = antwoordKeuzes.length > 3 ? (antwoordKeuzes.length - 3) * 25 : 0;
		this.setSize(500, 275 + extraHoogte);
		
		// Het middelste deel varieert naargelang type opdracht
		panelCenter = new JPanel(layout);
		add(panelCenter, BorderLayout.CENTER);
		
		buttonGroup = new ButtonGroup();
		keuzes = new JRadioButton[antwoordKeuzes.length];
		for (int i = 0; i < keuzes.length; i++) {
			keuzes[i] = new JRadioButton(antwoordKeuzes[i]);
			buttonGroup.add(keuzes[i]);
			
			constraints = new GridBagConstraints();
			constraints.gridy = i;
			constraints.gridx = 0;
			constraints.anchor = GridBagConstraints.WEST;
			panelCenter.add(keuzes[i], constraints);
		}
	
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
}
