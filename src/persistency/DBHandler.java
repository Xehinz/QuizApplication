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

/**
 * Facade-klasse van persistency package. DBHandler voorziet in methods om de
 * staat van het programma op te slaan en weer uit te lezen. Gebruik de setter
 * setUseCSV(boolean useCSV) om, in geval van opslag in tekst, het .csv formaat
 * in plaats van het .txt formaat te gebruiken.
 * 
 * @author Ben Vandenberk
 * @version 17/11/2014
 *
 */
public class DBHandler {

	private boolean useCSV;

	private DBStrategy dbStrategy;
	private OpdrachtCatalogus opdrachtCatalogus;
	private LeerlingContainer leerlingContainer;
	private QuizCatalogus quizCatalogus;

	/**
	 * Maakt een DBHandler object aan. Gebruik deze constructor als je enkel wil
	 * inlezen uit opslag
	 */
	public DBHandler() {
		opdrachtCatalogus = new OpdrachtCatalogus();
		leerlingContainer = new LeerlingContainer();
		quizCatalogus = new QuizCatalogus();
		dbStrategy = new TxtDB(useCSV);
	}

	/**
	 * Maakt een DBHandler object aan. Gebruik deze constructor voor
	 * wegschrijven of inlezen
	 * 
	 * @param opdrachtCatalogus
	 *            de OpdrachtCatalogus om weg te schrijven
	 * @param leerlingContainer
	 *            de LeerlingContainer om weg te schrijven
	 * @param quizCatalogus
	 *            de QuizCatalogus om weg te schrijven
	 */
	public DBHandler(OpdrachtCatalogus opdrachtCatalogus,
			LeerlingContainer leerlingContainer, QuizCatalogus quizCatalogus) {
		this.opdrachtCatalogus = opdrachtCatalogus;
		this.leerlingContainer = leerlingContainer;
		this.quizCatalogus = quizCatalogus;
		dbStrategy = new TxtDB(useCSV);
	}

	/**
	 * Method om de opgeslagen data in te lezen
	 * 
	 * @throws IOException
	 *             als er zich bij het inlezen een fout voordoet
	 */
	public void vulCatalogi() throws IOException {
		opdrachtCatalogus = new OpdrachtCatalogus(dbStrategy.leesOpdrachten());
		leerlingContainer = new LeerlingContainer(dbStrategy.leesLeerlingen());
		quizCatalogus = new QuizCatalogus(dbStrategy.leesQuizzen());
		koppelQuizzenAanOpdrachten();
		koppelLeerlingenAanQuizzen();
		koppelQuizDeelnamesAanQuizOpdrachten();
	}

	/**
	 * Method om de staat van het programma op te slaan
	 * 
	 * @throws IOException
	 *             als er zich bij het wegschrijven een fout voordoet
	 */
	public void saveCatalogi() throws IOException {
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

	/**
	 * Method om in te stellen of DBHandler met DB of tekst werkt. Omdat de
	 * andere klasses in het persistency package best onzichtbaar zijn voor de
	 * buitenwereld werken we met een enum: StorageStrategy
	 * 
	 * @param storageStrategy
	 *            de StorageStrategy (StorageStrategy.TEKST of
	 *            StorageStrategy.DATABASE)
	 */
	public void setDBStrategy(StorageStrategy storageStrategy) {
		switch (storageStrategy) {
		case TEKST:
			dbStrategy = new TxtDB(useCSV);			
			break;
		case DATABASE:
			dbStrategy = new MySQLDB();
			break;
		}
	}

	/**
	 * Haalt de OpdrachtCatalogus op
	 * 
	 * @return de OpdrachtCatalogus
	 */
	public OpdrachtCatalogus getOpdrachtCatalogus() {
		return opdrachtCatalogus;
	}

	/**
	 * Haalt de QuizCatalogus op
	 * 
	 * @return de QuizCatalogus
	 */
	public QuizCatalogus getQuizCatalogus() {
		return quizCatalogus;
	}

	/**
	 * Haalt de LeerlingContainer op
	 * 
	 * @return de LeerlingContainer
	 */
	public LeerlingContainer getLeerlingContainer() {
		return leerlingContainer;
	}

	/**
	 * Geef true mee om in .csv formaat te werken. Enkel van toepassing indien
	 * de StorageStrategy TEKST is
	 * 
	 * @param useCSV
	 *            true voor .csv, false voor .txt
	 */
	public void setUseCSV(boolean useCSV) {
		this.useCSV = useCSV;
		dbStrategy = new TxtDB(useCSV);
	}

	/**
	 * Deze method werkt enkel als ze pas wordt aangeroepen nadat de Catalogus
	 * en Container klasses zijn opgevuld.
	 */
	private void koppelQuizzenAanOpdrachten() throws IOException {
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

	/**
	 * Deze method werkt enkel als ze pas wordt aangeroepen nadat de Catalogus
	 * en Container klasses zijn opgevuld.
	 */
	private void koppelLeerlingenAanQuizzen() throws IOException {
		ArrayList<PseudoQuizDeelname> quizDeelnames = dbStrategy
				.leesQuizDeelnames();

		Quiz huidigeQuiz = null;
		Leerling huidigeLeerling = null;

		for (PseudoQuizDeelname quizDeelname : quizDeelnames) {
			huidigeQuiz = quizCatalogus.getQuiz(quizDeelname.getQuizID());
			huidigeLeerling = leerlingContainer.getLeerling(quizDeelname
					.getLeerlingID());

			QuizDeelname.koppelQuizAanLeerling(huidigeQuiz, huidigeLeerling,
					quizDeelname.getDeelnameDatum(), true);
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
				throw new IOException(
						"De QuizOpdracht en/of QuizDeelname werd niet gevonden");
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
