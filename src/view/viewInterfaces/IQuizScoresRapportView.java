package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public interface IQuizScoresRapportView extends IView {
	
	public void setLeerling(String volledigeNaam);
	
	public int getGeselecteerdeRij();
	
	public void setTableModel(TableModel quizDeelnameTableModel);
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public void addDetailKnopListener(ActionListener listener);

	public void setTableModel1(TableModel quizScoreTableModel);	
	
	public void addSelectionListener(ListSelectionListener listener);

}
