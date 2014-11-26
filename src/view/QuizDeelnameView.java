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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import model.Leerling;
import model.Quiz;

@SuppressWarnings("serial")
public class QuizDeelnameView extends JFrame {
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	private JLabel leerling, quizHeader;
	private JTable quizTabel;
	private JButton deelneemKnop;
	
	private QuizTableModel quizTableModel;
	
	public QuizDeelnameView() {
		super("Deelnemen aan Quiz");
		this.setSize(1200, 400);
		this.setLocationRelativeTo(null);
		
		quizTableModel = new QuizTableModel();
		
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
			
		quizTabel = new JTable(quizTableModel);	
		quizTabel.getColumnModel().getColumn(0).setPreferredWidth(400);
		quizTabel.setAutoCreateRowSorter(true);
		quizTabel.setFillsViewportHeight(true);	
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		JScrollPane scroller = new JScrollPane(quizTabel);
//		Dimension d = quizzen.getPreferredSize();
//		d.height = quizzen.getRowHeight() * (quizzen.getRowCount() + 2);
//		quizzen.setPreferredScrollableViewportSize(d);
		this.add(scroller, constraints);	
		
		deelneemKnop = new JButton("Deelnemen");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 0, 10, 10);
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.EAST;				
		this.add(deelneemKnop, constraints);
	}
	
	public void setLeerling(Leerling leerling) {
		this.leerling.setText(String.format("Leerling: %s", leerling.getNaam()));
	}
	
	public void setQuizzen(Collection<Quiz> quizzen) {
		quizTableModel.setQuizzen(quizzen);		
	}
	
	public Quiz getGeselecteerdeQuiz() {
		return quizTableModel.getQuiz(quizTabel.getSelectedRow());
	}
	
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void addDeelneemKnopListener(ActionListener listener) {
		deelneemKnop.addActionListener(listener);
	}

	class QuizTableModel extends AbstractTableModel {

		private String[] headers;
		private ArrayList<Quiz> quizzen;
		
		public QuizTableModel() {
			super();
			headers = new String[] {"Onderwerp", "Leraar", "Aantal deelnames", "Test?"};
			quizzen = new ArrayList<Quiz>();
		}
		
		public QuizTableModel(Collection<Quiz> quizzen) {
			this();
			this.quizzen = new ArrayList<Quiz>(quizzen);			
		}
		
		public Quiz getQuiz(int row) {
			if (row < quizzen.size() && row >= 0) {
				return quizzen.get(row);
			}
			return null;
		}
		
		public void setQuizzen(Collection<Quiz> quizzen) {
			this.quizzen = new ArrayList<Quiz>(quizzen);
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
	    public Class<?> getColumnClass(int columnIndex) {
	        if (quizzen.isEmpty()) {
	            return Object.class;
	        }
	        return getValueAt(0, columnIndex).getClass();
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
