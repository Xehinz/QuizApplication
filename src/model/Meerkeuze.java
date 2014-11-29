package model;

import java.util.ArrayList;
import java.util.Arrays;

import util.datumWrapper.Datum;

/**
 * De Meerkeuze klasse modelleert een meerkeuze opdracht. Als antwoord op de
 * gestelde vraag moet een keuze gemaakt worden tussen een aantal opties. Ze
 * erft over van de abstracte klase Opdracht. Bovenop de fields van Opdracht,
 * specifieert Meerkeuze:
 * 
 * <ul>
 * <li>Een aantal mogelijkheden: meegegeven als String aan de constructor. De
 * verschillende opties gescheiden door ";"</li>
 * <li>Het juiste antwoord dat verplicht een van de mogelijkheden is</li>
 * </ul>
 * 
 * @author Johan Boogers
 *
 */
public class Meerkeuze extends Opdracht implements Valideerbaar {

	private String meerkeuzeOpties;
	private String juisteOptie = "";

	/**
	 * Maakt een Meerkeuze object aan met een opdrachtcategorie en een auteur.<br/>
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
	public Meerkeuze(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Meerkeuze object aan met een vraag, een antwoord, een
	 * opdrachtcategorie en een auteur.<br/>
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
	 */
	public Meerkeuze(String vraag, String juisteAntwoord,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	/**
	 * Maakt een Meerkeuze object aan met een vraag, een maximum aantal
	 * pogingen, een antwoord, een opdrachtcategorie en een auteur.<br/>
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
	 */
	public Meerkeuze(String vraag, int maxAantalPogingen,
			String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	/**
	 * Maakt een KlassiekeOpdracht object aan met een vraag, een antwoord, een
	 * maximale antwoordtijd, een opdrachtcategorie en een auteur.<br/>
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
	 */
	public Meerkeuze(String vraag, String juisteAntwoord, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	/**
	 * Maakt een Meerkeuze object aan met een vraag, een maximum aantal
	 * pogingen, een maximale antwoordtijd, een opdrachtcategorie en een auteur
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
	 */
	public Meerkeuze(String vraag, String juisteAntwoord,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.juisteOptie = juisteAntwoord;
	}

	/**
	 * Maakt een Meerkeuze object aan dat al een ID en een datum van aanmaak
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
	 */
	public Meerkeuze(int ID, Datum aanmaakDatum, String vraag,
			String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(ID, aanmaakDatum, vraag, maxAantalPogingen, maxAntwoordTijd,
				opdrachtCategorie, auteur);
		this.juisteOptie = juisteAntwoord;
	}

	/**
	 * Haalt het juiste antwoord op
	 * 
	 * @return de String met het juiste antwoord
	 */
	public String getJuisteAntwoord() {
		return juisteOptie;
	}

	/**
	 * Stelt het juiste antwoord in
	 * 
	 * @param juisteAntwoord
	 *            de String met het juiste antwoord
	 * @throws IllegalStateException
	 *             wanneer de opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen ze hebben opgelost
	 * @throws IllegalArgumentException
	 *             wanneer het meegegeven antwoord niet behoort tot de
	 *             meerkeuzelijst
	 */
	@Override
	public void setJuisteAntwoord(String juisteAntwoord)
			throws IllegalStateException, IllegalArgumentException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (this.getAantalOpties() > 0) {
			// verificatie of het meegegeven antwoord wel als optie beschikbaar
			// is
			if (!this.getOpties().contains(juisteAntwoord.trim())) {
				throw new IllegalArgumentException(
						"Deze optie kan niet als juiste antwoord ingesteld worden. Dit juiste antwoord is niet als optie beschikbaar in de meerkeuzen.");
			}
		}
		this.juisteOptie = juisteAntwoord.trim();
	}

	/**
	 * Geeft een lijst van de meerkeuze-opties terug
	 * 
	 * @return een ArrayList&lt;String&gt; met de verschillende opties
	 */
	public ArrayList<String> getOpties() {
		ArrayList<String> checkList = new ArrayList<String>(
				Arrays.asList(this.meerkeuzeOpties.split(";")));
		ArrayList<String> newList = new ArrayList<String>();
		for (String a : checkList) {
			String b = a.trim();
			newList.add(b);
		}
		return newList;
	}
	
	public String getOptiesString(){
		return this.meerkeuzeOpties;
	}

	/**
	 * Stelt de mogelijke antwoordopties in. Geef de antwoorden in een String
	 * mee, gescheiden door punt-komma's
	 * 
	 * @param opties
	 *            de String met de antwoorden, gescheiden door punt-komma's
	 * @throws IllegalArgumentException
	 *             wanneer het juiste antwoord zich niet tussen de meegegeven
	 *             antwoordopties bevindt
	 */
	public void setOpties(String opties) throws IllegalArgumentException {
		this.meerkeuzeOpties = opties;
		// verificatie of de meegegeven opties ook wel het juiste antwoord
		// bevatten
		if (this.juisteOptie != "") {
			if (!this.getOpties().contains(this.juisteOptie)) {
				throw new IllegalArgumentException(
						"Deze opties kunnen niet als meerkeuzen ingesteld worden. Het juiste antwoord is niet beschikbaar in deze lijst met meerkeuzen.");
			}
		}
	}

	/**
	 * Haalt het aantal keuzes op
	 * 
	 * @return het aantal keuzes
	 */
	public int getAantalOpties() {
		return this.getOpties().size();
	}

	@Override
	public boolean isValide(String antwoord) {
		return this.getOpties().contains(antwoord);
	}

	@Override
	public String getValideerTekst() {
		return "Duid een optie aan uit de lijst";
	}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		return (antwoord.toUpperCase().equals(this.juisteOptie.toUpperCase()));
	}

	@Override
	public String toString() {
		return "Meerkeuze " + super.toString()
				+ juisteOptie + ", kies een geldige optie uit: " + meerkeuzeOpties;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		} else {
			Meerkeuze other = (Meerkeuze) obj;
			if ((this.juisteOptie != other.juisteOptie)
					|| (this.meerkeuzeOpties != other.meerkeuzeOpties)) {
				return false;
			} else {
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
