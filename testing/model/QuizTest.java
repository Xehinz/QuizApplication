package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author johan
 * @version 2014.10.25
 */
public class QuizTest {

	private Quiz myQuiz, otherQuiz;

	@Before
	public void setUp() throws Exception {
		myQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "nieuwe quiz");
		otherQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "andere quiz");
	}

	@Test
	public void testQuiz_GeefOnderwerp_IsOK() {
		Quiz raarOnderwerp = new Quiz(Leraar.CHARLOTTE_NEVEN, "raar onderwerp");
		assertTrue("Het onderwerp is hetzelfde", raarOnderwerp.getOnderwerp() == "raar onderwerp");
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
	public void testQuiz_getStatus_IsOK() {
		Quiz isStatus = new Quiz(Leraar.CHARLOTTE_NEVEN, "een quiz met een status", false);
		assertTrue("Deze status is bij initialisatie gelijk", isStatus.getQuizStatus() == QuizStatus.IN_CONSTRUCTIE);

		isStatus = new Quiz(Leraar.CHARLOTTE_NEVEN, "een andere quiz zonder status bij initialisatie");
		isStatus.setQuizStatus(QuizStatus.AFGEWERKT);
		assertTrue("Deze status is bij set-method gelijk", isStatus.getQuizStatus() == QuizStatus.AFGEWERKT);
	}

	@Test
	public void testQuiz_getOpdrachtenVanEenCloneQuiz_EqualsEenCloneQuiz_IsOK() {
		Quiz aQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "new quiz");
		Quiz anotherQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "another quiz");
		Opdracht aOpdracht = new KlassiekeOpdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);

		QuizOpdracht.attachOpdrachtToQuiz(aQuiz, aOpdracht, 5);
		QuizOpdracht.attachOpdrachtToQuiz(aQuiz, aOpdracht, 10);
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
	public void testQuiz_VergelijkOpdrachten_IsKleiner() {
		Quiz aQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "new quiz");
		Quiz anotherQuiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "another quiz");
		Opdracht aOpdracht = new KlassiekeOpdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);

		QuizOpdracht.attachOpdrachtToQuiz(aQuiz, aOpdracht, 5);
		QuizOpdracht.attachOpdrachtToQuiz(aQuiz, aOpdracht, 10);
		// QuizOpdracht aQuizOpdracht = new QuizOpdracht(aQuiz, aOpdracht, 5);
		// QuizOpdracht anotherQuizOpdracht = new QuizOpdracht(aQuiz, aOpdracht,
		// 10);
		// aQuiz.addQuizOpdracht(aQuizOpdracht);
		// aQuiz.addQuizOpdracht(anotherQuizOpdracht);

		QuizOpdracht.attachOpdrachtToQuiz(anotherQuiz, aOpdracht, 7);
		// aQuizOpdracht = new QuizOpdracht(anotherQuiz, aOpdracht, 7);
		// anotherQuiz.addQuizOpdracht(aQuizOpdracht);

		assertTrue("Deze quiz heeft minder opdrachten", anotherQuiz.compareTo(aQuiz) == -1);
	}

}
