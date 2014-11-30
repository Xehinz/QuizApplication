package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

public interface IOverzichtScoresQuizzenView extends IOverzichtScoresView {
	
	public void setTableModel(TableModel quizScoreTableModel);
	
	public void addDetailKnopListener(ActionListener listener);
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public int getGeselecteerdeRijIndex();

}
