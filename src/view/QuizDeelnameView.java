package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import model.Quiz;

@SuppressWarnings("serial")
public class QuizDeelnameView extends JFrame {
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	private JLabel leerling, quizHeader;
	private JTable quizzen;
	private JButton deelneemKnop;
	
	private QuizTableModel quizTableModel;
	
	public QuizDeelnameView(Collection<Quiz> mogelijkeQuizzen) {
		super("Deelnemen aan Quiz");
		this.setSize(1200, 400);
		
		quizTableModel = new QuizTableModel(mogelijkeQuizzen);
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		leerling = new JLabel("Leerling: ");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(leerling, constraints);
		
		quizHeader = new JLabel("Quizzen:");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 0, 0);
		constraints.gridy = 1;
		constraints.gridx = 0;		
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(quizHeader, constraints);		
			
		quizzen = new JTable(quizTableModel);	
		quizzen.getColumnModel().getColumn(0).setPreferredWidth(400);
		quizzen.setAutoCreateRowSorter(true);
		quizzen.setFillsViewportHeight(true);	
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		JScrollPane scroller = new JScrollPane(quizzen);
//		Dimension d = quizzen.getPreferredSize();
//		d.height = quizzen.getRowHeight() * (quizzen.getRowCount() + 2);
		//quizzen.setPreferredScrollableViewportSize(d);
		this.add(scroller, constraints);	
		
		deelneemKnop = new JButton("Deelnemen");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 0, 10, 10);
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.EAST;				
		this.add(deelneemKnop, constraints);
	}
	
	public void addDeelneemKnopListener(ActionListener listener) {
		deelneemKnop.addActionListener(listener);
	}
	
	public Quiz getGeselecteerdeQuiz() {
		return quizTableModel.getQuiz(quizzen.getSelectedRow());
	}

	class QuizTableModel extends AbstractTableModel {

		private String[] headers;
		private ArrayList<Quiz> quizzen;
		
		public QuizTableModel(Collection<Quiz> quizzen) {
			super();
			this.quizzen = new ArrayList<Quiz>(quizzen);
			headers = new String[] {"Onderwerp", "Leraar", "Aantal deelnames", "Test?"};
		}
		
		public Quiz getQuiz(int row) {
			if (row < quizzen.size() && row >= 0) {
				return quizzen.get(row);
			}
			return null;
		}
		
		@Override   
		public String getColumnName(int col) {
		        return headers[col];
		    }
		
		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return quizzen.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			Quiz quiz = quizzen.get(row);
			
			switch (col) {
				case 0: 
					return quiz.getOnderwerp();
				case 1:
					return quiz.getAuteur().toString();
				case 2:
					return quiz.getIsUniekeDeelname() ? "1" : "Meerdere deelnames";
				case 3:
					return quiz.getIsTest() ? "Test" : "Geen test";
			}
			
			return "";
		}		
	}

}
