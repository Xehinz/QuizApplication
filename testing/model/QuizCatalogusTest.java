package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.activity.InvalidActivityException;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author johan
 * @version 2014.10.25
 *
 */

public class QuizCatalogusTest {

	private QuizCatalogus quizCatalogusBasis;
	private QuizCatalogus quizCatalogusExtra;
	private QuizCatalogus catalogus;
	private Quiz myQuiz;

	@Before
	public void Setup() {
		myQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "test quiz");
		quizCatalogusBasis = new QuizCatalogus();
		quizCatalogusExtra = new QuizCatalogus();
		catalogus = new QuizCatalogus();
		catalogus.addQuiz(new Quiz(Leraar.JOS_VERBEEK, "De Hoofdsteden van Europa"));
	}

	@Test
	public void testQuizCatalogus_HasQuiz_IsOK() {
		quizCatalogusBasis.addQuiz(myQuiz);
		quizCatalogusExtra.addQuiz(myQuiz);
		assertTrue("Quiz object is aanwezig in de catalogus", quizCatalogusBasis.hasQuiz(myQuiz));
	}

	@Test
	public void testQuizCatalogus_NotHasQuiz_IsOK() {
		Quiz unexistingQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "onbestaande quiz");
		assertFalse("Quiz object is niet aanwezig in de catalogus", quizCatalogusBasis.hasQuiz(unexistingQuiz));
	}

	@Test
	public void testQuizCatalogus_EqualsWhenAddedInTwoCatalogi_IsOK() {
		Quiz newQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "nog een andere quiz", true);

		quizCatalogusBasis.addQuiz(newQuiz);
		quizCatalogusExtra.addQuiz(newQuiz);

		assertTrue("QuizCatalogus is dezelfde", quizCatalogusBasis.equals(quizCatalogusExtra));
	}

	@Test
	public void testQuizCatalogus_getCloneVergelijkenMetAndereCatalogusQuiz_IsOK() {
		QuizCatalogus vergelijkCataloog = quizCatalogusBasis.clone();
		assertTrue("Vergelijken met een andere CatalogusQuiz mbv getClone & Equals", quizCatalogusBasis.equals(vergelijkCataloog));
	}

	@Test
	public void testQuizCatalogus_CountVanQuizCatalogusEnCountVanClone_IsOK() {
		assertEquals("De count van origineel en clone is hetzelfde", quizCatalogusBasis.clone().count(),
				quizCatalogusBasis.count());
	}

	@Test
	public void testQuizCatalogus_addQuizAndCountIt_IsOK() {
		Quiz meQuick = new Quiz(Leraar.CHARLOTTE_NEVEN, "Quiz me quick");
		QuizCatalogus vergelijkCatalogus = quizCatalogusBasis.clone();

		quizCatalogusBasis.addQuiz(meQuick);
		vergelijkCatalogus.addQuiz(meQuick);

		assertTrue("Quiz toegevoegd", quizCatalogusBasis.count() == vergelijkCatalogus.count());
	}

	@Test
	public void testQuizCatalogus_removeQuizAfterAddingIt_IsOK() throws InvalidActivityException {
		Quiz newQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "een te verwijderen quiz", true);

		quizCatalogusExtra.addQuiz(newQuiz);
		assertTrue("Quiz toegevoegd voor het verwijderen", quizCatalogusExtra.hasQuiz(newQuiz));
		quizCatalogusExtra.removeQuiz(newQuiz);
		assertFalse("Quiz verwijderd", quizCatalogusExtra.hasQuiz(newQuiz));
	}

	@Test
	public void testGetQuizzen_ToevoegenAanVerkregenLijst_VerandertCatalogusNiet() {
		ArrayList<Quiz> quizzenKopie = quizCatalogusBasis.getQuizzen();
		quizzenKopie.add(myQuiz);
		assertTrue("Toevoegen aan verkregen lijst verandert catalogus niet", quizCatalogusBasis.count() == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddQuiz_OnderwerpIsGelijkOpSpatiesNa_ThrowsIllegalArgumentException() {
		catalogus.addQuiz(new Quiz(Leraar.CHARLOTTE_NEVEN, "    De    Hoofdsteden     van    Europa"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddQuiz_OnderwerpIsGelijkOpKapitalisatieNa_ThrowsIllegalArgumentException() {
		catalogus.addQuiz(new Quiz(Leraar.CHARLOTTE_NEVEN, "DE HOofDsTEdEn VaN eUrOPA"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddQuiz_OnderwerpIsGelijkOpLeestekensNa_ThrowsIllegalArgumentException() {
		catalogus.addQuiz(new Quiz(Leraar.CHARLOTTE_NEVEN, ",,D,e //@! Hoofdsted/...en;,, v('an ^$ù%Eur,;?opa"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddQuiz_OnderwerpIsGelijkOpVoegwoordenNa_ThrowsIllegalArgumentException() {
		catalogus.addQuiz(new Quiz(Leraar.CHARLOTTE_NEVEN, "van De in Hoofdsteden met een van het Europa"));
	}

}
