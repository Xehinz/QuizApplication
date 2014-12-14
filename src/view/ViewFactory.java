package view;

import java.util.Properties;

import view.viewInterfaces.IOpdrachtDeelnameViewFactory;
import view.viewInterfaces.IView;

public class ViewFactory {

	private Properties settings;

	public ViewFactory(Properties settings) {
		this.settings = settings;
	}

	public IView maakView(ViewType type) {
		switch (type) {
		case QuizDeelname:
			if (settings.getProperty("quizdeelname").equals("QuizDeelnameView")) {
				return new QuizDeelnameView();
			}
			break;
		case OverzichtScoresQuizzen:
			if (settings.getProperty("overzichtscoresquizzen").equals(
					"OverzichtScoresQuizzenView")) {
				return new OverzichtScoresQuizzenView();
			}
			break;
		case OverzichtScoresLeerlingen:
			if (settings.getProperty("overzichtscoresleerlingen").equals(
					"OverzichtScoresLeerlingenView")) {
				return new OverzichtScoresLeerlingenView();
			}
			break;
		case OverzichtScoresAntwoorden:
			if (settings.getProperty("overzichtscoresantwoorden").equals(
					"OverzichtScoresAntwoordenView")) {
				return new OverzichtScoresAntwoordenView();
			}
			break;
		case LoginView:
			switch (settings.getProperty("login")) {
			case "LoginView":
				return new LoginView();
			case "LoginView2":
				return new LoginView2();
			default:
				return null;
			}
		case MainLeerlingView:
			if (settings.getProperty("mainleerling").equals("MainLeerlingView")) {
				return new MainLeerlingView();
			}
			break;
		case MainLeraarView:
			if (settings.getProperty("mainleraar").equals("MainLeraarView")) {
				return new MainLeraarView();
			}
		case OpdrachtBeheerView:
			if (settings.getProperty("opdrachtbeheer").equals("OpdrachtBeheerView")) {
				return new OpdrachtBeheerView();
			}
		case OpdrachtAanpassingView:
			if (settings.getProperty("opdrachtaanpassing").equals("OpdrachtAanpassingView")) {
				return new OpdrachtAanpassingView();
			}
		case OpdrachtMeerkeuzeBeheerView:
			if (settings.getProperty("opdrachtmeerkeuzebeheer").equals("OpdrachtMeerkeuzeBeheerView")) {
				return new OpdrachtMeerkeuzeBeheerView();
			}
		case OpdrachtOpsommingBeheerView:
			if (settings.getProperty("opdrachtopsommingbeheerview").equals("OpdrachtOpsommingBeheerView")) {
				return new OpdrachtOpsommingBeheerView();
			}
		case OpdrachtReproductieBeheerView:
			if (settings.getProperty("opdrachtreproductiebeheerview").equals("OpdrachtReproductieBeheerView")) {
				return new OpdrachtReproductieBeheerView();
			}
		case BeheerLeerlingView:
			if (settings.getProperty("beheerleerlingview").equals("BeheerLeerlingView")) {
				return new BeheerLeerlingView();
			}
		case LeerlingAanpassingView:
			if (settings.getProperty("leerlingaanpassingview").equals("LeerlingAanpassingView")) {
				return new LeerlingAanpassingView();
			}
		}	
		return null;
	}

	public IOpdrachtDeelnameViewFactory maakOpdrachtDeelnameViewFactory() {
		switch (settings.getProperty("opdrachtdeelnameviewfactory")) {
		case "OpdrachtDeelnameViewFactory":
			return new OpdrachtDeelnameViewFactory();
		default:
			return null;
		}
	}

}
