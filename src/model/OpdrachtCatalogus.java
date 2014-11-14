package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * De OpdrachtCatalogus klasse, met 1 field: Een lijst met alle aangemaakte opdrachten
 *
 * @author Bert Neyt
 * @version 26/10/2014
 *
 */

public class OpdrachtCatalogus implements Comparable<OpdrachtCatalogus>, Cloneable, Iterable<Opdracht> {
	private HashSet<Opdracht> opdrachtcatalogus;
	private int hoogsteID;

	/**
	 * Maakt een nieuwe OpdrachtCatalogus aan
	 */

	public OpdrachtCatalogus() {
		this.opdrachtcatalogus = new HashSet<Opdracht>();
		hoogsteID = 0;
	}

	/**
	 * Maakt een nieuwe OpdrachtCatalogus aan van een reeds bestaande lijst van opdrachten
	 *
	 * @param oc
	 *            een lijst van opdrachten
	 */

	public OpdrachtCatalogus(Collection<Opdracht> oc) {
		this.opdrachtcatalogus = new HashSet<Opdracht>();
		hoogsteID = 0;
		for (Opdracht opdracht : oc) {
			addOpdracht(opdracht);
		}
	}

	/**
	 * voegt een opdracht toe
	 *
	 * @param O
	 *            de toe te voegen Opdracht
	 */

	public void addOpdracht(Opdracht O) {
		this.opdrachtcatalogus.add(O);
		if (O.getID() == 0) {
			O.setID(++hoogsteID);
		}
		else {
			if (O.getID() > hoogsteID) {
				hoogsteID = O.getID();
			}
		}
	}

	/**
	 * verwijdert een opdracht
	 *
	 * @param O
	 *            de te verwijderen Opdracht
	 * @throws IllegalStateException
	 *             als de opdracht reeds gelinkt is aan een quiz
	 */

	public void removeOpdracht(Opdracht O) throws IllegalStateException {
		if (!O.isVerwijderbaar()) {
			throw new IllegalStateException(
					"De opdracht kan niet verwijderd worden omdat ze reeds gelinkt is aan een quiz");
		}
		if (O.getID() == hoogsteID) {
			hoogsteID--;
		}
		this.opdrachtcatalogus.remove(O);
	}

	/**
	 * check of de catalogus een bepaalde opdracht bevat
	 *
	 * @param opdracht
	 *            de te zoeken opdracht
	 * @return <code>true</code> als de opdracht in de catalogus voorkomt
	 */

	public boolean hasOpdracht(Opdracht opdracht) {
		return this.opdrachtcatalogus.contains(opdracht);
	}

	/**
	 * telt het aantal opdrachten in de catalogus
	 *
	 * @return het aantal opdrachten in de catalogus
	 */

	public int count() {
		return this.opdrachtcatalogus.size();
	}

	/**
	 * Geeft een kopie van de interne lijst van Opdrachten terug
	 *
	 * @return een ArrayList&lt;Opdracht&gt; met een kopie van de interne lijst van Opdrachten
	 */
	public ArrayList<Opdracht> getOpdrachten() {
		ArrayList<Opdracht> opdrachten = new ArrayList<Opdracht>();
		for (Opdracht opdracht : opdrachtcatalogus) {
			opdrachten.add(opdracht);
		}
		return opdrachten;
	}
	
	/**
	 * Haalt de Opdracht op uit de OpdrachtCatalogus met een bepaalde ID
	 * 
	 * @param opdrachtID de ID van de gewenste Opdracht
	 * @return de Opdracht met matchende ID
	 * @throws IllegalArgumentException wanneer er geen Opdracht object gevonden wordt met de meegegeven ID
	 */
	public Opdracht getOpdracht(int opdrachtID) throws IllegalArgumentException {
		for (Opdracht opdracht : this) {
			if (opdracht.getID() == opdrachtID) {
				return opdracht;
			}
		} 
		throw new IllegalArgumentException("De OpdrachtCatalogus bevat geen Opdracht met ID=" + opdrachtID);
	}

	/**
	 * Override van de toString methode
	 */

	@Override
	public String toString() {
		return "Opdrachtcatalogus met " + this.count() + " opdrachten";
	}

	/**
	 * checkt of de lijsten van 2 catalogi dezelfde opdrachten bevat
	 *
	 * @param aOpdrachtCatalogus
	 *            de te vergelijken catalogus
	 */

	@Override
	public boolean equals(Object aOpdrachtCatalogus) {
		if (aOpdrachtCatalogus == null) {
			return false;
		}
		if (!(aOpdrachtCatalogus instanceof OpdrachtCatalogus)) {
			return false;
		}
		OpdrachtCatalogus other = (OpdrachtCatalogus) aOpdrachtCatalogus;
		return other.opdrachtcatalogus.containsAll(this.opdrachtcatalogus);
	}

	/**
	 * checkt of een catalogus meer of minder of evenveel opdrachten bevat
	 *
	 * @param OC
	 *            de te vergelijken catalogus
	 */
	@Override
	public int compareTo(OpdrachtCatalogus OC) {
		if (this.count() < OC.count()) {
			return -1;
		}
		if (this.count() > OC.count()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Iterator<Opdracht> iterator() {
		return this.opdrachtcatalogus.iterator();
	}

	@Override
	public int hashCode() {
		long hash = 1;
		for (Opdracht opdracht : this.opdrachtcatalogus) {
			hash *= 3;
			hash += opdracht.hashCode();
		}
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}
	
	/**
	 * Kloont OpdrachtCatalogus
	 *
	 * @return de gekloonde OpdrachtCatalogus
	 */

	@Override
	public OpdrachtCatalogus clone() {
		OpdrachtCatalogus clone = null;
		try {
			clone = (OpdrachtCatalogus) super.clone();
			clone.opdrachtcatalogus = new HashSet<Opdracht>();
			for (Opdracht opdracht : this.opdrachtcatalogus) {
				clone.opdrachtcatalogus.add(opdracht.clone());
			}
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return clone;
	}
}
