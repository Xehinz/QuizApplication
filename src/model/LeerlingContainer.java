package model;

import java.util.ArrayList;

public class LeerlingContainer {

	private Leerling leerling;
	
	public <leerling> Leerling getLeerling() {
		
		return leerling;
		
	}
	
	private ArrayList<Leerling> Leerlingen;
	
	public LeerlingContainer() {
		Leerlingen = new ArrayList<Leerling>();
	}

	public void addLeerling(Leerling l) {
		if (!Leerlingen.contains(l)) {
			Leerlingen.add(l);
		}
		return;
	}

	public void removeLeerling(Leerling l) {
		if (Leerlingen.contains(l)) {
			Leerlingen.remove(l);
		}
	}
	
}
