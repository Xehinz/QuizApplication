package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import model.Quiz;
import model.quizStatus.QuizStatus;

public interface IQuizAanpassingView extends IView {
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public int getSelectieAlleOpdrachten();
	
	public int getSelectieGeselecteerdeOpdrachten();
	
	public void toonFoutBoodschap(String fout);
	
	public void setLblAantalOpdrachten();
	
	public void setViewToQuiz(Quiz quiz);
	
	public void setTableModels (TableModel alleOpdrachtenTabelModel, TableModel geselecteerdeOpdrachtenTabelModel);
	
	public void setStatus(QuizStatus status);	

	public void addQuizBewarenKnopActionListener(ActionListener listener);
	
	public void addOpdrachtToevoegenKnopActionListener(ActionListener listener);
	
	public void addOpdrachtVerwijderenKnopActionListener(ActionListener listener);
	
	public void addWijzigVolgordeKnopActionListener(ActionListener listener);
	
	public void addSelecteerCategorieActionlistener(ActionListener listener);
	
	public void addSelecteerSorteringActionListener(ActionListener listener);	

	public String getOpdrachtCategorie();
	
	public String getSorteerString();
	
	public String getOnderwerpTxt();
	
	public String getKlasTxt();
	
	public boolean getIsTestckb();
	
	public boolean getIsUniekeDeelnameckb();
	
	public QuizStatus getQuizStatuscmb();
	
	public JTable getAlleOpdrachtenTabel();

}
