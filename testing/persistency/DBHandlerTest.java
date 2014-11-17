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
import model.QuizStatus;
import model.Reproductie;

import org.junit.Before;
import org.junit.Test;

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
		
		klassiek = new KlassiekeOpdracht("Waarom zijn de bananen krom?", "Daarom", 1, 20, OpdrachtCategorie.NEDERLANDS, Leraar.CHARLOTTE_NEVEN);
		klassiek.addHint("Denk niet te ver!");
		klassiek.addHint("Wat zouden je ouders antwoorden?");
		opsomming = new Opsomming("Geef de 5 kleinste priemgetallen in volgorde", "2;3;5;7;11", 2, 25, OpdrachtCategorie.WISKUNDE, Leraar.STEVEN_OPDEBEEK, true);
		opsomming.addHint("1 is geen priemgetal!");
		meerkeuze = new Meerkeuze("Welke stad ligt niet in Vlaanderen?", "Namen", 1, 10, OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.JOS_VERBEEK);
		((Meerkeuze)meerkeuze).setOpties("Namen;Gent;Antwerpen");
		reproductie = new Reproductie("Hoe maak je een croque monsieur?", "brood;boter;ham;kaas;bakken;toasten", 3, 1, 60, OpdrachtCategorie.WETENSCHAPPEN, Leraar.MARIA_AERTS);
		reproductie.addHint("Om in een pan te ... heb je ... nodig");
		
		opdrachtCatalogus = new OpdrachtCatalogus();
		opdrachtCatalogus.addOpdracht(klassiek);
		opdrachtCatalogus.addOpdracht(opsomming);
		opdrachtCatalogus.addOpdracht(meerkeuze);
		opdrachtCatalogus.addOpdracht(reproductie);
		
		quizA = new Quiz(Leraar.MIEKE_WITTEMANS, "Priemgetallen en steden", false);
		quizA.setDoelLeerjaren(1,2);
		quizA.setQuizStatus(QuizStatus.OPENGESTELD);
		quizB = new Quiz(Leraar.CHARLOTTE_NEVEN, "Bananen en croque monsieurs", true);
		quizB.setDoelLeerjaren(5,6);
		quizB.setQuizStatus(QuizStatus.OPENGESTELD);
		
		quizCatalogus = new QuizCatalogus();
		quizCatalogus.addQuiz(quizA);
		quizCatalogus.addQuiz(quizB);	
		
		QuizOpdracht A1 = QuizOpdracht.koppelOpdrachtAanQuiz(quizA, opsomming, 10);
		QuizOpdracht A2 = QuizOpdracht.koppelOpdrachtAanQuiz(quizA, meerkeuze, 20);
		QuizOpdracht B1 = QuizOpdracht.koppelOpdrachtAanQuiz(quizB, klassiek, 10);
		QuizOpdracht B2 = QuizOpdracht.koppelOpdrachtAanQuiz(quizB, reproductie, 15);
		
		QuizDeelname BenAanA = QuizDeelname.koppelQuizAanLeerling(quizA, Ben);
		QuizDeelname BertAanA = QuizDeelname.koppelQuizAanLeerling(quizA, Bert);
		QuizDeelname AdriaanAanB = QuizDeelname.koppelQuizAanLeerling(quizB, Adriaan);
		QuizDeelname JohanAanB = QuizDeelname.koppelQuizAanLeerling(quizB, Johan);
		
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BenAanA, A1, 3, 35, "2;3;5;7;11");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BenAanA, A2, 1, 8, "Namen");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BertAanA, A1, 1, 15, "2;3;5;7;11");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(BertAanA, A2, 1, 4, "Namen");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(AdriaanAanB, B1, 2, 15, "Daarom");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(AdriaanAanB, B2, 2, 40, "boter pan brood");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(JohanAanB, B1, 1, 5, "Daarom");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(JohanAanB, B2, 1, 30, "kaas ham boter");
		
		//Quizzen sluiten
		quizA.setQuizStatus(QuizStatus.AFGESLOTEN);
		quizB.setQuizStatus(QuizStatus.AFGESLOTEN);
		
		dbHandler = new DBHandler(opdrachtCatalogus, leerlingContainer, quizCatalogus);
		dbHandler.setUseCSV(true);
		
		oorspronkelijkeProgrammaString = programmaString();	
	}

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
		for (QuizDeelname quizDeelname : leerlingContainer.getAlleQuizDeelnames()) {
			programmaString += quizDeelname + scheiding();
		}
		
		programmaString += "\nAlle QuizOpdrachten in het programma:\n\n";		
		for(QuizOpdracht quizOpdracht : opdrachtCatalogus.getAlleQuizOpdrachten()) {
			programmaString += quizOpdracht + scheiding();
		}
		
		programmaString += "\nAlle OpdrachtAntwoorden in het programma:\n\n";
		for(OpdrachtAntwoord opdrachtAntwoord : leerlingContainer.getAlleOpdrachtAntwoorden()) {
			programmaString += opdrachtAntwoord + scheiding();
		}
		
		return programmaString;
	}
	
	private String scheiding() {
		return "\n----------------------------------------------------------------------------------------------------\n";
	}
}
