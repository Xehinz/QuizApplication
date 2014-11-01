package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class OpdrachtCatalogusTest {

	OpdrachtCatalogus legeOpdrachtCatalogus, opdrachtCatalogusGemaaktMetLijstOpdrachten, gevuldeCatalogusZelfdeOpdrachten,
			gevuldeCatalogusAndereOpdrachten, tweedeLegeCatalogus;
	ArrayList<Opdracht> opdrachten;
	Opdracht opdracht1, opdracht2, opdracht3;

	@Before
	public void setUp() {
		legeOpdrachtCatalogus = new OpdrachtCatalogus();
		tweedeLegeCatalogus = new OpdrachtCatalogus();

		opdracht1 = new Opdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		opdracht2 = new Opdracht(OpdrachtCategorie.WETENSCHAPPEN, Leraar.MARIA_AERTS);
		opdracht3 = new Opdracht(OpdrachtCategorie.NEDERLANDS, Leraar.STEVEN_OPDEBEEK);

		opdrachten = new ArrayList<Opdracht>();

		opdrachten.add(opdracht1);
		opdrachten.add(opdracht2);

		opdrachtCatalogusGemaaktMetLijstOpdrachten = new OpdrachtCatalogus(opdrachten);

		gevuldeCatalogusZelfdeOpdrachten = new OpdrachtCatalogus();
		gevuldeCatalogusZelfdeOpdrachten.addOpdracht(opdracht1);
		gevuldeCatalogusZelfdeOpdrachten.addOpdracht(opdracht2);

		gevuldeCatalogusAndereOpdrachten = new OpdrachtCatalogus();
		gevuldeCatalogusAndereOpdrachten.addOpdracht(opdracht1);
		gevuldeCatalogusAndereOpdrachten.addOpdracht(opdracht3);
	}

	@Test
	public void testOpdrachtCatalogus_ConstructorInputLijstjeOpdrachten_BevatCorrecteOpdrachten() {
		assertTrue("Bevat de juiste opdracht(en)", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht1));
		assertTrue("Bevat de juiste opdracht(en)", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht2));
	}

	@Test
	public void testAddOpdracht_ToevoegenOpdracht_CatalogusBevatToegevoegdeOpdracht() {
		legeOpdrachtCatalogus.addOpdracht(opdracht1);
		assertTrue("Toegevoegde opdracht zit in catalogus", legeOpdrachtCatalogus.hasOpdracht(opdracht1));

		opdrachtCatalogusGemaaktMetLijstOpdrachten.addOpdracht(opdracht3);
		assertTrue("Toegevoegde opdracht zit in catalogus", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht3));
	}

	@Test
	public void testRemoveOpdracht_VerwijderenOpdracht_CatalogusBevatVerwijderdeOpdrachtNietMeer() {
		legeOpdrachtCatalogus.addOpdracht(opdracht1);
		legeOpdrachtCatalogus.removeOpdracht(opdracht1);
		assertFalse("Verwijderde opdracht zit niet meer in de catalogus", legeOpdrachtCatalogus.hasOpdracht(opdracht1));

		opdrachtCatalogusGemaaktMetLijstOpdrachten.removeOpdracht(opdracht2);
		assertFalse("Verwijderde opdracht zit niet meer in de catalogus",
				opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht2));
	}

	@Test
	public void testHasOpdracht_OpdrachtDieErInZit_ReturnsTrue() {
		assertTrue("True voor opdracht die erin zit", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht1));
	}

	@Test
	public void testHasOpdracht_OpdrachtDieErNietInZit_ReturnsFalse() {
		assertFalse("False voor opdracht die er niet in zit", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht3));
	}

	@Test
	public void testHasOpdracht_BijToevoegenOpdracht_FalseVoorToevoegingTrueNa() {
		assertFalse("False voor toevoeging", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht3));
		opdrachtCatalogusGemaaktMetLijstOpdrachten.addOpdracht(opdracht3);
		assertTrue("True na toevoeging", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht3));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOpdracht_OphalenNuldeOpdracht_ThrowsIndexOutOfBoundsException() {
		opdrachtCatalogusGemaaktMetLijstOpdrachten.getOpdracht(0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOpdracht_OphalenOpdrachtUitLegeCatalogus_ThrowsIndexOutOfBoundsException() {
		legeOpdrachtCatalogus.getOpdracht(1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOpdracht_OphalenOpdrachtTeHoogVolgnummer_ThrowsIndexOutOfBoundsException() {
		opdrachtCatalogusGemaaktMetLijstOpdrachten.getOpdracht(3);
	}

	@Test
	public void testGetOpdracht_OphalenBestaandeOpdracht_HaaltJuisteOpdrachtOp() {
		Opdracht opgehaaldeOpdracht = opdrachtCatalogusGemaaktMetLijstOpdrachten.getOpdracht(2);
		assertEquals("Juiste opdracht wordt opgehaald", opdracht2, opgehaaldeOpdracht);
	}

	@Test
	public void testCount_LegeCatalogus_Geeft0() {
		assertEquals("Lege catalogus geeft count van 0", 0, legeOpdrachtCatalogus.count());
	}

	@Test
	public void testCount_GevuldeCatalogus_GeeftCorrecteCount() {
		assertEquals("Gevulde catalgous geeft correcte count", 2, opdrachtCatalogusGemaaktMetLijstOpdrachten.count());
	}

	@Test
	public void testEquals_NullCatalogus_GeeftFalse() {
		OpdrachtCatalogus nullCatalogus = null;
		assertFalse("Null catalogus geeft false", legeOpdrachtCatalogus.equals(nullCatalogus));
	}

	@Test
	public void testEquals_ObjectAnderType_GeeftFalse() {
		Opdracht anderTypeObject = new Opdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.MIEKE_WITTEMANS);
		assertFalse("OpdrachtCatalogus vergelijken met een Opdracht geeft false", legeOpdrachtCatalogus.equals(anderTypeObject));
	}

	@Test
	public void testEquals_CatalogiMetZelfdeOpdrachten_ZijnGelijk() {
		assertTrue("Catalogi met zelfde opdrachten zijn gelijk",
				opdrachtCatalogusGemaaktMetLijstOpdrachten.equals(gevuldeCatalogusZelfdeOpdrachten));
	}

	@Test
	public void testEquals_CatalogiMetAndereOpdrachten_ZijnNietGelijk() {
		assertFalse("Catalogi met andere opdrachten zijn niet gelijk",
				opdrachtCatalogusGemaaktMetLijstOpdrachten.equals(gevuldeCatalogusAndereOpdrachten));
	}

	@Test
	public void testEquals_TweeLegeCatalogi_ZijnGelijk() {
		assertTrue("Twee lege catalogi zijn gelijk", legeOpdrachtCatalogus.equals(tweedeLegeCatalogus));
	}

	@Test
	public void testClone_ClonenCatalogus_CatalogiBevattenZelfdeOpdrachten() {
		OpdrachtCatalogus clone = opdrachtCatalogusGemaaktMetLijstOpdrachten.clone();
		for (int i = 1; i <= clone.count(); i++) {
			assertEquals("Opdrachten van clone zijn dezelfde", opdrachtCatalogusGemaaktMetLijstOpdrachten.getOpdracht(i),
					clone.getOpdracht(i));
		}
	}

	@Test
	public void testClone_WijzigingenToebrengenAanClone_HebbenGeenInvloedOpOrigineel() {
		OpdrachtCatalogus clone = opdrachtCatalogusGemaaktMetLijstOpdrachten.clone();
		clone.addOpdracht(opdracht3);
		assertFalse("Origineel is niet aangepast", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht3));
		clone.removeOpdracht(opdracht1);
		assertTrue("Origineel is niet aangepast", opdrachtCatalogusGemaaktMetLijstOpdrachten.hasOpdracht(opdracht1));
	}

	@Test
	public void testClone_WijzigingenToebrengenAanOpdrachtenVanClone_HebbenGeenInvloedOpOpdrachtenOrigineel() {
		OpdrachtCatalogus clone = opdrachtCatalogusGemaaktMetLijstOpdrachten.clone();

		Opdracht teWijzigen = clone.getOpdracht(1);
		String onnozeleVraag = "Waarom zijn bananen krom?";
		teWijzigen.setVraag(onnozeleVraag);

		Opdracht origineel = opdrachtCatalogusGemaaktMetLijstOpdrachten.getOpdracht(1);
		assertFalse("Na wijzigen van een opdracht van de clone is de opdracht in de originele catalogus onveranderd",
				origineel.getVraag() == onnozeleVraag);
	}

	@Test
	public void testCompareTo_CatalogiMetZelfdeAantalOpdrachten_Geeft0() {
		assertEquals("Catalogi met zelfde aantal opdrachten geeft 0", 0,
				opdrachtCatalogusGemaaktMetLijstOpdrachten.compareTo(gevuldeCatalogusAndereOpdrachten));
	}

	@Test
	public void testCompareTo_CatalogusHeeftMinderOpdrachtenDanArgument_GeeftNegatief() {
		assertTrue("Catalogus met minder opdrachten dan argument geeft negatief",
				legeOpdrachtCatalogus.compareTo(opdrachtCatalogusGemaaktMetLijstOpdrachten) < 0);
	}

	@Test
	public void testCompareTo_CatalogusHeeftMeerOpdrachtenDanArgument_GeeftPositief() {
		assertTrue("Catalogus met meer opdrachten dan argument geeft positief",
				opdrachtCatalogusGemaaktMetLijstOpdrachten.compareTo(legeOpdrachtCatalogus) > 0);
	}
}
