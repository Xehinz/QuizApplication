package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Tests voor LeerlingContainer
 *
 * @author Adriaan
 * @version 17/11/2014
 */

public class LeerlingContainerTest {
	
	private LeerlingContainer leerlingContainer, copyLeerlingContainer, andereLeerlingContainer;
	private Leerling leerlingA, leerlingACopy, leerlingB, leerlingC;
	private ArrayList<Leerling> leerlingen;

	@Before
	public void setUp() throws Exception {
		leerlingen = new ArrayList<Leerling>();
		leerlingA = new Leerling("Prins", "Laurent", 2);
		leerlingen.add(leerlingA);
		leerlingACopy = leerlingA;
		leerlingB = new Leerling("Prinses", "Astrid", 4);
		leerlingen.add(leerlingB);
		leerlingC = new Leerling("Koning", "Filip", 6);
		leerlingContainer = new LeerlingContainer(leerlingen);
		copyLeerlingContainer = new LeerlingContainer(leerlingen);
		leerlingen.add(leerlingC);
		andereLeerlingContainer = new LeerlingContainer(leerlingen);
	}
	
	@Test
	public void test_Constructor_met_ArrayList() {
		assertEquals("Leerlingen in Container via Constructor", leerlingContainer.count(), 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_Constructor_met_Copy_in_ArrayList_ThrowsIllegalArgumentException() {
		leerlingen.add(leerlingACopy);
		leerlingContainer = new LeerlingContainer(leerlingen);
	}
	
	@Test
	public void test_addLeerling_Nieuwe_Leerling_OK() {
		leerlingContainer.addLeerling(leerlingC);
		assertEquals("Leerlingen in Container via add", leerlingContainer.count(), 3);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_addLeerling_Nieuwe_Leerling_Copy_ThrowsIllegalArgumentException() {
		leerlingContainer.addLeerling(leerlingACopy);
	}
	
	@Test
	public void test_removeLeerling_OK() {
		leerlingContainer.removeLeerling(leerlingA);
		assertEquals("Leerlingen in Container via add", leerlingContainer.count(), 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_removeLeerling_Leerling_niet_in_container_ThrowsIllegalArgumentException() {
		leerlingContainer.removeLeerling(leerlingC);
	}
	
	@Test
	public void test_getLeerlingen_OK() {
		leerlingen.remove(leerlingC);
		assertEquals("getLeerlingen gelijk aan input", leerlingContainer.getLeerlingen(), leerlingen);
	}
	
	@Test
	public void test_count_OK() {
		assertEquals("count gelijk aan aantal Leerlingen", leerlingContainer.count(), 2);
	}
	
	@Test
	public void test_getLeerling_OK() {
		assertEquals("getLeerling gelijk aan input", leerlingContainer.getLeerling(1), leerlingA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_getLeerling_ID_niet_in_container_ThrowsIllegalArgumentException() {
		leerlingContainer.getLeerling(5);
	}
	
	@Test
	public void test_getAlleQuizDeelnames_OK() {
		ArrayList<QuizDeelname> alleQuizDeelnames = new ArrayList<QuizDeelname>();
		for (Leerling leerling : leerlingen) {
			alleQuizDeelnames.addAll(leerling.getQuizDeelnames());
		}
		Collections.sort(alleQuizDeelnames);
		assertEquals("quizdeelnames OK", alleQuizDeelnames, andereLeerlingContainer.getAlleQuizDeelnames());
	}
	
	@Test
	public void test_getAlleOpdrachtAntwoorden_OK() {
		ArrayList<OpdrachtAntwoord> alleOpdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		for (Leerling leerling : leerlingen) {
			alleOpdrachtAntwoorden.addAll(leerling.getAlleOpdrachtAntwoorden());
		}
		Collections.sort(alleOpdrachtAntwoorden);
		assertEquals("opdrachtantwoorden OK", alleOpdrachtAntwoorden, andereLeerlingContainer.getAlleOpdrachtAntwoorden());
	}
	
	@Test
	public void test_equals_gelijk_geeft_true() {
		assertTrue(leerlingContainer.equals(copyLeerlingContainer));
	}
	
	@Test
	public void test_equals_niet_gelijk_geeft_false() {
		
		assertFalse(leerlingContainer.equals(andereLeerlingContainer));
	}
	
	@Test
	public void test_equals_ander_type_geeft_false() {		
		assertFalse(leerlingContainer.equals(leerlingC));
	}

	@Test
	public void test_clone_OK() {
		andereLeerlingContainer = leerlingContainer.clone();
		assertEquals("container na clone is gelijk", leerlingContainer, andereLeerlingContainer);
	}
	
	@Test
	public void test_compareTo_gelijk_geeft_0() {
		assertEquals("compareTo geeft 0 bij equal", leerlingContainer.compareTo(copyLeerlingContainer), 0);
	}
	
	@Test
	public void test_compareTo_groter_geeft_1() {
		leerlingen.remove(leerlingA);
		leerlingen.remove(leerlingB);
		andereLeerlingContainer = new LeerlingContainer(leerlingen);
		assertEquals("compareTo geeft 0 bij equal", leerlingContainer.compareTo(andereLeerlingContainer), 1);
	}
	
	@Test
	public void test_compareTo_kleiner_geeft_min1() {
		assertEquals("compareTo geeft 0 bij equal", leerlingContainer.compareTo(andereLeerlingContainer), -1);
	}
	
	@Test
	public void test_toString() {
		String result = String.format(
				"LeerlingContainer met %d leerlingen:\n\n", andereLeerlingContainer.count());
		Collections.sort(leerlingen);
		for (Leerling leerling : leerlingen) {
			result += leerling + "\n----------------------------------------------------------------------------------------------------\n";
		}		
		assertEquals("toString geeft correcte Stringvoorstelling terug", result, andereLeerlingContainer.toString());
	}
	
}
