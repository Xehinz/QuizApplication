package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import view.viewInterfaces.IOverzichtScoresLeerlingenView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
@SuppressWarnings("serial")
public class OverzichtScoresLeerlingenView extends JFrame implements IOverzichtScoresLeerlingenView {
	
	private JLabel lblDeelnames;
	private JTable tblDeelnames;
	private JButton btnDetail;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public OverzichtScoresLeerlingenView() {
		super("Overzicht Scores: Leerlingen");	
		this.setSize(800, 400);		
		
		layout = new GridBagLayout();
		this.setLayout(layout);		
		
		lblDeelnames = new JLabel("Leerlingen:");		
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 10, 0, 0);
		this.add(lblDeelnames, constraints);
		
		tblDeelnames = new JTable();
		tblDeelnames.setAutoCreateRowSorter(true);
		tblDeelnames.setFillsViewportHeight(true);	
		
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(0, 10, 10, 10);
		constraints.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(tblDeelnames), constraints);		
		
		btnDetail = new JButton("Toon antwoorden");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0, 0, 10, 10);
		this.add(btnDetail, constraints);	
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setTableModel(TableModel leerlingScoreTableModel, int... gecentreerdeKolommen) {
		tblDeelnames.setModel(leerlingScoreTableModel);	
		DefaultTableCellRenderer middleRenderer = new DefaultTableCellRenderer();
		middleRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for (int kolom : gecentreerdeKolommen) {
			tblDeelnames.getColumnModel().getColumn(kolom).setCellRenderer(middleRenderer);
		}
	}
	
	public void setQuizOnderwerp(String onderwerp) {
		lblDeelnames.setText(String.format("Deelnames van leerlingen aan quiz '%s':", onderwerp));
		this.setTitle(String.format("Overzicht Scores voor Quiz: '%s'", onderwerp));
	}
	
	public void addDetailKnopListener(ActionListener listener) {
		btnDetail.addActionListener(listener);
	}
	
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public int getGeselecteerdeRijIndex() {
		if (tblDeelnames.getSelectedRow() > -1) {
		return tblDeelnames.convertRowIndexToModel(tblDeelnames.getSelectedRow());
		}
		return -1;
	}

}
