package view;

/**
 * 
 * @author Adriaan Kuipers
 * @version 26/11/2014
 * 
 */

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JFrame;  // Window
import javax.swing.JButton;

import model.Quiz;

import java.util.Collection;

import javax.swing.JScrollPane;

import util.tableModels.QuizBeheerTableModel;

import javax.swing.table.TableColumn;


@SuppressWarnings("serial")
public class QuizBeheerView extends JFrame {
	
	private JPanel knoppenVeld;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JButton btnNieuweQuiz, btnAanpassenQuiz, btnVerwijderQuiz;
	private JTable table;
	private JScrollPane tabelVeld;
	private QuizBeheerTableModel model;
	
	public QuizBeheerView() {
		//Set window
		super("Beheer Quizzen");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		
		
		//Set layout
		layout = new GridBagLayout();
		this.setLayout(layout);
				
		//Set Table
		model = new QuizBeheerTableModel();
		table = new JTable(model);
		setKolomBreedte(table);
				
		//Set TabelVeld
				
		tabelVeld = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		//table.setAutoCreateRowSorter(true);
				//Set constraints
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		tabelVeld.setPreferredSize(new Dimension(800, 700));
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
		
	}	
	
	public void addNieuweQuizActionListener(ActionListener listener) {
		btnNieuweQuiz.addActionListener(listener);
	}
	
	public void addAanpassenQuizActionListener(ActionListener listener) {
		btnAanpassenQuiz.addActionListener(listener);
	}
	
	public void addVerwijderQuizActionListener(ActionListener listener) {
		btnVerwijderQuiz.addActionListener(listener);
	}
	
	public void setQuizzen(Collection<Quiz> quizzen) {
		model.setQuizzen(quizzen);		
	}
	
	public void setKolomBreedte (JTable table) {
		TableColumn column = null;
		for (int i = 0; i < 6; i++) {
			    column = table.getColumnModel().getColumn(i);
			    if (i == 3 || i == 5 || i == 6) {
			        column.setPreferredWidth(50); //Vragen, Test, Unieke Deelname
			    }
			    if (i == 0 || i == 2) {
				        column.setPreferredWidth(140); //Auteur, Klas
			    }
			    if (i == 1) {
			        column.setPreferredWidth(250); //Onderwerp
			    }
			}
	}

}
