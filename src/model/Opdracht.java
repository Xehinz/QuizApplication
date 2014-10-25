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
public class Opdracht {

	private String vraag;
	private String juisteAntwoord;

	private int maxAantalPogingen;
	private int maxAntwoordTijd;

	private OpdrachtCategorie opdrachtCategorie;
	private final Leraar auteur;

	private Datum aanmaakDatum;

	private ArrayList<String> antwoordHints;
	private ArrayList<QuizOpdracht> quizOpdrachten;

	/**
	 * Maakt een Opdracht object aan met een opdrachtcategorie en een auteur.<br/><br/>
	 * 
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is 1 (Default waarden)
	 * 
	 * @param opdrachtCategorie de OpdrachtCategorie
	 * @param auteur de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this("", "", 1, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een antwoord, een opdrachtcategorie en een auteur.<br/><br/>
	 * 
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is 1 (Default waarden)
	 * 
	 * @param vraag de String die de vraag bevat
	 * @param juisteAntwoord de String die het correcte antwoord bevat
	 * @param opdrachtCategorie de OpdrachtCategorie
	 * @param auteur de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, juisteAntwoord, 1, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een maximum aantal pogingen, een antwoord, een opdrachtcategorie en een auteur.<br/><br/>
	 * 
	 * De antwoordtijd is ongelimiteerd (default)
	 * 
	 * @param vraag de String die de vraag bevat
	 * @param maxAantalPogingen het maximaal aantal toegelaten antwoordpogingen
	 * @param juisteAntwoord de String die het correcte antwoord bevat
	 * @param opdrachtCategorie de OpdrachtCategorie
	 * @param auteur de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, int maxAantalPogingen, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, juisteAntwoord, maxAantalPogingen, 0, opdrachtCategorie, auteur);
	}

	/**
	 * Maakt een Opdracht object aan met een vraag, een antwoord, een maximale antwoordtijd, een opdrachtcategorie en een auteur.<br/><br/>
	 * 
	 * Het maximaal aantal antwoordpogingen is 1 (default)
	 * 
	 * @param vraag de String die de vraag bevat
	 * @param juisteAntwoord de String die het correcte antwoord bevat
	 * @param maxAntwoordTijd de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie de OpdrachtCategorie
	 * @param auteur de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, String juisteAntwoord, int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		this(vraag, juisteAntwoord, 1, maxAntwoordTijd, opdrachtCategorie, auteur);
	}

	/**
	 *  Maakt een Opdracht object aan met een vraag, een antwoord, een maximum aantal pogingen, een maximale antwoordtijd, een opdrachtcategorie en een auteur
	 *  
	 * @param vraag de String die de vraag bevat
	 * @param juisteAntwoord de String die het correcte antwoord bevat
	 * @param maxAantalPogingen het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie de OpdrachtCategorie
	 * @param auteur de Leraar die de Opdracht gecreëerd heeft
	 */
	public Opdracht(String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		setVraag(vraag);
		setJuisteAntwoord(juisteAntwoord);
		setMaxAantalPogingen(maxAantalPogingen);
		setMaxAntwoordTijd(maxAntwoordTijd);
		setOpdrachtCategorie(opdrachtCategorie);
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
	 * @param vraag de String die de vraag bevat
	 */
	public void setVraag(String vraag) {
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
	 * @param juisteAntwoord de String die het antwoord bevat
	 */
	public void setJuisteAntwoord(String juisteAntwoord) {
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
	 * @param maxAantalPogingen het maximum aantal antwoordpogingen
	 */
	public void setMaxAantalPogingen(int maxAantalPogingen) {
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
	 * @param maxAntwoordTijd de maximum toegestane antwoordtijd
	 */
	public void setMaxAntwoordTijd(int maxAntwoordTijd) {
		if (maxAntwoordTijd < 1) {
			throw new IllegalArgumentException("De antwoordtijd moet minstens 1 seconde bedragen");
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
	 * @param opdrachtCategorie de OpdrachtCategorie waartoe de Opdracht zal behoren
	 */
	public void setOpdrachtCategorie(OpdrachtCategorie opdrachtCategorie) {
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
	 * Haalt de aanmaakdatum van de opdracht op
	 * 
	 * @return de Datum waarop de opdracht aangemaakt is
	 */
	public Datum getAanmaakDatum() {
		return aanmaakDatum;
	}

	/**
	 * Voegt een QuizOpdracht toe aan de lijst van QuizOpdrachten teneinde de Opdracht te linken aan een Quiz
	 * 
	 * @param quizOpdracht de QuizOpdracht om toe te voegen
	 */
	public void addQuizOpdracht(QuizOpdracht quizOpdracht) {
		quizOpdrachten.add(quizOpdracht);
	}

	/**
	 * Verwijdert een QuizOpdracht uit de lijst van QuizOpdrachten
	 * 
	 * @param quizOpdracht de QuizOpdracht om te verwijderen
	 */
	public void removeQuizOpdracht(QuizOpdracht quizOpdracht) {
		quizOpdrachten.remove(quizOpdracht);
	}

	/**
	 * Voegt een antwoordhint toe aan een lijst van hints voor deze Opdracht
	 * 
	 * @param hint de String met de hint om toe te voegen
	 */
	public void addHint(String hint) {
		antwoordHints.add(hint);
	}

	/**
	 * Verwijdert een antwoordhint uit de lijst van hints voor deze Opdracht
	 * 
	 * @param hint de String met de hint om te verwijderen
	 */
	public void removeHint(String hint) {
		antwoordHints.remove(hint);
	}

	/**
	 * Verwijdert een antwoordhint uit de lijst van hints voor deze Opdracht
	 * 
	 * @param volgnr het volgnummer van de hint, beginnende bij 1
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
	 * @param antwoord de String met het te testen antwoord
	 * @return <code>true</code> als het meegegeven antwoord correct is
	 */
	public boolean isJuisteAntwoord(String antwoord) {
		return antwoord.equalsIgnoreCase(juisteAntwoord);
	}
}
