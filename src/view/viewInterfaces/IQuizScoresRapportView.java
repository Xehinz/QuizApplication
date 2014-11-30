package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

public interface IQuizScoresRapportView {
	
	public int getGeselecteerdeRij();
	
	public void setTableModel(TableModel quizScoreTableModel);
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public void addDetailKnopListener(ActionListener listener);	

}
