package model;

import java.util.List;

public class OpdrachtCatalogus implements Comparable<OpdrachtCatalogus>,
		Cloneable {
	private List<Opdracht> opdrachtcatalogus;

	public OpdrachtCatalogus() {
	}

	public OpdrachtCatalogus(List<Opdracht> oc) {
		this.opdrachtcatalogus = oc;
	}

	@Override
	public OpdrachtCatalogus clone() throws CloneNotSupportedException {
		return (OpdrachtCatalogus) super.clone();
	}

	public OpdrachtCatalogus getCloneOpdrachtCatalogus() {
		try {
			return this.clone();
		} catch (Exception E) {
			return null;
		}
	}

	public void addOpdracht(Opdracht O) {
		this.opdrachtcatalogus.add(O);
	}

	public void removeOpdracht(Opdracht O) {
		this.opdrachtcatalogus.remove(O);
	}

	public Opdracht getOpdracht(int volgnr) {
		return this.opdrachtcatalogus.get(volgnr - 1);
	}

	public boolean hasOpdracht(Opdracht opdracht) {
		return this.opdrachtcatalogus.contains(opdracht);
	}

	public int count() {
		return this.opdrachtcatalogus.size();
	}

	@Override
	public String toString() {
		return "Opdrachtcatalogus met " + this.count() + " opdrachten";
	}

	public boolean equals(OpdrachtCatalogus aOpdrachtCatalogus) {
		return aOpdrachtCatalogus.getCloneOpdrachtCatalogus().opdrachtcatalogus
				.containsAll(this.opdrachtcatalogus);
	}

	@Override
	public int compareTo(OpdrachtCatalogus OC) {
		if (this.count() < OC.count()) {
			return -1;
		}
		if (this.count() > OC.count()) {
			return 1;
		} else
			return 0;
	}

}
