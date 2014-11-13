package model;

import java.util.ArrayList;
import java.util.Arrays;

import util.datumWrapper.Datum;

/**
 * 
 * @author Bert Neyt
 * 
 */
public class Opsomming extends Opdracht implements Valideerbaar {

	private String opsommingJuisteAntwoord;
	private boolean inJuisteVolgorde;
	private int aantalAntwoordenInOpsomming;

	public Opsomming(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
		this.setJuisteAntwoord("");
		this.setAantalAntwoordenInOpsomming();
	}

	public Opsomming(String vraag, String juisteAntwoord,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean injuistevolgorde) {
		super(vraag, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = injuistevolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	public Opsomming(String vraag, int maxAantalPogingen,
			String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur, boolean injuistevolgorde) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = injuistevolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	public Opsomming(String vraag, String juisteAntwoord, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean injuistevolgorde) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = injuistevolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	public Opsomming(String vraag, String juisteAntwoord,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean injuistevolgorde) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = injuistevolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	public Opsomming(int ID, Datum aanmaakDatum, String vraag, String juisteAntwoord,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean injuistevolgorde) {
		super(ID, aanmaakDatum, vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = injuistevolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	public String getJuisteAntwoord() {
		return opsommingJuisteAntwoord;
	}

	public void setAantalAntwoordenInOpsomming() {
		this.aantalAntwoordenInOpsomming = Opsomming.getLijstJuisteAntwoord(
				this.opsommingJuisteAntwoord).size();
	}

	public void setJuisteAntwoord(String opsommingjuisteantwoord)
			throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.opsommingJuisteAntwoord = opsommingjuisteantwoord.trim();
	}

	public static ArrayList<String> getLijstJuisteAntwoord(String antwoord) {
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
	public boolean isValide(String antwoord) {
		if (Opsomming.getLijstJuisteAntwoord(antwoord).size() == this.aantalAntwoordenInOpsomming) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getValideerTekst() {
		return "Typ je antwoorden achter elkaar gescheiden door ;";
	}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		ArrayList<String> antwoorden = Opsomming
				.getLijstJuisteAntwoord(antwoord.toLowerCase());
		ArrayList<String> juisteantwoorden = Opsomming
				.getLijstJuisteAntwoord(this.opsommingJuisteAntwoord.toLowerCase());
		if (this.inJuisteVolgorde) {
			if (juisteantwoorden.equals(antwoorden)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (juisteantwoorden.containsAll(antwoorden)
					&& antwoorden.containsAll(juisteantwoorden)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public String toString() {
		return super.toString() + opsommingJuisteAntwoord + " met de volgorde "
				+ (inJuisteVolgorde ? "" : "niet") + " belangrijk.";
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Opsomming other = (Opsomming) obj;
		if (this.inJuisteVolgorde != other.inJuisteVolgorde) {
			return false;
		}
		if (this.aantalAntwoordenInOpsomming != other.aantalAntwoordenInOpsomming) {
			return false;
		}
		if (this.opsommingJuisteAntwoord != other.opsommingJuisteAntwoord) {
			if (Opsomming
					.getLijstJuisteAntwoord(this.opsommingJuisteAntwoord.toLowerCase())
					.containsAll(
							Opsomming
									.getLijstJuisteAntwoord(other.opsommingJuisteAntwoord.toLowerCase()))){
				return true;
			}
			else {return false;}
		}
		else {return true;}
	}

	@Override
	public int hashCode() {
		long hash = super.hashCode();
		hash = hash * 89 + aantalAntwoordenInOpsomming;
		hash = hash * 41 + opsommingJuisteAntwoord.hashCode();
		hash = hash * 13 + (inJuisteVolgorde ? 10 : 20);
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@Override
	public Opsomming clone() {
		return (Opsomming) super.clone();
	}
}
