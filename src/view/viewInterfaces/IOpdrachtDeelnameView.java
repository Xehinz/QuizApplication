package view.viewInterfaces;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public interface IOpdrachtDeelnameView extends IView {

	public void setVraagCounter(int count);

	public void setMaxTijd(int milliSeconden);
	
	public void setTijdOver(int milliSeconden);

	public void setPogingen(int pogingen);

	public void setVraag(String vraag);
	
	public void setOpdrachtCategorie(String categorie);

	public String getAntwoord();
	
	public void setAntwoordKeuzes(String[] antwoordKeuzes);

	public void setAntwoordVeldToolTip(String toolTip);

	public void useHint(boolean useHint);
	
	public void disableHint();

	public void opTijd(boolean opTijd);
	
	public void beperktePogingen(boolean beperktePogingen);

	public void setQuizOnderwerp(String onderwerp);

	public void addAntwoordKnopListener(ActionListener listener);

	public void addHintKnopListener(ActionListener listener);
	
	public void addVolgendeVraagKnopListener(ActionListener listener);
	
	public void addClosedListener(WindowListener listener);

	public void toonInformationDialog(String boodschap, String titel);
	
}
