package model;

import java.util.ArrayList;
import java.util.Arrays;

import util.datumWrapper.Datum;

/**
 * De Reproductie klasse modelleert een vraag met open antwoord. De respondent
 * moet een tekstje als antwoord intypen. Ze erft over van de abstracte klase
 * Opdracht. Bovenop de fields van Opdracht, specifieert Reproductie:
 * 
 * <ul>
 * <li>De juiste trefwoorden: meegegeven als String aan de constructor. De
 * verschillende trefwoorden gescheiden door ";".</li>
 * <li>Het minimum aantal trefwoorden dat het antwoordtekstje moet bevatten om
 * als een juist antwoord gerekend te worden</li>
 * </ul>
 * 
 * @author Bert Neyt
 * 
 */

public class Reproductie extends Opdracht {

	private String juisteTrefwoorden;
	private int minimumAantalTrefwoorden;

	/**
	 * Maakt een Reproductie object aan met een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 * 
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is
	 * 1 (Default waarden)
	 * 
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Reproductie(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
		this.setJuisteAntwoord("");
		this.setMinimumAantalTrefwoorden(0);
	}

	/**
	 * Maakt een Reproductie object aan met een vraag, een antwoord, het minimum
	 * aantal te vinden trefwoorden, een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 * 
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is
	 * 1 (Default waarden)
	 * 
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param minimumAantalTrefwoorden
	 *            het minimum aantal te vinden trefwoorden
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Reproductie(String vraag, String juisteAntwoord,
			int minimumAantalTrefwoorden, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	/**
	 * Maakt een Reproductie object aan met een vraag, een maximum aantal
	 * pogingen, een antwoord, het minimum aantal te vinden trefwoorden, een
	 * opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 * 
	 * De antwoordtijd is ongelimiteerd (default)
	 * 
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param minimumAantalTrefwoorden
	 *            het minimum aantal te vinden trefwoorden
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Reproductie(String vraag, int maxAantalPogingen,
			String juisteAntwoord, int minimumAantalTrefwoorden,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	/**
	 * Maakt een Reproductie object aan met een vraag, een antwoord, het minimum
	 * aantal te vinden trefwoorden, een maximale antwoordtijd, een
	 * opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 * 
	 * Het maximaal aantal antwoordpogingen is 1 (default)
	 * 
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param minimumAantalTrefwoorden
	 *            het minimum aantal te vinden trefwoorden
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Reproductie(String vraag, String juisteAntwoord,
			int minimumAantalTrefwoorden, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	/**
	 * Maakt een Reproductie object aan met een vraag, een antwoord, het minimum
	 * aantal te vinden trefwoorden, een maximum aantal pogingen, een maximale
	 * antwoordtijd, een opdrachtcategorie en een auteur.
	 * 
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param minimumAantalTrefwoorden
	 *            het minimum aantal te vinden trefwoorden
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Reproductie(String vraag, String juisteAntwoord,
			int minimumAantalTrefwoorden, int maxAantalPogingen,
			int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	/**
	 * Maakt een Reproductie object aan dat al een ID en een datum van aanmaak
	 * toegewezen had. Gebruik deze constructor alleen om een Opdracht te maken
	 * uit opslag (tekst / DB)
	 * 
	 * @param ID
	 *            de ID van de Opdracht
	 * @param aanmaakDatum
	 *            de Datum waarop de Opdracht is aangemaakt
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param minimumAantalTrefwoorden
	 *            het minimum aantal te vinden trefwoorden
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Reproductie(int ID, Datum aanmaakDatum, String vraag,
			String juisteAntwoord, int minimumAantalTrefwoorden,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(ID, aanmaakDatum, vraag, maxAantalPogingen, maxAntwoordTijd,
				opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.setMinimumAantalTrefwoorden(minimumAantalTrefwoorden);
	}

	/**
	 * Haalt de String met juiste trefwoorden op
	 * 
	 * @return de String met juiste trefwoorden op
	 */
	public String getJuisteTrefwoorden() {
		return juisteTrefwoorden;
	}

	/**
	 * Haalt het minimum aantal trefwoorden op dat het antwoordtekstje moet
	 * bevatten om als juist antwoord gerekend te worden
	 * 
	 * @return het minimum aantal trefwoorden op dat het antwoordtekstje moet
	 *         bevatten om als juist antwoord gerekend te worden
	 */
	public int getMinimumAantalTrefwoorden() {
		return minimumAantalTrefwoorden;
	}

	/**
	 * Stelt de juiste trefwoorden in. Scheid de trefwoorden met punt-komma's
	 * 
	 * @param juisteAntwoord
	 *            de String met de juiste trefwoorden gescheiden door
	 *            punt-komma's
	 * @throws IllegalStateException
	 *             wanneer de opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen ze hebben opgelost
	 */
	public void setJuisteAntwoord(String juisteAntwoord)
			throws IllegalStateException {
		ArrayList<String> lijst = new ArrayList<String>(
				Arrays.asList(juisteAntwoord.split(";")));
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (juisteAntwoord == "") {
			throw new IllegalArgumentException("Minimum 1 trefwoord nodig");
		}
		if (lijst.size() < this.minimumAantalTrefwoorden) {
			throw new IllegalArgumentException("Minimum "
					+ this.minimumAantalTrefwoorden + " trefwoorden nodig");
		}
		this.juisteTrefwoorden = juisteAntwoord.trim().toLowerCase();

	}

	/**
	 * Stelt het minimum aantal trefwoorden in dat het antwoordtekstje moet
	 * bevatten
	 * 
	 * @param minimumAantalTrefwoorden
	 *            het minimum aantal trefwoorden in dat het antwoordtekstje moet
	 *            bevatten
	 * @throws IllegalArgumentException
	 *             wanneer het meegegeven aantal kleiner is dan het aantal
	 *             juiste trefwoorden
	 * @throws IllegalStaeException
	 *             wanneer de opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen ze hebben opgelost
	 */
	public void setMinimumAantalTrefwoorden(int minimumAantalTrefwoorden)
			throws IllegalStateException, IllegalArgumentException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (minimumAantalTrefwoorden <= 0) {
			throw new IllegalArgumentException(
					"Minimum aantal trefwoorden moet groter zijn dan 0");
		}
		if (this.getLijstJuisteTrefwoorden()
				.size() >= minimumAantalTrefwoorden) {
			this.minimumAantalTrefwoorden = minimumAantalTrefwoorden;
		} else {
			throw new IllegalArgumentException(
					"Minimum aantal trefwoorden nodig voor een juist antwoord mag niet meer zijn dan het aantal juiste trefwoorden");
		}
	}

	/**
	 * Geeft een lijst terug met de juiste trefwoorden in het meegegeven
	 * antwoord
	 * 
	 * @param antwoord
	 *            de String met het antwoord waaruit de juiste trefwoorden
	 *            worden teruggegeven
	 * @return een ArrayList&lt;String&gt; met de juiste trefwoorden in het
	 *         meegegeven antwoord
	 */
	public ArrayList<String> getLijstJuisteTrefwoorden() {
		ArrayList<String> checkList = new ArrayList<String>(
				Arrays.asList(this.juisteTrefwoorden.split(";")));
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
		ArrayList<String> lijst = this.getLijstJuisteTrefwoorden();
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
		return "Reproductie " + super.toString() + juisteTrefwoorden
				+ " met minimum " + minimumAantalTrefwoorden
				+ " aantal trefwoorden die juist moeten zijn.";
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Reproductie other = (Reproductie) obj;
		if (this.getLijstJuisteTrefwoorden().containsAll(
				other.getLijstJuisteTrefwoorden())
				&& other.getLijstJuisteTrefwoorden().containsAll(
						this.getLijstJuisteTrefwoorden())) {
			if (this.minimumAantalTrefwoorden != other.minimumAantalTrefwoorden) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
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
