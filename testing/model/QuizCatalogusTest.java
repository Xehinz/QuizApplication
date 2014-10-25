package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import model.Quiz;
import model.QuizCatalogus;

/**
 * 
 * @author johan
 * @version 2014.10.25
 *
 */

public class QuizCatalogusTest {

	private List<Quiz> myQuizCatalogus;
	private QuizCatalogus quizCatalogusBasis;
	private QuizCatalogus quizCatalogusExtra;
	private Quiz myQuiz;
	private Quiz myOtherQuiz;

	@Before
	public void InitiliazeList()
	{
		myQuiz = new Quiz("test quiz");
		myOtherQuiz = new Quiz("test andere quiz");
		myQuizCatalogus.add(myQuiz);
		quizCatalogusBasis = new QuizCatalogus(myQuizCatalogus);
		quizCatalogusExtra = new QuizCatalogus(myQuizCatalogus);
	}
	
	@Test
	public void testQuizCatalogus_GetListVergelijkenMetAndereListOfCatalogusQuiz_IsOK()
	{
		assertEquals("List vergelijken met een List-object", quizCatalogusBasis.getList(), myQuizCatalogus); 
		assertEquals("List vergelijken met een andere CatalogusQuiz", quizCatalogusBasis.getList(), quizCatalogusExtra.getList());
	}

	@Test
	public void testQuizCatalogus_toString_IsOK()
	{
		assertEquals("De toString is hetzelfde", "Quizcatalogus met " + quizCatalogusBasis.count() + " quizzen", quizCatalogusBasis.toString());
	}

	@Test
	public void testQuizCatalogus_Count_IsOK()
	{
		assertEquals("De count is hetzelfde", myQuizCatalogus.size(), quizCatalogusBasis.count());
	}
	
	@Test
	public void testQuizCatalogus_HasQuiz_IsOK()
	{
		assertFalse("Quiz object is aanwezig in de catalogus", quizCatalogusBasis.hasQuiz(myQuiz));
	}
	
	@Test
	public void testQuizCatalogus_NotHasQuiz_IsOK()
	{
		assertFalse("Quiz object is niet aanwezig in de catalogus", quizCatalogusBasis.hasQuiz(myOtherQuiz));
	}

	@Test
	public void testQuizCatalogus_EqualsWhenAddedInTwoCatalogi_IsOK()
	{
		Quiz newQuiz = new Quiz("nog een andere quiz",true,"open");
		
		quizCatalogusBasis.addOpdracht(newQuiz);
		quizCatalogusExtra.addOpdracht(newQuiz);
		
		assertTrue("QuizCatalogus is dezelfde", quizCatalogusBasis.equals(quizCatalogusExtra));
	}
	
	@Test
	public void testQuizCatalogus_addQuizAndCountIt_IsOK()
	{
		int aantal = 0;
		
		aantal = quizCatalogusBasis.count();
		quizCatalogusBasis.addOpdracht(myOtherQuiz);
		
		assertTrue("Quiz toegevoegd", quizCatalogusBasis.hasQuiz(myOtherQuiz) && quizCatalogusBasis.count() == (aantal++));
	}

	@Test
	public void testQuizCatalogus_removeQuizAfterAddingIt_IsOK()
	{
		Quiz newQuiz = new Quiz("een te verwijderen quiz",true,"open");
		
		quizCatalogusExtra.addOpdracht(newQuiz);
		assertTrue("Quiz toegevoegd voor het verwijderen", quizCatalogusExtra.hasQuiz(newQuiz));
		quizCatalogusExtra.removeOpdracht(newQuiz);
		assertFalse("Quiz verwijderd", quizCatalogusExtra.hasQuiz(newQuiz));		
	}

	@Test
	public void testQuizCatalogus_getQuizIsTheSameWhenAddedInTwoCatalogi_IsOK()
	{
		Quiz newQuiz = new Quiz("een te vergelijken quiz",false,"pending");
		
		quizCatalogusBasis.addOpdracht(newQuiz);
		quizCatalogusExtra.addOpdracht(newQuiz);
		
		assertEquals("get Quiz by postion-number is the same", quizCatalogusBasis.getQuiz(quizCatalogusBasis.count()), quizCatalogusExtra.getQuiz(quizCatalogusExtra.count()));
	}

}
