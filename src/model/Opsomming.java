package model;

import java.util.ArrayList;
import java.util.Arrays;

import util.datumWrapper.Datum;

/**
 * De Opsomming klasse modelleert een vraag met een opsomming als antwoord. Ze
 * erft over van de abstracte klase Opdracht. Bovenop de fields van Opdracht,
 * specifieert Opsomming:
 * 
 * <ul>
 * <li>Een lijstje van antwoorden: meegegeven als String aan de constructor. De
 * verschillende delen van de opsomming moeten gescheiden worden door een ";".</li>
 * <li>Een indicator die aangeeft of de antwoorden in volgorde moeten gegeven
 * worden</li>
 * <li>Het aantal antwoorden in de opsomming</li>
 * </ul>
 * 
 * @author Bert Neyt
 * 
 */
public class Opsomming extends Opdracht implements Valideerbaar {

	private String opsommingJuisteAntwoord;
	private boolean inJuisteVolgorde;
	private int aantalAntwoordenInOpsomming;

	/**
	 * Maakt een Opsomming object aan met een opdrachtcategorie en een auteur.<br/>
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
	public Opsomming(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
		this.setJuisteAntwoord("");
		this.setAantalAntwoordenInOpsomming();
	}

	/**
	 * Maakt een Opsomming object aan met een vraag, een antwoord, een
	 * opdrachtcategorie, een auteur en een indicator die bepaalt of de
	 * antwoorden in volgorde moeten gegeven worden.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is
	 * 1 (Default waarden)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 * @param inJuisteVolgorde
	 *            de indicator die bepaalt of de antwoorden in volgorde moeten
	 *            gegeven worden
	 */
	public Opsomming(String vraag, String juisteAntwoord,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean inJuisteVolgorde) {
		super(vraag, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = inJuisteVolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	/**
	 * Maakt een Opsomming object aan met een vraag, een maximum aantal
	 * pogingen, een antwoord, een opdrachtcategorie, een auteur en een
	 * indicator die bepaalt of de antwoorden in volgorde moeten gegeven worden.<br/>
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
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 * @param inJuisteVolgorde
	 *            de indicator die bepaalt of de antwoorden in volgorde moeten
	 *            gegeven worden
	 */
	public Opsomming(String vraag, int maxAantalPogingen,
			String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur, boolean inJuisteVolgorde) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = inJuisteVolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	/**
	 * Maakt een Opsomming object aan met een vraag, een antwoord, een maximale
	 * antwoordtijd, een opdrachtcategorie, een auteur en een indicator die
	 * bepaalt of de antwoorden in volgorde moeten gegeven worden.<br/>
	 * <br/>
	 *
	 * Het maximaal aantal antwoordpogingen is 1 (default)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 * @param inJuisteVolgorde
	 *            de indicator die bepaalt of de antwoorden in volgorde moeten
	 *            gegeven worden
	 */
	public Opsomming(String vraag, String juisteAntwoord, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean inJuisteVolgorde) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = inJuisteVolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	/**
	 * Maakt een Opsomming object aan met een vraag, een maximum aantal
	 * pogingen, een maximale antwoordtijd, een opdrachtcategorie, een auteur en
	 * een indicator die bepaalt of de antwoorden in volgorde moeten gegeven
	 * worden.
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 * @param inJuisteVolgorde
	 *            de indicator die bepaalt of de antwoorden in volgorde moeten
	 *            gegeven worden
	 */
	public Opsomming(String vraag, String juisteAntwoord,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean inJuisteVolgorde) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = inJuisteVolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	/**
	 * Maakt een Opsomming object aan dat al een ID en een datum van aanmaak
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
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 * @param inJuisteVolgorde
	 *            de indicator die bepaalt of de antwoorden in volgorde moeten
	 *            gegeven worden
	 */
	public Opsomming(int ID, Datum aanmaakDatum, String vraag,
			String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean inJuisteVolgorde) {
		super(ID, aanmaakDatum, vraag, maxAantalPogingen, maxAntwoordTijd,
				opdrachtCategorie, auteur);
		this.setJuisteAntwoord(juisteAntwoord);
		this.inJuisteVolgorde = inJuisteVolgorde;
		this.setAantalAntwoordenInOpsomming();
	}

	/**
	 * Haalt de juiste antwoorden op gescheiden door punt-komma's
	 * 
	 * @return de String met de juiste antwoorden, gescheiden door punt-komma's
	 */
	public String getJuisteAntwoord() {
		return opsommingJuisteAntwoord;
	}

	/**
	 * Geeft terug of deze Opsomming al dan niet in volgorde moet beantwoord
	 * worden
	 * 
	 * @return true als deze Opsomming in juiste volgorde moet beantwoord worden
	 */
	public boolean getInJuisteVolgorde() {
		return inJuisteVolgorde;
	}

	/**
	 * Haalt het aantal antwoorden in deze opsomming op
	 * 
	 * @return het aantal antwoorden in deze opsomming
	 */
	public int getAantalAntwoordenInOpsomming() {
		return aantalAntwoordenInOpsomming;
	}

	/**
	 * Stelt de juiste antwoorden voor deze Opsomming in. Scheid de antwoorden
	 * van elkaar met ";".
	 * 
	 * @param opsommingjuisteantwoord
	 *            de String met de antwoorden gescheiden door ";"
	 * @throws IllegalStateException
	 *             wanneer de opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen ze hebben opgelost
	 */
	public void setJuisteAntwoord(String opsommingjuisteantwoord)
			throws IllegalStateException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.opsommingJuisteAntwoord = opsommingjuisteantwoord.trim();
	}

	/**
	 * Haalt een lijst op met de juiste antwoorden voor deze Opsomming
	 * 
	 * @param antwoord de String met de antwoorden gescheiden door punt-komma's
	 * @return een lijst op met de juiste antwoorden voor deze Opsomming
	 */
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
				.getLijstJuisteAntwoord(this.opsommingJuisteAntwoord
						.toLowerCase());
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
					.getLijstJuisteAntwoord(
							this.opsommingJuisteAntwoord.toLowerCase())
					.containsAll(
							Opsomming
									.getLijstJuisteAntwoord(other.opsommingJuisteAntwoord
											.toLowerCase()))) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
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

	private void setAantalAntwoordenInOpsomming() {
		this.aantalAntwoordenInOpsomming = Opsomming.getLijstJuisteAntwoord(
				this.opsommingJuisteAntwoord).size();
	}
}
