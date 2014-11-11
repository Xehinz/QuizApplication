package persistency;

import java.io.IOException;
import java.util.ArrayList;

import model.Leerling;
import model.LeerlingContainer;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.OpdrachtCatalogus;
import model.QuizDeelname;
import model.QuizOpdracht;

public class DBHandler {

	private DBStrategy dbStrategy;
	private OpdrachtCatalogus opdrachtCatalogus;
	private LeerlingContainer leerlingContainer;

	/**
	 * Deze method werkt enkel als ze pas wordt aangeroepen nadat de Catalogus
	 * en Container klasses zijn opgevuld én nadat ook alle QuizDeelnames en
	 * QuizOpdrachten zijn ingelezen.
	 */
	private void koppelQuizDeelnamesAanQuizOpdrachten() throws IOException {
		ArrayList<PseudoOpdrachtAntwoord> opdrachtAntwoorden = dbStrategy
				.leesOpdrachtAntwoorden();

		QuizOpdracht huidigeQuizOpdracht = null;
		QuizDeelname huidigeQuizDeelname = null;

		ArrayList<Opdracht> opdrachten = opdrachtCatalogus.getOpdrachten();
		ArrayList<Leerling> leerlingen = leerlingContainer.getLeerlingen();

		for (PseudoOpdrachtAntwoord opdrachtAntwoord : opdrachtAntwoorden) {
			for (int i = 0; i < opdrachten.size()
					&& huidigeQuizOpdracht == null; i++) {
				huidigeQuizOpdracht = opdrachten.get(i).getQuizOpdracht(
						opdrachtAntwoord.getQuizOpdrachtID());
			}
			for (int i = 0; i < leerlingen.size()
					&& huidigeQuizDeelname == null; i++) {
				huidigeQuizDeelname = leerlingen.get(i).getQuizDeelname(
						opdrachtAntwoord.getQuizDeelnameID());
			}
			if (huidigeQuizDeelname == null || huidigeQuizOpdracht == null) {
				throw new IOException(
						"De opgeslagen QuizDeelnameID en/of QuizOpdrachtID werd(en) niet gevonden");
			}
			OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
					huidigeQuizDeelname, huidigeQuizOpdracht,
					opdrachtAntwoord.getAantalPogingen(),
					opdrachtAntwoord.getAntwoordTijd(),
					opdrachtAntwoord.getLaatsteAntwoord());
			huidigeQuizDeelname = null;
			huidigeQuizOpdracht = null;
		}
	}

}
