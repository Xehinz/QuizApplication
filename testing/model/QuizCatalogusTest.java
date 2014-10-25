package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Quiz;
import model.QuizCatalogus;

/**
 * 
 * @author johan
 * @version 2014.10.25
 *
 */

public class QuizCatalogusTest {

	private QuizCatalogus quizCatalogusBasis;
	private QuizCatalogus quizCatalogusExtra;
	private Quiz myQuiz;
	private Quiz myOtherQuiz;

	@Before
	public void Setup() {
		myQuiz = new Quiz("test quiz");
		myOtherQuiz = new Quiz("test andere quiz");
		quizCatalogusBasis = new QuizCatalogus();
		quizCatalogusExtra = new QuizCatalogus();
	}

	@Test
	public void testQuizCatalogus_HasQuiz_IsOK() {
		quizCatalogusBasis.addOpdracht(myQuiz);
		quizCatalogusExtra.addOpdracht(myQuiz);
		assertTrue("Quiz object is aanwezig in de catalogus",
				quizCatalogusBasis.hasQuiz(myQuiz));
	}

	@Test
	public void testQuizCatalogus_NotHasQuiz_IsOK() {
		Quiz unexistingQuiz = new Quiz("onbestaande quiz");
		assertFalse("Quiz object is niet aanwezig in de catalogus",
				quizCatalogusBasis.hasQuiz(unexistingQuiz));
	}

	@Test
	public void testQuizCatalogus_EqualsWhenAddedInTwoCatalogi_IsOK() {
		Quiz newQuiz = new Quiz("nog een andere quiz", true, "open");

		quizCatalogusBasis.addOpdracht(newQuiz);
		quizCatalogusExtra.addOpdracht(newQuiz);

		assertTrue("QuizCatalogus is dezelfde",
				quizCatalogusBasis.equals(quizCatalogusExtra));
	}

	@Test
	public void testQuizCatalogus_getCloneVergelijkenMetAndereCatalogusQuiz_IsOK() {
		QuizCatalogus vergelijkCataloog = quizCatalogusBasis
				.getCloneQuizCatalogus();
		assertTrue(
				"Vergelijken met een andere CatalogusQuiz mbv getClone & Equals",
				quizCatalogusBasis.equals(vergelijkCataloog));
	}

	@Test
	public void testQuizCatalogus_toString_IsOK() {
		assertEquals("De toString is hetzelfde", "Quizcatalogus met "
				+ quizCatalogusBasis.count() + " quizzen",
				quizCatalogusBasis.toString());
	}

	@Test
	public void testQuizCatalogus_CountVanQuizCatalogusEnCountVanClone_IsOK() {
		assertEquals("De count van origineel en clone is hetzelfde",
				quizCatalogusBasis.getCloneQuizCatalogus().count(),
				quizCatalogusBasis.count());
	}

	@Test
	public void testQuizCatalogus_addQuizAndCountIt_IsOK() {
		Quiz meQuick = new Quiz("Quiz me quick");
		QuizCatalogus vergelijkCatalogus = quizCatalogusBasis
				.getCloneQuizCatalogus();

		quizCatalogusBasis.addOpdracht(meQuick);
		vergelijkCatalogus.addOpdracht(meQuick);
		System.out.println(quizCatalogusBasis.count() == (vergelijkCatalogus
				.count()));

		assertTrue("Quiz toegevoegd",
				quizCatalogusBasis.count() == vergelijkCatalogus.count());
	}

	@Test
	public void testQuizCatalogus_removeQuizAfterAddingIt_IsOK() {
		Quiz newQuiz = new Quiz("een te verwijderen quiz", true, "open");

		quizCatalogusExtra.addOpdracht(newQuiz);
		assertTrue("Quiz toegevoegd voor het verwijderen",
				quizCatalogusExtra.hasQuiz(newQuiz));
		quizCatalogusExtra.removeOpdracht(newQuiz);
		assertFalse("Quiz verwijderd", quizCatalogusExtra.hasQuiz(newQuiz));
	}

	@Test
	public void testQuizCatalogus_getQuizIsTheSameWhenAddedInTwoCatalogi_IsOK() {
		Quiz newQuiz = new Quiz("een te vergelijken quiz", false, "pending");

		quizCatalogusBasis.addOpdracht(myOtherQuiz);
		quizCatalogusBasis.addOpdracht(newQuiz);
		quizCatalogusExtra.addOpdracht(newQuiz);

		assertEquals("get Quiz by postion-number is the same",
				quizCatalogusBasis.getQuiz(quizCatalogusBasis.count()),
				quizCatalogusExtra.getQuiz(quizCatalogusExtra.count()));
	}

}
