package view.viewInterfaces;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionListener;

import model.Opdracht;

public interface IOpdrachtBeheerView extends IView {
	
	public void setOpdrachten(ArrayList<Opdracht> opdrachten);
	
	public void setOpdrachtCategorie();

	public Opdracht getGeselecteerdeOpdracht();

	public String getOpdrachtCategorie();

	public void setListCellRenderer(ListCellRenderer<Opdracht> LCR);

	public void addNieuweKlassiekeKnopActionListener(ActionListener listener);

	public void addNieuweMeerkeuzeKnopActionListener(ActionListener listener);

	public void addNieuweOpsommingKnopActionListener(ActionListener listener);

	public void addNieuweReproductieKnopActionListener(ActionListener listener);

	public void addPasOpdrachtAanKnopActionListener(ActionListener listener);

	public void addVerwijderOpdrachtKnopActionListener(ActionListener listener);

	public void addBekijkDetailsKnopActionListener(ActionListener listener);

	public void addSelecteerCategorieActionlistener(ActionListener listener);

	public void addListSelectionListener(ListSelectionListener listener);

	public void disableAanpassen(boolean b);
	
	public void toonErrorMessage(String boodschap, String titel);

}
