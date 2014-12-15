package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import persistency.DBHandler;
import model.OpdrachtCategorie;
import model.Quiz;
import model.quizStatus.Afgesloten;
import model.quizStatus.Afgewerkt;
import model.quizStatus.InConstructie;
import model.quizStatus.LaatsteKans;
import model.quizStatus.Opengesteld;
import model.quizStatus.QuizStatus;

/**
 * 
 * @author Adriaan Kuipers
 * @version 15/12/2014
 * 
 */

@SuppressWarnings("serial")
public class QuizAanpassingView extends JFrame {
	
	//FIELDS
	private String aantalOpdrachten;
	
	//SWINGFIELDS
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel lblOnderwerp, lblLeraar, lblStatus, lblKlas, lblFilterOpCategorie, lblSorteer, lblAantalOpdrachten, lblFoutboodschap;
	private JButton btnOpdrachtToevoegen, btnOpdrachtVerwijderen, btnQuizBewaren, btnWijzigVolgorde;
	private JTextField txtOnderwerp, txtLeraar, txtKlas;
	private JComboBox<QuizStatus> cmbStatus;
	private JComboBox<String> cmbCategorie, cmbSorteer;
	private JCheckBox ckbIsTest, ckbIsUniekeDeelname;
	private JTable alleOpdrachtenTabel, geselecteerdeOpdrachtenTabel;
	private JScrollPane alleOpdrachtenVeld, geselecteerdeOpdrachtenVeld;
	private JPanel opdrachtKnoppenVeld, quizInfoVeld, sorteerVeld;
	
	/**
	 * Default constructor met parameters
	 * @param quiz
	 * @param dbHandler
	 * 
	 */
	public QuizAanpassingView(Quiz quiz, DBHandler dbHandler) {
		//Set JFrame
		super("Quiz");
		this.setSize(1200, 800);		
		this.setLocationRelativeTo(null);
		
		//Set layout
		layout = new GridBagLayout();
		this.setLayout(layout);	
		
		//INIT BUTTON
		btnOpdrachtToevoegen = new JButton(">>>");
		btnOpdrachtVerwijderen = new JButton("<<<");
		btnQuizBewaren = new JButton("Quiz bewaren");
		btnWijzigVolgorde = new JButton("^^^^");
		
		//INIT COMBOBOX
		QuizStatus[] status = {new Afgesloten(), new Afgewerkt(), new InConstructie(), new LaatsteKans(), new Opengesteld()};
		cmbStatus = new JComboBox<QuizStatus>(status);
		cmbCategorie = new JComboBox<>();
		cmbCategorie.addItem("Alle categorieën");
		for (OpdrachtCategorie a : OpdrachtCategorie.values()){
			cmbCategorie.addItem(a.toString());			
		}
		String[] sorteerOpties = {"geen", "categorie", "vraag"};
		cmbSorteer = new JComboBox<String>(sorteerOpties);		
		
		//INIT TEXTFIELD
		txtOnderwerp = new JTextField();
		txtLeraar = new JTextField();
		txtLeraar.setEditable(false);
		txtKlas = new JTextField();
		
		//INIT CHECKBOX
		ckbIsTest = new JCheckBox("Test");
		ckbIsUniekeDeelname = new JCheckBox("Unieke Deelname");
		
		//INIT TABLE
		alleOpdrachtenTabel = new JTable();		
		geselecteerdeOpdrachtenTabel = new JTable();
		alleOpdrachtenVeld = new JScrollPane(alleOpdrachtenTabel);		
		geselecteerdeOpdrachtenVeld = new JScrollPane(geselecteerdeOpdrachtenTabel);
		alleOpdrachtenTabel.setFillsViewportHeight(true);
		geselecteerdeOpdrachtenTabel.setFillsViewportHeight(true);
		
		//INIT LABEL
		lblOnderwerp = new JLabel("Onderwerp");
		lblLeraar = new JLabel("Auteur");
		lblStatus = new JLabel("Status");
		lblKlas = new JLabel ("Klas");
		lblFilterOpCategorie = new JLabel("Toon opdrachten van categorie :");
		lblSorteer = new JLabel("Sorteer opdrachten op :");
		lblAantalOpdrachten = new JLabel();
		lblFoutboodschap = new JLabel();
		lblSorteer = new JLabel("Sorteer opdrachten op:");
		lblFilterOpCategorie = new JLabel("Toon opdrachten van categorie:");
		
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
		constraints.anchor = GridBagConstraints.WEST;
		sorteerVeld.add(cmbCategorie, constraints);
				
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		sorteerVeld.add(cmbSorteer, constraints);
		
			//ADD LABEL
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.EAST;
		sorteerVeld.add(lblAantalOpdrachten, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		sorteerVeld.add(lblFilterOpCategorie, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		sorteerVeld.add(lblSorteer, constraints);
		
			//ADD BUTTON
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 2;
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.EAST;
		sorteerVeld.add(btnWijzigVolgorde, constraints);
		
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
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.gridwidth = 6;
		quizInfoVeld.add(lblFoutboodschap, constraints);	
		
			//ADD BUTTON
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 4;
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 1;
		quizInfoVeld.add(btnQuizBewaren, constraints);
		
			//ADD COMBOBOX
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		quizInfoVeld.add(cmbStatus, constraints);			
		
			//ADD CHECKBOX
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 0;
		quizInfoVeld.add(ckbIsTest, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 2;
		quizInfoVeld.add(ckbIsUniekeDeelname, constraints);
		
			//ADD TEXTFIELDS
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
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
		txtKlas.setMinimumSize(new Dimension(40,22));
		constraints.weightx = 1;
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
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 1;
		this.add(alleOpdrachtenVeld, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 1;
		this.add(geselecteerdeOpdrachtenVeld, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 2;
		constraints.gridx = 1;
		this.add(opdrachtKnoppenVeld, constraints);
		
		//SET VIEW + TABELS
		setViewToQuiz(quiz);	
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
	
	/**
	 * Een message tonen op het scherm (JOptionPane)
	 * @param boodschap
	 * @param titel
	 */
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Ophalen van de geselecteerde opdracht uit de JTable
	 * @return geselecteerde rij
	 */
	public int getSelectieAlleOpdrachten() {
		return alleOpdrachtenTabel.getSelectedRow();
	}
	public int getSelectieGeselecteerdeOpdrachten() {
		return geselecteerdeOpdrachtenTabel.getSelectedRow();
	}
	
	/**
	 * Toon foutboodschap als Quizbewaren ongeldig is.
	 */
	public void toonFoutBoodschap(String fout) {
		lblFoutboodschap.setText(fout);
		lblFoutboodschap.setForeground(Color.RED);
	}
	
	/**
	 * Update de counter voor het aantal opdrachten
	 */
	public void setLblAantalOpdrachten() {
		int aantal = 0;
		if (geselecteerdeOpdrachtenTabel.getRowCount()>0) {
			aantal = geselecteerdeOpdrachtenTabel.getRowCount();
		}
		aantalOpdrachten = String.format("%s%d","Aantal toegevoegde opdrachten : ", aantal);
		lblAantalOpdrachten.setText(aantalOpdrachten);
	}	
	
	/**
	 * Set venster voor nieuwe quiz (gegevens & isAanpasbaar)
	 * @param quiz
	 * 
	 */	
	public void setViewToQuiz(Quiz quiz) {
		txtOnderwerp.setText(quiz.getOnderwerp());
		txtOnderwerp.setEditable(quiz.isAanpasbaar());		
		txtLeraar.setText(quiz.getAuteur().toString());
		txtKlas.setText(quiz.getDoelLeerjaren().toString());
		txtKlas.setEditable(quiz.isAanpasbaar());
		cmbStatus.setSelectedItem(quiz.getQuizStatus());
		ckbIsTest.setSelected(quiz.getIsTest());
		ckbIsTest.setEnabled(quiz.isAanpasbaar());
		ckbIsUniekeDeelname.setSelected(quiz.getIsUniekeDeelname());
		ckbIsUniekeDeelname.setEnabled(quiz.isAanpasbaar());
		btnOpdrachtToevoegen.setEnabled(quiz.isAanpasbaar());
		btnOpdrachtVerwijderen.setEnabled(quiz.isAanpasbaar());
		btnWijzigVolgorde.setEnabled(quiz.isAanpasbaar());
	}
	
	/**
	 * Instellen van de modellen voor de JTables
	 * @param tablemodel alleOpdrachtenTabel
	 * @param tablemodel quizOpdrachtenTabel
	 * 
	 */
	public void setTableModels (TableModel alleOpdrachtenTabelModel, TableModel geselecteerdeOpdrachtenTabelModel) {
		alleOpdrachtenTabel.setModel(alleOpdrachtenTabelModel); 
		geselecteerdeOpdrachtenTabel.setModel(geselecteerdeOpdrachtenTabelModel);
		setKolomBreedteAlleOpdrachten(alleOpdrachtenTabel);
		setKolomBreedteGeselecteerdeOpdrachten(geselecteerdeOpdrachtenTabel);
	}
	
	/**
	 * Instellen van de breedten van de verschillende kolommen van de JTable
	 * @param table
	 */
	private void setKolomBreedteAlleOpdrachten(JTable table) {
		table.getColumnModel().getColumn(0).setPreferredWidth(40);  //Categorie
		table.getColumnModel().getColumn(1).setPreferredWidth(40); //OpdrachtType
		table.getColumnModel().getColumn(2).setPreferredWidth(300); //Vraag
	}
	private void setKolomBreedteGeselecteerdeOpdrachten(JTable table) {
		table.getColumnModel().getColumn(0).setPreferredWidth(40);  //Categorie
		table.getColumnModel().getColumn(1).setPreferredWidth(40); //OpdrachtType
		table.getColumnModel().getColumn(2).setPreferredWidth(300); //Vraag
		table.getColumnModel().getColumn(3).setPreferredWidth(40);  //MaxScore
	}
	
	public void setStatus(QuizStatus status) {
		cmbStatus.setSelectedItem(status);		
	}
	
	//Listeners
	public void addQuizBewarenKnopActionListener(ActionListener listener) {
		btnQuizBewaren.addActionListener(listener);
	}	
	public void addOpdrachtToevoegenKnopActionListener(ActionListener listener) {
		btnOpdrachtToevoegen.addActionListener(listener);
	}	
	public void addOpdrachtVerwijderenKnopActionListener(ActionListener listener) {
		btnOpdrachtVerwijderen.addActionListener(listener);
	}	
	public void addWijzigVolgordeKnopActionListener(ActionListener listener) {
		btnWijzigVolgorde.addActionListener(listener);
	}
	public void addSelecteerCategorieActionlistener(ActionListener listener) {
		cmbCategorie.addActionListener(listener);
	}	
	public void addSelecteerSorteringActionListener(ActionListener listener) {
		cmbSorteer.addActionListener(listener);
	}	
	
	//Getters
	public String getOpdrachtCategorie() {
		return (String) cmbCategorie.getSelectedItem();
	}	
	public String getSorteerString () {
		return (String) cmbSorteer.getSelectedItem();
	}
	public String getOnderwerpTxt() {
		return txtOnderwerp.getText();
	}	
	public String getKlasTxt() {
		return txtKlas.getText();
	}	
	public boolean getIsTestckb() {
		return ckbIsTest.isSelected();
	}	
	public boolean getIsUniekeDeelnameckb() {
		return ckbIsUniekeDeelname.isSelected();
	}	
	public QuizStatus getQuizStatuscmb() {
		return (QuizStatus)cmbStatus.getSelectedItem();
	}
	public JTable getAlleOpdrachtenTabel() {
		return alleOpdrachtenTabel;	
	}
	
}
