package view.viewInterfaces;

import java.awt.event.ActionListener;

public interface ILeerlingAanpassingView extends IView {
	
	public void disableQuizDeelnames();
	
	public void addLeerlingBewarenListener(ActionListener listener);
	
	public String getID();
	
	public void setID(int id);
	
	public String getVoornaam();
	
	public void setVoornaam(String voornaam);
	
	public String getFamilienaam();
	
	public void setFamilienaam(String familienaam);
	
	public int getLeerjaar();
	
	public void setLeerjaar(int leerjaar);

}
