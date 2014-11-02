package model;

import java.util.ArrayList;
import java.util.Iterator;

public class LeerlingContainer implements Iterable<Leerling>, Cloneable, Comparable<LeerlingContainer> {

	private ArrayList<Leerling> leerlingen;

	public LeerlingContainer() {
		leerlingen = new ArrayList<Leerling>();
	}

	/**
	 * Voegt een Leerling toe aan de interne collectie van Leerlingen. Laat niet toe om dezelfde Leerling tweemaal toe
	 * te voegen
	 *
	 * @param l
	 *            de Leerling om toe te voegen
	 */
	public void addLeerling(Leerling l) {
		if (!leerlingen.contains(l)) {
			leerlingen.add(l);
		}
		return;
	}

	/**
	 * Verwijdert een Leerling van de interne collectie van Leerlingen
	 *
	 * @param l
	 *            de Leerling om te verwijderen
	 */
	public void removeLeerling(Leerling l) {
		leerlingen.remove(l);
	}

	/**
	 * Geeft een kopie van de lijst met Leerlingen terug. De referenties naar de Leerlingen wijzen naar dezelfde
	 * Leerling objecten als de referenties in deze LeerlingContainer
	 *
	 * @return een ArrayList&lt;Leerling&gt; met Leerlingen
	 */
	public ArrayList<Leerling> getLeerlingen() {
		ArrayList<Leerling> leerlingen = new ArrayList<Leerling>();
		for (Leerling leerling : this.leerlingen) {
			leerlingen.add(leerling);
		}
		return leerlingen;
	}

	/**
	 * Geeft het aantal Leerlingen in deze LeerlingContainer terug
	 *
	 * @return het aantal Leerlingen
	 */
	public int count() {
		return leerlingen.size();
	}

	@Override
	public Iterator<Leerling> iterator() {
		return leerlingen.iterator();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof LeerlingContainer)) {
			return false;
		}

		LeerlingContainer other = (LeerlingContainer) obj;
		return other.getLeerlingen().equals(this.leerlingen);
	}

	@Override
	public LeerlingContainer clone() {
		LeerlingContainer clone = null;
		try {
			clone = (LeerlingContainer) super.clone();
			clone.leerlingen = new ArrayList<Leerling>();
			for (Leerling leerling : this) {
				clone.leerlingen.add(leerling.clone());
			}
		} catch (CloneNotSupportedException ex) {
			// LeerlingContainer implementeert Cloneable dus kan geen CloneNotSupportedException throwen
			ex.printStackTrace();
		}
		return clone;
	}

	@Override
	public int hashCode() {
		long hash = 1;
		for (Leerling leerling : this) {
			hash *= 13;
			hash += leerling.hashCode();
		}
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	/**
	 * Vergelijkt een LeerlingContainer met een andere LeerlingContainer op basis van de hoeveelheid Leerlingen
	 */
	@Override
	public int compareTo(LeerlingContainer leerlingContainer) {
		if (leerlingContainer == null) {
			return 1;
		}
		if (this.count() < leerlingContainer.count()) {
			return -1;
		}
		if (this.count() > leerlingContainer.count()) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return String.format("LeerlingContainer met %d leerlingen", count());
	}
}
