package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

import view.viewInterfaces.IOpdrachtAanpassingView;
import model.Leraar;
import model.OpdrachtCategorie;

@SuppressWarnings("serial")
public class OpdrachtAanpassingView extends JFrame implements IOpdrachtAanpassingView {

	private JButton btnOpslaan = new JButton("Opslaan");
	private JButton btnAddHint = new JButton("vv Voeg hint toe vv");
	private JLabel lblOpdrachtCategorie = new JLabel(
			"Opdrachtcategorie: ");
	private JLabel lblVraag = new JLabel("Vraag: ");
	private JLabel lblJuisteAntwoord = new JLabel("Oplossing: ");
	private JLabel lblHints = new JLabel("Geef hint: ");
	private JLabel lblMaxAantalPogingen = new JLabel(
			"Maximum aantal pogingen: ");
	private JLabel lblMaxAntwoordTijd = new JLabel("Maximum antwoordtijd: ");

	private JLabel lblLeraar = new JLabel("Auteur: ");
	private JLabel lblNaamLeraar = new JLabel("");
	private JTextField txtVraag, txtJuisteAntwoord, txtHints,
			txtMaxAantalPogingen, txtMaxAntwoordTijd;
	private JComboBox<OpdrachtCategorie> cbbOpdrachtCategorie = new JComboBox<>(
			OpdrachtCategorie.values());
	private JList<String> lijstHints;

	public OpdrachtAanpassingView() {

		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setTitle("Klassieke opdracht");
		
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10, 10, 0, 0);
		this.add(lblLeraar, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		lblLeraar = new JLabel();
		this.add(lblNaamLeraar, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10, 10, 0, 0);
		this.add(lblOpdrachtCategorie, c);
		c.gridx = 2;
		c.weightx = 10;
		c.anchor = GridBagConstraints.WEST;
		cbbOpdrachtCategorie.setPreferredSize(new Dimension(150, 25));
		cbbOpdrachtCategorie.setMinimumSize(new Dimension(150, 25));
		this.add(cbbOpdrachtCategorie, c);

		c = new GridBagConstraints();
		c.gridy = 2;
		c.gridx = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		this.add(lblVraag, c);
		c.gridx = 2;
		c.insets = new Insets(10, 10, 0, 10);
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		txtVraag = new JTextField();
		this.add(txtVraag, c);

		c = new GridBagConstraints();
		c.gridy = 4;
		c.gridx = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		this.add(lblJuisteAntwoord, c);
		c.gridx = 2;
		c.insets = new Insets(10, 10, 0, 10);
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		txtJuisteAntwoord = new JTextField();
		this.add(txtJuisteAntwoord, c);

		c = new GridBagConstraints();
		c.gridy = 5;
		c.gridx = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		this.add(lblHints, c);
		c.gridx = 2;
		c.insets = new Insets(10, 10, 0, 10);
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		txtHints = new JTextField();
		this.add(txtHints, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0);
		this.add(btnAddHint, c);

		c = new GridBagConstraints();
		c.gridy = 7;
		c.gridx = 2;
		c.weightx = 0.8;
		c.weighty = 10;
		c.insets = new Insets(10, 10, 0, 10);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		lijstHints = new JList<>();
		JScrollPane scroller = new JScrollPane();
		scroller.setMinimumSize(new Dimension(200, 80));
		scroller.add(lijstHints);		
		this.add(scroller, c);

		c = new GridBagConstraints();
		c.gridy = 8;
		c.gridx = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		this.add(lblMaxAantalPogingen, c);
		c.gridx = 2;
		c.weightx = 0.8;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		txtMaxAantalPogingen = new JTextField();
		txtMaxAantalPogingen.setMinimumSize(new Dimension(100, 20));
		txtMaxAantalPogingen.setPreferredSize(new Dimension(100, 20));
		this.add(txtMaxAantalPogingen, c);

		c = new GridBagConstraints();
		c.gridy = 9;
		c.gridx = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		this.add(lblMaxAntwoordTijd, c);
		c.gridx = 2;		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		txtMaxAntwoordTijd = new JTextField();
		txtMaxAntwoordTijd.setPreferredSize(new Dimension(100, 20));
		txtMaxAntwoordTijd.setMinimumSize(new Dimension(100, 20));
		this.add(txtMaxAntwoordTijd, c);

		c.gridy = 10;
		c.insets = new Insets(10, 10, 10, 0);
		c.fill = GridBagConstraints.NONE;
		this.add(btnOpslaan, c);

		this.setVisible(true);
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void setVraag(String vraag) {
		txtVraag.setText(vraag);
	}

	public String getVraag() {
		if (txtVraag.getText().equals("")) {
			throw new IllegalArgumentException("Gelieve een vraag in te geven");
		}
		return txtVraag.getText();
	}

	public void setJuisteAntwoord(String juisteAntwoord) {
		txtJuisteAntwoord.setText(juisteAntwoord);
	}

	public String getJuisteAntwoord() {
		if (txtJuisteAntwoord.getText().equals("")) {
			throw new IllegalArgumentException(
					"Gelieve een oplossing voor de vraag in te geven");
		}
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
		ListModel<String> lijst = lijstHints.getModel();
		ArrayList<String> s = new ArrayList<>();
		for (int i = 0; i < lijst.getSize(); i++) {
			s.add(lijst.getElementAt(i));
		}
		return s;
	}

	public String getHint() {
		return txtHints.getText();
	}

	public void setMaxAantalPogingen(String maxAantalPogingen) {
		txtMaxAantalPogingen.setText(maxAantalPogingen);
	}

	public int getMaxAantalPogingen() {
		try {
			Integer.parseInt(txtMaxAantalPogingen.getText());
		} catch (Exception e) {
			throw new NumberFormatException(
					"Gelieve een positief geheel getal in te geven voor maximum aantal pogingen");
		}
		return Integer.parseInt(txtMaxAantalPogingen.getText());
	}

	public void setMaxAntwoordTijd(String maxAntwoordTijd) {
		txtMaxAntwoordTijd.setText(maxAntwoordTijd);
	}

	public int getMaxAntwoordTijd() {
		try {
			Integer.parseInt(txtMaxAntwoordTijd.getText());
		} catch (Exception e) {
			throw new NumberFormatException(
					"Gelieve een positief geheel getal in te geven voor maximum antwoordtijd");
		}
		return Integer.parseInt(txtMaxAntwoordTijd.getText());
	}

	public OpdrachtCategorie getOpdrachtCategorie() {
		if (((OpdrachtCategorie) cbbOpdrachtCategorie.getSelectedItem()) == null) {
			throw new IllegalArgumentException(
					"Gelieve een opdrachtcategorie te selecteren");
		}
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

	public void setAuteur(Leraar leraar) {
		lblNaamLeraar.setText(leraar.toString());
	}

	public void disableAanpassen() {
		btnOpslaan.setEnabled(false);
		btnAddHint.setEnabled(false);
		cbbOpdrachtCategorie.setEnabled(false);
		cbbOpdrachtCategorie.setForeground(Color.black);
		txtVraag.setEditable(false);
		txtJuisteAntwoord.setEditable(false);
		txtHints.setEditable(false);
		txtMaxAantalPogingen.setEditable(false);
		txtMaxAntwoordTijd.setEditable(false);
	}

	public void toonErrorMessage(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel,
				JOptionPane.ERROR_MESSAGE);
	}

}
