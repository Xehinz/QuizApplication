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
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import view.viewInterfaces.IRapportNaDeelnameView;

@SuppressWarnings("serial")
public class RapportNaDeelnameView extends JFrame implements IRapportNaDeelnameView {

	private JLabel lblLeerling, lblQuizScore;
	private JTable tblRapport;
	private JButton btnDeelnameMenu;
	private JTextArea txtareaAntwoordDetail;

	private GridBagLayout layout;
	private GridBagConstraints constraints;

	public RapportNaDeelnameView() {
		super("Rapport");	
		setSize(1200, 400);

		layout = new GridBagLayout();
		setLayout(layout);

		lblLeerling = new JLabel("Leerling:");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		add(lblLeerling, constraints);

		lblQuizScore = new JLabel("Quiz score:");
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.insets = new Insets(10, 0, 0, 10);
		add(lblQuizScore, constraints);

		tblRapport = new JTable();
		tblRapport.setFillsViewportHeight(true);
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(0, 10, 0, 10);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 10;
		add(new JScrollPane(tblRapport), constraints);

		txtareaAntwoordDetail = new JTextArea();
		txtareaAntwoordDetail.setLineWrap(true);
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.weighty = 2;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(txtareaAntwoordDetail, constraints);

		btnDeelnameMenu = new JButton("Terug naar Deelname Menu");
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.SOUTHEAST;
		constraints.insets = new Insets(10, 0, 10, 10);
		add(btnDeelnameMenu, constraints);
		
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void setLeerling(String volledigeNaam) {
		lblLeerling.setText(String.format("Leerling: %s", volledigeNaam));
	}

	public void setQuizScore(int score) {
		lblQuizScore.setText(String.format("Totaalscore: %d/10", score));
	}

	public void setQuiz(String onderwerp) {
		setTitle(String.format("Rapport voor quiz: %s", onderwerp));
	}

	public void setRapportTableModel(TableModel rapportTableModel) {
		tblRapport.setModel(rapportTableModel);
	}

	public void addDeelnameMenuKnopListener(ActionListener listener) {
		btnDeelnameMenu.addActionListener(listener);
	}

	public void addAntwoordGeselecteerdListener(ListSelectionListener listener) {
		tblRapport.getSelectionModel().addListSelectionListener(listener);
	}

	public void setAntwoordDetail(String antwoordDetail) {
		txtareaAntwoordDetail.setText(antwoordDetail);
	}

	public int getGeselecteerdeRijIndex() {
		if (tblRapport.getSelectedRow() > -1) {
			return tblRapport.convertRowIndexToModel(tblRapport
					.getSelectedRow());
		}
		return -1;
	}

}
