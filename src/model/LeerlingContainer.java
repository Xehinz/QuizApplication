package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class LeerlingContainer implements Iterable<Leerling>, Cloneable, Comparable<LeerlingContainer> {

	private ArrayList<Leerling> leerlingen;

	/**
	 * Maakt een LeerlingContainer aan
	 */
	public LeerlingContainer() {
		leerlingen = new ArrayList<Leerling>();
	}
	
	public LeerlingContainer(Collection<Leerling> leerlingen) {
		this.leerlingen = new ArrayList<Leerling>(leerlingen);
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
	
	/**
	 * Haalt de Leerling met een bepaald ID op uit de LeerlingContainer
	 * 
	 * @param leerlingID de ID van de gewenste Leerling
	 * @return de Leerling met matchende ID
	 * @throws IllegalArgumentException wanneer er geen Leerling object gevonden wordt met de meegegeven ID
	 */
	public Leerling getLeerling(int leerlingID) throws IllegalArgumentException {
		for (Leerling leerling : this) {
			if (leerling.getID() == leerlingID) {
				return leerling;
			}
		}
		throw new IllegalArgumentException("De LeerlingContainer bevat geen leerling met ID=" + leerlingID);
	}
	
	/**
	 * Haalt een lijstje op van alle QuizDeelnames, van alle Leerlingen
	 * 
	 * @return een ArrayList&lt;QuizDeelname&gt; van alle QuizDeelnames, van alle Leerlingen
	 */
	public ArrayList<QuizDeelname> getAlleQuizDeelnames() {
		ArrayList<QuizDeelname> alleQuizDeelnames = new ArrayList<QuizDeelname>();
		for (Leerling leerling : this) {
			alleQuizDeelnames.addAll(leerling.getQuizDeelnames());
		}
		return alleQuizDeelnames;
	}
	
	/**
	 * Haalt een lijstje op van alle OpdrachtAntwoorden, van alle Leerlingen, in alle Quizzen
	 * 
	 * @return een ArrayList&lt;OpdrachtAntwoord&gt;, van alle Leerlingen, in alle Quizzen
	 */
	public ArrayList<OpdrachtAntwoord> getAlleOpdrachtAntwoorden() {
		ArrayList<OpdrachtAntwoord> alleOpdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		for (Leerling leerling : this) {
			alleOpdrachtAntwoorden.addAll(leerling.getAlleOpdrachtAntwoorden());
		}
		return alleOpdrachtAntwoorden;
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
		String result = String.format("LeerlingContainer met %d leerlingen:\n\n", count());
		for (Leerling leerling : this) {
			result += leerling + scheiding();
		}
		return result;
	}
	
	private String scheiding() {
		return "\n----------------------------------------------------------------------------------------------------\n";
	}
}
