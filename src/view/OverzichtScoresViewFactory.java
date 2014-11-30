package view;

import view.viewInterfaces.IOverzichtScoresView;
import view.viewInterfaces.IOverzichtScoresViewFactory;

public class OverzichtScoresViewFactory implements IOverzichtScoresViewFactory {

	/**
	 * Geeft een IOverzichtScoresView van het juiste type terug. Opties zijn "quiz", "leerling" en "antwoord"	 * 
	 */
	@Override
	public IOverzichtScoresView maakOverzichtScoresView(String type) {
		IOverzichtScoresView overzichtScoresView = null;
		switch(type) {
		case "quiz":
			overzichtScoresView = new OverzichtScoresQuizzenView();
			break;
		case "leerling":
			overzichtScoresView = new OverzichtScoresLeerlingenView();
			break;
		case "antwoord":
			overzichtScoresView = new OverzichtScoresAntwoordenView();
			break;
		}
		return overzichtScoresView;
	}

}
