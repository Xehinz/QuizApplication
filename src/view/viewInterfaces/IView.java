package view.viewInterfaces;

import java.awt.Component;
import java.awt.event.WindowListener;

public interface IView {
	
	public void setVisible(boolean isVisible);
	
	public void setLocationRelativeTo(Component component);
	
	public void dispose();
	
	public void setDefaultCloseOperation(int defaultCloseOperation);
	
	public void addWindowListener(WindowListener listener);
	
	public void toFront();

}
