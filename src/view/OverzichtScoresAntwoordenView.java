package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import view.viewInterfaces.IOverzichtScoresAntwoordenView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
@SuppressWarnings("serial")
public class OverzichtScoresAntwoordenView extends JFrame implements IOverzichtScoresAntwoordenView {
	
	private JLabel lblVragen;
	private JTable tblAntwoorden;
	private JLabel lblJuisteAntwoord, lblGemiddeldeScore;
	private JScrollPane scroller;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public OverzichtScoresAntwoordenView() {
		super("Antwoorden van Leerling aan Quiz");
		this.setSize(1200, 350);
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		lblVragen = new JLabel("Vragen:");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 10, 0, 0);
		this.add(lblVragen, constraints);		

		tblAntwoorden = new JTable();
		tblAntwoorden.setFillsViewportHeight(true);		
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weighty = 10;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 0, 10);
		scroller = new JScrollPane(tblAntwoorden);
		this.add(scroller, constraints);	
		
		lblJuisteAntwoord = new JLabel("Juiste antwoord: ");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		this.add(lblJuisteAntwoord, constraints);
		
		lblGemiddeldeScore = new JLabel("Gemiddelde score: ");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 10, 10, 10);
		this.add(lblGemiddeldeScore, constraints);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
	public void setAntwoordTableModel(TableModel antwoordTableModel) {
		tblAntwoorden.setModel(antwoordTableModel);
		tblAntwoorden.getColumnModel().getColumn(0).setPreferredWidth(300);
		tblAntwoorden.getColumnModel().getColumn(1).setPreferredWidth(300);
	}
	
	public void addAntwoordenSelectionListener(ListSelectionListener listener) {
		tblAntwoorden.getSelectionModel().addListSelectionListener(listener);
	}
	
	public int getGeselecteerdeRij() {
		return tblAntwoorden.getSelectedRow();
	}
	
	public void setJuisteAntwoord(String juisteAntwoord) {
		lblJuisteAntwoord.setText("Juiste antwoord: " + juisteAntwoord);
	}
	
	public void setGemiddeldeScore(double gemiddeldeScore, int maxScore) {
		lblGemiddeldeScore.setText(String.format("Gemiddelde score: %.2f/%d", gemiddeldeScore, maxScore));
	}
	
	public void setTitel(String leerlingNaam, String quizOnderwerp) {
		setTitle(String.format("Deelname van %s aan quiz '%s'", leerlingNaam, quizOnderwerp));
	}

}
