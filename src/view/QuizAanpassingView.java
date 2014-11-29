package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

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
	private ArrayList<Opdracht> alleOpdrachten, geselecteerdeOpdrachten;
	private Quiz quiz;
	private Leraar leraar;
	private String aantalOpdrachten;
	
	//SWINGFIELDS	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel lblOnderwerp, lblLeraar, lblStatus, lblKlas, lblIsTest, lblIsUniekeDeelname, lblFilterOpCategorie, lblSorteer, lblAantalOpdrachten;
	private JButton btnOpdrachtToevoegen, btnOpdrachtVerwijderen, btnQuizOpslaan;
	private JTextField txtOnderwerp, txtLeraar, txtKlas;
	private JComboBox cmbCategorie, cmbSorteer;
	private JTable alleOpdrachtenTabel, geselecteerdeOpdrachtenTabel;
	private QuizAanpassingTableModel tabelModel;	
	private JScrollPane alleOpdrachtenVeld, geselecteerdeopdrachtenVeld;
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
		this.geselecteerdeOpdrachten = quiz.getOpdrachten();		
		
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
		setLblAantalOpdrachten();
		
		//INIT BUTTONS
		btnOpdrachtToevoegen = new JButton(">>>");
		btnOpdrachtVerwijderen = new JButton("<<<");
		btnQuizOpslaan = new JButton("Quiz bewaren");
		
		//INIT TEXTFIELDS
		txtOnderwerp = new JTextField();
		txtLeraar = new JTextField(leraar.toString());
		txtLeraar.setEditable(false);
		txtKlas = new JTextField();
		
		//INIT COMBOBOX
		QuizStatus[] status = {new Afgesloten(), new Afgewerkt(), new InConstructie(), new LaatsteKans(), new Opengesteld()};
		cmbCategorie = new JComboBox<QuizStatus>(status);
		String[] sorteerOpties = {"geen", "categorie", "vraag"};
		cmbSorteer = new JComboBox<String>(sorteerOpties);
		
		//INIT TABLE + SCROLLPANE
		
		//INIT PANEL
		
		
	}
	
	public void setLblAantalOpdrachten() {
		aantalOpdrachten = new String("Aantal toegevoegde opdrachten : " + geselecteerdeOpdrachten.size());
		lblAantalOpdrachten.setText(aantalOpdrachten);
	}
	
	

}
