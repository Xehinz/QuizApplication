package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.JFrame;  // Window
import javax.swing.JButton;
import model.Quiz;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import util.tableModels.QuizBeheerTableModel;


@SuppressWarnings("serial")
public class QuizBeheerView extends JFrame {
	private JFrame frame;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JButton btnNieuweQuiz, btnAanpassenQuiz, btnVerwijderQuiz;
	private JTable table;
	private JScrollPane scrollPane;
	private QuizBeheerTableModel model;
	
	public QuizBeheerView(ArrayList<Quiz> quizzen) {
		//Set window
		super("Beheer Quizzen");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		
		//Set layout
		layout = new GridBagLayout();
		this.setLayout(layout);
				
		//Set Table
		table = new JTable();
		updateTable(quizzen);
				
		//Set TableVeld
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		constraints = new GridBagConstraints();
			//Set constraints
		this.add(scrollPane, constraints);
		
		
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
	
	public Quiz getGeselecteerdeQuiz () {
		//TODO
		return null;
	}
	
	public void updateTable (ArrayList<Quiz> quizzen) {
		
	}

}
