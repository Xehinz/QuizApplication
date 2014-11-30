package persistency;

import static org.junit.Assert.*;

import java.io.IOException;

import model.KlassiekeOpdracht;
import model.Leerling;
import model.LeerlingContainer;
import model.Leraar;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.OpdrachtCatalogus;
import model.OpdrachtCategorie;
import model.Opsomming;
import model.Quiz;
import model.QuizCatalogus;
import model.QuizDeelname;
import model.QuizOpdracht;
import model.Reproductie;
import model.quizStatus.Afgesloten;
import model.quizStatus.Afgewerkt;
import model.quizStatus.InConstructie;
import model.quizStatus.LaatsteKans;
import model.quizStatus.Opengesteld;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Ben Vandenberk
 *
 */
public class DBHandlerTest {

	private DBHandler dbHandler;

	private LeerlingContainer leerlingContainer;
	private QuizCatalogus quizCatalogus;
	private OpdrachtCatalogus opdrachtCatalogus;

	private Leerling Ben, Bert, Adriaan, Jef, Johan;
	private Opdracht klassiek, opsomming, meerkeuze, reproductie;
	private Quiz quizA, quizB;

	private String oorspronkelijkeProgrammaString;

	@Before
	public void setUp() {
		Ben = new Leerling("Ben", "Vandenberk", 1);
		Bert = new Leerling("Bert", "Neyt", 2);
		Adriaan = new Leerling("Adriaan", "Kuipers", 6);
		Jef = new Leerling("Jef", "Bellens", 4);
		Johan = new Leerling("Johan", "Boogers", 5);

		leerlingContainer = new LeerlingContainer();
		leerlingContainer.addLeerling(Ben);
		leerlingContainer.addLeerling(Bert);
		leerlingContainer.addLeerling(Adriaan);
		leerlingContainer.addLeerling(Jef);
		leerlingContainer.addLeerling(Johan);
		
		// Zaken wijzigen tijdens sessie
		Ben.setLeerjaar(2);
		Bert.setLeerlingFamilienaam("Neyts");

		klassiek = new KlassiekeOpdracht("Waarom zijn de bananen krom?",
				"Daarom", 0, 20, OpdrachtCategorie.NEDERLANDS,
				Leraar.CHARLOTTE_NEVEN);
		klassiek.addHint("Denk niet te ver!");
		klassiek.addHint("Wat zouden je ouders antwoorden?");
		opsomming = new Opsomming(
				"Geef de 5 kleinste priemgetallen in volgorde", "2;3;5;7;11",
				0, 25, OpdrachtCategorie.WISKUNDE, Leraar.STEVEN_OPDEBEEK, true);
		opsomming.addHint("1 is geen priemgetal!");
		meerkeuze = new Meerkeuze("Welke stad ligt niet in Vlaanderen?",
				"Namen", 1, 0, OpdrachtCategorie.AARDRIJKSKUNDE,
				Leraar.JOS_VERBEEK);
		((Meerkeuze) meerkeuze).setOpties("Namen;Gent;Antwerpen");
		reproductie = new Reproductie("Hoe maak je een croque monsieur?",
				"brood;boter;ham;kaas;bakken;toasten", 3, 1, 60,
				OpdrachtCategorie.WETENSCHAPPEN, Leraar.MARIA_AERTS);
		reproductie.addHint("Om in een pan te ... heb je ... nodig");

		opdrachtCatalogus = new OpdrachtCatalogus();
		opdrachtCatalogus.addOpdracht(klassiek);
		opdrachtCatalogus.addOpdracht(opsomming);
		opdrachtCatalogus.addOpdracht(meerkeuze);
		opdrachtCatalogus.addOpdracht(reproductie);
		
		// Zaken wijzigen tijdens sessie
		klassiek.setMaxAantalPogingen(2);
		((KlassiekeOpdracht)klassiek).setJuisteAntwoord("daarom");
		opsomming.setMaxAntwoordTijd(100);
		meerkeuze.setOpdrachtCategorie(OpdrachtCategorie.WETENSCHAPPEN);
		reproductie.setVraag("Hoe maak je croque monsieurs?");

		quizA = new Quiz(Leraar.MIEKE_WITTEMANS, "Priemgetallen en steden",
				false);
		quizA.setDoelLeerjaren(1, 2);
		quizA.setQuizStatus(new Opengesteld());
		quizB = new Quiz(Leraar.CHARLOTTE_NEVEN, "Bananen en croque monsieurs",
				false);
		quizB.setDoelLeerjaren(5, 6);
		quizB.setQuizStatus(new Opengesteld());

		quizCatalogus = new QuizCatalogus();
		quizCatalogus.addQuiz(quizA);
		quizCatalogus.addQuiz(quizB);
		
		// Zaken wijzigen tijdens sessie
		quizA.setIsUniekeDeelname(true);
		quizA.setIsTest(true);
		quizB.setDoelLeerjaren(4,5,6);
		quizB.setQuizStatus(new LaatsteKans());
		quizB.setOnderwerp("Eten");		

		QuizOpdracht A1 = QuizOpdracht.koppelOpdrachtAanQuiz(quizA, opsomming,
				10);
		QuizOpdracht A2 = QuizOpdracht.koppelOpdrachtAanQuiz(quizA, meerkeuze,
				20);
		QuizOpdracht B1 = QuizOpdracht.koppelOpdrachtAanQuiz(quizB, klassiek,
				10);
		QuizOpdracht B2 = QuizOpdracht.koppelOpdrachtAanQuiz(quizB,
				reproductie, 15);

		QuizDeelname BenAanA = QuizDeelname.koppelQuizAanLeerling(quizA, Ben);
		QuizDeelname BertAanA = QuizDeelname.koppelQuizAanLeerling(quizA, Bert);
		QuizDeelname AdriaanAanB = QuizDeelname.koppelQuizAanLeerling(quizB,
				Adriaan);
		QuizDeelname JohanAanB = QuizDeelname.koppelQuizAanLeerling(quizB,
				Johan);

		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BenAanA, A1, 3, 35,
				"2;3;5;7;11");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BenAanA, A2, 1, 8,
				"Namen");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BertAanA, A1, 1, 15,
				"2;3;5;7;11");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BertAanA, A2, 1, 4,
				"Namen");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(AdriaanAanB, B1, 2,
				15, "Daarom");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(AdriaanAanB, B2, 2,
				40, "boter pan brood");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(JohanAanB, B1, 1, 5,
				"Daarom");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(JohanAanB, B2, 1,
				30, "kaas ham boter");

		// Quizzen sluiten
		quizA.setQuizStatus(new Afgesloten());
		quizB.setQuizStatus(new Afgesloten());

		dbHandler = new DBHandler(opdrachtCatalogus, leerlingContainer,
				quizCatalogus);
		dbHandler.setUseCSV(false);

		oorspronkelijkeProgrammaString = programmaString();
	}

	/**
	 * Alle toStrings() van alle objecten geven een volledig overzicht van het
	 * object, met alle variabelen.
	 *
	 * In de test wordt een sessie met ons programma gesimuleerd en van de
	 * huidige staat van het programma een String-representatie gemaakt.
	 * Vervolgens wordt DBHandler gebruikt om de staat van het programma te
	 * saven en weer op te laden.
	 *
	 * De test slaagt als de oorspronkelijke staat van het programma (een String
	 * dus) identiek is aan de opgeladen staat
	 */
	@Test
	public void testDBHandler_StaatProgrammaNaLezen_IsDezelfdeAlsVoorSchrijven() {
		try {
			dbHandler.saveCatalogi();
			dbHandler.vulCatalogi();
			leerlingContainer = dbHandler.getLeerlingContainer();
			opdrachtCatalogus = dbHandler.getOpdrachtCatalogus();
			quizCatalogus = dbHandler.getQuizCatalogus();
		} catch (IOException Iex) {
			Iex.printStackTrace();
		}
		assertEquals(oorspronkelijkeProgrammaString, programmaString());
	}

	private String programmaString() {
		String programmaString = leerlingContainer.toString() + "\n";
		programmaString += opdrachtCatalogus.toString() + "\n";
		programmaString += quizCatalogus.toString() + "\n";

		programmaString += "Alle QuizDeelnames in het programma:\n\n";
		for (QuizDeelname quizDeelname : leerlingContainer
				.getAlleQuizDeelnames()) {
			programmaString += quizDeelname + scheiding();
		}

		programmaString += "\nAlle QuizOpdrachten in het programma:\n\n";
		for (QuizOpdracht quizOpdracht : opdrachtCatalogus
				.getAlleQuizOpdrachten()) {
			programmaString += quizOpdracht + scheiding();
		}

		programmaString += "\nAlle OpdrachtAntwoorden in het programma:\n\n";
		for (OpdrachtAntwoord opdrachtAntwoord : leerlingContainer
				.getAlleOpdrachtAntwoorden()) {
			programmaString += opdrachtAntwoord + scheiding();
		}

		return programmaString;
	}

	private String scheiding() {
		return "\n----------------------------------------------------------------------------------------------------\n";
	}
}
