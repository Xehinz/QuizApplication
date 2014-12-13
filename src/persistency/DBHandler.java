package persistency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
 * staat van het programma op te slaan en weer uit te lezen. Gebruik de method
 * setDBStrategy(StorageStrategy storageStrategy) om een StorageStrategy in te
 * stellen. Keuze tussen StorageStrategy.TEKST, StorageStrategy.CSV en
 * StorageStrategy.DATABASE. DBHandler werkt niet als er geen StorageStrategy
 * ingesteld wordt.
 * 
 * @author Ben Vandenberk
 * @version 17/11/2014
 *
 */
public class DBHandler implements Observer {

	private DBStrategy dbStrategy;
	private OpdrachtCatalogus opdrachtCatalogus;
	private LeerlingContainer leerlingContainer;
	private QuizCatalogus quizCatalogus;
	private StorageStrategy storageStrategyBijStart,
			storageStrategyBijAfsluiten;
	private String connectionString;

	/**
	 * Maakt een DBHandler object aan. Gebruik deze constructor als je enkel wil
	 * inlezen uit opslag
	 */
	public DBHandler() {
		opdrachtCatalogus = new OpdrachtCatalogus();
		leerlingContainer = new LeerlingContainer();
		quizCatalogus = new QuizCatalogus();
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
	}

	/**
	 * Method om de opgeslagen data in te lezen
	 * 
	 * @throws IOException
	 *             als er zich bij het inlezen een fout voordoet
	 * @throws NullPointerException
	 *             als er geen StorageStrategy is ingesteld met behulp van de
	 *             method setDBStrategy(StorageStrategy storageStrategy)
	 */
	public void vulCatalogi() throws IOException, NullPointerException {
		if (dbStrategy == null) {
			throw new NullPointerException("Geen StorageStrategy ingesteld");
		}
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
	 * @throws NullPointerException
	 *             als er geen StorageStrategy is ingesteld met behulp van de
	 *             method setDBStrategy(StorageStrategy storageStrategy)
	 */
	public void saveCatalogi() throws IOException, NullPointerException {
		if (dbStrategy == null) {
			throw new NullPointerException("Geen StorageStrategy ingesteld");
		}

		boolean storageStrategySwitched = !(storageStrategyBijAfsluiten == null || storageStrategyBijStart == storageStrategyBijAfsluiten);
		for (int i = 2; i > 0; i--) {
			if (storageStrategySwitched) {
				if (i == 1) {
					setDBStrategy(storageStrategyBijAfsluiten);
				}
			} else {
				if (i == 1) {
					break;
				}
			}
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

	}

	/**
	 * Method om in te stellen of DBHandler met DB of tekst werkt. Omdat de
	 * andere klasses in het persistency package best onzichtbaar zijn voor de
	 * buitenwereld werken we met een enum: StorageStrategy
	 * 
	 * @param storageStrategy
	 *            de StorageStrategy (StorageStrategy.TEKST of
	 *            StorageStrategy.CSV of StorageStrategy.DATABASE)
	 * @throws IllegalStateException
	 *             als er nog geen connection string is ingesteld
	 */
	public void setDBStrategy(StorageStrategy storageStrategy)
			throws IllegalStateException {
		if (storageStrategyBijStart == null) {
			storageStrategyBijStart = storageStrategy;
		}
		switch (storageStrategy) {
		case TEKST:
			dbStrategy = new TxtDB(false);
			break;
		case CSV:
			dbStrategy = new TxtDB(true);
			break;
		case DATABASE:
			if (connectionString == null) {
				throw new IllegalStateException(
						"Er moet een connection string ingesteld zijn");
			}
			dbStrategy = new MySQLDB(connectionString);
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
	 * Stelt de connection string in
	 * 
	 * @param connectionString
	 *            de connection string
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	@Override
	public void update(Observable observable, Object dataObject) {
		if (dataObject instanceof StorageStrategy) {
			storageStrategyBijAfsluiten = (StorageStrategy) dataObject;
		}
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

			QuizOpdracht.koppelOpdrachtAanQuiz(quizOpdracht.getID(),
					huidigeQuiz, huidigeOpdracht, quizOpdracht.getMaxScore(),
					true);
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

			QuizDeelname.koppelQuizAanLeerling(
					quizDeelname.getQuizDeelnameID(), huidigeQuiz,
					huidigeLeerling, quizDeelname.getDeelnameDatum(), true);
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
				if (quizOpdracht.getID().equals(
						opdrachtAntwoord.getQuizOpdrachtID())) {
					huidigeQuizOpdracht = quizOpdracht;
				}
			}
			for (QuizDeelname quizDeelname : quizDeelnames) {
				if (quizDeelname.getID().equals(
						opdrachtAntwoord.getQuizDeelnameID())) {
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
