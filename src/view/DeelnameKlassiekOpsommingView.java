package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class DeelnameKlassiekOpsommingView extends JFrame {

	protected GridBagLayout layout;
	protected GridBagConstraints constraints;

	protected JPanel gemeenschappelijkPanelBoven, gemeenschappelijkPanelOnder,
			panelCenter;
	protected JLabel vraagCounter, opdrachtCategorie, pogingen,
			pogingenCounter;
	protected JLabel vraag;
	protected JTextField antwoordVeld;
	protected JButton antwoordKnop, hintKnop;

	protected JProgressBar tijdVoortgang;
	protected Timer timer;

	public DeelnameKlassiekOpsommingView() {
		super("Quiz: ");
		this.setSize(500, 200);
		this.setLocationRelativeTo(null);

		// Maakt een JPanel met alle GUI Componenten die gemeenschappelijk zijn
		// voor alle soorten Opdrachten
		layout = new GridBagLayout();
		gemeenschappelijkPanelBoven = new JPanel(layout);
		add(gemeenschappelijkPanelBoven, BorderLayout.NORTH);

		vraagCounter = new JLabel("Vraag 0");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.WEST;
		gemeenschappelijkPanelBoven.add(vraagCounter, constraints);
		
		opdrachtCategorie = new JLabel("Categorie: ");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 10, 0, 0);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		gemeenschappelijkPanelBoven.add(opdrachtCategorie, constraints);

		tijdVoortgang = new JProgressBar();
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 0, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		gemeenschappelijkPanelBoven.add(tijdVoortgang, constraints);

		pogingen = new JLabel("Resterende pogingen: ");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.EAST;
		gemeenschappelijkPanelBoven.add(pogingen, constraints);

		pogingenCounter = new JLabel("0");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 0, 10);
		constraints.gridy = 0;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		gemeenschappelijkPanelBoven.add(pogingenCounter, constraints);

		vraag = new JLabel("Vraag?");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(15, 0, 0, 0);
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		gemeenschappelijkPanelBoven.add(vraag, constraints);

		// Het middelste deel varieert naargelang type opdracht
		panelCenter = new JPanel(layout);
		add(panelCenter, BorderLayout.CENTER);

		antwoordVeld = new JTextField();
		antwoordVeld.setMinimumSize(new Dimension(50, 20));
		antwoordVeld.setMaximumSize(new Dimension(10000, 20));
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 20, 0, 20);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.gridy = 0;
		panelCenter.add(antwoordVeld, constraints);

		ToolTipManager.sharedInstance().setInitialDelay(300);
		ToolTipManager.sharedInstance().setReshowDelay(300);

		// AntwoordKnop is gemeenschappelijk voor alle Opdrachten
		gemeenschappelijkPanelOnder = new JPanel(layout);
		add(gemeenschappelijkPanelOnder, BorderLayout.SOUTH);

		hintKnop = new JButton("Geef Hint");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 20, 10, 0);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		gemeenschappelijkPanelOnder.add(hintKnop, constraints);

		antwoordKnop = new JButton("Geef Antwoord");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 10, 20);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.EAST;
		gemeenschappelijkPanelOnder.add(antwoordKnop, constraints);

	}

	public void setVraagCounter(int count) {
		vraagCounter.setText(String.format("Vraag %d", count));
	}

	public void setMaxTijd(int milliSeconden) {
		tijdVoortgang.setMaximum(milliSeconden);
		tijdVoortgang.setMinimum(0);
		tijdVoortgang.setValue(milliSeconden);
	}
	
	public void setTijdOver(int milliSeconden) {
		tijdVoortgang.setValue(milliSeconden);
	}

	public void setPogingen(int pogingen) {
		pogingenCounter.setText(Integer.toString(pogingen));
	}

	public void setVraag(String vraag) {
		this.vraag.setText(vraag);
	}
	
	public void setOpdrachtCategorie(String categorie) {
		opdrachtCategorie.setText("Categorie: " + categorie);
	}

	public String getAntwoord() {
		return antwoordVeld.getText();
	}

	public void setAntwoordVeldToolTip(String toolTip) {
		antwoordVeld.setToolTipText(toolTip);
	}

	public void useHint(boolean useHint) {
		if (!useHint) {
			hintKnop.setVisible(false);
		}
	}
	
	public void disableHint() {
		hintKnop.setEnabled(false);
	}

	public void opTijd(boolean opTijd) {
		if (!opTijd) {
			tijdVoortgang.setVisible(false);
		}
	}
	
	public void beperktePogingen(boolean beperktePogingen) {
		pogingenCounter.setText("ongelimiteerd");
	}

	public void setQuizOnderwerp(String onderwerp) {
		this.setTitle("Quiz: " + onderwerp);
	}

	public void addAntwoordKnopListener(ActionListener listener) {
		antwoordKnop.addActionListener(listener);
	}

	public void addHintKnopListener(ActionListener listener) {
		hintKnop.addActionListener(listener);
	}

	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel,
				JOptionPane.INFORMATION_MESSAGE);
	}

}
