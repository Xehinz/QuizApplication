package view;

/**
 * 
 * @author Adriaan Kuipers
 * @version 08/12/2014
 * 
 */

import java.awt.Dimension;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import model.Quiz;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class QuizBeheerView extends JFrame {
	
	private JPanel knoppenVeld;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JButton btnNieuweQuiz, btnAanpassenQuiz, btnVerwijderQuiz;
	private JTable quiztabel;
	private JScrollPane tabelVeld;
	private QuizBeheerTableModel tabelModel;
	
	public QuizBeheerView() {
		//Set window
		super("Beheer Quizzen");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		
		
		//Set layout
		layout = new GridBagLayout();
		this.setLayout(layout);
				
		//Set Table
		tabelModel = new QuizBeheerTableModel();
		quiztabel = new JTable(tabelModel);
		setKolomBreedte(quiztabel);
				
		//Set TabelVeld
				
		tabelVeld = new JScrollPane(quiztabel);
		quiztabel.setFillsViewportHeight(true);
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
	
	//TODO btnVerwijderQuiz disabled als quiz niet verwijderbaar is.
	
	public void addNieuweQuizKnopActionListener(ActionListener listener) {
		btnNieuweQuiz.addActionListener(listener);
	}
	
	public void addAanpassenQuizKnopActionListener(ActionListener listener) {
		btnAanpassenQuiz.addActionListener(listener);
	}
	
	public void addVerwijderQuizKnopActionListener(ActionListener listener) {
		btnVerwijderQuiz.addActionListener(listener);
	}
	
	public void setQuizzen(Collection<Quiz> quizzen) {
		tabelModel.setQuizzen(quizzen);
		tabelModel.fireTableDataChanged();
	}
	
	public Quiz getGeselecteerdeQuiz() {
		return tabelModel.getQuiz(quiztabel.getSelectedRow());
	}
	
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void setKolomBreedte (JTable table) {
		TableColumn kolom = null;
		for (int i = 0; i < 6; i++) {
			    kolom = table.getColumnModel().getColumn(i);
			    if (i == 3 || i == 5 || i == 6) {
			        kolom.setPreferredWidth(50); //Vragen, Test, Unieke Deelname
			    }
			    if (i == 0 || i == 2) {
				        kolom.setPreferredWidth(140); //Auteur, Klas
			    }
			    if (i == 1) {
			        kolom.setPreferredWidth(250); //Onderwerp
			    }
			}
	}
	
	//TABELMODEL
	public class QuizBeheerTableModel extends AbstractTableModel {

		private ArrayList<Quiz> quizzen;
		private String[] headers;
		
		
		public QuizBeheerTableModel () {
			headers = new String[]{"Auteur","Onderwerp","Klas","Vragen","Status","Test","Unieke Deelname"};
			quizzen = new ArrayList<Quiz>();
		}
		
		
		@Override
		public int getColumnCount() {
			return headers.length;
		}
		@Override
		public int getRowCount() {
			return quizzen.size();
		}
		
		@Override   
		public String getColumnName(int col) {
		        return headers[col];
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			Quiz quiz = quizzen.get(row);
		     switch (col) {
		     case 0: return quiz.getAuteur();
		     case 1: return quiz.getOnderwerp();
		     case 2: return quiz.getDoelLeerjaren();
		     case 3: return (quiz.getQuizOpdrachten()).size();
		     case 4: return quiz.getQuizStatus();
		     case 5: return quiz.getIsTest();
		     case 6: return quiz.getIsUniekeDeelname();
		     
		     default: return null;
		    }
		}

		public void setQuizzen (Collection<Quiz> quizzen) {
			this.quizzen = new ArrayList<Quiz>(quizzen);
		}
		
		public Quiz getQuiz(int row) {
			if (row < quizzen.size() && row >= 0) {
				return quizzen.get(row);
			}
			return null;
		}
			
	}

}
