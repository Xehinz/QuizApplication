package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import view.viewInterfaces.IOverzichtScoresQuizzenView;
import view.viewInterfaces.IQuizScoresRapportView;

@SuppressWarnings("serial")
public class QuizScoresRapportView extends JFrame implements
		IQuizScoresRapportView {

	private GridBagLayout layout ;
	private GridBagConstraints constraints;
	
	private JLabel lblDeelnamenRapport;
	private JLabel lblQuizzenRapport;
	
	private JTable tblDeelnamen;
	private JTable tblQuizzen;
	
	private JButton btnToonQuizzen;
	
	public QuizScoresRapportView() {
		super("Deelnamen en quizrapporten");
		this.setSize(800, 600);
		
		layout = new GridBagLayout();
		this.setLayout(layout);

		
		lblDeelnamenRapport = new JLabel("Deelnamen Rapport");
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
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		JScrollPane scroller = new JScrollPane(tblQuizzen);
		this.add(scroller, constraints);	

		
		lblQuizzenRapport = new JLabel("Quizzen Rapport");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 1;
		constraints.gridx = 0;		
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(lblQuizzenRapport, constraints);		
			
//		tblQuizzen = new JTable();			
//		tblQuizzen.setAutoCreateRowSorter(true);
//		tblQuizzen.setFillsViewportHeight(true);	
//		
//		constraints = new GridBagConstraints();
//		constraints.insets = new Insets(10, 10, 10, 10);
//		constraints.gridy = 2;
//		constraints.gridx = 0;
//		constraints.weighty = 10;
//		constraints.fill = GridBagConstraints.BOTH;
//		constraints.anchor = GridBagConstraints.NORTHWEST;
//		
//		JScrollPane scroller1 = new JScrollPane(tblQuizzen);
//		this.add(scroller1, constraints);	

	}
	
	@Override
	public void setTableModel(TableModel quizScoreTableModel) {
		tblDeelnamen.setModel(quizScoreTableModel);
		tblQuizzen.setModel(quizScoreTableModel);
		
	}

	@Override
	public void addDetailKnopListener(ActionListener listener) {
		btnToonQuizzen.addActionListener(listener);

	}

	@Override
	public void toonInformationDialog(String boodschap, String titel) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getGeselecteerdeRij() {
		return tblDeelnamen.convertRowIndexToModel(tblDeelnamen.getSelectedRow());
	}

}
