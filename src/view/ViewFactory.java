package view;

import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
