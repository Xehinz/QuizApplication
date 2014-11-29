package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Quiz;
import model.quizStatus.Afgesloten;
import model.quizStatus.Afgewerkt;
import model.quizStatus.InConstructie;
import model.quizStatus.LaatsteKans;
import model.quizStatus.Opengesteld;
import model.quizStatus.QuizStatus;
import util.tableModels.QuizAanpassingTableModel;

/**
 * 
 * @author Adriaan Kuipers
 * @version 29/11/2014
 * 
 */

@SuppressWarnings("serial")
public class QuizAanpassingView extends JFrame {
	
	//FIELDS
	private ArrayList<Opdracht> alleOpdrachten;
	private Quiz quiz;
	private Leraar leraar;
	private String aantalOpdrachten;
	
	//SWINGFIELDS	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel lblOnderwerp, lblLeraar, lblStatus, lblKlas, lblIsTest, lblIsUniekeDeelname, lblFilterOpCategorie, lblSorteer, lblAantalOpdrachten;
	private JButton btnOpdrachtToevoegen, btnOpdrachtVerwijderen, btnQuizBewaren;
	private JTextField txtOnderwerp, txtLeraar, txtKlas;
	private JComboBox cmbStatus, cmbCategorie, cmbSorteer;
	private JTable alleOpdrachtenTabel, geselecteerdeOpdrachtenTabel;
	private QuizAanpassingTableModel alleOpdrachtenTabelModel, geselecteerdeOpdrachtenTabelModel;	
	private JScrollPane alleOpdrachtenVeld, geselecteerdeOpdrachtenVeld;
	private JPanel opdrachtKnoppenVeld, quizInfoVeld, sorteerVeld;
	
	
	
	
	public QuizAanpassingView(Quiz quiz, Leraar leraar, ArrayList<Opdracht> alleOpdrachten) {
		super("Quiz");
		this.setSize(1200, 800);		
		this.setLocationRelativeTo(null);
		
		layout = new GridBagLayout();
		this.setLayout(layout);
				
		this.quiz = quiz;
		this.leraar = leraar;
		this.alleOpdrachten = alleOpdrachten;				
		
		//INIT BUTTONS
		btnOpdrachtToevoegen = new JButton(">>>");
		btnOpdrachtVerwijderen = new JButton("<<<");
		btnQuizBewaren = new JButton("Quiz bewaren");
		
		//INIT TEXTFIELDS
		txtOnderwerp = new JTextField(quiz.getOnderwerp());
		txtLeraar = new JTextField(leraar.toString());
		txtLeraar.setEditable(false);
		txtKlas = new JTextField(quiz.getDoelLeerjaren().toString());
		
		//INIT COMBOBOX
		QuizStatus[] status = {new Afgesloten(), new Afgewerkt(), new InConstructie(), new LaatsteKans(), new Opengesteld()};
		cmbStatus = new JComboBox<QuizStatus>(status);
		cmbCategorie = new JComboBox<OpdrachtCategorie>(OpdrachtCategorie.values());
		String[] sorteerOpties = {"geen", "categorie", "vraag"};
		cmbSorteer = new JComboBox<String>(sorteerOpties);
		
		//INIT TABLE + SCROLLPANE
		alleOpdrachtenTabelModel = new QuizAanpassingTableModel();
		alleOpdrachtenTabel = new JTable(alleOpdrachtenTabelModel);
		alleOpdrachtenVeld = new JScrollPane(alleOpdrachtenTabel);		
		geselecteerdeOpdrachtenTabelModel = new QuizAanpassingTableModel();
		geselecteerdeOpdrachtenTabel = new JTable(geselecteerdeOpdrachtenTabelModel);
		geselecteerdeOpdrachtenVeld = new JScrollPane(geselecteerdeOpdrachtenTabel);
		setOpdrachtTabellen(alleOpdrachten, quiz);
		
		//INIT LABELS
		lblOnderwerp = new JLabel("Onderwerp");
		lblLeraar = new JLabel("Auteur");
		lblStatus = new JLabel("Status");
		lblKlas = new JLabel ("Klas");
		lblIsTest = new JLabel("Test");
		lblIsUniekeDeelname = new JLabel("Unieke Deelname");
		lblFilterOpCategorie = new JLabel("Toon opdrachten van categorie :");
		lblSorteer = new JLabel("Sorteer opdrachten op :");
		lblAantalOpdrachten = new JLabel();
		//setLblAantalOpdrachten();
		
		//INIT KNOPPENVELD
		opdrachtKnoppenVeld = new JPanel();
		opdrachtKnoppenVeld.setLayout(layout);
		
			//ADD BUTTONS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		opdrachtKnoppenVeld.add(btnOpdrachtToevoegen, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		opdrachtKnoppenVeld.add(btnOpdrachtVerwijderen, constraints);
		
		//INIT SORTEERVELD
		sorteerVeld = new JPanel();
		sorteerVeld.setLayout(layout);
		
			//ADD COMBOBOX
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		sorteerVeld.add(cmbCategorie, constraints);
				
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		sorteerVeld.add(cmbSorteer, constraints);	
		
		//INIT QUIZINFOVELD
		quizInfoVeld = new JPanel();
		quizInfoVeld.setLayout(layout);
		
		//ADD LABELS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		quizInfoVeld.add(lblOnderwerp, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 4;
		quizInfoVeld.add(lblLeraar, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		quizInfoVeld.add(lblStatus, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 2;
		quizInfoVeld.add(lblKlas, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 5;
		constraints.anchor = GridBagConstraints.WEST;
		quizInfoVeld.add(lblIsUniekeDeelname, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 5;
		constraints.anchor = GridBagConstraints.WEST;
		quizInfoVeld.add(lblIsTest, constraints);		
		
		//ADD BUTTON
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		quizInfoVeld.add(btnQuizBewaren, constraints);
		
		//ADD COMBOBOX
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		quizInfoVeld.add(cmbStatus, constraints);	
		
		//ADD TEXTFIELDS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		quizInfoVeld.add(txtOnderwerp, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		quizInfoVeld.add(txtLeraar, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		quizInfoVeld.add(txtKlas, constraints);
		
		
		//BUILD WINDOW
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTH;
		this.add(quizInfoVeld, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(sorteerVeld, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.SOUTH;
		this.add(alleOpdrachtenVeld, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.SOUTH;
		this.add(geselecteerdeOpdrachtenVeld, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 1;
		this.add(opdrachtKnoppenVeld, constraints);
	}
	
	public void setLblAantalOpdrachten() {
		//aantalOpdrachten = new String("Aantal toegevoegde opdrachten : " + geselecteerdeOpdrachtenTabel.getRowCount());
		//lblAantalOpdrachten.setText(aantalOpdrachten);
		lblAantalOpdrachten.setText("test");
	}
	
	public void setOpdrachtTabellen(Collection<Opdracht> alleOpdrachten, Quiz quiz) {
		geselecteerdeOpdrachtenTabelModel.setOpdrachten(quiz.getOpdrachten());
		alleOpdrachten.removeAll(quiz.getOpdrachten());
		alleOpdrachtenTabelModel.setOpdrachten(alleOpdrachten);
		//setLblAantalOpdrachten();
	}
	
	public Opdracht getGeselecteerdeOpdracht(String richting) {
		if(richting.equals(">>>")) {
			return alleOpdrachtenTabelModel.getOpdracht(alleOpdrachtenTabel.getSelectedRow());
		} else {
			return geselecteerdeOpdrachtenTabelModel.getOpdracht(geselecteerdeOpdrachtenTabel.getSelectedRow());
		}
	}
	
	public void addQuizBewarenKnopActionListener(ActionListener listener) {
		btnQuizBewaren.addActionListener(listener);
	}
	
	public void addOpdrachtToevoegenKnopActionListener(ActionListener listener) {
		btnOpdrachtToevoegen.addActionListener(listener);
	}
	
	public void addopdrachtVerwijderenKnopActionListener(ActionListener listener) {
		btnOpdrachtVerwijderen.addActionListener(listener);
	}
	
	public void addCategorieLijstSelectieActionListener(ActionListener listener) {
		cmbCategorie.addActionListener(listener);
	}
	
	public void addSorteerLijstSelectieActionListener(ActionListener listener) {
		cmbSorteer.addActionListener(listener);
	}
	

}
