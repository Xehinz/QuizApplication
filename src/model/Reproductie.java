package model;

import java.util.ArrayList;
import java.util.Arrays;

import util.datumWrapper.Datum;

/**
 * @author Bert Neyt
 * 
 */

public class Reproductie extends Opdracht {

	private String juisteTrefwoorden;
	private int minimumAantalTrefwoorden;

	public Reproductie(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
		this.setJuisteAntwoord("");
		this.setMinimumAantalTrefwoorden(0);
	}

	public Reproductie(String vraag, String juisteAntwoord,
			int minimumAantalTrefwoorden, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	public Reproductie(String vraag, int maxAantalPogingen,
			String juisteAntwoord, int minimumAantalTrefwoorden,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	public Reproductie(String vraag, String juisteAntwoord,
			int minimumAantalTrefwoorden, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	public Reproductie(String vraag, String juisteAntwoord,
			int minimumAantalTrefwoorden, int maxAantalPogingen,
			int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	public Reproductie(int ID, Datum aanmaakDatum, String vraag,
			String juisteAntwoord, int minimumAantalTrefwoorden,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(ID, aanmaakDatum, vraag, maxAantalPogingen, maxAntwoordTijd,
				opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	public String getJuisteTrefwoorden() {
		return juisteTrefwoorden;
	}

	public void setJuisteAntwoord(String juisteAntwoord)
			throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.juisteTrefwoorden = juisteAntwoord.trim();
	}

	public void setMinimumAantalTrefwoorden(int minimumAantalTrefwoorden)
			throws UnsupportedOperationException {
		if (Reproductie.getLijstJuisteTrefwoorden(this.juisteTrefwoorden)
				.size() >= minimumAantalTrefwoorden) {
			this.minimumAantalTrefwoorden = minimumAantalTrefwoorden;
		} else {
			throw new UnsupportedOperationException(
					"Minimum aantal trefwoorden nodig voor een juist antwoord mag niet meer zijn dan het aantal juiste trefwoorden");
		}
	}

	public static ArrayList<String> getLijstJuisteTrefwoorden(String antwoord) {
		ArrayList<String> checkList = new ArrayList<String>(
				Arrays.asList(antwoord.split(";")));
		ArrayList<String> newList = new ArrayList<String>();
		for (String A : checkList) {
			String B = A.trim();
			newList.add(B);
		}
		return newList;
	}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		String antw = antwoord.toLowerCase();
		int aantalJuisteAntwoorden = 0;
		ArrayList<String> lijst = Reproductie
				.getLijstJuisteTrefwoorden(this.juisteTrefwoorden.toLowerCase());
		for (String A : lijst) {
			if (antw.contains(A)) {
				aantalJuisteAntwoorden++;
			}
		}
		if (aantalJuisteAntwoorden >= this.minimumAantalTrefwoorden) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return super.toString() + juisteTrefwoorden + " met minimum "
				+ minimumAantalTrefwoorden
				+ " aantal trefwoorden die juist moeten zijn.";
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Reproductie other = (Reproductie) obj;
		if (Reproductie.getLijstJuisteTrefwoorden(
				this.juisteTrefwoorden.toLowerCase()).containsAll(
				Reproductie.getLijstJuisteTrefwoorden(other.juisteTrefwoorden
						.toLowerCase()))) {
			if (this.minimumAantalTrefwoorden != other.minimumAantalTrefwoorden) {
				return false;
			} else {
				return true;
			}
		}
		else {return false;}
	}

	@Override
	public int hashCode() {
		long hash = super.hashCode();
		hash = hash * 89 + minimumAantalTrefwoorden;
		hash = hash * 41 + juisteTrefwoorden.hashCode();
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@Override
	public Reproductie clone() {
		return (Reproductie) super.clone();
	}
}
