package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public interface IRapportNaDeelnameView extends IView {
	
	public void setLeerling(String volledigeNaam);

	public void setQuizScore(int score);

	public void setQuiz(String onderwerp);

	public void setRapportTableModel(TableModel rapportTableModel);

	public void addDeelnameMenuKnopListener(ActionListener listener);

	public void addAntwoordGeselecteerdListener(ListSelectionListener listener);

	public void setAntwoordDetail(String antwoordDetail);

	public int getGeselecteerdeRijIndex();

}
