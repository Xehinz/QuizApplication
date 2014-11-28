package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Opdracht;
import model.OpdrachtCategorie;

public class OpdrachtAanpassingView extends JFrame {

	private JButton btnOpslaan = new JButton("Opslaan");
	private JButton btnAddHint = new JButton("vv Voeg hint toe vv");
	private JLabel lblOpdrachtCategorie = new JLabel(
			"Selecteer opdrachtcategorie: ");
	private JLabel lblVraag = new JLabel("Vraag: ");
	private JLabel lblJuisteAntwoord = new JLabel("Oplossing: ");
	private JLabel lblHints = new JLabel("Geef hint: ");
	private JLabel lblMaxAantalPogingen = new JLabel(
			"Maximum aantal pogingen: ");
	private JLabel lblMaxAntwoordTijd = new JLabel("Maximum antwoordtijd: ");
	
	private JLabel lblInJuisteVolgorde = new JLabel("In juiste volgorde: ");
	private JLabel lblMinimumAantalTrefwoorden = new JLabel(
			"Minimum aantal trefwoorden: ");
	private JTextField txtVraag, txtJuisteAntwoord, txtHints,
			txtMaxAantalPogingen, txtMaxAntwoordTijd,
			 txtMinimumAantalTrefwoorden;
	private JCheckBox chbInJuisteVolgorde;
	private JComboBox<OpdrachtCategorie> cbbOpdrachtCategorie = new JComboBox<>(
			OpdrachtCategorie.values());
	private JList lijstHints;
	protected JPanel bovenPanel, middenPanel, onderPanel, grootPanel;

	public OpdrachtAanpassingView() {

		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);

		grootPanel = new JPanel();
		grootPanel.setLayout(new GridBagLayout());
		GridBagConstraints b = new GridBagConstraints();
		
		bovenPanel = new JPanel();
		bovenPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10, 10, 0, 0);
		bovenPanel.add(lblOpdrachtCategorie, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		bovenPanel.add(cbbOpdrachtCategorie, c);

		c = new GridBagConstraints();
		c.gridy = 2;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		bovenPanel.add(lblVraag, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtVraag = new JTextField();
		txtVraag.setPreferredSize(new Dimension(600, 25));
		bovenPanel.add(txtVraag, c);

		b.gridx = 1;
		b.gridy = 1;
		b.insets = new Insets (10, 10, 10, 10);
		b.fill = GridBagConstraints.BOTH;
		grootPanel.add(bovenPanel, b);
		
		onderPanel = new JPanel();
		onderPanel.setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		onderPanel.add(lblJuisteAntwoord, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtJuisteAntwoord = new JTextField();
		txtJuisteAntwoord.setPreferredSize(new Dimension(600, 25));
		onderPanel.add(txtJuisteAntwoord, c);

		c = new GridBagConstraints();
		c.gridy = 2;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		onderPanel.add(lblHints, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtHints = new JTextField();
		txtHints.setPreferredSize(new Dimension(600, 25));
		onderPanel.add(txtHints, c);
		c.gridy = 3;
		onderPanel.add(btnAddHint, c);

		c = new GridBagConstraints();
		c.gridy = 4;
		c.gridx = 2;
		c.weightx = 0.8;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		lijstHints = new JList();
		lijstHints.setPreferredSize(new Dimension(600, 75));
		onderPanel.add(lijstHints, c);

		c = new GridBagConstraints();
		c.gridy = 5;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		onderPanel.add(lblMaxAantalPogingen, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMaxAantalPogingen = new JTextField();
		txtMaxAantalPogingen.setPreferredSize(new Dimension(100, 25));
		onderPanel.add(txtMaxAantalPogingen, c);

		c = new GridBagConstraints();
		c.gridy = 6;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		onderPanel.add(lblMaxAntwoordTijd, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMaxAntwoordTijd = new JTextField();
		txtMaxAntwoordTijd.setPreferredSize(new Dimension(100, 25));
		onderPanel.add(txtMaxAntwoordTijd, c);

		c.gridy = 7;
		c.insets = new Insets (10, 10, 10, 10);
		onderPanel.add(btnOpslaan, c);
		
		b.gridy = 3;
		grootPanel.add(onderPanel, b);
		
		this.add(grootPanel);
		this.setVisible(true);
	}

	public void setVraag(String vraag) {
		txtVraag.setText(vraag);
	}

	public String getVraag() {
		return txtVraag.getText();
	}

	public void setJuisteAntwoord(String juisteAntwoord) {
		txtJuisteAntwoord.setText(juisteAntwoord);
	}

	public String getJuisteAntwoord() {
		return txtJuisteAntwoord.getText();
	}

	public void setHints(ArrayList<String> hints) {
		DefaultListModel<String> model = new DefaultListModel<>();
		for (String S : hints) {
			model.addElement(S);
		}
		lijstHints.setModel(model);
	}

	public ArrayList<String> getHints() {
		return (ArrayList<String>) lijstHints.getModel();
	}

	public String getHint() {
		return txtHints.getText();
	}

	public void setMaxAantalPogingen(String maxAantalPogingen) {
		txtMaxAantalPogingen.setText(maxAantalPogingen);
	}

	public int getMaxAantalPogingen() {
		return Integer.parseInt(txtMaxAantalPogingen.getText());
	}

	public void setMaxAntwoordTijd(String maxAntwoordTijd) {
		txtMaxAntwoordTijd.setText(maxAntwoordTijd);
	}

	public int getMaxAntwoordTijd() {
		return Integer.parseInt(txtMaxAntwoordTijd.getText());
	}

	public OpdrachtCategorie getOpdrachtCategorie() {
		return (OpdrachtCategorie) cbbOpdrachtCategorie.getSelectedItem();
	}

	public void setOpdrachtCategorie(OpdrachtCategorie oc) {
		cbbOpdrachtCategorie.setSelectedItem(oc);
	}
	
	public void NieuweHintKnopActionListener(ActionListener listener) {
		btnAddHint.addActionListener(listener);
	}

	public void OpslaanKnopActionListener(ActionListener listener) {
		btnOpslaan.addActionListener(listener);
	}
	
	public static void main(String[] args) {
		OpdrachtAanpassingView oav = new OpdrachtReproductieBeheerView();
		//OpdrachtAanpassingView obv = new OpdrachtMeerkeuzeBeheerView();
		OpdrachtAanpassingView ocv = new OpdrachtOpsommingBeheerView();
		OpdrachtAanpassingView odv = new OpdrachtAanpassingView();
		
	}

}
