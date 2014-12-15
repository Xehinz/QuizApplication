package view.viewInterfaces;

import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

public interface IQuizBeheerView extends IView {
	
	public void addNieuweQuizKnopActionListener(ActionListener listener);
	
	public void addAanpassenQuizKnopActionListener(ActionListener listener);
	
	public void addVerwijderQuizKnopActionListener(ActionListener listener);
	
	public void setTabelModel(TableModel model);
	
	public int getGeselecteerdeRij();
	
	public void toonInformationDialog(String boodschap, String titel);	

}
