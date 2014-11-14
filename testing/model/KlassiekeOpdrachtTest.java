package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class KlassiekeOpdrachtTest {
	
	private Opdracht opdracht;

	@Before
	public void setUp() throws Exception {
		opdracht = new KlassiekeOpdracht("Wat is de hoofdstad van België?", "Brussel", OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MIEKE_WITTEMANS);			
	}

	@Test
	public void testIsJuisteAntwoord_JuisteAntwoord_GeeftTrue() {
		assertTrue("Juiste antwoord wordt juist gerekend", opdracht.isJuisteAntwoord("Brussel"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAntwoordOpSpatiesNa_GeeftTrue() {
		assertTrue("Juiste antwoord op spaties na wordt juist gerekend", opdracht.isJuisteAntwoord("    Brussel     "));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAntwoordOpKapitalisatieNa_GeeftTrue() {
		assertTrue("Juiste antwoord op kapitalisatie na wordt juist gerekend", opdracht.isJuisteAntwoord("bRuSSeL"));
	}
	
	@Test
	public void testIsJuisteAntwoord_FouteAntwoord_GeeftFalse() {
		assertFalse("Foute antwoord wordt afgekeurd", opdracht.isJuisteAntwoord("Blussel"));
	}
	
	@Test
	public void testEquals_CloneAlsArgument_GeeftTrue() {
		assertTrue(opdracht.equals(opdracht.clone()));
	}

}
