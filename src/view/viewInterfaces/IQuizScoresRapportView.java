package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

import model.Leerling;

public interface IQuizScoresRapportView extends IView {
	
	public void setLeerling(Leerling leerling);
	
	public int getGeselecteerdeRij();
	
	public void setTableModel(TableModel quizScoreTableModel);
	
	public void toonInformationDialog(String boodschap, String titel);
	
	public void addDetailKnopListener(ActionListener listener);	

}