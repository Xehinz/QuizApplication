package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelListener;

import persistency.DBHandler;
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
 * @version 08/12/2014
 * 
 */

@SuppressWarnings("serial")
public class QuizAanpassingView extends JFrame {
	
	//FIELDS
	private Quiz quiz;
	private String aantalOpdrachten;
	
	//SWINGFIELDS	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel lblOnderwerp, lblLeraar, lblStatus, lblKlas, lblFilterOpCategorie, lblSorteer, lblAantalOpdrachten, lblMaxPunten;
	private JButton btnOpdrachtToevoegen, btnOpdrachtVerwijderen, btnQuizBewaren;
	private JTextField txtOnderwerp, txtLeraar, txtKlas;
	private JSpinner txtMaxScore;
	private JComboBox<QuizStatus> cmbStatus;
	private JComboBox<String> cmbCategorie, cmbSorteer;
	private JCheckBox ckbIsTest, ckbIsUniekeDeelname;
	private JTable alleOpdrachtenTabel, geselecteerdeOpdrachtenTabel;
	private QuizAanpassingTableModel alleOpdrachtenTabelModel, geselecteerdeOpdrachtenTabelModel;	
	private JScrollPane alleOpdrachtenVeld, geselecteerdeOpdrachtenVeld;
	private JPanel opdrachtKnoppenVeld, quizInfoVeld, sorteerVeld;
	private TableRowSorter<TableModel> rowSorter;
	private List<RowSorter.SortKey> sortKeys;
	
	
	
	
	public QuizAanpassingView(Quiz quiz, Leraar leraar, DBHandler dbHandler) {
		super("Quiz");
		this.setSize(1200, 800);		
		this.setLocationRelativeTo(null);
		
		layout = new GridBagLayout();
		this.setLayout(layout);	
				
		//INIT BUTTONS
		btnOpdrachtToevoegen = new JButton(">>>");
		btnOpdrachtToevoegen.setEnabled(quiz.isAanpasbaar());
		btnOpdrachtVerwijderen = new JButton("<<<");
		btnOpdrachtVerwijderen.setEnabled(quiz.isAanpasbaar());
		btnQuizBewaren = new JButton("Quiz bewaren");
		
		//INIT COMBOBOX
		QuizStatus[] status = {new Afgesloten(), new Afgewerkt(), new InConstructie(), new LaatsteKans(), new Opengesteld()};
		cmbStatus = new JComboBox<QuizStatus>(status);
		cmbCategorie = new JComboBox<>();
		cmbCategorie.addItem("Alle categorieŽn");
		for (OpdrachtCategorie a : OpdrachtCategorie.values()){
			cmbCategorie.addItem(a.toString());			
		}
		String[] sorteerOpties = {"geen", "categorie", "vraag"};
		cmbSorteer = new JComboBox<String>(sorteerOpties);
		
		//INIT TEXTFIELD
		txtOnderwerp = new JTextField();
		txtOnderwerp.setEditable(quiz.isAanpasbaar());
		txtLeraar = new JTextField();
		txtLeraar.setEditable(false);
		txtKlas = new JTextField();
		txtKlas.setEditable(quiz.isAanpasbaar());
		txtMaxScore = new JSpinner();
		txtMaxScore.enableInputMethods(quiz.isAanpasbaar());
		
		//INIT CHECKBOX
		ckbIsTest = new JCheckBox("Test");
		ckbIsTest.setEnabled(quiz.isAanpasbaar());
		ckbIsUniekeDeelname = new JCheckBox("Unieke Deelname");
		ckbIsUniekeDeelname.setEnabled(quiz.isAanpasbaar());
		
		//INIT TABLE + SCROLLPANE
		alleOpdrachtenTabelModel = new QuizAanpassingTableModel();
		alleOpdrachtenTabel = new JTable(alleOpdrachtenTabelModel);
		rowSorter = new TableRowSorter<TableModel>(alleOpdrachtenTabel.getModel());		
		alleOpdrachtenTabel.setRowSorter(rowSorter);
		alleOpdrachtenVeld = new JScrollPane(alleOpdrachtenTabel);
		alleOpdrachtenVeld.setPreferredSize(new Dimension(400, 400));
		geselecteerdeOpdrachtenTabelModel = new QuizAanpassingTableModel();
		geselecteerdeOpdrachtenTabel = new JTable(geselecteerdeOpdrachtenTabelModel);
		geselecteerdeOpdrachtenVeld = new JScrollPane(geselecteerdeOpdrachtenTabel);
		geselecteerdeOpdrachtenVeld.setPreferredSize(new Dimension(400, 400));		
		
		//INIT LABELS
		lblOnderwerp = new JLabel("Onderwerp");
		lblLeraar = new JLabel("Auteur");
		lblStatus = new JLabel("Status");
		lblKlas = new JLabel ("Klas");
		lblFilterOpCategorie = new JLabel("Toon opdrachten van categorie :");
		lblSorteer = new JLabel("Sorteer opdrachten op :");
		lblAantalOpdrachten = new JLabel();
		lblMaxPunten = new JLabel("Max score voor deze vraag :");
		
		initViewForQuiz(dbHandler.getOpdrachtCatalogus().getOpdrachten(), quiz);
		
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
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		sorteerVeld.add(cmbCategorie, constraints);
				
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		sorteerVeld.add(cmbSorteer, constraints);
		
			//ADD LABEL
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 0;
		constraints.gridx = 1;
		sorteerVeld.add(lblAantalOpdrachten, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 1;
		sorteerVeld.add(lblMaxPunten, constraints);
		
			//ADD TEXTFIELD
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		txtMaxScore.setValue(1);
		sorteerVeld.add(txtMaxScore, constraints);
		
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
		
		//ADD BUTTON
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridy = 1;
		constraints.gridx = 4;
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
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
		int aantal = 0;
		if (geselecteerdeOpdrachtenTabel.getRowCount()>0) {
			aantal = geselecteerdeOpdrachtenTabel.getRowCount();
		}
		aantalOpdrachten = String.format("%s%d","Aantal toegevoegde opdrachten : ", aantal);
		lblAantalOpdrachten.setText(aantalOpdrachten);
	}
	
	public void setOpdrachtTabellen(Collection<Opdracht> alleOpdrachten, Quiz quiz) {		
		geselecteerdeOpdrachtenTabelModel.setOpdrachten(quiz.getOpdrachten());
		geselecteerdeOpdrachtenTabel.setModel(geselecteerdeOpdrachtenTabelModel);
		geselecteerdeOpdrachtenTabelModel.fireTableDataChanged();
		alleOpdrachten.removeAll(quiz.getOpdrachten());
		alleOpdrachtenTabelModel.setOpdrachten(alleOpdrachten);
		alleOpdrachtenTabelModel.fireTableDataChanged();
		setLblAantalOpdrachten();
	}
	
	public String getOpdrachtCategorie() {
		return (String) cmbCategorie.getSelectedItem();
	}
	
	public String getSorteerString () {
		return (String) cmbSorteer.getSelectedItem();
	}
	public void setRowSorter (int columnIndex) {		
		sortKeys = new ArrayList<>();
		rowSorter.setSortKeys(null);	
		if (!(columnIndex == 1)) {
			sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
			rowSorter.setSortKeys(sortKeys);
		}
		rowSorter.sort();
	}
	
	public void checkTxtMaxPunten () {
		//TODO check int na input
	}
	
	public Opdracht getGeselecteerdeOpdrachtAlleOpdrachten() {
			return alleOpdrachtenTabelModel.getOpdracht(alleOpdrachtenTabel.getSelectedRow());
	}
	
	public Opdracht getGeselecteerdeOpdrachtQuizOpdrachten() {		
			return geselecteerdeOpdrachtenTabelModel.getOpdracht(geselecteerdeOpdrachtenTabel.getSelectedRow());
	}	
	
	public void toonInformationDialog(String boodschap, String titel) {
		JOptionPane.showMessageDialog(this, boodschap, titel, JOptionPane.INFORMATION_MESSAGE);
	}
		
	public void initViewForQuiz(ArrayList<Opdracht> alleOpdrachten, Quiz quiz) {
		this.quiz = quiz;
		
		//SET FIELDS
		txtOnderwerp.setText(quiz.getOnderwerp());
		txtOnderwerp.setEditable(quiz.isAanpasbaar());
		txtLeraar.setEditable(true);
		txtLeraar.setText(quiz.getAuteur().toString());
		txtLeraar.setEditable(false);
		txtKlas.setText(quiz.getDoelLeerjaren().toString());
		txtKlas.setEditable(quiz.isAanpasbaar());
		cmbStatus.setSelectedItem(quiz.getQuizStatus());
		ckbIsTest.setSelected(quiz.getIsTest());
		ckbIsTest.setEnabled(quiz.isAanpasbaar());
		ckbIsUniekeDeelname.setSelected(quiz.getIsUniekeDeelname());
		ckbIsUniekeDeelname.setEnabled(quiz.isAanpasbaar());
		btnOpdrachtToevoegen.setEnabled(quiz.isAanpasbaar());
		btnOpdrachtVerwijderen.setEnabled(quiz.isAanpasbaar());
		setOpdrachtTabellen(alleOpdrachten, quiz);
		setLblAantalOpdrachten();
	}
	
	//LISTENERS
	public void addQuizBewarenKnopActionListener(ActionListener listener) {
		btnQuizBewaren.addActionListener(listener);
	}	
	public void addOpdrachtToevoegenKnopActionListener(ActionListener listener) {
		btnOpdrachtToevoegen.addActionListener(listener);
	}	
	public void addOpdrachtVerwijderenKnopActionListener(ActionListener listener) {
		btnOpdrachtVerwijderen.addActionListener(listener);
	}	
	public void addSelecteerCategorieActionlistener(ActionListener listener) {
		cmbCategorie.addActionListener(listener);
	}	
	public void addSelecteerSorteringActionListener(ActionListener listener) {
		cmbSorteer.addActionListener(listener);
	}	
	public void addAlleOpdrachtenTabelSelectieListener(TableModelListener listener) {
		alleOpdrachtenTabelModel.addTableModelListener(listener);
	}	
	public void addGeselecteerdeOpdrachtenTabelSelectieListener(TableModelListener listener) {
		geselecteerdeOpdrachtenTabelModel.addTableModelListener(listener);
	}
	public void addVeranderMaxScoreChangeListener(ChangeListener listener) {
		txtMaxScore.addChangeListener(listener);
	}
	
	//GETTERS
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
	
	public Quiz getQuiz() {
		return this.quiz;
	}
	
	public int getMaxScore() {
		return (Integer) txtMaxScore.getValue();		
	}

}
