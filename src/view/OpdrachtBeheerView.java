package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import persistency.DBHandler;
import controller.OpdrachtBeheerController;
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
	private JComboBox<OpdrachtCategorie> selecteerCategorie = new JComboBox<>(
			OpdrachtCategorie.values());

	public OpdrachtBeheerView() {
		super("Opdrachtenbeheer");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		this.setVisible(true);
	}

	public void setOpdrachten(ArrayList<Opdracht> opdrachten) {
		DefaultListModel<Opdracht> model = new DefaultListModel<>();
		for (Opdracht O : opdrachten) {
			model.addElement(O);
		}
		lijstOpdrachten.setModel(model);
		lijstOpdrachten.setSelectedIndex(0);
	}

	public Opdracht getGeselecteerdeOpdracht() {
		return (Opdracht) lijstOpdrachten.getSelectedValue();
	}
	
	public OpdrachtCategorie getOpdrachtCategorie(){
		return (OpdrachtCategorie) selecteerCategorie.getSelectedItem();
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
	
	public void addSelecteerCategorieActionlistener(ActionListener listener){
		selecteerCategorie.addActionListener(listener);
	}

}