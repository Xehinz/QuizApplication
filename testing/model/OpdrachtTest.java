package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.quizStatus.InConstructie;
import model.quizStatus.Opengesteld;

import org.junit.Before;
import org.junit.Test;

import util.datumWrapper.Datum;

/**
 * Test de basisfunctionaliteiten van de abstracte klasse Opdracht. De
 * specifiekere functionaliteiten van de concrete subklasses worden in aparte
 * Test Cases getest.
 * 
 * @author Ben Vandenberk
 * @version 16/11/2014
 *
 */
public class OpdrachtTest {

	Opdracht aanpasbareOpdracht, nietAanpasbareOpdracht;
	Opdracht verwijderbareOpdracht, nietVerwijderbareOpdracht;
	Opdracht opTijd, nietOpTijd;
	Opdracht beperktePogingen, nietBeperktePogingen;
	Opdracht gelinkteOpdracht, nietGelinkteOpdracht;
	Opdracht referentie, zelfdeCategorieKleinereVraag, kleinereCategorie;

	QuizOpdracht gelinkteOpdrachtQuizOpdracht, toeTeVoegenQuizOpdracht;

	@Before
	public void setUp() {
		aanpasbareOpdracht = new KlassiekeOpdracht(
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MIEKE_WITTEMANS);
		nietAanpasbareOpdracht = new KlassiekeOpdracht(
				OpdrachtCategorie.NEDERLANDS, Leraar.JOS_VERBEEK);
		nietAanpasbareOpdracht.addHint("hint");

		verwijderbareOpdracht = new KlassiekeOpdracht(
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MIEKE_WITTEMANS);
		nietVerwijderbareOpdracht = new KlassiekeOpdracht(
				OpdrachtCategorie.NEDERLANDS, Leraar.JOS_VERBEEK);

		opTijd = new KlassiekeOpdracht("Wat?", "Dat", 20,
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.JOS_VERBEEK);
		nietOpTijd = new KlassiekeOpdracht("Wat?", "Dat", 0,
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.JOS_VERBEEK);

		beperktePogingen = new KlassiekeOpdracht("Wat?", 1, "Dat",
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MIEKE_WITTEMANS);
		nietBeperktePogingen = new KlassiekeOpdracht("Wat?", 0, "Dat",
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MIEKE_WITTEMANS);

		gelinkteOpdracht = new KlassiekeOpdracht(
				OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MIEKE_WITTEMANS);
		nietGelinkteOpdracht = new KlassiekeOpdracht(
				OpdrachtCategorie.NEDERLANDS, Leraar.JOS_VERBEEK);
		
		// om de compareTo te testen
		referentie = new KlassiekeOpdracht("VraagB", "antwoord", OpdrachtCategorie.NEDERLANDS, Leraar.CHARLOTTE_NEVEN);
		zelfdeCategorieKleinereVraag = new KlassiekeOpdracht("VraagA", "antwoord", OpdrachtCategorie.NEDERLANDS, Leraar.CHARLOTTE_NEVEN);
		kleinereCategorie = new KlassiekeOpdracht("VraagB", "antwoord", OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);

		// nietAanpasbareOpdracht daadwerkelijk niet aanpasbaar maken door een
		// Leerling te laten deelnemen aan een quiz met de opdracht
		Quiz quiz = new Quiz(Leraar.MIEKE_WITTEMANS, "mijnQuiz");
		quiz.setQuizStatus(new InConstructie());
		Leerling leerling = new Leerling("Boris", "Jeltsin", 1);
		QuizOpdracht.koppelOpdrachtAanQuiz(quiz, nietAanpasbareOpdracht, 10);
		quiz.setQuizStatus(new Opengesteld());
		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);

		// nietVerwijderbareOpdracht daadwerkelijk niet verwijderbaar maken door
		// ze aan een quiz toe te voegen
		quiz.setQuizStatus(new InConstructie());
		QuizOpdracht.koppelOpdrachtAanQuiz(quiz, nietVerwijderbareOpdracht, 10);

		// gelinkteOpdracht linken aan een quiz
		gelinkteOpdrachtQuizOpdracht = QuizOpdracht.koppelOpdrachtAanQuiz(quiz,
				gelinkteOpdracht, 10);

		// QuizOpdracht maken om te kunnen toevoegen in een test
		toeTeVoegenQuizOpdracht = QuizOpdracht.koppelOpdrachtAanQuiz(quiz,
				opTijd, 10);
	}

	@Test
	public void testSetVraag_AanpasbareOpdracht_VraagJuistGeset() {
		aanpasbareOpdracht.setVraag("Hoezo?");
		assertEquals(aanpasbareOpdracht.getVraag(), "Hoezo?");
	}

	@Test(expected = IllegalStateException.class)
	public void testSetVraag_NietAanpasbareOpdracht_ThrowsIllegalStateException() {
		nietAanpasbareOpdracht.setVraag("Hoezo?");
	}

	@Test
	public void testGetMaxAantalPogingen_OpdrachtNietBeperktePogingen_Geeft0() {
		assertEquals(0, nietBeperktePogingen.getMaxAantalPogingen());
	}

	@Test
	public void testSetMaxAantalPogingen_LegaalAantal_WordtGeset() {
		aanpasbareOpdracht.setMaxAantalPogingen(10);
		assertEquals(10, aanpasbareOpdracht.getMaxAantalPogingen());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetMaxAantalPogingen_NegatiefAantal_ThrowsIllegalArgumentException() {
		aanpasbareOpdracht.setMaxAantalPogingen(-1);
	}

	@Test(expected = IllegalStateException.class)
	public void test_SetMaxAantalPogingen_NietAanpasbareOpdracht_ThrowsIllegalStateException() {
		nietAanpasbareOpdracht.setMaxAantalPogingen(10);
	}

	@Test
	public void testGetMaxAntwoordTijd_OpdrachtNietOpTijd_Geeft0() {
		assertEquals(0, nietOpTijd.getMaxAntwoordTijd());
	}

	@Test
	public void testSetMaxAntwoordTijd_LegaleTijd_WordtGeset() {
		aanpasbareOpdracht.setMaxAntwoordTijd(10);
		assertEquals(10, aanpasbareOpdracht.getMaxAntwoordTijd());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetMaxAntwoordTijd_NegatieveTijd_ThrowsIllegalArgumentException() {
		aanpasbareOpdracht.setMaxAntwoordTijd(-1);
	}

	@Test(expected = IllegalStateException.class)
	public void testSetMaxAntwoordTijd_NietAanpasbareOpdracht_ThrowsIllegalStateException() {
		nietAanpasbareOpdracht.setMaxAntwoordTijd(10);
	}

	@Test
	public void testSetOpdrachtCategorie_VanAanpasbareOpdracht_WordtGeset() {
		aanpasbareOpdracht.setOpdrachtCategorie(OpdrachtCategorie.NEDERLANDS);
		assertEquals(OpdrachtCategorie.NEDERLANDS,
				aanpasbareOpdracht.getOpdrachtCategorie());
	}

	@Test(expected = IllegalStateException.class)
	public void testSetOpdrachtCategorie_NietAanpasbareOpdracht_ThrowsIllegalStateException() {
		nietAanpasbareOpdracht
				.setOpdrachtCategorie(OpdrachtCategorie.AARDRIJKSKUNDE);
	}

	@Test
	public void testGetAanmaakDatum_AanpassenDatumObject_VerandertAanmaakDatumOpdrachtNiet() {
		Datum aanmaakDatum = aanpasbareOpdracht.getAanmaakDatum();
		aanmaakDatum.setDatum(1, 1, 2000);
		assertFalse(aanmaakDatum.equals(aanpasbareOpdracht.getAanmaakDatum()));
	}

	@Test
	public void testGetQuizOpdrachten_NietGelinkteOpdracht_GeeftLegeLijst() {
		assertTrue(nietGelinkteOpdracht.getQuizOpdrachten().size() == 0);
	}

	@Test
	public void testGetQuizOpdrachten_GelinkteOpdracht_GeeftCorrecteQuizOpdrachten() {
		assertTrue(gelinkteOpdracht.getQuizOpdrachten().contains(
				gelinkteOpdrachtQuizOpdracht)
				&& gelinkteOpdracht.getQuizOpdrachten().size() == 1);
	}

	@Test
	public void testGetQuizOpdrachten_ToevoegenAanLijst_VerandertLijstInOpdrachtNiet() {
		ArrayList<QuizOpdracht> quizOpdrachten = gelinkteOpdracht
				.getQuizOpdrachten();
		quizOpdrachten.add(toeTeVoegenQuizOpdracht);
		assertFalse(gelinkteOpdracht.getQuizOpdrachten().contains(
				toeTeVoegenQuizOpdracht));
	}

	@Test
	public void testAddHint_ToevoegenHint_GebeurtCorrect() {
		aanpasbareOpdracht.addHint("hint");
		assertTrue(aanpasbareOpdracht.getHints().contains("hint")
				&& aanpasbareOpdracht.getHints().size() == 1);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddHint_NietAanpasbareOpdracht_ThrowsIllegalStateException() {
		nietAanpasbareOpdracht.addHint("hint");
	}

	@Test
	public void testRemoveHint_VerwijderenHint_GebeurtCorrect() {
		aanpasbareOpdracht.addHint("hint");
		aanpasbareOpdracht.removeHint("hint");
		assertTrue(!aanpasbareOpdracht.getHints().contains("hint")
				&& aanpasbareOpdracht.getHints().size() == 0);

		aanpasbareOpdracht.addHint("hint");
		aanpasbareOpdracht.removeHint(1);
		assertTrue(!aanpasbareOpdracht.getHints().contains("hint")
				&& aanpasbareOpdracht.getHints().size() == 0);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveHintStringInput_NietAanpasbareOpdracht_ThrowsIllegalStateException() {
		nietAanpasbareOpdracht.removeHint("hint");
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveHintIntInput_NietAanpasbareOpdracht_ThrowsIllegalStateException() {
		nietAanpasbareOpdracht.removeHint(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveHintIntInput_VolgnummerBestaatNiet_ThrowsIllegalArgumentException() {
		aanpasbareOpdracht.addHint("hint");
		aanpasbareOpdracht.removeHint(10);
	}

	@Test
	public void testGetHints_HaaltJuisteHintsOp() {
		aanpasbareOpdracht.addHint("hint1");
		aanpasbareOpdracht.addHint("hint2");
		assertTrue(aanpasbareOpdracht.getHints().contains("hint1"));
		assertTrue(aanpasbareOpdracht.getHints().contains("hint2"));
		assertTrue(aanpasbareOpdracht.getHints().size() == 2);
	}

	@Test
	public void testGetHints_ToevoegenAanLijst_VerandertHintsVanOpdrachtNiet() {
		ArrayList<String> hints = nietAanpasbareOpdracht.getHints();
		hints.add("hint2");
		hints.add("hint3");
		assertFalse(nietAanpasbareOpdracht.getHints().contains("hint2"));
		assertFalse(nietAanpasbareOpdracht.getHints().contains("hint3"));
	}

	@Test
	public void testGetQuizOpdracht_BestaandeID_GeeftJuisteQuizOpdrachtTerug() {
		String ID = gelinkteOpdrachtQuizOpdracht.getID();
		assertEquals(gelinkteOpdrachtQuizOpdracht,
				gelinkteOpdracht.getQuizOpdracht(ID));
	}

	@Test
	public void testGetQuizOpdracht_OnbestaandeID_GeeftNullTerug() {
		assertEquals(null, gelinkteOpdracht.getQuizOpdracht("1"));
	}

	@Test
	public void testEquals_CloneAlsArgument_GeeftTrue() {
		assertTrue(aanpasbareOpdracht.equals(aanpasbareOpdracht.clone()));
	}

	@Test
	public void testIsAanpasbaar_AanpasbareOpdracht_GeeftTrue() {
		assertTrue(aanpasbareOpdracht.isAanpasbaar());
	}

	@Test
	public void testIsAanpasbaar_NietAanpasbareOpdracht_GeeftFalse() {
		assertFalse(nietAanpasbareOpdracht.isAanpasbaar());
	}

	@Test
	public void testIsVerwijderbaar_VerwijderbareOpdracht_GeeftTrue() {
		assertTrue(verwijderbareOpdracht.isVerwijderbaar());
	}

	@Test
	public void testIsVerwijderbaar_NietVerwijderbareOpdracht_GeeftFalse() {
		assertFalse(nietVerwijderbareOpdracht.isVerwijderbaar());
	}

	@Test
	public void testHeeftTijdsbeperking_OpdrachtOpTijd_GeeftTrue() {
		assertTrue(opTijd.heeftTijdsbeperking());
	}

	@Test
	public void testHeeftTijdsbeperking_OpdrachtNietOpTijd_GeeftFalse() {
		assertFalse(nietOpTijd.heeftTijdsbeperking());
	}

	@Test
	public void testHeeftPogingsBeperking_OpdrachtBeperktePogingen_GeeftTrue() {
		assertTrue(beperktePogingen.heeftPogingBeperking());
	}

	@Test
	public void testHeeftPogingsBeperking_OpdrachtNietBeperktePogingen_GeeftFalse() {
		assertFalse(nietBeperktePogingen.heeftPogingBeperking());
	}
	
	@Test
	public void testCompareTo_ZelfdeOpdracht_Geeft0() {
		assertEquals(0, referentie.compareTo(referentie));
	}
	
	@Test
	public void testCompareTo_ZelfdeCategorieKleinereVraag_GeeftPositief() {
		assertTrue(referentie.compareTo(zelfdeCategorieKleinereVraag) > 0);
	}
	
	@Test
	public void testCompareTo_KleinereCategorie_GeeftPositief() {
		assertTrue(referentie.compareTo(kleinereCategorie) > 0);
	}
	
	@Test
	public void testCompareTo_ZelfdeCategorieGrotereVraag_GeeftNegatief() {
		assertTrue(zelfdeCategorieKleinereVraag.compareTo(referentie) < 0);
	}
	
	@Test
	public void testCompareTo_KleinerCategorie_GeeftNegatief() {
		assertTrue(kleinereCategorie.compareTo(referentie) < 0);
	}

}
