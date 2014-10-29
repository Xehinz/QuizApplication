package model;

import java.util.List;

/**
 * De OpdrachtCatalogus klasse, met 1 field: Een lijst met alle aangemaakte opdrachten
 *
 * @author Bert Neyt
 * @version 26/10/2014
 *
 */

public class OpdrachtCatalogus implements Comparable<OpdrachtCatalogus>, Cloneable {
	private List<Opdracht> opdrachtcatalogus;

	/**
	 * Maakt een nieuwe OpdrachtCatalogus aan
	 */

	public OpdrachtCatalogus() {
	}

	/**
	 * Maakt een nieuwe OpdrachtCatalogus aan van een reeds bestaande lijst van opdrachten
	 *
	 * @param oc
	 *            een lijst van opdrachten
	 */

	public OpdrachtCatalogus(List<Opdracht> oc) {
		this.opdrachtcatalogus = oc;
	}

	/**
	 * Kloont OpdrachtCatalogus
	 *
	 * @return de gekloonde OpdrachtCatalogus
	 */

	@Override
	public OpdrachtCatalogus clone() throws CloneNotSupportedException {
		return (OpdrachtCatalogus) super.clone();
	}

	/**
	 * Haalt de gekloonde OpdrachtCatalogus op
	 *
	 * @return de gekloonde OpdrachtCatalogus
	 */

	public OpdrachtCatalogus getCloneOpdrachtCatalogus() {
		try {
			return this.clone();
		} catch (Exception E) {
			return null;
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
	}

	/**
	 * verwijdert een opdracht
	 *
	 * @param O
	 *            de te verwijderen Opdracht
	 * @throws UnsupportedOperationException
	 *             als de opdracht reeds gelinkt is aan een quiz
	 */

	public void removeOpdracht(Opdracht O) throws UnsupportedOperationException {
		if (!O.isVerwijderbaar()) {
			throw new UnsupportedOperationException(
					"De opdracht kan niet verwijderd worden omdat ze reeds gelinkt is aan een quiz");
		}
		this.opdrachtcatalogus.remove(O);
	}

	/**
	 * Haalt een opdracht op met een bepaald volgnummer
	 *
	 * @param volgnr
	 *            het volgnummer
	 * @return de opdracht met het ingegeven volgnummer
	 */

	public Opdracht getOpdracht(int volgnr) {
		return this.opdrachtcatalogus.get(volgnr - 1);
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

	public boolean equals(OpdrachtCatalogus aOpdrachtCatalogus) {
		return aOpdrachtCatalogus.getCloneOpdrachtCatalogus().opdrachtcatalogus.containsAll(this.opdrachtcatalogus);
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

}
