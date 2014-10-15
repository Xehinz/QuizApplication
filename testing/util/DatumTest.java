package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import util.datumWrapper.Datum;

public class DatumTest {

	private Datum geldigeDatum, eerdereDatum, latereDatum, zelfdeDatum, datum, datumString, veranderdeDatum;

	@Before
	public void setUp() {
		datum = new Datum(11, 10, 2014);
		datumString = new Datum("30/09/2014");
		veranderdeDatum = new Datum(14, 10, 2014);
		geldigeDatum = new Datum(1, 2, 1980);
		eerdereDatum = new Datum(31, 1, 1980);
		latereDatum = new Datum(2, 2, 1980);
		zelfdeDatum = new Datum(1, 2, 1980);
	}

	@Test
	public void test_Datum_Datum_datums_gelijk() {
		Datum datum = new Datum(geldigeDatum);
		assertEquals("Dag gelijk", geldigeDatum.getDag(), datum.getDag());
		assertEquals("Maand gelijk", geldigeDatum.getMaand(), datum.getMaand());
		assertEquals("Jaar gelijk", geldigeDatum.getJaar(), datum.getJaar());
	}

	@Test
	public void test_Datum_NoParam_datum_gelijk_aan_systeemdatum() {
		Datum datum = new Datum();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		assertEquals("Dag gelijk", gregorianCalendar.get(Calendar.DAY_OF_MONTH), datum.getDag());
		assertEquals("Maand gelijk", gregorianCalendar.get(Calendar.MONTH) + 1, datum.getMaand());
		assertEquals("Jaar gelijk", gregorianCalendar.get(Calendar.YEAR), datum.getJaar());
	}

	@Test
	public void test_getDatumInAmerikaansFormaat_string_in_juist_formaat() {
		assertEquals("Datumstrings gelijk", "1980/2/1", geldigeDatum.getDatumInAmerikaansFormaat());
	}

	@Test
	public void test_compareTo_zelfde_datum_geeft_0() {
		assertEquals("Zelfde datum", 0, geldigeDatum.compareTo(zelfdeDatum));
	}

	@Test
	public void test_compareTo_eerdere_datum_geeft_positief() {
		assertTrue(geldigeDatum.compareTo(eerdereDatum) > 0);
	}

	@Test
	public void test_compareTo_latere_datum_geeft_negatief() {
		assertTrue(geldigeDatum.compareTo(latereDatum) < 0);
	}

	@Test
	public void test_verschilInDagen_geen_verschil_geeft_0() {
		assertEquals("Zelfde datum", 0, geldigeDatum.verschilInDagen(zelfdeDatum));
	}

	@Test
	public void test_verschilInDagen_groot_verschil_correcte_waarde() {
		// Juiste verschilwaarde berekend met on-line tool (http://www.timeanddate.com/date/duration.html)
		Datum datumLangGeleden = new Datum(13, 2, 1599);
		assertEquals("Verschil in dagen", 139145, geldigeDatum.verschilInDagen(datumLangGeleden));
	}

	@Test
	public void test_verschilInDagen_verschillende_datum_minder_dan_24_uur_geeft_1() throws InterruptedException {
		Datum vandaag = new Datum();
		Thread.sleep(10);
		Datum vandaagEenBeetjeLater = new Datum();
		vandaag.veranderDatum(1);
		assertEquals("Verschillende dag, minder dan 24 uur verschil", 1, vandaagEenBeetjeLater.verschilInDagen(vandaag));
	}

	@Test
	public void test_Datum_3int_gelijk_aan_input() {
		assertEquals("Dag gelijk", 1, geldigeDatum.getDag());
		assertEquals("Maand gelijk", 2, geldigeDatum.getMaand());
		assertEquals("Jaar gelijk", 1980, geldigeDatum.getJaar());
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_3int_ongeldige_dag() {
		Datum datum = new Datum(31, 4, 2000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_3int_ongeldige_maand() {
		Datum datum = new Datum(4, 15, 2000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_3int_ongeldig_jaar() {
		Datum datum = new Datum(4, 4, 50000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_3int_ongeldige_schrikkeldag() {
		Datum datum = new Datum(29, 2, 1999);
	}

	@Test
	public void test_setDatum_gelijk_aan_input() {
		geldigeDatum.setDatum(4, 4, 2000);
		assertEquals("Dag gelijk", 4, geldigeDatum.getDag());
		assertEquals("Maand gelijk", 4, geldigeDatum.getMaand());
		assertEquals("Jaar gelijk", 2000, geldigeDatum.getJaar());
	}

	@Test
	public void test_setDatum_ongeldige_dag_geeft_false() {
		assertFalse(geldigeDatum.setDatum(31, 4, 2000));
	}

	@Test
	public void test_setDatum_ongeldige_maand_geeft_false() {
		assertFalse(geldigeDatum.setDatum(4, 15, 2000));
	}

	@Test
	public void test_setDatum_ongeldig_jaar_geeft_false() {
		assertFalse(geldigeDatum.setDatum(4, 4, 50000));
	}

	@Test
	public void test_setDatum_ongeldige_schrikkeldag_geeft_false() {
		assertFalse(geldigeDatum.setDatum(29, 2, 1999));
	}

	@Test
	public void test_equals_zelfde_datum_geeft_true() {
		assertTrue(geldigeDatum.equals(zelfdeDatum));
	}

	@Test
	public void test_equals_null_geeft_false() {
		assertTrue(!geldigeDatum.equals(null));
	}

	@Test
	public void test_equals_andere_klasse_geeft_false() {
		Object obj = new Object();
		assertTrue(!geldigeDatum.equals(obj));
	}

	@Test
	public void test_equals_andere_dag_geeft_false() {
		Datum datum = new Datum(2, 2, 1980);
		assertTrue(!geldigeDatum.equals(datum));
	}

	@Test
	public void test_equals_andere_maand_geeft_false() {
		Datum datum = new Datum(1, 4, 1980);
		assertTrue(!geldigeDatum.equals(datum));
	}

	@Test
	public void test_equals_ander_jaar_geeft_false() {
		Datum datum = new Datum(1, 2, 1984);
		assertTrue(!geldigeDatum.equals(datum));
	}

	@Test
	public void test_verschilInMaanden_correcte_waarde() {
		Datum datum = new Datum(5, 1, 1981);
		assertEquals("Verschil in Maanden", 11, geldigeDatum.verschilInMaanden(datum));
	}

	@Test
	public void test_verschilInMaanden_geen_verschil_geeft_0() {
		assertEquals("Zelfde datum", 0, geldigeDatum.verschilInDagen(zelfdeDatum));
	}

	@Test
	public void test_verschilInMaanden_kleiner_dan_1_geeft_0() {
		Datum datum = new Datum(5, 1, 1980);
		assertEquals("Verschil in Maanden", 0, geldigeDatum.verschilInMaanden(datum));
	}

	@Test
	public void test_Datum_Met_String_Datum_wordt_aangemaakt() {
		assertEquals(30, datumString.getDag());
		assertEquals(9, datumString.getMaand());
		assertEquals(2014, datumString.getJaar());
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_Met_String_faalt_voor_ongeldige_string() {
		datum = new Datum("3a/09/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_Met_String_faalt_voor_ongeldig_formaat() {
		datum = new Datum("01/28/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_Met_String_faalt_voor_ongeldige_dag() {
		datum = new Datum("29/02/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_Met_String_faalt_voor_ongeldige_maand() {
		datum = new Datum("29/24/2014");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_Datum_Met_String_faalt_voor_ongeldig_jaar() {
		datum = new Datum("29/02/10200");
	}

	@Test
	public void test_getDatumInEuropeesFormaat_Geeft_Juiste_Formaat() {
		assertEquals("11/10/2014", datum.getDatumInEuropeesFormaat());
	}

	@Test
	public void test_kleinerDan_true_voor_parameter_Datum_die_kleiner_is() {
		assertTrue(datum.kleinerDan(datumString));
	}

	@Test
	public void test_kleinerDan_false_voor_parameter_Datum_die_groter_is() {
		assertFalse(datumString.kleinerDan(datum));
	}

	@Test
	public void test_veranderDatum_geeft_juiste_datum() {
		datum.veranderDatum(3);
		assertEquals(datum, veranderdeDatum);
	}
	
	@Test
	public void test_setDatum_veel_random_datums_correct_geevalueerd() {

		Random random = new Random();
		int[][] geldigeDatums = new int[10000][];
		int[][] ongeldigeDatums = new int[10000][];
		int dag, maand, jaar;
		int ongeldigeIndex = 0, geldigeIndex = 0;

		GregorianCalendar datumTester = new GregorianCalendar();
		datumTester.setLenient(false);

		for (int i = 0; i < 10000; i++) {
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
	public void test_Datum_3int_veel_random_datums_correct_verwerkt() {

		Random random = new Random();
		int[][] geldigeDatums = new int[10000][];
		int[][] ongeldigeDatums = new int[10000][];
		int dag, maand, jaar;
		int ongeldigeIndex = 0, geldigeIndex = 0;

		GregorianCalendar datumTester = new GregorianCalendar();
		datumTester.setLenient(false);

		for (int i = 0; i < 10000; i++) {
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
