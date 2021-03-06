package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.quizStatus.InConstructie;
import model.quizStatus.Opengesteld;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse voor QuizDeelname
 *
 * @author Bert Neyt
 * @version 2/11/2014
 */

public class QuizDeelnameTest {

	private QuizDeelname quizdeelname, quizdeelname2, quizdeelname4;
	private Quiz quiz, quiz2, quiz3, quiz4;
	private Leerling leerling, leerling2;
	private KlassiekeOpdracht opdracht1, opdracht2;
	private QuizOpdracht quizopdracht1, quizopdracht2;
	private OpdrachtAntwoord opdrachtantwoord1, opdrachtantwoord2;
	private ArrayList<OpdrachtAntwoord> opdrachtantwoordlijst;

	@Before
	public void setUp() {
		quiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "Onderwerp 1");
		quiz.setQuizStatus(new Opengesteld());
		quiz2 = new Quiz(Leraar.JOS_VERBEEK);
		quiz2.setQuizStatus(new InConstructie());
		quiz3 = new Quiz(Leraar.MARIA_AERTS);
		quiz3.setQuizStatus(new Opengesteld());
		quiz3.setDoelLeerjaren(1);
		quiz4 = new Quiz(Leraar.CHARLOTTE_NEVEN, "Onderwerp 2");
		quiz4.setQuizStatus(new Opengesteld());

		leerling = new Leerling("Bram", "Verhelst", 3);
		leerling2 = new Leerling("An", "Stijnen", 2);

		quizdeelname = QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
		quizdeelname2 = QuizDeelname.koppelQuizAanLeerling(quiz, leerling2);
		QuizDeelname.koppelQuizAanLeerling(quiz4, leerling);
	
		quizdeelname4 = quizdeelname;

		opdracht1 = new KlassiekeOpdracht("Wat is de hoofdstad van Nederland", "Amsterdam", 1, 15,
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.JOS_VERBEEK);
		opdracht2 = new KlassiekeOpdracht("Hoeveel is 2 maal 2", "4", 1, 10, OpdrachtCategorie.WISKUNDE, Leraar.MIEKE_WITTEMANS);

		quiz.setQuizStatus(new InConstructie());
		quizopdracht1 = QuizOpdracht.koppelOpdrachtAanQuiz(quiz, opdracht1, 5);
		quizopdracht2 = QuizOpdracht.koppelOpdrachtAanQuiz(quiz, opdracht2, 5);
		quiz.setQuizStatus(new Opengesteld());

		opdrachtantwoord1 = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizdeelname, quizopdracht1, 1, 11, "Brussel");
		opdrachtantwoord2 = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizdeelname, quizopdracht2, 1, 10, "4");

		opdrachtantwoordlijst = new ArrayList<OpdrachtAntwoord>();
		opdrachtantwoordlijst.add(opdrachtantwoord1);
		opdrachtantwoordlijst.add(opdrachtantwoord2);
	}

	/**
	 * test het koppelen van een opengestelde quiz aan een leerling
	 */

	@Test
	public void testKoppelQuizAanLeerling_QuizEnLeerling_IsOk() {
		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
		assertTrue(quiz.getLeerlingenDieDeelnamen().contains(leerling));
	}

	/**
	 * test het koppelen van een opengestelde quiz aan een leerling
	 */

	@Test
	public void testKoppelQuizAanLeerling_QuizEnLeerling_IsOkB() {
		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
		assertTrue(leerling.getDeelgenomenQuizzen().contains(quiz));
	}

	/**
	 * test het koppelen van een quiz in constructie aan een leerling
	 */

	@Test(expected = IllegalStateException.class)
	public void testKoppelQuizAanLeerling_QuizEnLeerling_StatusLaatDeelnameNietToe() {
		QuizDeelname.koppelQuizAanLeerling(quiz2, leerling);
	}

	/**
	 * test het koppelen van een quiz aan een leerling van een foutief leerjaar
	 */

	@Test(expected = IllegalStateException.class)
	public void testKoppelQuizAanLeerling_QuizEnLeerling_LeerjaarLaatDeelnameNietToe() {
		QuizDeelname.koppelQuizAanLeerling(quiz3, leerling);
	}

	/**
	 * test het toevoegen van een opdrachtantwoord
	 */

	@Test
	public void testAddOpdrachtAntwoord_OpdrachtAntwoord_IsOk() {
		quizdeelname.addOpdrachtAntwoord(opdrachtantwoord1);
		assertTrue(quizdeelname.getOpdrachtAntwoorden().contains(opdrachtantwoord1));
	}

	/**
	 * test het ophalen van de opdrachtantwoorden
	 */

	@Test
	public void testGetOpdrachtAntwoorden_NoParam_IsOk() {
		assertEquals(quizdeelname.getOpdrachtAntwoorden(), opdrachtantwoordlijst);
	}

	/**
	 * test de equals methode voor een deelname van dezelfde leerling aan dezelfde quiz
	 */

	@Test
	public void testEquals_TweeDezelfdeQuizDeelnames_GeeftTrue() {
		assertTrue(quizdeelname.equals(quizdeelname4));
	}

	/**
	 * test de equals methode voor een deelname van verschillende leerlingen aan dezelfde quiz
	 */

	@Test
	public void testEquals_TweeVerschillendeQuizDeelnames_GeeftFalse() {
		assertFalse(quizdeelname.equals(quizdeelname2));
	}

	/**
	 * test de equals methode voor een deelname van dezelfde leerling aan 2 verschillende quizzen
	 */

	@Test
	public void testEquals_TweeVerschillendeQuizDeelnames_GeeftFalseB() {
		assertFalse(quizdeelname.equals(quizdeelname2));
	}

	/**
	 * test de compareTo methode voor een quizdeelname op dezelfde dag van 2 verschillende leerlingen
	 */

	@Test
	public void testCompareTo_Quizdeelnames_GeeftPositief() {
		assertTrue(quizdeelname.compareTo(quizdeelname2) > 0);
	}

	// Test voor compareTo met verschillende data? Kan datum geset worden voor
	// quizdeelname?

}
