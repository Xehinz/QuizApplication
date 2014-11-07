package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import util.datumWrapper.Datum;

/**
 * Test de modelklasse OpdrachtAntwoord. Test volgende functionaliteiten:
 * <ul>
 * <li>Een QuizDeelname aan een QuizOpdracht koppelen mbv de static method koppelQuizDeelnameAanQuizOpdracht(...)</li>
 * <li>De scoreberekening - opgehaald door GetBehaaldeScore()</li>
 * <li>equals()</li>
 * <li>clone()</li>
 * <li>compareTo - vergelijken van twee OpdrachtAntwoorden, eerst op basis van de QuizDeelname, dan op basis van de
 * QuizOpdracht</li>
 * </ul>
 *
 * @author Ben Vandenberk
 * @version 01/11/2014
 *
 */
public class OpdrachtAntwoordTest {

	private Quiz quiz, kleinereQuiz;
	private Opdracht opdrachtZonderTijdZonderPogingen, opdrachtOpTijdZonderPogingen, opdrachtZonderTijd1Poging;
	private Leerling leerling, kleinereLeerling;
	private QuizDeelname quizDeelname, kleinereQuizDeelname;
	private QuizOpdracht quizOpdrachtZonderTijdZonderPogingen, quizOpdrachtOpTijdZonderPogingen, quizOpdrachtZonderTijd1Poging,
	kleinereQuizOpdracht;

	@Before
	public void setUp() {
		quiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "De Hoofdsteden van Europa");
		quiz.setQuizStatus(QuizStatus.OPENGESTELD);

		kleinereQuiz = new Quiz(Leraar.MIEKE_WITTEMANS, "Quiz met minder opdrachten");
		kleinereQuiz.setQuizStatus(QuizStatus.OPENGESTELD);

		opdrachtZonderTijdZonderPogingen = new KlassiekeOpdracht("Wat is de hoofdstad van Frankrijk?", 0, "Parijs",
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MARIA_AERTS);
		opdrachtOpTijdZonderPogingen = new KlassiekeOpdracht("Wat is de hoofdstad van België?", "Brussel", 0, 20,
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.JOS_VERBEEK);
		opdrachtZonderTijd1Poging = new KlassiekeOpdracht("Wat is de hoofdstad van België?", "Brussel", 1, 0,
				OpdrachtCategorie.WETENSCHAPPEN, Leraar.JOS_VERBEEK);

		leerling = new Leerling("Karel", "Boghen", 3);
		kleinereLeerling = new Leerling("Laura", "Aaltert", 4);

		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdrachtZonderTijdZonderPogingen, 10);
		quizOpdrachtZonderTijdZonderPogingen = quiz.getQuizOpdrachten().get(0);
		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdrachtOpTijdZonderPogingen, 10);
		quizOpdrachtOpTijdZonderPogingen = quiz.getQuizOpdrachten().get(1);
		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdrachtZonderTijd1Poging, 10);
		quizOpdrachtZonderTijd1Poging = quiz.getQuizOpdrachten().get(2);

		// De QuizOpdracht is kleiner omdat de Quiz kleiner is (minder opdrachten)
		QuizOpdracht.attachOpdrachtToQuiz(kleinereQuiz, opdrachtZonderTijdZonderPogingen, 5);
		kleinereQuizOpdracht = kleinereQuiz.getQuizOpdrachten().get(0);

		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
		quizDeelname = quiz.getQuizDeelnames().get(0);
		// De QuizDeelname is kleiner omdat de Leerling kleiner is (familienaam alfabetisch eerder)
		QuizDeelname.koppelQuizAanLeerling(kleinereQuiz, kleinereLeerling);
		kleinereQuizDeelname = kleinereQuiz.getQuizDeelnames().get(0);
	}

	@Test
	public void testKoppelQuizDeelnameAanQuizOpdracht_CorrecteKoppeling() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		assertTrue("QuizDeelname bevat het OpdrachtAntwoord", quizDeelname.getOpdrachtAntwoorden().contains(opdrachtAntwoord));
		assertTrue("QuizOpdracht bevat het OpdrachtAntwoord", quizOpdrachtZonderTijdZonderPogingen.getOpdrachtAntwoorden()
				.contains(opdrachtAntwoord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_AantalPogingen0_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdrachtZonderTijdZonderPogingen, 0, 10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_AantalPogingenNegatief_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdrachtZonderTijdZonderPogingen, -10, 10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_AntwoordTijdNegatief_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdrachtZonderTijdZonderPogingen, 1, -10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_QuizDeelnameNull_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(null, quizOpdrachtZonderTijdZonderPogingen, 1, 10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_QuizOpdrachtNull_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, null, 1, 10, "");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_TweeAntwoordenVoorZelfdeOpdracht_ThrowsUnsupportedOperationException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdrachtOpTijdZonderPogingen, 1, 10, "Athene");
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdrachtOpTijdZonderPogingen, 2, 15, "Ouagadougou");
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordEerstePoging_MaximumScore() {
		OpdrachtAntwoord juistAntwoordEenPoging = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		assertEquals("Juist antwoord in 1 poging geeft maximum score", quizOpdrachtZonderTijdZonderPogingen.getMaxScore(),
				juistAntwoordEenPoging.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordTweedePoging_HelftVanMaxScore() {
		OpdrachtAntwoord juisteAntwoordTweePogingen = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 2, 10, "Parijs");
		assertEquals("Juist antwoord in 2 pogingen geeft helft van de punten",
				quizOpdrachtZonderTijdZonderPogingen.getMaxScore() / 2.0, juisteAntwoordTweePogingen.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordVeelPogingen_HelftVanMaxScore() {
		OpdrachtAntwoord juisteAntwoordVeelPogingen = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 8, 10, "Parijs");
		assertEquals("Juist antwoord in veel pogingen geeft helft van de punten",
				quizOpdrachtZonderTijdZonderPogingen.getMaxScore() / 2.0, juisteAntwoordVeelPogingen.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_FoutAntwoord_0Punten() {
		OpdrachtAntwoord foutAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Londen");
		assertEquals("Fout antwoord levert geen punten op", 0, foutAntwoord.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_GeenAntwoord_0Punten() {
		OpdrachtAntwoord geenAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "");
		assertEquals("Fout antwoord levert geen punten op", 0, geenAntwoord.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordTeveelPogingen_0Punten() {
		OpdrachtAntwoord juisteAntwoordTeveelPogingen = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijd1Poging, 2, 10, "Brussel");
		assertEquals("Meerdere poging op opdracht met 1 poging levert 0 punten op", 0,
				juisteAntwoordTeveelPogingen.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordAantalPogingenOke_MaxScore() {
		OpdrachtAntwoord juisteAntwoordAantalPogingenOke = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijd1Poging, 1, 10, "Brussel");
		assertEquals("Juiste antwoord na 1 poging bij opdracht met pogingsbeperking levert max punten op",
				quizOpdrachtZonderTijd1Poging.getMaxScore(), juisteAntwoordAantalPogingenOke.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_BuitenTijd_0Punten() {
		OpdrachtAntwoord antwoordBuitenTijd = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtOpTijdZonderPogingen, 1, 30, "Brussel");
		assertEquals("Antwoord buiten de tijd levert geen punten op", 0, antwoordBuitenTijd.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testEquals_NullOpdrachtAntwoord_GeeftFalse() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		assertFalse("Null meegeven met equals geeft false", opdrachtAntwoord.equals(null));
	}

	@Test
	public void testEquals_AnderTypeObject_GeeftFalse() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		Datum datum = new Datum();
		assertFalse("Object van ander type is niet gelijk", opdrachtAntwoord.equals(datum));
	}

	@Test
	public void testEquals_ZelfdeOpdrachtAntwoorden_ZijnGelijk() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		OpdrachtAntwoord zelfdeAntwoord = opdrachtAntwoord.clone();
		assertTrue("Twee dezelfde OpdrachtAntwoorden zijn gelijk", opdrachtAntwoord.equals(zelfdeAntwoord));
	}

	@Test
	public void testEquals_VerschillendeOpdrachtAntwoorden_ZijnNietGelijk() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		OpdrachtAntwoord anderOpdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijd1Poging, 1, 10, "Parijs");
		assertFalse("Verschillende OpdrachtAntwoorden zijn niet gelijk", opdrachtAntwoord.equals(anderOpdrachtAntwoord));
	}

	@Test
	public void testClone_GeeftZelfdeOpdrachtAntwoordTerug() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		OpdrachtAntwoord clone = opdrachtAntwoord.clone();
		assertEquals("Clone en origineel zijn gelijk", opdrachtAntwoord, clone);
	}

	// Hier worden OpdrachtAntwoorden vergeleken die bij verschillende QuizDeelnames horen
	@Test
	public void testCompareTo_KleinerOpdrachtAntwoordAlsArgument_GeeftPositief() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		OpdrachtAntwoord kleinerOpdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(kleinereQuizDeelname,
				kleinereQuizOpdracht, 1, 10, "MaaktNietUit");
		assertTrue("'Kleiner' OpdrachtAntwoord als argument van compareTo geeft een positief resultaat",
				opdrachtAntwoord.compareTo(kleinerOpdrachtAntwoord) > 0);
	}

	// Hier worden OpdrachtAntwoorden vergeleken die bij verschillende QuizDeelnames horen
	@Test
	public void testCompareTo_GroterOpdrachtAntwoordAlsArgument_geeftNegatief() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Parijs");
		OpdrachtAntwoord kleinerOpdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(kleinereQuizDeelname,
				kleinereQuizOpdracht, 1, 10, "MaaktNietUit");
		assertTrue("'Groter' OpdrachtAntwoord als argument van compareTo geeft een positief resultaat",
				kleinerOpdrachtAntwoord.compareTo(opdrachtAntwoord) < 0);
	}

	// Hier worden OpdrachtAntwoorden vergeleken die bij dezelfde QuizDeelname horen
	@Test
	public void testCompareTo_KleinerArgumentEnkelDoorOpdracht_GeeftPositief() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijd1Poging, 1, 10, "Sarajevo");
		OpdrachtAntwoord kleinerOpdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Sarajevo");
		assertTrue("OpdrachtAntwoord als argument dat enkel kleiner is door de QuizOpdracht geeft positief resultaat",
				opdrachtAntwoord.compareTo(kleinerOpdrachtAntwoord) > 0);
	}

	// Hier worden OpdrachtAntwoorden vergeleken die bij dezelfde QuizDeelname horen
	@Test
	public void testCompareTo_GroterArgumentEnkelDoorOpdracht_GeeftNegatief() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijd1Poging, 1, 10, "Sarajevo");
		OpdrachtAntwoord kleinerOpdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijdZonderPogingen, 1, 10, "Sarajevo");
		assertTrue("OpdrachtAntwoord als argument dat enkel groter is door de QuizOpdracht geeft negatief resultaat",
				kleinerOpdrachtAntwoord.compareTo(opdrachtAntwoord) < 0);
	}

	@Test
	public void testCompareTo_ZelfdeOpdrachtAntwoord_Geeft0() {
		OpdrachtAntwoord opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
				quizOpdrachtZonderTijd1Poging, 1, 10, "Sarajevo");
		OpdrachtAntwoord zelfdeOpdrachtAntwoord = opdrachtAntwoord.clone();
		assertTrue("Zelfde OpdrachtAntwoorden vergelijken geeft 0", opdrachtAntwoord.compareTo(zelfdeOpdrachtAntwoord) == 0);
	}

}
