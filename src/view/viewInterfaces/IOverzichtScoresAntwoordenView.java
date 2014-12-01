package view.viewInterfaces;

import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public interface IOverzichtScoresAntwoordenView extends IView {
	
	public void setAntwoordTableModel(TableModel antwoordTableModel);
	
	public void addAntwoordenSelectionListener(ListSelectionListener listener);
	
	public int getGeselecteerdeRij();
	
	public void setJuisteAntwoord(String juisteAntwoord);
	
	public void setGemiddeldeScore(double gemiddeldeScore, int maxScore);
	
	public void setTitel(String leerlingNaam, String quizOnderwerp);

}
