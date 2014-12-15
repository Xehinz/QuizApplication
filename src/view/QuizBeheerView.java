package view;

/**
 * 
 * @author Adriaan Kuipers
 * @version 15/12/2014
 * 
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import view.viewInterfaces.IQuizBeheerView;

@SuppressWarnings("serial")
public class QuizBeheerView extends JFrame implements IQuizBeheerView {
	
	private JPanel knoppenVeld;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JButton btnNieuweQuiz, btnAanpassenQuiz, btnVerwijderQuiz;
	private JTable quizTabel;
	private JScrollPane tabelVeld;
	
	public QuizBeheerView() {
		//Set window
		super("Beheer Quizzen");
		this.setSize(1350, 400);
		this.setLocationRelativeTo(null);
		
		
		//Set layout
		layout = new GridBagLayout();
		this.setLayout(layout);
				
		//Set Table
		quizTabel = new JTable();
				
		//Set TabelVeld
				
		tabelVeld = new JScrollPane(quizTabel);
		quizTabel.setFillsViewportHeight(true);
		
				//Set constraints
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weighty = 1;
		constraints.weightx = 1;
		this.add(tabelVeld, constraints);
		
		//Set knoppenVeld
		knoppenVeld = new JPanel();
		knoppenVeld.setLayout(new GridBagLayout());
		
		//Set knoppen in knoppenVeld
		btnNieuweQuiz = new JButton("Nieuwe Quiz");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		knoppenVeld.add(btnNieuweQuiz, constraints);
		
		btnAanpassenQuiz = new JButton("Quiz Aanpassen");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		knoppenVeld.add(btnAanpassenQuiz, constraints);
		
		btnVerwijderQuiz = new JButton("Quiz Verwijderen");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		knoppenVeld.add(btnVerwijderQuiz, constraints);
		
		//Add knoppenVeld
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		this.add (knoppenVeld, constraints);
		
		SwingUtilities.updateComponentTreeUI(this);		
	}	
	
	public void addNieuweQuizKnopActionListener(ActionListener listener) {
		btnNieuweQuiz.addActionListener(listener);
	}
	
	public void addAanpassenQuizKnopActionListener(ActionListener listener) {
		btnAanpassenQuiz.addActionListener(listener);
	}
	
	public void addVerwijderQuizKnopActionListener(ActionListener listener) {
		btnVerwijderQuiz.addActionListener(listener);
	}
	
	public void setTabelModel(TableModel model) {
		quizTabel.setModel(model);
		setKolomBreedte(quizTabel);
	}
	
	public int getGeselecteerdeRij() {
		return quizTabel.getSelectedRow();
	}
	
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}	
	
	private void setKolomBreedte(JTable table) {
		table.getColumnModel().getColumn(0).setPreferredWidth(140);  //Auteur
		table.getColumnModel().getColumn(1).setPreferredWidth(250); //Onderwerp
		table.getColumnModel().getColumn(2).setPreferredWidth(140); //klas
		table.getColumnModel().getColumn(3).setPreferredWidth(50);  //vragen
		//table.getColumnModel().getColumn(4).setPreferredWidth(50);  
		table.getColumnModel().getColumn(5).setPreferredWidth(50);  //test
		table.getColumnModel().getColumn(6).setPreferredWidth(50);  //isuniekedeelname
	}

}
