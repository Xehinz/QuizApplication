package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Test de klasse Leerling
 *
 * @author Bert
 * @version 27/10/2014
 *
 */

public class LeerlingTest {

	private Leerling leerling, zelfdeleerling, tweedeleerling, derdeleerling, vierdeleerling;
	private Quiz quiz;

	@Before
	public void setUp() throws Exception {
		leerling = new Leerling("Marie", "Vandamme", 3);
		zelfdeleerling = new Leerling("Marie", "Vandamme", 3);
		tweedeleerling = new Leerling("Bart", "Vandamme", 1);
		derdeleerling = new Leerling("Rebecca", "Hofkens", 6);
		vierdeleerling = new Leerling("Jef", "hOfKeNS", 4);
		quiz = new Quiz(Leraar.CHARLOTTE_NEVEN);
		quiz.setQuizStatus(QuizStatus.OPENGESTELD);
		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
	}

	/**
	 * Test of twee leerlingen met dezelfde via de constructor ingegeven parameters ook effectief dezelfde waardes
	 * hebben voor hun fields
	 */

	@Test
	public void testLeerling_VoornaamFamilieNaamEnLeerjaar_LeerlingIsDezelfde() {
		assertEquals("Voornaam gelijk", leerling.getLeerlingVoornaam(), zelfdeleerling.getLeerlingVoornaam());
		assertEquals("Familienaam gelijk", leerling.getLeerlingFamilienaam(), zelfdeleerling.getLeerlingFamilienaam());
		assertEquals("Leerjaar gelijk", leerling.getLeerjaar(), zelfdeleerling.getLeerjaar());
	}

	/**
	 * Test de method getNaam
	 */

	@Test
	public void testGetNaam_NoParam_geeftDezelfdeString() {
		assertEquals("Marie Vandamme", leerling.getNaam());
	}

	/**
	 * Test of een leerjaar zonder fouten aangepast kan worden via setLeerjaar
	 */

	@Test
	public void testSetLeerjaar_GeldigJaar_ZetJuisteLeerjaar() {
		leerling.setLeerjaar(2);
		assertEquals("Voornaam gelijk", "Marie", leerling.getLeerlingVoornaam());
		assertEquals("Familienaam gelijk", "Vandamme", leerling.getLeerlingFamilienaam());
		assertEquals("Leerjaar gelijk", 2, leerling.getLeerjaar());
	}

	/**
	 * Test of een foutief leerjaar herkend wordt als een fout in setLeerjaar
	 */

	@Test(expected = IllegalArgumentException.class)
	public void testSetLeerjaar_OngeldigJaar_ThrowsIllegalArgumentException() {
		leerling.setLeerjaar(10);
	}

	/**
	 * Test of een voornaam zonder fouten aangepast kan worden via setVoornaam
	 */

	@Test
	public void testSetLeerlingVoornaam_Voornaam_ZetJuisteVoornaam() {
		leerling.setLeerlingVoornaam("Kim");
		assertEquals("Voornaam gelijk", "Kim", leerling.getLeerlingVoornaam());
		assertEquals("Familienaam gelijk", "Vandamme", leerling.getLeerlingFamilienaam());
		assertEquals("Leerjaar gelijk", 3, leerling.getLeerjaar());
	}

	/**
	 * Test of een familienaam zonder fouten aangepast kan worden via setFamilienaam
	 */

	@Test
	public void testSetLeerlingFamilienaam_Familienaam_ZetJuisteFamilienaam() {
		leerling.setLeerlingFamilienaam("Vogels");
		assertEquals("Voornaam gelijk", "Marie", leerling.getLeerlingVoornaam());
		assertEquals("Familienaam gelijk", "Vogels", leerling.getLeerlingFamilienaam());
		assertEquals("Leerjaar gelijk", 3, leerling.getLeerjaar());
	}

	/**
	 * Test of een familienaam met spaties ervoor zonder fouten aangepast kan worden via setFamilienaam
	 */

	@Test
	public void testSetLeerlingFamilienaam_FamilienaamMetSpaties_ZetJuisteFamilienaam() {
		leerling.setLeerlingFamilienaam("     Vogels ");
		assertEquals("Voornaam gelijk", "Marie", leerling.getLeerlingVoornaam());
		assertEquals("Familienaam gelijk", "Vogels", leerling.getLeerlingFamilienaam());
		assertEquals("Leerjaar gelijk", 3, leerling.getLeerjaar());
	}

	/**
	 * Test of het toevoegen van quizzen aan of het veranderen van quizzen in de lijst met deelgenomen quizzen niets wijzigt aan het Leerling object en zijn quizdeelnames
	 */
	
	@Test
	public void testGetDeelgenomenQuizzen_ToevoegenOfAanpassenQuiz_GeenEffectOpLeerlingObject() {
		ArrayList<Quiz> quizzenLeerling = leerling.getDeelgenomenQuizzen();
		quizzenLeerling.get(0).setOnderwerp("Veranderd onderwerp");
		assertEquals("Veranderen onderwerp in lijst van deelgenomen quizzen verandert niets aan oorspronkelijk Quiz object", "",
				leerling.getDeelgenomenQuizzen().get(0).getOnderwerp());

		quizzenLeerling.add(new Quiz(Leraar.JOS_VERBEEK));
		assertEquals("Toevoegen quiz aan lijst van deelgenomen quizzen verandert niets aan oorspronkelijk Leerling object", 1,
				leerling.getDeelgenomenQuizzen().size());
	}	

	/**
	 * Test de toString method
	 */

	@Test
	public void testToString_NoParam_IsOK() {
		assertEquals("Leerling Marie Vandamme uit leerjaar 3", leerling.toString());
	}

	/**
	 * Test de CompareTo method voor 2 leerlingen met dezelfde familienaam
	 */

	@Test
	public void testCompareTo_TweeLeerlingenMetZelfdeFamilieNaam_FamilieNaamIsZelfde() {
		assertTrue("Leerlingen met zelfde familienaam", leerling.compareTo(tweedeleerling) == 0);
	}

	/**
	 * Test de CompareTo method voor 2 leerlingen met verschillende familienaam
	 */

	@Test
	public void testCompareTo_TweeLeerlingenMetVerschillendeFamilieNaam_GeeftFalse() {
		assertFalse("Leerlingen met verschillende familienaam", leerling.compareTo(derdeleerling) == 0);
	}

	/**
	 * Test de CompareTo method voor 2 leerlingen met dezelfde familienaam met verschillende hoofdletters
	 */

	@Test
	public void testCompareTo_TweeLeerlingenMetZelfdeFamilieNaamCases_FamilieNaamIsZelfde() {
		assertTrue("Leerlingen met zelfde familienaam", derdeleerling.compareTo(vierdeleerling) == 0);
	}

	/**
	 * Test de Equals method voor 2 dezelfde leerlingen
	 */

	@Test
	public void testEquals_TweeZelfdeLeerlingen_GeeftTrue() {
		assertTrue(leerling.equals(zelfdeleerling));
	}

	/**
	 * Test de Equals method voor 2 verschillende leerlingen
	 */

	@Test
	public void testEquals_TweeVerschillendeLeerlingen_GeeftFalse() {
		assertFalse(leerling.equals(tweedeleerling));
	}

	/**
	 * Test de Equals method voor een leerling en een ander type object
	 */

	@Test
	public void testEquals_ObjectIsGeenLeerling_GeeftFalse() {
		assertFalse(leerling.equals(quiz));
	}

}
