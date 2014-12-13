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

import view.viewInterfaces.IQuizDeelnameView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
@SuppressWarnings("serial")
public class QuizDeelnameView extends JFrame implements IQuizDeelnameView {
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	private JLabel lblLeerling, lblQuizzen;
	private JTable tblQuizzen;
	private JButton btnDeelnemen;
	
	public QuizDeelnameView() {
		super("Deelnemen aan Quiz");
		this.setSize(1200, 400);
		this.setLocationRelativeTo(null);
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		lblLeerling = new JLabel("Leerling: ");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblLeerling, constraints);
		
		lblQuizzen = new JLabel("Quizzen:");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 1;
		constraints.gridx = 0;		
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblQuizzen, constraints);		
			
		tblQuizzen = new JTable();			
		tblQuizzen.setAutoCreateRowSorter(true);
		tblQuizzen.setFillsViewportHeight(true);		
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		JScrollPane scroller = new JScrollPane(tblQuizzen);
		this.add(scroller, constraints);	
		
		btnDeelnemen = new JButton("Deelnemen");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 0, 10, 10);
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.EAST;				
		this.add(btnDeelnemen, constraints);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setLeerling(String volledigeNaam) {
		this.lblLeerling.setText(String.format("Leerling: %s", volledigeNaam));
	}
	
	public int getGeselecteerdeRij() {		
		return tblQuizzen.convertRowIndexToModel(tblQuizzen.getSelectedRow());
	}
	
	public void setTableModel(TableModel tableModel) {
		tblQuizzen.setModel(tableModel);
	}
	
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void addDeelneemKnopListener(ActionListener listener) {
		btnDeelnemen.addActionListener(listener);
	}
	
}
