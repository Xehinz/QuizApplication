package view;

import view.viewInterfaces.IOpdrachtDeelnameView;
import view.viewInterfaces.IOpdrachtDeelnameViewFactory;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class OpdrachtDeelnameViewFactory implements IOpdrachtDeelnameViewFactory {

	public OpdrachtDeelnameViewFactory() {
		
	}
	
	@Override
	public IOpdrachtDeelnameView maakOpdrachtDeelnameView(String type) {
		IOpdrachtDeelnameView iOpdrachtDeelnameView = null;
		switch (type) {
		case "KlassiekeOpdracht":		
			iOpdrachtDeelnameView = new DeelnameKlassiekOpsommingView();
			break;
		case "Opsomming":
			iOpdrachtDeelnameView = new DeelnameKlassiekOpsommingView();
			iOpdrachtDeelnameView.setAntwoordVeldToolTip("Scheid je antwoorden met ;");
			break;
		case "Meerkeuze":
			iOpdrachtDeelnameView = new DeelnameMeerkeuzeView();
			break;
		case "Reproductie":
			iOpdrachtDeelnameView = new DeelnameReproductieView();
			break;
		}
		return iOpdrachtDeelnameView;
	}

}
