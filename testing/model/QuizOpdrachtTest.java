package model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import model.quizStatus.Opengesteld;
import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse van de Klasse QuizOpdracht
 *
 * @author Jef Bellens & Tim Cool
 * @version 23/11/2014
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
	
	@Before
	public void setUp() throws Exception {
		
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

		quizOpdracht = QuizOpdracht.koppelOpdrachtAanQuiz(quiz1, opdracht1, 0);
				
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
		assertTrue("Opdracht1 heeft juiste quiz", opdracht1.getQuizOpdrachten().contains(quizOpdracht));
		quizOpdracht = QuizOpdracht.koppelOpdrachtAanQuiz(quiz2, opdracht2, 10);
		assertTrue("Quiz2 heeft juiste opdracht", quiz2.getOpdrachten().contains(opdracht2));
		assertTrue("Opdracht2 heeft juiste quiz", opdracht2.getQuizOpdrachten().contains(quizOpdracht));
	}
	
	@Test
	public void testDetachOpdrachtFromQuiz_OntkoppelOpdrachtVanQuiz_CorrecteOntkoppeling() {		
		quiz1.removeQuizOpdracht(quizOpdracht);
		opdracht1.removeQuizOpdracht(quizOpdracht);
		assertFalse("Correcte ontkoppeling", quiz1.getQuizOpdrachten().contains(quizOpdracht));
		assertFalse("Correcte ontkoppeling", opdracht1.getQuizOpdrachten().contains(quizOpdracht));
	}
	
	@Test
	public void testAddOpdrachtAntwoord_VoegOpdrachtAntwoordToe_CorrectToegevoegd() {
		Leerling leerling = new Leerling("Lolli", "Pop", 1);
		quiz1.setQuizStatus(new Opengesteld());
		QuizDeelname quizDeelname = QuizDeelname.koppelQuizAanLeerling(quiz1, leerling);		
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 10, 10, "HUDUUH");
		quizOpdracht.addOpdrachtAntwoord(opdrachtAntwoord);
		assertTrue("", quizOpdracht.getOpdrachtAntwoorden().contains(opdrachtAntwoord));
	}
	
	@Test
	public void testGetGemiddeldeScore_VerkrijgGemiddeldeScore_CorrecteGemiddeldeScore() {
		Leerling leerling = new Leerling("Lolli", "Pop", 1);
		Leerling leerling1 = new Leerling("Jan", "Jansen", 2);
		QuizOpdracht quizOpdracht1 = QuizOpdracht.koppelOpdrachtAanQuiz(quiz2, opdracht2, 5);
		quiz1.setQuizStatus(new Opengesteld());
		quiz2.setQuizStatus(new Opengesteld());
		QuizDeelname quizDeelname = QuizDeelname.koppelQuizAanLeerling(quiz1, leerling);
		QuizDeelname quizDeelname1 = QuizDeelname.koppelQuizAanLeerling(quiz2, leerling1);
		OpdrachtAntwoord opdrachtAntwoord2 = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 5, 5, "Audi");
		OpdrachtAntwoord opdrachtAntwoord3 = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname1, quizOpdracht1, 6, 6, "Mercedes");
		ArrayList<OpdrachtAntwoord> opdrachtAntwoorden;
		opdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		opdrachtAntwoorden.add(maxScore, opdrachtAntwoord2);
		opdrachtAntwoorden.add(maxScore, opdrachtAntwoord3);
		double somScores = 20;
		for ( OpdrachtAntwoord opdrachtAntwoord1 : opdrachtAntwoorden) {
			somScores += opdrachtAntwoord1.getBehaaldeScore();
		}
		assertEquals(10 , (somScores / opdrachtAntwoorden.size()), quizOpdracht.getGemiddeldeScore());
	}
	
	@Test
	public void testCompareTo_ZelfdeQuizEnOpdracht_Geeft0() {
		assertEquals(0, opdracht1.compareTo(quizOpdracht.getOpdracht()));
		assertEquals(0, quiz1.compareTo(quizOpdracht.getQuiz()));
	}
	
	@Test
	public void testEquals_GeenQuizOpdracht_GeeftFalse() {
		assertFalse(quizOpdracht.equals(null));
		assertFalse(quizOpdracht.equals(10));
		assertFalse(quizOpdracht.equals(50));
		assertTrue(quizOpdracht.equals(quizOpdracht));
//		if (this.opdracht1.equals(other.getOpdracht())){
//			assertFalse(quizOpdracht.equals(1));
//		}
	}
	
	@Test
	public void testToString_GeenParam_IsOk() {
		String result = "QuizOpdracht [ID=" + quizOpdracht.getID() + "] - Maximale score: " + maxScore;
		result += "\nDIE\n" + opdracht1.toString().replaceAll("(?m)^", "\t");;
		result += "\nKOPPELT AAN\n" + quiz1.toString().replaceAll("(?m)^", "\t");;
		assertEquals(result , quizOpdracht.toString());
	}
}
