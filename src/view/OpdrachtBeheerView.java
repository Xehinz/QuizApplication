package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;

import view.viewInterfaces.IOpdrachtBeheerView;
import model.Opdracht;
import model.OpdrachtCategorie;

@SuppressWarnings("serial")
public class OpdrachtBeheerView extends JFrame implements IOpdrachtBeheerView {

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

	private JList<Opdracht> lijstOpdrachten = new JList<Opdracht>();
	private JComboBox<String> selecteerCategorie = new JComboBox<>();

	public OpdrachtBeheerView() {
		super("Opdrachtenbeheer");

		this.setSize(800, 500);
		selecteerCategorie.setSelectedItem(null);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		selecteerCategorie.setPreferredSize(new Dimension(175, 25));
		selecteerCategorie.setMinimumSize(new Dimension(175, 25));

		JPanel boxPanelBoven = new JPanel();
		BoxLayout boxLayout = new BoxLayout(boxPanelBoven, BoxLayout.Y_AXIS);
		boxPanelBoven.setLayout(boxLayout);

		boxPanelBoven.add(btnNieuweKlassieke);
		boxPanelBoven.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanelBoven.add(btnNieuweMeerkeuze);
		boxPanelBoven.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanelBoven.add(btnNieuweOpsomming);
		boxPanelBoven.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanelBoven.add(btnNieuweReproductie);
		
		JPanel boxPanelOnder = new JPanel();
		BoxLayout boxLayoutOnder = new BoxLayout(boxPanelOnder, BoxLayout.Y_AXIS);
		boxPanelOnder.setLayout(boxLayoutOnder);
		boxPanelOnder.add(btnPasOpdrachtAan);
		boxPanelOnder.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanelOnder.add(btnVerwijderOpdracht);
		boxPanelOnder.add(Box.createRigidArea(new Dimension(10, 10)));
		boxPanelOnder.add(btnBekijkDetails);

		c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(boxPanelBoven, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 2;
		c.weighty = 0.5;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		this.add(selecteerCategorie, c);
		
		c = new GridBagConstraints();
		c.gridy = 3;
		c.gridx = 0;
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.insets = new Insets(0, 10, 10, 10);
		this.add(boxPanelOnder, c);

		c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 10;
		c.weighty = 10;
		c.gridheight = 3;
		c.insets = new Insets(5, 0, 10, 10);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		this.add(new JScrollPane(lijstOpdrachten), c);

		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setOpdrachtCategorie(){
		selecteerCategorie.addItem("Alle categorieën");
		for (OpdrachtCategorie a : OpdrachtCategorie.values()){
			selecteerCategorie.addItem(a.toString());			
		}
	}
	
	public int getGeselecteerdeRij() {
		return lijstOpdrachten.getSelectedIndex();
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
	
	public void setListModel(ListModel<Opdracht> listModel) {		
		lijstOpdrachten.setModel(listModel);
	}
	
}
