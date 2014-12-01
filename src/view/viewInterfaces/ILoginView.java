package view.viewInterfaces;

import java.awt.event.ActionListener;

public interface ILoginView extends IView {
	
	public String getVoornaam();
	
	public String getFamilienaam();
	
	public String getVolledigeNaam();
	
	public void addLoginActionListener(ActionListener listener);
	
	public void toonErrorMessage(String boodschap, String titel);
	
	public void toonInformationMessage(String boodschap, String titel);

}
