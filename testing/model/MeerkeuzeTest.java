package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class MeerkeuzeTest {

	Meerkeuze opdracht;
	
	@Before
	public void setUp() {
		opdracht = new Meerkeuze("Welk van de volgende dieren is geen zoogdier?", "Schildpad", 1, 20, OpdrachtCategorie.WETENSCHAPPEN, Leraar.MARIA_AERTS);
		opdracht.setOpties("Olifant;Schildpad;Hond");
	}

	@Test
	public void testIsJuisteAntwoord_JuisteAntwoord_GeeftTrue() {
		assertTrue(opdracht.isJuisteAntwoord("Schildpad"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAntwoordOpKapitalisatieNa_GeeftTrue() {
		assertTrue(opdracht.isJuisteAntwoord("sCHilDpaD"));
	}
	
	@Test
	public void testIsJuisteAntwoord_JuisteAntwoordOpSpatiesNa_GeeftTrue() {
		assertTrue(opdracht.isJuisteAntwoord("     Schildpad     "));
	}
	
	@Test
	public void testIsJuisteAntwoord_FouteAntwoord_GeeftFalse() {
		assertFalse(opdracht.isJuisteAntwoord("Olifant"));
	}
	
	@Test
	public void testIsJuisteAntwoord_FouteAntwoordWasGeenKeuze_GeeftFalse() {
		assertFalse(opdracht.isJuisteAntwoord("Kat"));
	}
	
	@Test
	public void testIsValide_BehoortNietTotDeOpties_GeeftFalse() {
		assertFalse(opdracht.isValide("Kat"));
	}
	
	@Test
	public void testIsValide_BehoortTotDeOpties_GeeftTrue() {
		assertTrue(opdracht.isValide("Hond"));
	}
	
	@Test
	public void testIsValide_LegeString_GeeftFalse() {
		assertFalse(opdracht.isValide(""));
	}
	
	@Test
	public void testSetJuisteAntwoord_GeldigeOptie_FieldWordtGezet() {
		opdracht.setJuisteAntwoord("Hond");
		assertTrue(opdracht.getJuisteAntwoord().equals("Hond"));
	}
	
	@Test
	public void testSetJuisteAntwoord_GeldigeOptieOpSpatiesNa_FieldWordtGeset() {
		opdracht.setJuisteAntwoord("  Hond  ");
		assertTrue(opdracht.getJuisteAntwoord().equals("Hond"));
	}
	
	@Test
	public void testSetJuisteAntwoord_GeldigeOptieOpKapitalisatieNa_FieldWordtGeset() {
		opdracht.setJuisteAntwoord("HOND");
		assertTrue(opdracht.getJuisteAntwoord().equals("Hond"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetJuisteAntwoord_OngeldigeOptie_ThrowsIllegalArgumentException() {
		opdracht.setJuisteAntwoord("Kat");		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetJuisteAntwoord_LegeString_ThrowsIllegalArgumentException() {
		opdracht.setJuisteAntwoord("");		
	}
	
	@Test
	public void testSetOpties_GeldigeOpties_WordenGeset() {
		opdracht.setOpties("Schildpad;Giraf;Leeuw");		
		assertTrue(opdracht.getOpties().containsAll(Arrays.asList(new String[] {"Schildpad", "Giraf", "Leeuw"})));
	}
	
	@Test
	public void testSetOpties_OngeldigeOpties_OudeOptiesBlijvenBehouden() {
		ArrayList<String> oudeOpties = opdracht.getOpties();
		try {
		opdracht.setOpties("Giraf;Leeuw");
		} catch (Exception ex) {}
		assertEquals(oudeOpties, opdracht.getOpties());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetOpties_OngeldigeOpties_ThrowsIllegalArgumentException() {
		opdracht.setOpties("Giraf;Leeuw");
	}
	
	@Test
	public void testEquals_CloneAlsArgument_GeeftTrue() {
		assertTrue(opdracht.equals(opdracht.clone()));
	}
}
