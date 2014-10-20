package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import util.datumScratch.Datum;

public class DatumTest {

	private Datum geldigeDatum, eerdereDatum, latereDatum, zelfdeDatum, datum, datumString, veranderdeDatum, eersteDatum,
			laatsteDatum, schrikkelDatum;

	@Before
	public void setUp() {
		//Datum.setLocale(new Locale("nl", "BE"));
		datum = new Datum(11, 10, 2014);
		datumString = new Datum("30/09/2014");
		veranderdeDatum = new Datum(14, 10, 2014);
		geldigeDatum = new Datum(1, 2, 1980);
		eerdereDatum = new Datum(31, 1, 1980);
		latereDatum = new Datum(2, 3, 1980);
		zelfdeDatum = new Datum(1, 2, 1980);
		eersteDatum = new Datum(1, 1, 1);
		laatsteDatum = new Datum(31, 12, 9999);
		schrikkelDatum = new Datum(29, 2, 2012);
	}

	@Test
	public void testDatum_DatumAlsInput_DatumsZijnGelijk() {
		Datum datum = new Datum(geldigeDatum);
		assertEquals("Dag gelijk", geldigeDatum.getDag(), datum.getDag());
		assertEquals("Maand gelijk", geldigeDatum.getMaand(), datum.getMaand());
		assertEquals("Jaar gelijk", geldigeDatum.getJaar(), datum.getJaar());
	}

	@Test
	public void testDatum_NoParam_DatumGelijkAanSysteemDatum() {
		Datum datum = new Datum();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		assertEquals("Dag gelijk", gregorianCalendar.get(Calendar.DAY_OF_MONTH), datum.getDag());
		assertEquals("Maand gelijk", gregorianCalendar.get(Calendar.MONTH) + 1, datum.getMaand());
		assertEquals("Jaar gelijk", gregorianCalendar.get(Calendar.YEAR), datum.getJaar());
	}

	@Test
	public void testGetDatumInAmerikaansFormaat_VanEenderWelkeDatum_StringInJuistFormaat() {
		assertEquals("Datumstrings gelijk", "1980/2/1", geldigeDatum.getDatumInAmerikaansFormaat());
	}

	@Test
	public void testCompareTo_zelfdeDatum_Geeft0() {
		assertEquals("Zelfde datum", 0, geldigeDatum.compareTo(zelfdeDatum));
	}

	@Test
	public void testCompareTo_EerdereDatum_GeeftPositief() {
		assertTrue(geldigeDatum.compareTo(eerdereDatum) > 0);
	}

	@Test
	public void testCompareTo_LatereDatum_GeeftNegatief() {
		assertTrue(geldigeDatum.compareTo(latereDatum) < 0);
	}

	@Test
	public void testVerschilInDagen_GeenVerschil_Geeft0() {
		assertEquals("Zelfde datum", 0, geldigeDatum.verschilInDagen(zelfdeDatum));
	}

	@Test
	public void testVerschilInDagen_GrootVerschil_GeeftCorrecteWaarde() {
		// Juiste verschilwaarde berekend met on-line tool (http://www.timeanddate.com/date/duration.html)
		Datum datumLangGeleden = new Datum(13, 2, 1599);
		assertEquals("Verschil in dagen", 139145, geldigeDatum.verschilInDagen(datumLangGeleden));
	}

	@Test
	public void testVerschilInDagen_VerschillendeDatumMinderDan24Uur_Geeft1() throws InterruptedException {
		Datum vandaag = new Datum();
		Thread.sleep(10);
		Datum vandaagEenBeetjeLater = new Datum();
		vandaag.veranderDatum(1);
		assertEquals("Verschillende dag, minder dan 24 uur verschil", 1, vandaagEenBeetjeLater.verschilInDagen(vandaag));
	}

	@Test
	public void testDatum_3IntInput_MaaktJuisteDatum() {
		assertEquals("Dag gelijk", 1, geldigeDatum.getDag());
		assertEquals("Maand gelijk", 2, geldigeDatum.getMaand());
		assertEquals("Jaar gelijk", 1980, geldigeDatum.getJaar());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatum3Int_OngeldigeDag_ThrowsIllegalArgumentException() {
		@SuppressWarnings("unused")
		Datum datum = new Datum(31, 4, 2000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatum3Int_OngeldigeMaand_ThrowsIllegalArgumentException() {
		@SuppressWarnings("unused")
		Datum datum = new Datum(4, 15, 2000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatum3Int_TeHoogJaar_ThrowsIllegalArgumentException() {
		@SuppressWarnings("unused")
		Datum datum = new Datum(4, 4, 50000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatum3Int_TeLaagJaar_ThrowsIllegalArgumentException() {
		@SuppressWarnings("unused")
		Datum datum = new Datum(4, 4, -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatum3Int_OngeldigeSchrikkeldag_ThrowsIllegalArgumentException() {
		@SuppressWarnings("unused")
		Datum datum = new Datum(29, 2, 1999);
	}

	@Test
	public void testSetDatum_GeldigeInput_ZetJuisteDatum() {
		geldigeDatum.setDatum(4, 4, 2000);
		assertEquals("Dag gelijk", 4, geldigeDatum.getDag());
		assertEquals("Maand gelijk", 4, geldigeDatum.getMaand());
		assertEquals("Jaar gelijk", 2000, geldigeDatum.getJaar());
	}

	@Test
	public void testSetDatum_OngeldigeDag_GeeftFalse() {
		assertFalse(geldigeDatum.setDatum(31, 4, 2000));
	}

	@Test
	public void testSetDatum_OngeldigeMaand_GeeftFalse() {
		assertFalse(geldigeDatum.setDatum(4, 15, 2000));
	}

	@Test
	public void testSetDatum_TeHoogJaar_GeeftFalse() {
		assertFalse(geldigeDatum.setDatum(4, 4, 50000));
	}

	@Test
	public void testSetDatum_TeLaagJaar_GeeftFalse() {
		assertFalse(geldigeDatum.setDatum(4, 4, -10));
	}

	@Test
	public void testSetDatum_OngeldigeSchrikkeldag_GeeftFalse() {
		assertFalse(geldigeDatum.setDatum(29, 2, 1999));
	}

	@Test
	public void testEquals_ZelfdeDatum_GeeftTrue() {
		assertTrue(geldigeDatum.equals(zelfdeDatum));
	}

	@Test
	public void testEquals_Null_GeeftFalse() {
		assertTrue(!geldigeDatum.equals(null));
	}

	@Test
	public void testEquals_AndereKlasse_GeeftFalse() {
		Object obj = new Object();
		assertTrue(!geldigeDatum.equals(obj));
	}

	@Test
	public void testEquals_AndereDag_GeeftFalse() {
		Datum datum = new Datum(2, 2, 1980);
		assertTrue(!geldigeDatum.equals(datum));
	}

	@Test
	public void testEquals_AndereMaand_GeeftFalse() {
		Datum datum = new Datum(1, 4, 1980);
		assertTrue(!geldigeDatum.equals(datum));
	}

	@Test
	public void testEquals_AnderJaar_GeeftFalse() {
		Datum datum = new Datum(1, 2, 1984);
		assertTrue(!geldigeDatum.equals(datum));
	}

	@Test
	public void testVerschilInMaanden_EenVerschil_WordtCorrectBerekend() {
		Datum datum = new Datum(5, 1, 1981);
		assertEquals("Verschil in Maanden", 11, geldigeDatum.verschilInMaanden(datum));
	}

	@Test
	public void testVerschilInMaanden_GeenVerschil_Geeft0() {
		assertEquals("Zelfde datum", 0, geldigeDatum.verschilInDagen(zelfdeDatum));
	}

	@Test
	public void testVerschilInMaanden_MinderDanMaandVerschil_Geeft0() {
		Datum datum = new Datum(5, 1, 1980);
		assertEquals("Verschil in Maanden", 0, geldigeDatum.verschilInMaanden(datum));
	}

	@Test
	public void testDatum_StringAlsInput_MaaktJuisteDatumAan() {
		assertEquals(30, datumString.getDag());
		assertEquals(9, datumString.getMaand());
		assertEquals(2014, datumString.getJaar());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatumStringInput_OngeldigeInputString_ThrowsIllegalArgumentException() {
		datum = new Datum("3a/09/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatumStringInput_OngeldigeInputStringFormaat_ThrowsIllegalArgumentException() {
		datum = new Datum("01/28/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatumStringInput_OngeldigeDag_ThrowsIllegalArgumentException() {
		datum = new Datum("29/02/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatumStringInput_OngeldigeMaand_ThrowsIllegalArgumentException() {
		datum = new Datum("29/24/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatumStringInput_TeHoogJaar_ThrowsIllegalArgumentException() {
		datum = new Datum("29/02/10200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDatumStringInput_TeLaagJaar_ThrowsIllegalArgumentException() {
		datum = new Datum("29/02/0");
	}

	@Test
	public void testGetDatumInEuropeesFormaat_VanEenderWelkeDatum_GeeftJuisteFormaat() {
		assertEquals("11/10/2014", datum.getDatumInEuropeesFormaat());
	}

	@Test
	public void testKleinerDan_InputKleinereDatum_GeeftTrue() {
		assertTrue(datum.kleinerDan(datumString));
	}

	@Test
	public void testKleinerDan_InputGrotereDatum_GeeftFalse() {
		assertFalse(datumString.kleinerDan(datum));
	}

	@Test
	public void testVeranderDatum_GeeftJuisteDatum() {
		datum.veranderDatum(3);
		assertEquals(datum, veranderdeDatum);
	}

	@Test
	public void testGetJaar_VanGeldigeDatum_GeeftCorrectResultaat() {
		assertTrue("foutief jaar 2014", geldigeDatum.getJaar() == 1980);
	}

	@Test
	public void testGetJaar_VanLaatsteJaar_Geeft9999AlsResultaat() {
		assertTrue("foutief jaar 9999", laatsteDatum.getJaar() == 9999);
	}

	@Test
	public void testGetJaar_VanEersteJaar_Geeft1AlsResultaat() {
		assertTrue("foutief jaar 1", eersteDatum.getJaar() == 1);
	}

	@Test
	public void testGetMaand_VanMaand10_Geeft10AlsResultaat() {
		assertTrue("foutieve maand 10", geldigeDatum.getMaand() == 2);
	}

	@Test
	public void testGetDag_VanDag1_Geeft1AlsResultaat() {
		assertTrue("foutieve dag 1", geldigeDatum.getDag() == 1);
	}

	@Test
	public void testGetDag_VanDag31_Geeft31AlsResultaat() {
		assertTrue("foutieve dag 31", laatsteDatum.getDag() == 31);
	}

	@Test
	public void testGetDag_VanSchrikkeldag_Geeft29AlsResultaat() {
		assertTrue("foutieve schrikkeldag 29", schrikkelDatum.getDag() == 29);
	}

	@Test
	public void testToString_VanMaand10DitJaar_GeeftEenCorrectResultaat() {
		assertEquals("foutieve toString 01/10/2014", "1 februari 1980", geldigeDatum.toString());
	}

	@Test
	public void testToString_VanEersteDatum_GeeftEenCorrectResultaat() {
		assertEquals("foutieve toString 01/01/0001", "1 januari 1", eersteDatum.toString());
	}

	@Test
	public void testToString_VanLaatsteDatum_GeeftEenCorrectResultaat() {
		assertEquals("foutieve toString 31/12/9999", "31 december 9999", laatsteDatum.toString());
	}

	@Test
	public void testVerschilInJaren_VanHetzelfdeJaar_Geeft0AlsResultaat() {
		assertTrue("foutief verschil in jaren <> 0", geldigeDatum.verschilInJaren(eerdereDatum) == 0);
	}

	@Test
	public void testVerschilInJaren_VanHetzelfdeJaarMetGetter_Geeft0AlsResultaat() {
		assertTrue("foutief verschil in jaren <> 0 obv getters",
				geldigeDatum.verschilInJaren(latereDatum) == (geldigeDatum.getJaar() - latereDatum.getJaar()));
	}

	@Test
	public void testVerschilInJaren_VolgordeKleinsteOfGrootsteMaaktNietsUit_GeeftCorrectResultaat() {
		assertTrue("foutief verschil in jaren afhankelijk van volgorde kleinste en grootste jaar",
				geldigeDatum.verschilInJaren(laatsteDatum) == laatsteDatum.verschilInJaren(geldigeDatum));
	}

	@Test
	public void testVerschilInJaren_TussenEersteEnLaatsteDatum_Geeft9999AlsResultaat() {
		assertTrue("foutief verschil in jaren <> 9999", laatsteDatum.verschilInJaren(eersteDatum) == (9999 - 1));
	}

	@Test
	public void testVeranderDatum_VerhoogMet30Dagen_GeeftCorrectResultaat() {
		geldigeDatum.veranderDatum(30);
		assertEquals("foutieve verhoging met 30 dagen", geldigeDatum, latereDatum);
	}

	@Test
	public void testVeranderDatum_VerlaagMet1Dagen_GeeftCorrectResultaat() {
		geldigeDatum.veranderDatum(-1);
		assertEquals("foutieve verlaging met 1 dag", geldigeDatum, eerdereDatum);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVeranderDatum_VerlaagOnderDeLaagsteDatum_ThrowsIllegalArgumentException() {
		eersteDatum.veranderDatum(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVeranderDatum_VerhoogVoorbijDeHoogsteDatum_ThrowsIllegalArgumentException() {
		laatsteDatum.veranderDatum(1);
	}

	@Test
	public void testSetDatum_VeelRandomDatums_WordenCorrectGeevalueerd() {

		final int AANTAL_DATUMS = 10000;
		Random random = new Random();
		int[][] geldigeDatums = new int[AANTAL_DATUMS][];
		int[][] ongeldigeDatums = new int[AANTAL_DATUMS][];
		int dag, maand, jaar;
		int ongeldigeIndex = 0, geldigeIndex = 0;

		GregorianCalendar datumTester = new GregorianCalendar();
		datumTester.setLenient(false);

		for (int i = 0; i < AANTAL_DATUMS; i++) {
			dag = random.nextInt(40);
			maand = random.nextInt(16);
			jaar = random.nextInt(11500) - 500;

			datumTester.set(jaar, maand - 1, dag);
			try {
				datumTester.getTime();
				if (jaar < 0 || jaar > 9999) {
					throw new IllegalArgumentException();
				}
				geldigeDatums[geldigeIndex++] = new int[] { dag, maand, jaar };
			} catch (IllegalArgumentException ex) {
				ongeldigeDatums[ongeldigeIndex++] = new int[] { dag, maand, jaar };
			}
		}

		for (int i = 0; i < geldigeIndex; i++) {
			assertTrue(datum.setDatum(geldigeDatums[i][0], geldigeDatums[i][1], geldigeDatums[i][2]));
		}

		for (int i = 0; i < ongeldigeIndex; i++) {
			assertFalse(datum.setDatum(ongeldigeDatums[i][0], ongeldigeDatums[i][1], ongeldigeDatums[i][2]));
		}
	}

	@Test
	public void testDatum3Int_VeelRandomDatums_WordenCorrectVerwerkt() {

		final int AANTAL_DATUMS = 10000;
		Random random = new Random();
		int[][] geldigeDatums = new int[AANTAL_DATUMS][];
		int[][] ongeldigeDatums = new int[AANTAL_DATUMS][];
		int dag, maand, jaar;
		int ongeldigeIndex = 0, geldigeIndex = 0;

		GregorianCalendar datumTester = new GregorianCalendar();
		datumTester.setLenient(false);

		for (int i = 0; i < AANTAL_DATUMS; i++) {
			dag = random.nextInt(40);
			maand = random.nextInt(16);
			jaar = random.nextInt(11500) - 500;

			datumTester.set(jaar, maand - 1, dag);
			try {
				datumTester.getTime();
				if (jaar < 0 || jaar > 9999) {
					throw new IllegalArgumentException();
				}
				geldigeDatums[geldigeIndex++] = new int[] { dag, maand, jaar };
			} catch (IllegalArgumentException ex) {
				ongeldigeDatums[ongeldigeIndex++] = new int[] { dag, maand, jaar };
			}
		}

		@SuppressWarnings("unused")
		Datum testDatum;

		for (int i = 0; i < geldigeIndex; i++) {
			try {
				testDatum = new Datum(geldigeDatums[i][0], geldigeDatums[i][1], geldigeDatums[i][2]);
			} catch (Exception ex) {
				fail("Een geldige datum is niet aanvaard");
			}
		}

		int aantalExceptions = 0;
		for (int i = 0; i < ongeldigeIndex; i++) {
			try {
				testDatum = new Datum(ongeldigeDatums[i][0], ongeldigeDatums[i][1], ongeldigeDatums[i][2]);
			} catch (IllegalArgumentException ex) {
				aantalExceptions++;
			}
		}

		assertEquals("Niet aanvaarden van ongeldige datums", ongeldigeIndex, aantalExceptions);
	}
}
