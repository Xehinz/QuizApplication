package view.viewInterfaces;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ListModel;

import model.Leraar;
import model.OpdrachtCategorie;

public interface IOpdrachtAanpassingView extends IView {
	
	public void setVraag(String vraag);

	public String getVraag();

	public void setJuisteAntwoord(String juisteAntwoord);

	public String getJuisteAntwoord();

	public void setHints(ArrayList<String> hints);

	public ArrayList<String> getHints();

	public String getHint();

	public void setMaxAantalPogingen(String maxAantalPogingen);

	public int getMaxAantalPogingen();

	public void setMaxAntwoordTijd(String maxAntwoordTijd);

	public int getMaxAntwoordTijd();

	public OpdrachtCategorie getOpdrachtCategorie();

	public void setOpdrachtCategorie(OpdrachtCategorie oc);

	public void NieuweHintKnopActionListener(ActionListener listener);
	
	public void VerwijderHintKnopActionListener(ActionListener listener);

	public void OpslaanKnopActionListener(ActionListener listener);

	public void setAuteur(Leraar leraar);

	public void disableAanpassen();

	public void toonErrorMessage(String boodschap, String titel);
	
	public void setHintListModel(ListModel<String> listModel);
	
	public int getGeselecteerdeHintIndex();


}
