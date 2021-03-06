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
import javax.swing.table.TableModel;

import view.viewInterfaces.IOverzichtScoresQuizzenView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
@SuppressWarnings("serial")
public class OverzichtScoresQuizzenView extends JFrame implements IOverzichtScoresQuizzenView {
	
	private JLabel lblQuizzen;
	private JTable tblQuizzen;
	private JButton btnDetail;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public OverzichtScoresQuizzenView() {
		super("Overzicht Scores: Quizzen");	
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		
		layout = new GridBagLayout();
		this.setLayout(layout);		
		
		lblQuizzen = new JLabel("Quizzen:");		
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 10, 0, 0);
		this.add(lblQuizzen, constraints);
		
		tblQuizzen = new JTable();
		tblQuizzen.setAutoCreateRowSorter(true);
		tblQuizzen.setFillsViewportHeight(true);		
		
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(0, 10, 10, 10);
		constraints.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(tblQuizzen), constraints);		
		
		btnDetail = new JButton("Toon deelnemers");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0, 0, 10, 10);
		this.add(btnDetail, constraints);	
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setTableModel(TableModel quizScoreTableModel, int... kolomBreedtes) {
		tblQuizzen.setModel(quizScoreTableModel);
		for (int i = 0; i < tblQuizzen.getColumnCount() && i < kolomBreedtes.length; i++) {
			tblQuizzen.getColumnModel().getColumn(i).setPreferredWidth(kolomBreedtes[i] * 1000);
		}		
	}
	
	public void addDetailKnopListener(ActionListener listener) {
		btnDetail.addActionListener(listener);
	}
	
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public int getGeselecteerdeRijIndex() {
		if (tblQuizzen.getSelectedRow() > -1) {
		return tblQuizzen.convertRowIndexToModel(tblQuizzen.getSelectedRow());
		}
		return -1;
	}

}
