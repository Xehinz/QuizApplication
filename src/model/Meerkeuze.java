package model;

import java.util.ArrayList;
import java.util.Arrays;

import util.datumWrapper.Datum;

/**
 * 
 * @author johan
 *
 */

public class Meerkeuze extends Opdracht implements Valideerbaar {

	private String meerkeuzeOpties;
	private String juisteOptie = "";
	
	public Meerkeuze(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
	}

	public Meerkeuze(String vraag, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	public Meerkeuze(String vraag, int maxAantalPogingen, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	public Meerkeuze(String vraag, String juisteAntwoord, int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	public Meerkeuze(String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}
	
	public Meerkeuze(int ID, Datum aanmaakDatum, String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(ID, aanmaakDatum, vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	public String getJuisteAntwoord() {
		return juisteOptie;
	}

	public void setJuisteAntwoord(String juisteAntwoord) throws IllegalStateException, IllegalArgumentException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (this.getAantalOpties() > 0) {
			//verificatie of het meegegeven antwoord wel als optie beschikbaar is
			if (!this.getOpties().contains(juisteAntwoord)) {
				throw new IllegalArgumentException(
						"Deze optie kan niet als juiste antwoord ingesteld worden. Dit juiste antwoord is niet als optie beschikbaar in de meerkeuzen.");
			}
		}
		this.juisteOptie = juisteAntwoord;			
	}	
	
	public ArrayList<String> getOpties() {
		ArrayList<String> checkList = new ArrayList<String>(Arrays.asList(this.meerkeuzeOpties.split(";")));
		ArrayList<String> newList = new ArrayList<String>();
		for (String a : checkList) {
			String b = a.trim();
			newList.add(b);
		}
		return newList;
	}
	
	public void setOpties(String opties) throws IllegalArgumentException {
		this.meerkeuzeOpties = opties;
		//verificatie of de meegegeven opties ook wel het juiste antwoord bevatten
		if (this.juisteOptie!="") {
			if (!this.getOpties().contains(this.juisteOptie)) {
				throw new IllegalArgumentException(
						"Deze opties kunnen niet als meerkeuzen ingesteld worden. Het juiste antwoord is niet beschikbaar in deze lijst met meerkeuzen.");				
			}
		}
	}
	
	public int getAantalOpties() {
		return this.getOpties().size();
	}
	
	@Override
	public boolean isValide(String antwoord) {
		return this.getOpties().contains(antwoord);
	}

	@Override
	public String getValideerTekst() {
		return "Typ je de meerkeuze-optie uit de lijst met meerkeuze-opties.";
}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		return (antwoord.toUpperCase() == this.juisteOptie.toUpperCase());
	}

	@Override
	public String toString() {
		return super.toString() + ", kies een geldige optie uit de meerkeuzelijst."; 
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		else {
			Meerkeuze other = (Meerkeuze) obj;
			if ((this.juisteOptie != other.juisteOptie) || (this.meerkeuzeOpties != other.meerkeuzeOpties)) {
				return false;
				}
			else {
				return true;
				}			
		}
	}

	@Override
	public int hashCode() {
		long hash = super.hashCode();
		hash = hash * 89 + this.meerkeuzeOpties.hashCode();
		hash = 41 * hash + this.juisteOptie.hashCode();
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@Override
	public Meerkeuze clone() {
		return (Meerkeuze) super.clone();
	}
}
