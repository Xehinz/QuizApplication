package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import persistency.DBHandler;
import controller.OpdrachtBeheerController;
import controller.OpstartController;
import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;

public class OpdrachtBeheerView extends JFrame {

	private JButton btnNieuweKlassieke = new JButton(
			"Nieuwe klassieke opdracht");
	private JButton btnNieuweMeerkeuze = new JButton(
			"Nieuwe meerkeuze opdracht");
	private JButton btnNieuweOpsomming = new JButton(
			"Nieuwe opsomming opdracht");
	private JButton btnNieuweReproductie = new JButton(
			"Nieuwe reproductie opdracht");
	private JButton btnPasOpdrachtAan = new JButton("Pas opdracht aan");
	private JButton btnVerwijderOpdracht = new JButton("Verwijder opdracht");
	private JButton btnBekijkDetails = new JButton("Bekijk details");

	private JList<Opdracht> lijstOpdrachten = new JList<>();
	private JComboBox<String> selecteerCategorie = new JComboBox<>();

	public OpdrachtBeheerView() {
		super("Opdrachtenbeheer");

		this.setSize(800, 500);
		selecteerCategorie.setSelectedItem(null);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		selecteerCategorie.setPreferredSize(new Dimension(175, 25));
		selecteerCategorie.setMinimumSize(new Dimension(175, 25));

		JPanel boxPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
		boxPanel.setLayout(boxLayout);

		boxPanel.add(btnNieuweKlassieke);
		boxPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanel.add(btnNieuweMeerkeuze);
		boxPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanel.add(btnNieuweOpsomming);
		boxPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanel.add(btnNieuweReproductie);
		boxPanel.add(Box.createRigidArea(new Dimension(10, 150)));
		boxPanel.add(btnPasOpdrachtAan);
		boxPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanel.add(btnVerwijderOpdracht);
		boxPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanel.add(btnBekijkDetails);

		c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(10, 10, 0, 10);
		newPanel.add(boxPanel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 2;
		c.weighty = 0.5;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		newPanel.add(selecteerCategorie, c);

		c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 10;
		c.weighty = 10;
		c.insets = new Insets(5, 0, 10, 10);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		newPanel.add(new JScrollPane(lijstOpdrachten), c);

		this.add(newPanel);
	}

	public void setOpdrachten(ArrayList<Opdracht> opdrachten) {
		DefaultListModel<Opdracht> model = new DefaultListModel<Opdracht>();
		for (Opdracht O : opdrachten) {
			model.addElement(O);
		}
		lijstOpdrachten.setModel(model);
	}
	
	public void setOpdrachtCategorie(){
		selecteerCategorie.addItem("Alle categorieën");
		for (OpdrachtCategorie a : OpdrachtCategorie.values()){
			selecteerCategorie.addItem(a.toString());			
		}
	}

	public Opdracht getGeselecteerdeOpdracht() {
		if (lijstOpdrachten.getSelectedValue() == null){
			throw new IllegalArgumentException(
					"Gelieve een opdracht te selecteren");
		}
		return (Opdracht) lijstOpdrachten.getSelectedValue();
	}

	public String getOpdrachtCategorie() {
		return (String) selecteerCategorie.getSelectedItem();
	}

	public void setListCellRenderer(ListCellRenderer<Opdracht> LCR) {
		lijstOpdrachten.setCellRenderer(LCR);
	}

	public void addNieuweKlassiekeKnopActionListener(ActionListener listener) {
		btnNieuweKlassieke.addActionListener(listener);
	}

	public void addNieuweMeerkeuzeKnopActionListener(ActionListener listener) {
		btnNieuweMeerkeuze.addActionListener(listener);
	}

	public void addNieuweOpsommingKnopActionListener(ActionListener listener) {
		btnNieuweOpsomming.addActionListener(listener);
	}

	public void addNieuweReproductieKnopActionListener(ActionListener listener) {
		btnNieuweReproductie.addActionListener(listener);
	}

	public void addPasOpdrachtAanKnopActionListener(ActionListener listener) {
		btnPasOpdrachtAan.addActionListener(listener);
	}

	public void addVerwijderOpdrachtKnopActionListener(ActionListener listener) {
		btnVerwijderOpdracht.addActionListener(listener);
	}

	public void addBekijkDetailsKnopActionListener(ActionListener listener) {
		btnBekijkDetails.addActionListener(listener);
	}

	public void addSelecteerCategorieActionlistener(ActionListener listener) {
		selecteerCategorie.addActionListener(listener);
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		lijstOpdrachten.addListSelectionListener(listener);
	}

	public void disableAanpassen(boolean b) {
		btnPasOpdrachtAan.setEnabled(b);
	}
	
	public void toonErrorMessage(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel,
				JOptionPane.ERROR_MESSAGE);
	}
	
}
