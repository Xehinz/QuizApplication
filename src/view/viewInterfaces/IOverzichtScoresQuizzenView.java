package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public interface IOverzichtScoresQuizzenView extends IView {
	
	public void setTableModel(TableModel quizScoreTableModel);
	
	public void addDetailKnopListener(ActionListener listener);
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public int getGeselecteerdeRijIndex();

}
