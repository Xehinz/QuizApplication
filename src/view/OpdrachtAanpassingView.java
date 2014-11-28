package view;

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
	private JLabel lblMogelijkeAntwoordenMeerkeuze = new JLabel(
			"Mogelijke antwoorden: ");
	private JLabel lblInJuisteVolgorde = new JLabel("In juiste volgorde: ");
	private JLabel lblMinimumAantalTrefwoorden = new JLabel(
			"Minimum aantal trefwoorden: ");
	private JTextField txtVraag, txtJuisteAntwoord, txtHints,
			txtMaxAantalPogingen, txtMaxAntwoordTijd,
			txtMogelijkeAntwoordenMeerkeuze, txtMinimumAantalTrefwoorden;
	private JCheckBox chbInJuisteVolgorde;
	private JComboBox<OpdrachtCategorie> cbbOpdrachtCategorie = new JComboBox<>(
			OpdrachtCategorie.values());
	private JList lijstHints;

	public OpdrachtAanpassingView() {

		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10, 10, 0, 0);
		newPanel.add(lblOpdrachtCategorie, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		newPanel.add(cbbOpdrachtCategorie, c);

		c = new GridBagConstraints();
		c.gridy = 2;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblVraag, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtVraag = new JTextField();
		txtVraag.setPreferredSize(new Dimension(600, 25));
		newPanel.add(txtVraag, c);

		c = new GridBagConstraints();
		c.gridy = 3;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblMogelijkeAntwoordenMeerkeuze, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMogelijkeAntwoordenMeerkeuze = new JTextField();
		txtMogelijkeAntwoordenMeerkeuze.setPreferredSize(new Dimension(600, 75));
		newPanel.add(txtMogelijkeAntwoordenMeerkeuze, c);
		
		c = new GridBagConstraints();
		c.gridy = 3;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblInJuisteVolgorde, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		chbInJuisteVolgorde = new JCheckBox();
		newPanel.add(chbInJuisteVolgorde, c);
		
		c = new GridBagConstraints();
		c.gridy = 3;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblMinimumAantalTrefwoorden, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMinimumAantalTrefwoorden = new JTextField();
		txtMinimumAantalTrefwoorden.setPreferredSize(new Dimension(200, 25));
		newPanel.add(txtMinimumAantalTrefwoorden, c);
		
		c = new GridBagConstraints();
		c.gridy = 4;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblJuisteAntwoord, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtJuisteAntwoord = new JTextField();
		txtJuisteAntwoord.setPreferredSize(new Dimension(600, 25));
		newPanel.add(txtJuisteAntwoord, c);

		c = new GridBagConstraints();
		c.gridy = 5;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblHints, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtHints = new JTextField();
		txtHints.setPreferredSize(new Dimension(600, 25));
		newPanel.add(txtHints, c);
		c.gridy = 6;
		newPanel.add(btnAddHint, c);

		c = new GridBagConstraints();
		c.gridy = 7;
		c.gridx = 2;
		c.weightx = 0.8;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		lijstHints = new JList();
		lijstHints.setPreferredSize(new Dimension(600, 75));
		newPanel.add(lijstHints, c);

		c = new GridBagConstraints();
		c.gridy = 8;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblMaxAantalPogingen, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMaxAantalPogingen = new JTextField();
		txtMaxAantalPogingen.setPreferredSize(new Dimension(100, 25));
		newPanel.add(txtMaxAantalPogingen, c);

		c = new GridBagConstraints();
		c.gridy = 9;
		c.gridx = 1;
		c.weightx = 0.2;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		newPanel.add(lblMaxAntwoordTijd, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		txtMaxAntwoordTijd = new JTextField();
		txtMaxAntwoordTijd.setPreferredSize(new Dimension(100, 25));
		newPanel.add(txtMaxAntwoordTijd, c);

		c.gridy = 10;
		newPanel.add(btnOpslaan, c);

		this.add(newPanel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		OpdrachtAanpassingView OAV = new OpdrachtAanpassingView();
		OAV.setOpsomming();
		OAV.show();
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

	public void setKlassieke() {
		this.setTitle("Klassieke opdracht");
		this.lblMinimumAantalTrefwoorden.setVisible(false);
		this.lblMogelijkeAntwoordenMeerkeuze.setVisible(false);
		this.lblInJuisteVolgorde.setVisible(false);
		this.txtMinimumAantalTrefwoorden.setVisible(false);
		this.txtMogelijkeAntwoordenMeerkeuze.setVisible(false);
		this.chbInJuisteVolgorde.setVisible(false);
	}

	public void setMeerkeuze() {
		this.setTitle("Meerkeuze opdracht");
		this.lblMinimumAantalTrefwoorden.setVisible(false);
		this.lblInJuisteVolgorde.setVisible(false);
		this.txtMinimumAantalTrefwoorden.setVisible(false);
		this.chbInJuisteVolgorde.setVisible(false);
	}

	public void setOpsomming() {
		this.setTitle("Opsomming opdracht");
		this.txtMinimumAantalTrefwoorden.setVisible(false);
		this.lblMinimumAantalTrefwoorden.setVisible(false);
		this.lblMogelijkeAntwoordenMeerkeuze.setVisible(false);
		this.txtMogelijkeAntwoordenMeerkeuze.setVisible(false);
	}

	public void setReproductie() {
		this.setTitle("Reproductie opdracht");
		this.lblMogelijkeAntwoordenMeerkeuze.setVisible(false);
		this.lblInJuisteVolgorde.setVisible(false);
		this.txtMogelijkeAntwoordenMeerkeuze.setVisible(false);
		this.chbInJuisteVolgorde.setVisible(false);		
	}

	public void setBekijkDetails(){
		this.btnOpslaan.setVisible(false);
	}
	
	public void NieuweHintKnopActionListener(ActionListener listener) {
		btnAddHint.addActionListener(listener);
	}

	public void OpslaanKnopActionListener(ActionListener listener) {
		btnOpslaan.addActionListener(listener);
	}

}
