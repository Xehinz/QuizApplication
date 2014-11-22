package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse van de Klasse QuizOpdracht
 *
 * @author Jef Bellens
 * @version 15/11/2014
 *
 */

public class QuizOpdrachtTest {

	OpdrachtCatalogus legeOpdrachtCatalogus, opdrachtCatalogusGemaaktMetLijstOpdrachten, gevuldeCatalogusZelfdeOpdrachten,
	gevuldeCatalogusAndereOpdrachten, tweedeLegeCatalogus;
	ArrayList<Opdracht> opdrachten;
	Opdracht opdracht1, opdracht2, opdracht3;
	Quiz quiz1, quiz2;
	QuizCatalogus quizCatalogus;
	ArrayList<Quiz> quizen;
	QuizOpdracht quizOpdracht;
	int maxScore;
	ArrayList<OpdrachtAntwoord> opdrachtAntwoord = quizOpdracht.getOpdrachtAntwoorden();
	
	@Before
	public void setUp() throws Exception {
		//maxScore = quizOpdracht.getMaxScore();
		
		quiz1 = new Quiz(Leraar.CHARLOTTE_NEVEN, "1ste quiz");
		quiz2 = new Quiz(Leraar.CHARLOTTE_NEVEN, "2de quiz");
		
		quizen = new ArrayList<Quiz>();
		
		quizCatalogus = new QuizCatalogus();
		quizCatalogus.addQuiz(quiz1);
		quizCatalogus.addQuiz(quiz2);
		
		legeOpdrachtCatalogus = new OpdrachtCatalogus();
		tweedeLegeCatalogus = new OpdrachtCatalogus();

		opdracht1 = new KlassiekeOpdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		opdracht2 = new KlassiekeOpdracht(OpdrachtCategorie.WETENSCHAPPEN, Leraar.MARIA_AERTS);
		opdracht3 = new KlassiekeOpdracht(OpdrachtCategorie.NEDERLANDS, Leraar.STEVEN_OPDEBEEK);

		opdrachten = new ArrayList<Opdracht>();

		opdrachten.add(opdracht1);
		opdrachten.add(opdracht2);

		opdrachtCatalogusGemaaktMetLijstOpdrachten = new OpdrachtCatalogus(opdrachten);

		gevuldeCatalogusZelfdeOpdrachten = new OpdrachtCatalogus();
		gevuldeCatalogusZelfdeOpdrachten.addOpdracht(opdracht1);
		gevuldeCatalogusZelfdeOpdrachten.addOpdracht(opdracht2);

		gevuldeCatalogusAndereOpdrachten = new OpdrachtCatalogus();
		gevuldeCatalogusAndereOpdrachten.addOpdracht(opdracht1);
		gevuldeCatalogusAndereOpdrachten.addOpdracht(opdracht3);
				
	}
	
	@Test
	public void testAttachOpdrachtToQuiz_KoppelOpdrachtAanQuiz_CorrecteKoppeling() {
		QuizOpdracht.koppelOpdrachtAanQuiz(quiz1, opdracht1, 5);
		//assertTrue("Quiz1 heeft juiste opdracht", quiz1.getOpdrachten().contains(quizOpdracht));
		assertTrue("Opdracht1 heeft juiste quiz", opdracht1.getQuizOpdrachten().contains(quiz1));
		QuizOpdracht.koppelOpdrachtAanQuiz(quiz2, opdracht2, 10);
		assertTrue("Quiz2 heeft juiste opdracht", quiz2.getOpdrachten().contains(opdracht2));
		assertTrue("Opdracht2 heeft juiste quiz", opdracht2.getQuizOpdrachten().contains(quiz2));
	}
	
	@Test
	public void testDetachOpdrachtFromQuiz_OntkoppelOpdrachtVanQuiz_CorrecteOntkoppeling() {
		quiz1.removeQuizOpdracht(quizOpdracht);
		opdracht1.removeQuizOpdracht(quizOpdracht);
		assertFalse("Correcte ontkoppeling", (quizOpdracht.getOpdracht().equals(opdracht1)));
		assertFalse("Correcte ontkoppeling", (quizOpdracht.getQuiz().equals(quiz1)));
	}
	
	@Test
	public void AddOpdrachtAntwoord_VoegOpdrachtAntwoordToe_CorrectToegevoegd() {
		assertFalse("Kopie van OpdrachtAntwoord is leeg" , (opdrachtAntwoord.isEmpty()));
		OpdrachtAntwoord opdrachtAntwoord1 = opdrachtAntwoord.get(0);
		assertTrue("Opdrachtantwoord correct toegevoegd", opdrachtAntwoord.add(opdrachtAntwoord1));
		
	}
	
	@Test
	public void GetGemiddeldeScore_VerkrijgGemiddeldeScore_CorrecteGemiddeldeScore() {
		double somScores = 0;
		for (OpdrachtAntwoord opdrachtAntwoord1 : opdrachtAntwoord) {
			somScores += opdrachtAntwoord1.getBehaaldeScore();
		}
		assertEquals("Correcte Gemiddelde Score", (somScores / opdrachtAntwoord.size()), quizOpdracht.getGemiddeldeScore());
	}
}
