package model;

import java.util.ArrayList;

import util.datumWrapper.Datum;

/**
 * De Opdracht klasse modelleert een opdracht die gebruikt kan worden in
 * meerdere quizzen. Het is een abstracte klasse. Bij een opdracht horen
 * volgende fields:
 *
 * <ul>
 * <li>De vraag waarop een antwoord moet gevonden worden (String)</li>
 * <li>De opdrachtcategorie (Enum OpdrachtCategorie: Aardrijkskunde, Nederlands,
 * Wetenschappen, Wiskunde)</li>
 * <li>De auteur van de opdracht (Enum Leraar)</li>
 * <li>De datum waarop de opdracht is aangemaakt (Datum)</li>
 * <li>Een lijst met antwoordhints (ArrayList&lt;String&gt;). Deze lijst kan
 * leeg zijn</li>
 * <li>Een lijst met QuizOpdrachten (ArrayList&lt;QuizOpdracht&gt;). De klasse
 * QuizOpdracht linkt de opdracht aan 0, 1 of meerdere quizzen</li>
 * <li>[OPTIONEEL] Het maximum aantal toegestane antwoordpogingen (int). De
 * default waarde is 1. Om een ongelimiteerd aantal pogingen toe te laten op 0
 * zetten</li>
 * <li>[OPTIONEEL] De maximum toegestane antwoordtijd in seconden (int). Default
 * is de antwoordtijd ongelimiteerd</li>
 * <li>Een unieke ID. Deze ID wordt toegewezen door de OpdrachtCatalogus</li>
 * </ul>
 *
 * @author Ben Vandenberk
 * @version 25/10/2014
 *
 */
public abstract class Opdracht implements Comparable<Opdracht>, Cloneable {

	private String vraag;

	private int maxAantalPogingen;
	private int maxAntwoordTijd;
	private int ID;

	private OpdrachtCategorie opdrachtCategorie;
	private final Leraar auteur;

	private Datum aanmaakDatum;

	private ArrayList<String> antwoordHints;
	private ArrayList<QuizOpdracht> quizOpdrachten;

	/**
	 * Maakt een Opdracht object aan met een opdrachtcategorie en een auteur.<br/>
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
	public Opdracht(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this("", 1, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een opdrachtcategorie en een
	 * auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is
	 * 1 (Default waarden)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Opdracht(String vraag, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		this(vraag, 1, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een maximum aantal pogingen,
	 * een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd (default)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 */
	public Opdracht(String vraag, int maxAantalPogingen, Leraar auteur,
			OpdrachtCategorie opdrachtCategorie) {
		this(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een maximale antwoordtijd,
	 * een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 *
	 * Het maximaal aantal antwoordpogingen is 1 (default)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Opdracht(String vraag, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, 1, maxAntwoordTijd, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een maximum aantal pogingen,
	 * een maximale antwoordtijd, een opdrachtcategorie en een auteur
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Opdracht(String vraag, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this.auteur = auteur;
		antwoordHints = new ArrayList<String>();
		quizOpdrachten = new ArrayList<QuizOpdracht>();
		aanmaakDatum = new Datum();
		setVraag(vraag);
		setMaxAantalPogingen(maxAantalPogingen);
		setMaxAntwoordTijd(maxAntwoordTijd);
		setOpdrachtCategorie(opdrachtCategorie);
	}

	/**
	 * Maakt een Opdracht object aan dat al een ID en een datum van aanmaak
	 * toegewezen had. Gebruik deze constructor alleen om een Opdracht te maken
	 * uit opslag (tekst / DB)
	 *
	 * @param ID
	 *            de ID van de Opdracht
	 * @param aanmaakDatum
	 *            de Datum waarop de Opdracht is aangemaakt
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Opdracht(int ID, Datum aanmaakDatum, String vraag,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.ID = ID;
		this.aanmaakDatum = aanmaakDatum;
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
	 * @throws IllegalStateException
	 *             als de Opdracht niet meer aanpasbaar is
	 */
	public void setVraag(String vraag) throws IllegalStateException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.vraag = vraag;
	}

	/**
	 * Haalt het maximum aantal antwoordpogingen op. Geeft 0 terug wanneer het aantal pogingen ongelimiteerd is
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
	 * @throws IllegalStateException
	 *             als de Opdracht niet meer aanpasbaar is
	 * @throws IllegalArgumentException
	 *             als het argument negatief is
	 */
	public void setMaxAantalPogingen(int maxAantalPogingen)
			throws IllegalStateException, IllegalArgumentException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (maxAantalPogingen < 0) {
			throw new IllegalArgumentException(
					"Het aantal pogingen moet minstens 0 (= ongelimiteerd) zijn");
		}
		this.maxAantalPogingen = maxAantalPogingen;
	}

	/**
	 * Haalt de maximum toegestane antwoordtijd op. Geeft 0 terug wanneer de antwoordtijd ongelimiteerd is.
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
	 * @throws IllegalStateException
	 *             als de Opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen deze opdracht hebben opgelost
	 * @throws IllegalArgumentException
	 *             als het argument negatief is
	 */
	public void setMaxAntwoordTijd(int maxAntwoordTijd)
			throws IllegalStateException, IllegalArgumentException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (maxAntwoordTijd < 0) {
			throw new IllegalArgumentException(
					"De antwoordtijd moet positief zijn");
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
	 * @throws IllegalStateException
	 *             als de Opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen deze opdracht hebben opgelost
	 */
	public void setOpdrachtCategorie(OpdrachtCategorie opdrachtCategorie)
			throws IllegalStateException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.opdrachtCategorie = opdrachtCategorie;
	}

	/**
	 * Haalt de leraar op die de opdracht gecre&euml;erd heeft
	 *
	 * @return de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public Leraar getAuteur() {
		return auteur;
	}

	/**
	 * Geeft een kopie van het Datum object terug dat de aanmaakdatum van deze
	 * opdracht voorstelt
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
	 * Geeft een boolean terug om aan te geven of er voor de Opdracht slechts
	 * een beperkt aantal pogingen is toegestaan
	 *
	 * @return true als het aantal pogingen beperkt is
	 */
	public boolean heeftPogingBeperking() {
		return maxAantalPogingen != 0;
	}

	/**
	 * Wanneer een opdracht in een quiz is opgenomen die al door een leerling is
	 * gemaakt, mag de opdracht niet meer gewijzigd worden
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
	 * Wanneer een opdracht in een quiz is opgenomen kan ze niet meer verwijderd
	 * worden
	 *
	 * @return true als de opdracht nog niet gelinkt is en dus verwijderd kan
	 *         worden
	 */
	public boolean isVerwijderbaar() {
		return this.quizOpdrachten.size() == 0;
	}

	/**
	 * Voegt een QuizOpdracht toe aan de lijst van QuizOpdrachten teneinde de
	 * Opdracht te linken aan een Quiz
	 *
	 * @param quizOpdracht
	 *            de QuizOpdracht om toe te voegen
	 */
	protected void addQuizOpdracht(QuizOpdracht quizOpdracht) {
		quizOpdrachten.add(quizOpdracht);
	}

	/**
	 * Verwijdert een QuizOpdracht uit de lijst van QuizOpdrachten
	 *
	 * @param quizOpdracht
	 *            de QuizOpdracht om te verwijderen
	 */
	protected void removeQuizOpdracht(QuizOpdracht quizOpdracht) {
		quizOpdrachten.remove(quizOpdracht);
	}

	/**
	 * Method die door de container klasse gebruikt kan worden om de ID van deze
	 * Opdracht te zetten
	 *
	 * @param id
	 *            de ID van deze Opdracht
	 */
	protected void setID(int id) {
		ID = id;
	}

	/**
	 * Haalt de ID van de Opdracht op
	 *
	 * @return de ID van de Opdracht
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Geeft een kopie van de lijst met QuizOpdrachten gelinkt aan deze Opdracht
	 * terug. De QuizOpdrachten in de teruggegeven lijst zijn geen kopies omdat
	 * aan een QuizOpdracht toch niets gewijzigd kan worden
	 *
	 * @return een kopie van de lijst met QuizOpdrachten gelinkt aan deze
	 *         Opdracht
	 */
	public ArrayList<QuizOpdracht> getQuizOpdrachten() {
		ArrayList<QuizOpdracht> kopie = new ArrayList<QuizOpdracht>();
		for (QuizOpdracht quizOpdracht : quizOpdrachten) {
			kopie.add(quizOpdracht);
		}
		return kopie;
	}

	/**
	 * Voegt een antwoordhint toe aan een lijst van hints voor deze Opdracht
	 *
	 * @param hint
	 *            de String met de hint om toe te voegen
	 * @throws IllegalStateException
	 *             als de Opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen deze opdracht hebben opgelost
	 */
	public void addHint(String hint) throws IllegalStateException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		antwoordHints.add(hint);
	}

	/**
	 * Verwijdert een antwoordhint uit de lijst van hints voor deze Opdracht
	 *
	 * @param hint
	 *            de String met de hint om te verwijderen
	 * @throws IllegalStateException
	 *             als de Opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen deze opdracht hebben opgelost
	 */
	public void removeHint(String hint) throws IllegalStateException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		antwoordHints.remove(hint);
	}

	/**
	 * Verwijdert een antwoordhint uit de lijst van hints voor deze Opdracht
	 *
	 * @param volgnr
	 *            het volgnummer van de hint, beginnende bij 1
	 * @throws IllegalStateException
	 *             als de Opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen deze opdracht hebben opgelost
	 * @throws IllegalArgumentException
	 *             als er voor het meegegeven volgnummer geen hint bestaat
	 */
	public void removeHint(int volgnr) throws IllegalArgumentException,
			IllegalStateException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		if (volgnr < 1 || volgnr > antwoordHints.size()) {
			throw new IllegalArgumentException("De hint met volgnummer "
					+ volgnr + " bestaat niet");
		}
		antwoordHints.remove(volgnr - 1);
	}

	/**
	 * Geeft een kopie van de lijst met antwoordhints terug
	 *
	 * @return een kopie van de lijst met antwoordhints
	 */
	public ArrayList<String> getHints() {
		ArrayList<String> kopie = new ArrayList<String>();
		for (String hint : antwoordHints) {
			kopie.add(hint);
		}
		return kopie;
	}

	/**
	 * Controleert of het meegegeven antwoord de correcte oplossing is van de
	 * vraag van deze Opdracht
	 *
	 * @param antwoord
	 *            de String met het te testen antwoord
	 * @return <code>true</code> als het meegegeven antwoord correct is
	 */
	public abstract boolean isJuisteAntwoord(String antwoord);
	
	/**
	 * Geeft het juiste antwoord op deze opdracht terug
	 * 
	 * @return de String met het juiste antwoord
	 */
	public abstract String getJuisteAntwoord();

	/**
	 * Stelt het juiste antwoord in
	 * 
	 * @param juisteAntwoord de String met het te setten juiste antwoord
	 */
	public abstract void setJuisteAntwoord(String juisteAntwoord);
	
	/**
	 * Zoekt naar een QuizOpdracht met een bepaalde ID. Geeft null terug als die
	 * voor deze Opdracht niet bestaat.
	 * 
	 * @param quizOpdrachtID
	 *            de gewenste ID
	 * @return de QuizOpdracht met matchende ID. Null als die niet gevonden
	 *         wordt
	 */
	public QuizOpdracht getQuizOpdracht(String quizOpdrachtID) {
		QuizOpdracht toReturn = null;
		for (QuizOpdracht quizOpdracht : quizOpdrachten) {
			if (quizOpdracht.getID().equals(quizOpdrachtID)) {
				toReturn = quizOpdracht;
			}
		}
		return toReturn;
	}

	@Override
	public String toString() {
		return "Opdracht [ID="
				+ ID
				+ "] - aangemaakt: "
				+ aanmaakDatum
				+ " - auteur: "
				+ auteur
				+ "\nCategorie: "
				+ opdrachtCategorie
				+ "\nAntwoordtijd: "
				+ (heeftTijdsbeperking() ? maxAntwoordTijd : "ongelimiteerd")
				+ " - Aantal toegestane pogingen: "
				+ (heeftPogingBeperking() ? maxAantalPogingen : "ongelimiteerd")
				+ "\nHints: " + hintsString() + "\nVraag: " + vraag
				+ "\nAntwoord: ";
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (!(this.getClass() == obj.getClass())) {
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
		if (!opdracht.hintsString().equals(this.hintsString())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		String hashString = aanmaakDatum.toString() + auteur + vraag
				+ opdrachtCategorie;
		long hash = hashString.hashCode();
		hash = hash * 13 + (heeftTijdsbeperking() ? maxAntwoordTijd : 5);
		hash = hash * 23 + (heeftPogingBeperking() ? maxAantalPogingen : 6);
		hash = hash * 41 + hintsString().hashCode();
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	/**
	 * Vergelijkt twee opdrachten alfabetisch, eerst op de opdrachtcategorie,
	 * dan op de vraag
	 */
	@Override
	public int compareTo(Opdracht opdracht) {
		if (this.opdrachtCategorie.toString().compareToIgnoreCase(
				opdracht.opdrachtCategorie.toString()) == 0) {
			return this.vraag.compareToIgnoreCase(opdracht.vraag);
		} else if (this.opdrachtCategorie.toString().compareToIgnoreCase(
				opdracht.opdrachtCategorie.toString()) < 0) {
			return -1;
		} else {
			return 1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Opdracht clone() {
		Opdracht clone = null;
		try {
			clone = (Opdracht) super.clone();
			clone.antwoordHints = (ArrayList<String>) this.antwoordHints
					.clone();
			clone.quizOpdrachten = (ArrayList<QuizOpdracht>) this.quizOpdrachten
					.clone();
		} catch (CloneNotSupportedException ex) {
			// Opdracht is Cloneable, kan geen CloneNotSupportedException
			// throwen
			ex.printStackTrace();
		}
		return clone;
	}

	private String hintsString() {
		if (antwoordHints.size() == 0) {
			return "geen";
		}
		String hintsString = "";
		for (int i = 0; i < antwoordHints.size(); i++) {
			if (i < antwoordHints.size() - 1) {
				hintsString += antwoordHints.get(i) + " | ";
			} else {
				hintsString += antwoordHints.get(i);
			}
		}
		return hintsString;
	}

	public void setHints(ArrayList<String> hints) {
		this.antwoordHints = hints;
	}

	public String toStringVoorLijst() {
		return opdrachtCategorie + " - " + vraag;
	}

}
