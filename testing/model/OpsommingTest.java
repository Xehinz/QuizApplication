package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class OpsommingTest {

	Opsomming opdrachtNietInVolgorde, opdrachtInVolgorde;

	@Before
	public void setUp() throws Exception {
		opdrachtNietInVolgorde = new Opsomming("Som de weekdagen op", "maandag;dinsdag;woensdag;donderdag;vrijdag",
				OpdrachtCategorie.NEDERLANDS, Leraar.CHARLOTTE_NEVEN, false);
		opdrachtInVolgorde = new Opsomming("Som de weekdagen op", "maandag;dinsdag;woensdag;donderdag;vrijdag",
				OpdrachtCategorie.NEDERLANDS, Leraar.CHARLOTTE_NEVEN, true);
	}

	@Test
	public void testIsJuisteAntwoord_VolgordeMaaktNietUitAntwoordFouteVolgorde_GeeftTrue() {
		assertTrue(opdrachtNietInVolgorde.isJuisteAntwoord("dinsdag;maandag;vrijdag;donderdag;woensdag"));
	}

	@Test
	public void testIsJuisteAntwoord_VolgordeMaaktUitAntwoordFouteVolgorde_GeeftFalse() {
		assertFalse(opdrachtInVolgorde.isJuisteAntwoord("dinsdag;maandag;vrijdag;donderdag;woensdag"));
	}

	@Test
	public void testIsJuisteAntwoord_VolgordeMaaktUitAntwoordJuisteVolgorde_GeeftTrue() {
		assertTrue(opdrachtInVolgorde.isJuisteAntwoord("maandag;dinsdag;woensdag;donderdag;vrijdag"));
	}

	@Test
	public void testIsJuisteAntwoord_JuisteAntwoordOpSpatiesNa_GeeftTrue() {
		assertTrue(opdrachtInVolgorde.isJuisteAntwoord(" maandag  ;  dinsdag; woensdag   ;   donderdag ; vrijdag"));
	}

	@Test
	public void testIsJuisteAntwoord_JuisteAntwoordOpKapitalisatieNa_GeeftTrue() {
		assertTrue(opdrachtInVolgorde.isJuisteAntwoord("Maandag;dinSdag;WoensdAg;DonderDag;VRIJDAG"));
	}

	@Test
	public void testIsValide_1Antwoord_GeeftFalse() {
		assertFalse(opdrachtInVolgorde.isValide("maandag"));
	}

	@Test
	public void testIsValide_FoutGescheiden_GeeftFalse() {
		assertFalse(opdrachtInVolgorde.isValide("maandag,dinsdag,woensdag,donderdag"));
	}

	@Test
	public void testIsValide_JuisteAantalAntwoorden_GeeftTrue() {
		assertTrue(opdrachtInVolgorde.isValide("maandag;dinsdag;woensdag;donderdag;vrijdag"));
	}
	
	@Test
	public void testEquals_CloneAlsArgument_GeeftTrue() {
		assertTrue(opdrachtInVolgorde.equals(opdrachtInVolgorde.clone()));
	}
}
