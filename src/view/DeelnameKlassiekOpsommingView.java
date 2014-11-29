package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.ToolTipManager;

@SuppressWarnings("serial")
public class DeelnameKlassiekOpsommingView extends JFrame {

	protected GridBagLayout layout;
	protected GridBagConstraints constraints;

	protected JPanel pnlGemeenschappelijkBoven, pnlGemeenschappelijkOnder,
			pnlCentrum;
	protected JLabel lblVraagCounter, lblOpdrachtCategorie, lblPogingen,
			lblPogingCounter;
	protected JLabel lblVraag;
	protected JTextField txtAntwoord;
	protected JButton btnAntwoord, btnHint, btnVolgendeVraag;

	protected JProgressBar tijdVoortgang;
	protected Timer timer;

	public DeelnameKlassiekOpsommingView() {
		super("Quiz: ");
		this.setSize(500, 275);
		this.setLocationRelativeTo(null);

		// Maakt een JPanel met alle GUI Componenten die gemeenschappelijk zijn
		// voor alle soorten Opdrachten
		layout = new GridBagLayout();
		pnlGemeenschappelijkBoven = new JPanel(layout);
		add(pnlGemeenschappelijkBoven, BorderLayout.NORTH);

		lblVraagCounter = new JLabel("Vraag 0");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.WEST;
		pnlGemeenschappelijkBoven.add(lblVraagCounter, constraints);
		
		lblOpdrachtCategorie = new JLabel("Categorie: ");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 10, 0, 0);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		pnlGemeenschappelijkBoven.add(lblOpdrachtCategorie, constraints);

		tijdVoortgang = new JProgressBar();
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 0, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		pnlGemeenschappelijkBoven.add(tijdVoortgang, constraints);

		lblPogingen = new JLabel("Resterende pogingen: ");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.EAST;
		pnlGemeenschappelijkBoven.add(lblPogingen, constraints);

		lblPogingCounter = new JLabel("0");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 0, 10);
		constraints.gridy = 0;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		pnlGemeenschappelijkBoven.add(lblPogingCounter, constraints);

		lblVraag = new JLabel("Vraag?");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(15, 0, 0, 0);
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		pnlGemeenschappelijkBoven.add(lblVraag, constraints);

		// Het middelste deel varieert naargelang type opdracht
		pnlCentrum = new JPanel(layout);
		add(pnlCentrum, BorderLayout.CENTER);

		txtAntwoord = new JTextField();
		txtAntwoord.setMinimumSize(new Dimension(50, 20));
		txtAntwoord.setMaximumSize(new Dimension(10000, 20));
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 20, 0, 20);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.gridy = 0;
		pnlCentrum.add(txtAntwoord, constraints);

		ToolTipManager.sharedInstance().setInitialDelay(300);
		ToolTipManager.sharedInstance().setReshowDelay(300);

		// AntwoordKnop is gemeenschappelijk voor alle Opdrachten
		pnlGemeenschappelijkOnder = new JPanel(layout);
		add(pnlGemeenschappelijkOnder, BorderLayout.SOUTH);

		btnHint = new JButton("Geef Hint");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 20, 10, 0);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		pnlGemeenschappelijkOnder.add(btnHint, constraints);
		
		btnVolgendeVraag = new JButton("Volgende Vraag");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.weightx = 10;
		constraints.anchor = GridBagConstraints.EAST;
		pnlGemeenschappelijkOnder.add(btnVolgendeVraag, constraints);

		btnAntwoord = new JButton("Geef Antwoord");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 10, 20);
		constraints.gridy = 0;
		constraints.gridx = 2;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.EAST;
		pnlGemeenschappelijkOnder.add(btnAntwoord, constraints);

	}

	public void setVraagCounter(int count) {
		lblVraagCounter.setText(String.format("Vraag %d", count));
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
		lblPogingCounter.setText(Integer.toString(pogingen));
	}

	public void setVraag(String vraag) {
		this.lblVraag.setText(vraag);
	}
	
	public void setOpdrachtCategorie(String categorie) {
		lblOpdrachtCategorie.setText("Categorie: " + categorie);
	}

	public String getAntwoord() {
		return txtAntwoord.getText();
	}

	public void setAntwoordVeldToolTip(String toolTip) {
		txtAntwoord.setToolTipText(toolTip);
	}

	public void useHint(boolean useHint) {
		if (!useHint) {
			btnHint.setVisible(false);
		}
	}
	
	public void disableHint() {
		btnHint.setEnabled(false);
	}

	public void opTijd(boolean opTijd) {
		if (!opTijd) {
			tijdVoortgang.setVisible(false);
		}
	}
	
	public void beperktePogingen(boolean beperktePogingen) {
		lblPogingCounter.setText("ongelimiteerd");
	}

	public void setQuizOnderwerp(String onderwerp) {
		this.setTitle("Quiz: " + onderwerp);
	}

	public void addAntwoordKnopListener(ActionListener listener) {
		btnAntwoord.addActionListener(listener);
	}

	public void addHintKnopListener(ActionListener listener) {
		btnHint.addActionListener(listener);
	}
	
	public void addVolgendeVraagKnopListener(ActionListener listener) {
		btnVolgendeVraag.addActionListener(listener);
	}
	
	public void addClosedListener(WindowListener listener) {
		this.addWindowListener(listener);
	}

	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel,
				JOptionPane.INFORMATION_MESSAGE);
	}

}
