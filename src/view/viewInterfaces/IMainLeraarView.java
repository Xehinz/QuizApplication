package view.viewInterfaces;

import java.awt.event.ActionListener;

public interface IMainLeraarView extends IView {
	
	public void setLeraar(String leraar);
	
	public void addLogoutKnopActionListener(ActionListener listener);
	
	public void addAfsluitenKnopActionListener(ActionListener listener);
	
	public void addOpdrachtBeheerKnopActionListener(ActionListener listener);
	
	public void addQuizBeheerKnopActionListener(ActionListener listener);
	
	public void addLeerlingBeheerKnopActionListener(ActionListener listener);
	
	public void addOverzichtScoresKnopActionListener(ActionListener listener);

}
