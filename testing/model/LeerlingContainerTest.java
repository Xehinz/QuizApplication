package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests voor LeerlingContainer
 *
 * @author Adriaan
 * @version 17/11/2014
 */

public class LeerlingContainerTest {
	
	private LeerlingContainer leerlingContainer;
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
	
	

}
