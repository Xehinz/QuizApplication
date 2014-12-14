package view.viewInterfaces;

import java.awt.event.ActionListener;
import java.awt.event.WindowFocusListener;

import javax.swing.table.TableModel;

public interface IBeheerLeerlingView extends IView {
	
	public void addFrameWindowListener(WindowFocusListener listener);
	
	public void addNieuweLeerlingListener(ActionListener listener);
	
	public void addAanpassenLeerlingListener(ActionListener listener);
	
	public void addVerwijderLeerlingListener(ActionListener listener);
	
	public void addLeerlingScoresListener(ActionListener listener);

	public void setTableModel (TableModel aModel);
	
	public int getGeselecteerdeRij();
	
	public void toonInformationDialog(String boodschap, String titel);

}
