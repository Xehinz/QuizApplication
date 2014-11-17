package persistency;

import java.io.IOException;
import java.util.ArrayList;

import model.Leerling;
import model.LeerlingContainer;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.OpdrachtCatalogus;
import model.Quiz;
import model.QuizCatalogus;
import model.QuizDeelname;
import model.QuizOpdracht;

public class DBHandler {

	private DBStrategy dbStrategy;
	private OpdrachtCatalogus opdrachtCatalogus;
	private LeerlingContainer leerlingContainer;
	private QuizCatalogus quizCatalogus;

	public DBHandler() {
		opdrachtCatalogus = new OpdrachtCatalogus();
		leerlingContainer = new LeerlingContainer();
		quizCatalogus = new QuizCatalogus();
		dbStrategy = new TxtDB();
	}

	public DBHandler(OpdrachtCatalogus opdrachtCatalogus,
			LeerlingContainer leerlingContainer, QuizCatalogus quizCatalogus) {
		this.opdrachtCatalogus = opdrachtCatalogus;
		this.leerlingContainer = leerlingContainer;
		this.quizCatalogus = quizCatalogus;
		dbStrategy = new TxtDB();
	}

	public void vulCatalogi() throws IOException {
		opdrachtCatalogus = new OpdrachtCatalogus(dbStrategy.leesOpdrachten());
		leerlingContainer = new LeerlingContainer(dbStrategy.leesLeerlingen());
		quizCatalogus = new QuizCatalogus(dbStrategy.leesQuizzen());
		koppelQuizzenAanOpdrachten();
		koppelLeerlingenAanQuizzen();
		koppelQuizDeelnamesAanQuizOpdrachten();
	}

	public void saveCatalogi() {
		dbStrategy.schrijfOpdrachten(opdrachtCatalogus.getOpdrachten());
		dbStrategy.schrijfLeerlingen(leerlingContainer.getLeerlingen());
		dbStrategy.schrijfQuizzen(quizCatalogus.getQuizzen());
		dbStrategy.schrijfQuizOpdrachten(opdrachtCatalogus
				.getAlleQuizOpdrachten());
		dbStrategy.schrijfQuizDeelnames(leerlingContainer
				.getAlleQuizDeelnames());
		dbStrategy.schrijfOpdrachtAntwoorden(leerlingContainer
				.getAlleOpdrachtAntwoorden());
	}

	public void setDBStrategy(StorageStrategy storageStrategy) {
		switch (storageStrategy) {
		case TEKST:
			dbStrategy = new TxtDB();
			break;
		case DATABASE:
			dbStrategy = new MySQLDB();
			break;
		}
	}

	public OpdrachtCatalogus getOpdrachtCatalogus() {
		return opdrachtCatalogus;
	}

	public QuizCatalogus getQuizCatalogus() {
		return quizCatalogus;
	}

	public LeerlingContainer getLeerlingContainer() {
		return leerlingContainer;
	}

	private void koppelQuizzenAanOpdrachten() {
		ArrayList<PseudoQuizOpdracht> quizOpdrachten = dbStrategy
				.leesQuizOpdrachten();

		Quiz huidigeQuiz = null;
		Opdracht huidigeOpdracht = null;

		for (PseudoQuizOpdracht quizOpdracht : quizOpdrachten) {
			huidigeQuiz = quizCatalogus.getQuiz(quizOpdracht.getQuizID());
			huidigeOpdracht = opdrachtCatalogus.getOpdracht(quizOpdracht
					.getOpdrachtID());

			QuizOpdracht.koppelOpdrachtAanQuiz(huidigeQuiz, huidigeOpdracht,
					quizOpdracht.getMaxScore());
		}
	}

	private void koppelLeerlingenAanQuizzen() {
		ArrayList<PseudoQuizDeelname> quizDeelnames = dbStrategy
				.leesQuizDeelnames();

		Quiz huidigeQuiz = null;
		Leerling huidigeLeerling = null;

		for (PseudoQuizDeelname quizDeelname : quizDeelnames) {
			huidigeQuiz = quizCatalogus.getQuiz(quizDeelname.getQuizID());
			huidigeLeerling = leerlingContainer.getLeerling(quizDeelname
					.getLeerlingID());

			QuizDeelname.koppelQuizAanLeerling(huidigeQuiz, huidigeLeerling,
					true);
		}
	}

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

		ArrayList<QuizOpdracht> quizOpdrachten = opdrachtCatalogus
				.getAlleQuizOpdrachten();
		ArrayList<QuizDeelname> quizDeelnames = leerlingContainer
				.getAlleQuizDeelnames();

		for (PseudoOpdrachtAntwoord opdrachtAntwoord : opdrachtAntwoorden) {
			for (QuizOpdracht quizOpdracht : quizOpdrachten) {
				if (quizOpdracht.getID() == opdrachtAntwoord
						.getQuizOpdrachtID()) {
					huidigeQuizOpdracht = quizOpdracht;
				}
			}
			for (QuizDeelname quizDeelname : quizDeelnames) {
				if (quizDeelname.getID() == opdrachtAntwoord
						.getQuizDeelnameID()) {
					huidigeQuizDeelname = quizDeelname;
				}
			}
			if (huidigeQuizDeelname == null || huidigeQuizOpdracht == null) {
				throw new IOException("De QuizOpdracht en/of QuizDeelname werd niet gevonden");
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
