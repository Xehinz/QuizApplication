package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.quizStatus.Afgewerkt;
import model.quizStatus.InConstructie;

import org.junit.Before;
import org.junit.Test;

import util.datumWrapper.*;

/**
 *
 * @author johan, adriaan
 * @version 21/11/2014
 */
public class QuizTest {

	private Quiz myQuiz, otherQuiz, quizMet3Opdrachten;
	private Opdracht opdracht, opdracht2, opdracht3;

	@Before
	public void setUp() throws Exception {
		myQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "nieuwe quiz");
		otherQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "andere quiz");
		quizMet3Opdrachten = new Quiz(Leraar.CHARLOTTE_NEVEN, "Quiz met 3 opdrachten");
		opdracht = new KlassiekeOpdracht(OpdrachtCategorie.NEDERLANDS, Leraar.CHARLOTTE_NEVEN);
		opdracht2 = new KlassiekeOpdracht(OpdrachtCategorie.WETENSCHAPPEN, Leraar.CHARLOTTE_NEVEN);
		opdracht3 = new KlassiekeOpdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.JOS_VERBEEK);
		
		QuizOpdracht.koppelOpdrachtAanQuiz(quizMet3Opdrachten, opdracht, 10);
		QuizOpdracht.koppelOpdrachtAanQuiz(quizMet3Opdrachten, opdracht2, 5);
		QuizOpdracht.koppelOpdrachtAanQuiz(quizMet3Opdrachten, opdracht3, 15);
	}

	@Test
	public void testQuiz_GeefOnderwerp_IsOK() {
		Quiz raarOnderwerp = new Quiz(Leraar.CHARLOTTE_NEVEN, "raar onderwerp");
		assertTrue("Het onderwerp is hetzelfde", raarOnderwerp.getOnderwerp() == "raar onderwerp");
	}

	@Test
	public void testQuiz_getStatus_IsOK() {
		Quiz isStatus = new Quiz(Leraar.CHARLOTTE_NEVEN, "een quiz met een status", false);
		assertTrue("Deze status is bij initialisatie gelijk", isStatus.getQuizStatus() instanceof InConstructie);

		isStatus = new Quiz(Leraar.CHARLOTTE_NEVEN, "een andere quiz zonder status bij initialisatie");
		isStatus.setQuizStatus(new Afgewerkt());
		assertTrue("Deze status is bij set-method gelijk", isStatus.getQuizStatus() instanceof Afgewerkt);
	}
	
	@Test
	public void testQuiz_IsTest_IsOK() {
		Quiz isTest = new Quiz(Leraar.CHARLOTTE_NEVEN, "is test");
		isTest.setIsTest(true);

		assertTrue("Deze quiz is een test", isTest.getIsTest());
	}

	@Test
	public void testQuiz_IsUniekeDeelname_IsOK() {
		Quiz isUniekeDeelname = new Quiz(Leraar.CHARLOTTE_NEVEN, "is uniekedeelname", true);
		assertTrue("Een unieke deelname is een constraint (bij constructor)", isUniekeDeelname.getIsUniekeDeelname());

		isUniekeDeelname = new Quiz(Leraar.CHARLOTTE_NEVEN, "is uniekedeelname");
		isUniekeDeelname.setIsUniekeDeelname(false);
		assertFalse("Een unieke deelname is een constraint (bij setter)", isUniekeDeelname.getIsUniekeDeelname());
	}

	@Test
	public void test_getAanmaakDatum_isOK() {
		Datum datum = new Datum();
		assertEquals("Aanmaakdatum is datum van aanmaak", myQuiz.getAanmaakDatum(), datum);
	}
	
	@Test
	public void test_getAuteur_isOK() {
		Leraar leraar = Leraar.CHARLOTTE_NEVEN;
		assertEquals("getAuteur geeft auteur", myQuiz.getAuteur(), leraar);
	}
	
	@Test
	public void test_getMaxScore_isOK() {
		//TODO;
	}
	
	@Test
	public void test_getGemiddeldeScore_isOK() {
		//TODO;
	}
	
	@Test
	public void test_setDoeljaren_isOK() {
		//TODO;
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_setDoeljaren_buiten_scope_throwsIllegalArgumentException() {
		myQuiz.setDoelLeerjaren(0);
	}
	
	@Test
	public void test_getDoelJaren_isOK() {
		//TODO;
	}
	
	@Test
	public void test_setOnderwerp_isOK() {
		//TODO;
	}
	
	@Test
	public void test_setQuizStatus_isOK() {
		//TODO;
	}
	
	@Test
	public void test_isGeldigLeerjaar_true_false() {
		//TODO;
	}
		
	@Test
	public void test_isDeelnameMogelijk_isOK() {
		//TODO;
	}
	
	@Test
	public void test_isVerwijderbaar_true_false() {
		//TODO;
	}
	
	@Test
	public void test_ToStirng_isOK() {
		//TODO;
	}
	
	@Test
	public void test_toString_isOK() {
		//TODO;
	}
	
	@Test
	public void test_compareTo_isOK() {
		//TODO;
	}
	
	@Test
	public void test_equals_true_false() {
		//TODO;
	}
	
	@Test
	public void testVerplaatsOpdrachtEenHoger_OpdrachtOpTweedePlaats_KomtOpEerstePlaats() {
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(0).equals(opdracht));
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(1).equals(opdracht2));
		quizMet3Opdrachten.verplaatsOpdrachtEenHoger(opdracht2);
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(0).equals(opdracht2));
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(1).equals(opdracht));
	}
	
	@Test
	public void testVerplaatsOpdrachtEenHoger_OpdrachtOpEerstePlaats_KomtOpLaatsePlaats() {
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(0).equals(opdracht));
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(1).equals(opdracht2));
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(2).equals(opdracht3));
		quizMet3Opdrachten.verplaatsOpdrachtEenHoger(opdracht);
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(0).equals(opdracht2));
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(1).equals(opdracht3));
		assertTrue(quizMet3Opdrachten.getOpdrachten().get(2).equals(opdracht));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testVerplaatsOpdrachtEenHoger_OpdrachtDieNietInQuizZit_ThrowsIllegalArgumentException() {
		Opdracht opdrachtNietInQuiz = new KlassiekeOpdracht(OpdrachtCategorie.WISKUNDE, Leraar.STEVEN_OPDEBEEK);
		quizMet3Opdrachten.verplaatsOpdrachtEenHoger(opdrachtNietInQuiz);
	}
	
	@Test
	public void testQuiz_getOpdrachtenVanEenCloneQuiz_EqualsEenCloneQuiz_IsOK() {
		Quiz aQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "new quiz");
		Quiz anotherQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "another quiz");
		Opdracht aOpdracht = new KlassiekeOpdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);

		QuizOpdracht.koppelOpdrachtAanQuiz(aQuiz, aOpdracht, 5);
		QuizOpdracht.koppelOpdrachtAanQuiz(aQuiz, aOpdracht, 10);
		// QuizOpdracht aQuizOpdracht = new QuizOpdracht(aQuiz, aOpdracht, 5);
		// QuizOpdracht anotherQuizOpdracht = new QuizOpdracht(aQuiz, aOpdracht,
		// 10);

		// aQuiz.addQuizOpdracht(aQuizOpdracht);
		// aQuiz.addQuizOpdracht(anotherQuizOpdracht);

		anotherQuiz = aQuiz.clone();

		assertEquals("Deze quiz-opdrachten zijn dezelfde", aQuiz.getOpdrachten(), anotherQuiz.getOpdrachten());
		assertTrue("Deze quizen zijn gelijk", aQuiz.equals(anotherQuiz));
	}

	@Test
	public void testCompareTo_GrotereQuizOpBasisVanAantalOpdrachtenAlsArgument_GeeftNegatief() {
		QuizOpdracht.koppelOpdrachtAanQuiz(myQuiz, opdracht, 10);
		QuizOpdracht.koppelOpdrachtAanQuiz(myQuiz, opdracht2, 10);
		
		QuizOpdracht.koppelOpdrachtAanQuiz(otherQuiz, opdracht, 10);
		
		assertTrue(otherQuiz.compareTo(myQuiz) < 0);
	}
	
	@Test
	public void testCompareTo_KleinerQuizOpBasisVanAantalOpdrachtenAlsArgument_GeeftPositief() {
		QuizOpdracht.koppelOpdrachtAanQuiz(myQuiz, opdracht, 10);
		QuizOpdracht.koppelOpdrachtAanQuiz(myQuiz, opdracht2, 10);
		
		QuizOpdracht.koppelOpdrachtAanQuiz(otherQuiz, opdracht, 10);
		
		assertTrue(myQuiz.compareTo(otherQuiz) > 0);
	}
	
	@Test
	public void testCompareTo_GrotereQuizOpBasisVanOnderwerpAlsArgument_GeeftNegatief() {		
		assertTrue(otherQuiz.compareTo(myQuiz) < 0);
	}
	
	@Test
	public void testCompareTo_KleinereQuizOpBasisVanOnderwerpAlsArgument_GeeftPositief() {		
		assertTrue(myQuiz.compareTo(otherQuiz) > 0);
	}

}
