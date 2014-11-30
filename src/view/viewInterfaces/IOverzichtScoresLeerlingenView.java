package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public interface IOverzichtScoresLeerlingenView extends IOverzichtScoresView {
	
	public void setTableModel(TableModel leerlingScoreTableModel);
	
	public void setQuizOnderwerp(String onderwerp);
	
	public void addDetailKnopListener(ActionListener listener);
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public int getGeselecteerdeRijIndex();

}
