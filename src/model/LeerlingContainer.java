package model;

import java.util.ArrayList;
import java.util.Iterator;

public class LeerlingContainer implements Iterable<Leerling> {

	private Leerling leerling;

	public <leerling> Leerling getLeerling() {

		return leerling;

	}

	private ArrayList<Leerling> leerlingen;

	public LeerlingContainer() {
		leerlingen = new ArrayList<Leerling>();
	}

	public void addLeerling(Leerling l) {
		if (!leerlingen.contains(l)) {
			leerlingen.add(l);
		}
		return;
	}

	public void removeLeerling(Leerling l) {
		if (leerlingen.contains(l)) {
			leerlingen.remove(l);
		}
	}

	@Override
	public Iterator<Leerling> iterator() {
		return leerlingen.iterator();
	}

}
