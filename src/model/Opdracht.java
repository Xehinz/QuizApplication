package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.datumWrapper.Datum;

/**
 * De Opdracht klasse modelleert een opdracht die gebruikt kan worden in meerdere quizzen. Bij een opdracht horen
 * volgende parameters:
 *
 * <ul>
 * <li>De vraag waarop een antwoord moet gevonden worden (String)</li>
 * <li>Het juiste antwoord op de vraag (String)</li>
 * <li>De opdrachtcategorie (Enum OpdrachtCategorie: Aardrijkskunde, Nederlands, Wetenschappen, Wiskunde)</li>
 * <li>De auteur van de opdracht (Enum Leraar)</li>
 * <li>De datum waarop de opdracht is aangemaakt (Datum)</li>
 * <li>Een lijst met antwoordhints (ArrayList&lt;String&gt;). Deze lijst kan leeg zijn</li>
 * <li>Een lijst met QuizOpdrachten (ArrayList&lt;QuizOpdracht&gt;). De klasse QuizOpdracht linkt de opdracht aan 0, 1
 * of meerdere quizzen</li>
 * <li>[OPTIONEEL] Het maximum aantal toegestane antwoordpogingen (int). De default waarde is 1</li>
 * <li>[OPTIONEEL] De maximum toegestane antwoordtijd in seconden (int). Default is de antwoordtijd ongelimiteerd</li>
 * </ul>
 *
 * @author Ben Vandenberk
 * @version 25/10/2014
 *
 */
public class Opdracht implements Comparable<Opdracht>, Cloneable {

	private String vraag;
	private String juisteAntwoord;

	private int maxAantalPogingen;
	private int maxAntwoordTijd;

	private OpdrachtCategorie opdrachtCategorie;
	private final Leraar auteur;

	private final Datum aanmaakDatum;

	private ArrayList<String> antwoordHints;
	private ArrayList<QuizOpdracht> quizOpdrachten;

	/**
	 * Maakt een Opdracht object aan met een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is 1 (Default waarden)
	 *
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this("", "", 1, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een antwoord, een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is 1 (Default waarden)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het correcte antwoord bevat
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, juisteAntwoord, 1, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een maximum aantal pogingen, een antwoord, een opdrachtcategorie en
	 * een auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd (default)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param juisteAntwoord
	 *            de String die het correcte antwoord bevat
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, int maxAantalPogingen, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, juisteAntwoord, maxAantalPogingen, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een antwoord, een maximale antwoordtijd, een opdrachtcategorie en
	 * een auteur.<br/>
	 * <br/>
	 *
	 * Het maximaal aantal antwoordpogingen is 1 (default)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het correcte antwoord bevat
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, String juisteAntwoord, int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, juisteAntwoord, 1, maxAntwoordTijd, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een antwoord, een maximum aantal pogingen, een maximale
	 * antwoordtijd, een opdrachtcategorie en een auteur
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het correcte antwoord bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		try {
			setVraag(vraag);
			setJuisteAntwoord(juisteAntwoord);
			setMaxAantalPogingen(maxAantalPogingen);
			setMaxAntwoordTijd(maxAntwoordTijd);
			setOpdrachtCategorie(opdrachtCategorie);
		} catch (UnsupportedOperationException ex) {
			// Bij het aanmaken van een Opdracht zal de Opdracht steeds aanpasbaar zijn. De Exception zal zich nooit
			// voordoen
		}
		this.auteur = auteur;
		antwoordHints = new ArrayList<String>();
		quizOpdrachten = new ArrayList<QuizOpdracht>();
		aanmaakDatum = new Datum();
	}

	/**
	 * Haalt de te beantwoorden vraag op
	 *
	 * @return de vraag
	 */
	public String getVraag() {
		return vraag;
	}

	/**
	 * Stelt de te beantwoorden vraag in
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @throws UnsupportedOperationException
	 *             als de Opdracht niet meer aanpasbaar is
	 */
	public void setVraag(String vraag) throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.vraag = vraag;
	}

	/**
	 * Haalt het correcte antwoord op de vraag op
	 *
	 * @return een String met het antwoord op de vraag
	 */
	public String getJuisteAntwoord() {
		return juisteAntwoord;
	}

	/**
	 * Stelt het correcte antwoord op de vraag in
	 *
	 * @param juisteAntwoord
	 *            de String die het antwoord bevat
	 * @throws UnsupportedOperationException
	 *             als de Opdracht niet meer aanpasbaar is
	 */
	public void setJuisteAntwoord(String juisteAntwoord) throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.juisteAntwoord = juisteAntwoord;
	}

	/**
	 * Haalt het maximum aantal antwoordpogingen op
	 *
	 * @return het maximum aantal antwoordpogingen
	 */
	public int getMaxAantalPogingen() {
		return maxAantalPogingen;
	}

	/**
	 * Stelt het maximum aantal antwoordpogingen in
	 *
	 * @param maxAantalPogingen
	 *            het maximum aantal antwoordpogingen
	 * @throws UnsupportedOperationException
	 *             als de Opdracht niet meer aanpasbaar is
	 */
	public void setMaxAantalPogingen(int maxAantalPogingen) throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (maxAantalPogingen < 1) {
			throw new IllegalArgumentException("Het aantal pogingen moet minstens 1 zijn");
		}
		this.maxAantalPogingen = maxAantalPogingen;
	}

	/**
	 * Haalt de maximum toegestane antwoordtijd op
	 *
	 * @return de maximum toegestane antwoordtijd
	 */
	public int getMaxAntwoordTijd() {
		return maxAntwoordTijd;
	}

	/**
	 * Stelt de maximum toegestane antwoordtijd in
	 *
	 * @param maxAntwoordTijd
	 *            de maximum toegestane antwoordtijd
	 * @throws UnsupportedOperationException
	 *             als de Opdracht niet meer aanpasbaar is
	 */
	public void setMaxAntwoordTijd(int maxAntwoordTijd) throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (maxAntwoordTijd < 0) {
			throw new IllegalArgumentException("De antwoordtijd moet positief zijn");
		}
		this.maxAntwoordTijd = maxAntwoordTijd;
	}

	/**
	 * Haalt de opdrachtcategorie op
	 *
	 * @return de OpdrachtCategorie waartoe de Opdracht behoort
	 */
	public OpdrachtCategorie getOpdrachtCategorie() {
		return opdrachtCategorie;
	}

	/**
	 * Stelt de opdrachtcategorie in
	 *
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie waartoe de Opdracht zal behoren
	 * @throws UnsupportedOperationException
	 *             als de Opdracht niet meer aanpasbaar is
	 */
	public void setOpdrachtCategorie(OpdrachtCategorie opdrachtCategorie) throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.opdrachtCategorie = opdrachtCategorie;
	}

	/**
	 * Haalt de leraar op die de opdracht gecreëerd heeft
	 *
	 * @return de Leraar die de Opdracht gecreëerd heeft
	 */
	public Leraar getAuteur() {
		return auteur;
	}

	/**
	 * Geeft een kopie van het Datum objectje terug dat de aanmaakdatum van deze opdracht voorstelt
	 *
	 * @return de Datum van aanmaak van deze opdracht
	 */
	public Datum getAanmaakDatum() {
		return new Datum(aanmaakDatum);
	}

	/**
	 * Geeft een boolean terug om aan te geven of de Opdracht op tijd is of niet
	 *
	 * @return true als er een tijdsbeperking geldt
	 */
	public boolean heeftTijdsbeperking() {
		return maxAntwoordTijd != 0;
	}

	/**
	 * Wanneer een opdracht in een quiz is opgenomen die al door een leerling is gemaakt, mag de opdracht niet meer
	 * gewijzigd worden
	 *
	 * @return true als de opdracht nog niet is opgelost in een actieve quiz
	 */
	public boolean isAanpasbaar() {
		boolean isAanpasbaar = true;
		for (QuizOpdracht quizOpdracht : this.quizOpdrachten) {
			if (quizOpdracht.getQuiz().getLeerlingenDieDeelnamen().size() > 0) {
				isAanpasbaar = false;
			}
		}
		return isAanpasbaar;
	}

	/**
	 * Wanneer een opdracht in een quiz is opgenomen kan ze niet meer verwijderd worden
	 *
	 * @return true als de opdracht nog niet gelinkt is en dus verwijderd kan worden
	 */
	public boolean isVerwijderbaar() {
		return this.quizOpdrachten.size() == 0;
	}

	/**
	 * Voegt een QuizOpdracht toe aan de lijst van QuizOpdrachten teneinde de Opdracht te linken aan een Quiz
	 *
	 * @param quizOpdracht
	 *            de QuizOpdracht om toe te voegen
	 */
	public void addQuizOpdracht(QuizOpdracht quizOpdracht) {
		quizOpdrachten.add(quizOpdracht);
	}

	/**
	 * Verwijdert een QuizOpdracht uit de lijst van QuizOpdrachten
	 *
	 * @param quizOpdracht
	 *            de QuizOpdracht om te verwijderen
	 */
	public void removeQuizOpdracht(QuizOpdracht quizOpdracht) {
		quizOpdrachten.remove(quizOpdracht);
	}

	/**
	 * Voegt een antwoordhint toe aan een lijst van hints voor deze Opdracht
	 *
	 * @param hint
	 *            de String met de hint om toe te voegen
	 */
	public void addHint(String hint) {
		antwoordHints.add(hint);
	}

	/**
	 * Verwijdert een antwoordhint uit de lijst van hints voor deze Opdracht
	 *
	 * @param hint
	 *            de String met de hint om te verwijderen
	 */
	public void removeHint(String hint) {
		antwoordHints.remove(hint);
	}

	/**
	 * Verwijdert een antwoordhint uit de lijst van hints voor deze Opdracht
	 *
	 * @param volgnr
	 *            het volgnummer van de hint, beginnende bij 1
	 */
	public void removeHint(int volgnr) {
		if (volgnr < 0 || volgnr > antwoordHints.size() - 1) {
			return;
		}
		antwoordHints.remove(volgnr - 1);
	}

	/**
	 * Haalt een read-only versie van het lijstje met antwoordhints op
	 *
	 * @return de read-only List&lt;String&gt; van antwoordhints
	 */
	public List<String> getHints() {
		return Collections.unmodifiableList(antwoordHints);
	}

	/**
	 * Controleert of het meegegeven antwoord de correcte oplossing is van de vraag van deze Opdracht
	 *
	 * @param antwoord
	 *            de String met het te testen antwoord
	 * @return <code>true</code> als het meegegeven antwoord correct is
	 */
	public boolean isJuisteAntwoord(String antwoord) {
		return antwoord.equalsIgnoreCase(juisteAntwoord);
	}

	@Override
	public String toString() {
		return "Opdracht - aangemaakt: " + aanmaakDatum + " - auteur: " + auteur + "\nCategorie: " + opdrachtCategorie
				+ "\nVraag: " + vraag + "\nAntwoord: " + juisteAntwoord + "\nAntwoordtijd: "
				+ (maxAntwoordTijd == 0 ? "ongelimiteerd" : maxAntwoordTijd) + " - Aantal toegestane pogingen: "
				+ maxAantalPogingen;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Opdracht)) {
			return false;
		}

		Opdracht opdracht = (Opdracht) obj;
		if (!opdracht.aanmaakDatum.equals(this.aanmaakDatum)) {
			return false;
		}
		if (!opdracht.auteur.equals(this.auteur)) {
			return false;
		}
		if (!opdracht.vraag.equals(this.vraag)) {
			return false;
		}
		if (!opdracht.opdrachtCategorie.equals(this.opdrachtCategorie)) {
			return false;
		}
		if (opdracht.maxAantalPogingen != this.maxAantalPogingen) {
			return false;
		}
		if (opdracht.maxAntwoordTijd != this.maxAntwoordTijd) {
			return false;
		}
		if (!opdracht.juisteAntwoord.equals(this.juisteAntwoord)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		String hashString = aanmaakDatum.toString() + auteur + vraag + opdrachtCategorie + juisteAntwoord;
		int hash = hashString.hashCode();
		hash = hash * 13 + maxAntwoordTijd;
		hash = hash * 23 + maxAantalPogingen;
		return hash;
	}

	/**
	 * Vergelijkt twee opdrachten alfabetisch, eerst op de opdrachtcategorie, dan op de vraag
	 */
	@Override
	public int compareTo(Opdracht opdracht) {
		if (this.opdrachtCategorie.toString().compareToIgnoreCase(opdracht.opdrachtCategorie.toString()) == 0) {
			return this.vraag.compareToIgnoreCase(opdracht.vraag);
		} else if (this.opdrachtCategorie.toString().compareToIgnoreCase(opdracht.opdrachtCategorie.toString()) < 0) {
			return -1;
		} else {
			return 1;
		}
	}

	@Override
	public Opdracht clone() {
		Opdracht clone = null;
		try {
			clone = (Opdracht) super.clone();
			clone.antwoordHints = (ArrayList<String>) this.antwoordHints.clone();
			clone.quizOpdrachten = (ArrayList<QuizOpdracht>) this.quizOpdrachten.clone();
		} catch (CloneNotSupportedException ex) {
			// Opdracht is Cloneable, kan geen CloneNotSupportedException throwen
			ex.printStackTrace();
		}
		return clone;
	}
}
