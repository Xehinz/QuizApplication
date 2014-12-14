package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ReproductieTest {

	Reproductie opdracht5TrefWoordenNodig;
	
	@Before
	public void setUp() {
		opdracht5TrefWoordenNodig = new Reproductie("Beschrijf het zonnestelsel met een kort tekstje", "zon;mercurius;venus;aarde;mars;jupiter;saturnus;uranus;neptunus;komeet;kometen;planeten;planeet;ster", 5, 1, 10, OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MARIA_AERTS);
	}

	@Test
	public void testIsJuisteAntwoord_JuistGenoegTrefwoorden_GeeftTrue() {
		assertTrue(opdracht5TrefWoordenNodig.isJuisteAntwoord("zon aarde mars mercurius venus"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuistTeVeelTrefwoorden_GeeftTrue() {
		assertTrue(opdracht5TrefWoordenNodig.isJuisteAntwoord("zon aarde mars mercurius venus komeet"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuistTeWeinigTrefwoorden_GeeftFalse() {
		assertFalse(opdracht5TrefWoordenNodig.isJuisteAntwoord("zon aarde mars mercurius"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAantalTrefwoordenTrefwoordenGescheidenDoorKomma_GeeftTrue() {
		assertTrue(opdracht5TrefWoordenNodig.isJuisteAntwoord("De zon wordt omringd door een hoop planeten. Enkele zijn aarde,mars en venus."));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAantalTrefwoordenVerstoptInAndereWoorden_GeeftFalse() {
		assertFalse(opdracht5TrefWoordenNodig.isJuisteAntwoord("Zonet waarde sterk opmars jupiterbier"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAantalTrefwoordenTweemaalZelfdeTrefwoord_GeeftFalse() {
		assertFalse(opdracht5TrefWoordenNodig.isJuisteAntwoord("zon zon aarde mars venus"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAntwoordOpKapitalisatieNa_GeeftTrue() {
		assertTrue(opdracht5TrefWoordenNodig.isJuisteAntwoord("ZON aArDe MERcurIUS komeeT PLAnEet"));
	}
	
	@Test
	public void testSetMinimumAantalTrefwoorden_AantalGelijkAanAantalTrefwoorden_Lukt() {
		opdracht5TrefWoordenNodig.setMinimumAantalTrefwoorden(14);
		assertTrue(opdracht5TrefWoordenNodig.getMinimumAantalTrefwoorden() == 14);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetMinimumAantalTrefwoorden_AantalHogerDanAantalTrefwoorden_ThrowsIllegalArgumentException() {
		opdracht5TrefWoordenNodig.setMinimumAantalTrefwoorden(15);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetMinimumAantalTrefwoorden_0Trefwoorden_ThrowsIllegalArgumentException() {
		opdracht5TrefWoordenNodig.setMinimumAantalTrefwoorden(0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetJuisteAntwoord_GeenTrefwoorden_ThrowsIllegalArgumentException() {
		opdracht5TrefWoordenNodig.setJuisteAntwoord("");
	}
	
	@Test
	public void testEquals_TweeDezelfdeOpJuisteTrefwoordenNa_GeeftFalse() {
		Reproductie clone = opdracht5TrefWoordenNodig.clone();
		clone.setJuisteAntwoord("neptunus;komeet;kometen;planeten;planeet;ster");
		assertFalse(opdracht5TrefWoordenNodig.equals(clone));
	}
	
	@Test
	public void testEquals_CloneAlsArgument_GeeftTrue() {
		assertTrue(opdracht5TrefWoordenNodig.equals(opdracht5TrefWoordenNodig.clone()));
	}

}
