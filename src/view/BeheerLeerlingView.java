package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import model.Leerling;
import util.tableModels.BeheerLeerlingTableModel;

/**
 * @author Johan Boogers
 *
 */

@SuppressWarnings("serial")
public class BeheerLeerlingView extends JFrame  {

	private JPanel aKnoppenVeld = new JPanel();
	private GridBagLayout aGBLayout = new GridBagLayout();
	private GridBagConstraints aGBConstraints;
	private JButton btnNieuweLeerling, btnAanpassenLeerling, btnVerwijderLeerling;
	//volgende objecten hangen in cascade aan elkaar vast
	private BeheerLeerlingTableModel aTabelModel = new BeheerLeerlingTableModel();
	private JTable aLeerlingTabel = new JTable(aTabelModel);
	private JScrollPane aTabelVeld = new JScrollPane(aLeerlingTabel);

	/**
	 * Default constructor
	 */
	public BeheerLeerlingView() {
		
		//Set Window
		super("Beheer Leerlingen");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		//Set Layout
		this.setLayout(aGBLayout); //layout @JFrame
		//Set JTable
		setKolomBreedte(aLeerlingTabel);
		aLeerlingTabel.setFillsViewportHeight(true);

		//Set JScrollPane
		aTabelVeld.setPreferredSize(new Dimension(800, 700));
		aGBConstraints = new GridBagConstraints();
		aGBConstraints.insets = new Insets(10, 10, 10, 10);
		aGBConstraints.gridy = 0;
		aGBConstraints.gridx = 0;
		aGBConstraints.fill = GridBagConstraints.BOTH;
		aGBConstraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(aTabelVeld, aGBConstraints); //JScrollPane @JFrame
 
		//Set JPanel
		aKnoppenVeld.setLayout(aGBLayout); //layout @JPanel
		
		//Set buttons in JPanel
		btnNieuweLeerling = new JButton("Nieuwe Leerling");
		aGBConstraints = new GridBagConstraints();
		aGBConstraints.insets = new Insets(10, 10, 10, 10);
		aGBConstraints.gridy = 0;
		aGBConstraints.gridx = 0;
		aGBConstraints.anchor = GridBagConstraints.CENTER;
		aKnoppenVeld.add(btnNieuweLeerling, aGBConstraints);
		
		btnAanpassenLeerling = new JButton("Leerling Aanpassen");
		aGBConstraints = new GridBagConstraints();
		aGBConstraints.insets = new Insets(10, 10, 10, 10);
		aGBConstraints.gridy = 1;
		aGBConstraints.gridx = 0;
		aGBConstraints.anchor = GridBagConstraints.CENTER;
		aKnoppenVeld.add(btnAanpassenLeerling, aGBConstraints);
		
		btnVerwijderLeerling = new JButton("Leerling Verwijderen");
		aGBConstraints = new GridBagConstraints();
		aGBConstraints.insets = new Insets(10, 10, 10, 10);
		aGBConstraints.gridy = 2;
		aGBConstraints.gridx = 0;
		aGBConstraints.anchor = GridBagConstraints.CENTER;
		aKnoppenVeld.add(btnVerwijderLeerling, aGBConstraints);
		
		//Add knoppenVeld
		aGBConstraints = new GridBagConstraints();
		aGBConstraints.insets = new Insets(10, 10, 10, 10);
		aGBConstraints.gridy = 0;
		aGBConstraints.gridx = 1;		
		aGBConstraints.fill = GridBagConstraints.BOTH;
		aGBConstraints.anchor = GridBagConstraints.NORTHEAST;
		this.add (aKnoppenVeld, aGBConstraints); //JPanel @JFrame
	}

	/**
	 * Toevoegen van de verschillende listeners voor de buttons
	 * @param listener
	 */
	public void addNieuweLeerlingListener(ActionListener listener) {
		btnNieuweLeerling.addActionListener(listener);
	}
	
	public void addAanpassenLeerlingListener(ActionListener listener) {
		btnAanpassenLeerling.addActionListener(listener);
	}
	
	public void addVerwijderLeerlingListener(ActionListener listener) {
		btnVerwijderLeerling.addActionListener(listener);
	}
	
	/**
	 * Opvullen van de JTable met een collectie van leerlingen
	 * @param leerlingen
	 */
	public void setLeerlingen(Collection<Leerling> leerlingen) {
		aTabelModel.setLeerlingen(leerlingen);		
	}
	
	/**
	 * Ophalen van de geselecteerde leerling uit de JTable
	 * @return Leerling
	 */
	public Leerling getGeselecteerdeLeerling() {
		return aTabelModel.getLeerling(aLeerlingTabel.getSelectedRow());
	}
	
	/**
	 * Instellen van de breedten van de verschillende kolommen van de JTable
	 * @param aJTable
	 */
	private void setKolomBreedte(JTable aJTable) {
		aJTable.getColumnModel().getColumn(0).setPreferredWidth(50);  //ID
		aJTable.getColumnModel().getColumn(1).setPreferredWidth(140); //Voornaam
		aJTable.getColumnModel().getColumn(2).setPreferredWidth(190); //Familienaam
		aJTable.getColumnModel().getColumn(3).setPreferredWidth(50);  //Leerjaar
	}

	/**
	 * Een message tonen op het scherm (JOptionPane)
	 * @param boodschap
	 * @param titel
	 */
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}	
	
}
