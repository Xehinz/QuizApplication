package view.viewInterfaces;

import java.awt.event.ActionListener;

public interface IMainLeerlingView extends IView {
	
	public void setLeerling(String leerling);
	
	public void addLogoutKnopActionListener(ActionListener listener);
	
	public void addAfsluitenKnopActionListener(ActionListener listener);
	
	public void addDeelnemenKnopActionListener(ActionListener listener);
	
	public void addQuizRapportKnopActionListener(ActionListener listener);

}
