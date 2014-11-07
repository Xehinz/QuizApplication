package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import util.datumWrapper.Datum;

/**
 * Testklasse voor QuizDeelname
 *
 * @author Bert Neyt
 * @version 2/11/2014
 */

public class QuizDeelnameTest {

	private QuizDeelname quizdeelname, quizdeelname2, quizdeelname3, quizdeelname4;
	private Quiz quiz, quiz2, quiz3, quiz4;
	private Leerling leerling, leerling2, leerling3;
	private KlassiekeOpdracht opdracht1, opdracht2;
	private QuizOpdracht quizopdracht1, quizopdracht2;
	private OpdrachtAntwoord opdrachtantwoord1, opdrachtantwoord2;
	private ArrayList<OpdrachtAntwoord> opdrachtantwoordlijst;
	private Datum datum;

	@Before
	public void setUp() {
		datum = new Datum();
		quiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "Onderwerp 1");
		quiz.setQuizStatus(QuizStatus.OPENGESTELD);
		quiz2 = new Quiz(Leraar.JOS_VERBEEK);
		quiz2.setQuizStatus(QuizStatus.IN_CONSTRUCTIE);
		quiz3 = new Quiz(Leraar.MARIA_AERTS);
		quiz3.setQuizStatus(QuizStatus.OPENGESTELD);
		quiz3.setDoelLeerjaren(1);
		quiz4 = new Quiz(Leraar.CHARLOTTE_NEVEN, "Onderwerp 2");
		quiz4.setQuizStatus(QuizStatus.OPENGESTELD);

		leerling = new Leerling("Bram", "Verhelst", 3);
		leerling2 = new Leerling("An", "Stijnen", 2);
		leerling3 = new Leerling("Bram", "Verhelst", 3);

		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
		QuizDeelname.koppelQuizAanLeerling(quiz, leerling2);
		QuizDeelname.koppelQuizAanLeerling(quiz, leerling3);
		QuizDeelname.koppelQuizAanLeerling(quiz4, leerling);

		quizdeelname = quiz.getQuizDeelnames().get(0);
		quizdeelname2 = quiz.getQuizDeelnames().get(1);
		quizdeelname3 = quiz.getQuizDeelnames().get(2);
		quizdeelname4 = quiz.getQuizDeelnames().get(0);

		opdracht1 = new KlassiekeOpdracht("Wat is de hoofdstad van Nederland", "Amsterdam", 1, 15,
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.JOS_VERBEEK);
		opdracht2 = new KlassiekeOpdracht("Hoeveel is 2 maal 2", "4", 1, 10, OpdrachtCategorie.WISKUNDE, Leraar.MIEKE_WITTEMANS);

		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdracht1, 5);
		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdracht2, 5);
		quizopdracht1 = quiz.getQuizOpdrachten().get(0);
		quizopdracht2 = quiz.getQuizOpdrachten().get(1);

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

	@Test(expected = UnsupportedOperationException.class)
	public void testKoppelQuizAanLeerling_QuizEnLeerling_StatusLaatDeelnameNietToe() {
		QuizDeelname.koppelQuizAanLeerling(quiz2, leerling);
	}

	/**
	 * test het koppelen van een quiz aan een leerling van een foutief leerjaar
	 */

	@Test(expected = UnsupportedOperationException.class)
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
	 * test de toString methode
	 */

	@Test
	public void testToString_NoParam_IsOk() {
		assertEquals("Deelname van Bram Verhelst aan quiz: Onderwerp 1 op " + datum.getDatumInEuropeesFormaat(),
				quizdeelname.toString());
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
