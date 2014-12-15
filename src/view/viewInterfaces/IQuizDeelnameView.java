package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public interface IQuizDeelnameView extends IView {
	
	public void setLeerling(String volledigeNaam);
	
	public int getGeselecteerdeRij();
	
	public void setTableModel(TableModel tableModel, int... kolomBreedtes);
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public void addDeelneemKnopListener(ActionListener listener);		
	
}
