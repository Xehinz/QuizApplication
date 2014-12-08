package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import model.Leerling;
import view.viewInterfaces.IOverzichtScoresQuizzenView;
import view.viewInterfaces.IQuizScoresRapportView;
import controller.QuizScoresRapportController;

@SuppressWarnings("serial")
public class QuizScoresRapportView extends JFrame implements
		IQuizScoresRapportView {

	private GridBagLayout layout ;
	private GridBagConstraints constraints;
	
	private JLabel lblLeerling;
	private JLabel lblDeelnamenRapport;
	private JLabel lblQuizzenRapport;
	
	private JTable tblDeelnamen;
	private JTable tblQuizzen;
	
	private JButton btnToonQuizzen;
	
	private Leerling leerling;
	
	
	public QuizScoresRapportView() {
		super("Deelnamen en quizrapporten");
		this.setSize(800, 600);
		
		layout = new GridBagLayout();
		this.setLayout(layout);

		
		lblDeelnamenRapport = new JLabel("Deelnamen Rapport: ");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblDeelnamenRapport, constraints);
		
		tblDeelnamen = new JTable();			
		tblDeelnamen.setAutoCreateRowSorter(true);
		tblDeelnamen.setFillsViewportHeight(true);
		
		

		
		
		
		constraints = new GridBagConstraints();
		constraints.gridy = 10;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(0, 10, 10, 10);
		constraints.fill = GridBagConstraints.BOTH;
		
		JScrollPane scroller1 = new JScrollPane(tblDeelnamen);
		this.add(scroller1, constraints);	

		
		lblQuizzenRapport = new JLabel("Quiz Scores Rapport: ");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 10, -10, 0);
		constraints.gridy = 400;
		constraints.gridx = 0;		
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblQuizzenRapport, constraints);		
			
		tblQuizzen = new JTable();			
		tblQuizzen.setAutoCreateRowSorter(true);
		tblQuizzen.setFillsViewportHeight(true);	
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 10, 10, 10);
		constraints.gridy = 410;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		
		JScrollPane scroller2 = new JScrollPane(tblQuizzen);
		this.add(scroller2, constraints);	

	}
	
	@Override
	public void setTableModel(TableModel quizDeelnameTableModel) {
		
		tblDeelnamen.setModel(quizDeelnameTableModel);
		tblDeelnamen.getColumnModel().getColumn(0).setPreferredWidth(200);	
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tblDeelnamen.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
	}
	
	public void setTableModel1(TableModel quizScoreTableModel) {
		
		tblQuizzen.setModel(quizScoreTableModel);
		tblQuizzen.getColumnModel().getColumn(0).setPreferredWidth(250);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tblQuizzen.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
	}
	
	public void setLeerling(Leerling leerling) {
		this.leerling = leerling;
		this.lblDeelnamenRapport.setText(String.format("%s%s",this.lblDeelnamenRapport.getText(), this.leerling.getLeerlingFamilienaam() + " " + this.leerling.getLeerlingVoornaam()));
	}
	
	
	@Override
	public void addDetailKnopListener(ActionListener listener) {
		btnToonQuizzen.addActionListener(listener);

	}

	@Override
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public int getGeselecteerdeRij() {
		return tblDeelnamen.convertRowIndexToModel(tblDeelnamen.getSelectedRow());
	}
	
	public void addSelectionListener(ListSelectionListener listener) {
		tblDeelnamen.getSelectionModel().addListSelectionListener(listener);
	}

}
