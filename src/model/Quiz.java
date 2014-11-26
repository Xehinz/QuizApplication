package model;

import java.util.ArrayList;

import model.quizStatus.InConstructie;
import model.quizStatus.QuizStatus;
import util.datumWrapper.Datum;

/**
 * De Quiz klasse modelleert een quiz. Een Quiz wordt beschreven door volgende
 * fields:
 * 
 * <ul>
 * <li>Het onderwerp van de quiz (String)</li>
 * <li>De datum van aanmaak van de quiz (Datum)</li>
 * <li>De auteur van de quiz (enum: Leraar)</li>
 * <li>De leerjaren waarvoor de quiz bedoeld is. In te stellen met
 * setDoelLeerjaren(int...). Op te vragen met getDoelLeerjaren:
 * ArrayList&lt;Integer&gt;</li>
 * <li>Een indicator of aan de quiz maar 1 keer mag deelgenomen worden
 * (isUniekeDeelname - boolean)</li>
 * <li>Een indicator of de quiz een test is (isTest - boolean)</li>
 * <li>De status van de Quiz (QuizStatus). De verschillende statussen van
 * een quiz zijn IN_CONSTRUCTIE, AFGEWERKT, OPENGESTELD, LAATSTE_KANS en
 * AFGESLOTEN</li>
 * <li>Een unieke ID (int). De ID wordt ingesteld door de QuizCatalogus, wanneer
 * de Quiz aan een QuizCatalogus wordt toegevoegd</li>
 * </ul>
 * 
 * Een Quiz object heeft relaties met Leerlingen via QuizDeelname en met
 * Opdrachten via QuizOpdracht.
 * 
 * @author Johan Boogers, Ben Vandenberk (constructor, zijnDoelLeerjaren)
 * @version 2014.10.28
 *
 */

public class Quiz implements Comparable<Quiz>, Cloneable {

	private int ID;
	private String onderwerp = "";
	private boolean isTest = false;
	private boolean isUniekeDeelname = false;
	private QuizStatus quizStatus = new InConstructie();
	private ArrayList<QuizOpdracht> quizOpdrachten;
	private ArrayList<QuizDeelname> quizDeelnames;
	private boolean[] zijnDoelLeerjaren = new boolean[7];
	private Datum aanmaakDatum;
	private final Leraar auteur;

	/**
	 * Maakt Quiz object met enkel een auteur als argument
	 *
	 * @param auteur
	 *            de Leraar die de Quiz heeft aangemaakt
	 */
	public Quiz(Leraar auteur) {
		this(auteur, "", false);
	}

	/**
	 * constructor van de Quiz-class
	 *
	 * @param auteur
	 *            de Leraar die de Quiz heeft aangemaakt
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 */
	public Quiz(Leraar auteur, String onderwerp) {
		this(auteur, onderwerp, false);
	}

	/**
	 * constructor van de Quiz-class
	 *
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 * @param isUniekeDeelname
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 * @param auteur
	 *            de Leraar die de Quiz heeft aangemaakt
	 */
	public Quiz(Leraar auteur, String onderwerp, boolean isUniekeDeelname) {
		this.onderwerp = onderwerp;
		this.isUniekeDeelname = isUniekeDeelname;
		this.auteur = auteur;
		quizOpdrachten = new ArrayList<QuizOpdracht>();
		quizDeelnames = new ArrayList<QuizDeelname>();
		aanmaakDatum = new Datum();

		// Default kunnen leerlingen uit alle leerjaren meedoen aan de Quiz
		for (int i = 1; i < zijnDoelLeerjaren.length; i++) {
			zijnDoelLeerjaren[i] = true;
		}
	}

	/**
	 * Constructor om een Quiz aan te maken die al een ID, aanmaakDatum en
	 * quizStatus had. Gebruik deze constructor alleen om een Quiz aan te maken
	 * vanuit opslag (tekst / DB)
	 * 
	 * @param ID
	 *            de ID van de Quiz
	 * @param aanmaakDatum
	 *            de Datum waarop de Quiz is aangemaakt
	 * @param quizStatus
	 *            de status waarin de Quiz zich bevindt
	 * @param auteur
	 *            de Leraar die de Quiz heeft aangemaakt
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 * @param isUniekeDeelname
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 */
	public Quiz(int ID, Datum aanmaakDatum, QuizStatus quizStatus,
			Leraar auteur, String onderwerp, boolean isUniekeDeelname) {
		this(auteur, onderwerp, isUniekeDeelname);
		this.ID = ID;
		this.aanmaakDatum = aanmaakDatum;
		this.quizStatus = quizStatus;
	}

	/**
	 * Ophalen van het onderwerp van de quiz
	 *
	 * @return de String met het onderwerp
	 */
	public String getOnderwerp() {
		return this.onderwerp;
	}

	/**
	 * Ophalen van de status van de quiz
	 *
	 * @return de QuizStatus van de Quiz
	 */
	public QuizStatus getQuizStatus() {
		return this.quizStatus;
	}

	/**
	 * Ophalen van de indicator of deze quiz een test is of niet
	 *
	 * @return true als deze Quiz een test is
	 */
	public boolean getIsTest() {
		return this.isTest;
	}

	/**
	 * Ophalen van de indicator of aan deze quiz slechts 1x mag deelgenomen
	 * worden
	 *
	 * @return true als aan deze Quiz slechts eenmaal mag deelgenomen worden
	 */
	public boolean getIsUniekeDeelname() {
		return this.isUniekeDeelname;
	}

	/**
	 * Geeft een kopie van het Datum object terug dat de aanmaakdatum van deze
	 * quiz voorstelt
	 *
	 * @return de Datum van aanmaak van deze Quiz
	 */
	public Datum getAanmaakDatum() {
		return new Datum(aanmaakDatum);
	}

	/**
	 * Geeft de auteur van de Quiz terug
	 *
	 * @return de Leraar die de Quiz heeft aangemaakt
	 */
	public Leraar getAuteur() {
		return auteur;
	}

	/**
	 * Geeft de som terug van de maximum te behalen scores van alle opdrachten
	 * van deze quiz
	 *
	 * @return de som van de maximum te behalen scores van alle opdrachten van
	 *         deze quiz
	 */
	public int getMaxScore() {
		int maxScore = 0;
		for (QuizOpdracht quizOpdracht : this.quizOpdrachten) {
			maxScore += quizOpdracht.getMaxScore();
		}
		return maxScore;
	}

	/**
	 * Geeft de gemiddelde score van alle deelnames aan deze Quiz terug
	 *
	 * @return een double dat de gemiddelde score van alle deelnames aan deze
	 *         Quiz voorstelt
	 */
	public double getGemiddeldeScore() {
		double somScores = 0;
		for (QuizDeelname quizDeelname : this.quizDeelnames) {
			somScores += quizDeelname.getBehaaldeScore();
		}
		return somScores / this.quizDeelnames.size();
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
	 * Method om in te stellen voor welke leerjaren de quiz bedoeld is. Default
	 * is een quiz beschikbaar voor alle leerjaren. Enkel de leerjaren die bij
	 * de l&aacute;&aacute;tste aanroep van deze method meegegeven zijn, bepalen
	 * voor welke leerjaren de quiz beschikbaar is.
	 *
	 * @param doelLeerjaar
	 *            1 of meerdere leerjaren waarvoor de quiz beschikbaar wordt
	 *            gemaakt
	 */
	public void setDoelLeerjaren(int... doelLeerjaar) {
		for (int i = 1; i < zijnDoelLeerjaren.length; i++) {
			zijnDoelLeerjaren[i] = false;
		}
		for (int leerjaar : doelLeerjaar) {
			if (leerjaar < 1 || leerjaar > 6) {
				continue;
			}
			zijnDoelLeerjaren[leerjaar] = true;
		}
	}

	/**
	 * Haalt een lijstje op met de leerjaren waarvoor de Quiz bedoeld is
	 * 
	 * @return een ArrayList&lt;Integer&gt; met de leerjaren waarvoor de Quiz
	 *         bedoeld is
	 */
	public ArrayList<Integer> getDoelLeerjaren() {
		ArrayList<Integer> doelLeerjaren = new ArrayList<Integer>();
		for (int i = 1; i < zijnDoelLeerjaren.length; i++) {
			if (zijnDoelLeerjaren[i]) {
				doelLeerjaren.add(i);
			}
		}
		return doelLeerjaren;
	}

	/**
	 * Instellen van het onderwerp van de quiz
	 *
	 * @param onderwerp
	 *            het onderwerp van deze quiz *
	 */
	public void setOnderwerp(String onderwerp) {
		this.onderwerp = onderwerp;
	}

	/**
	 * Instellen van de status van de quiz
	 *
	 * @param quizStatus
	 *            de status van de quiz
	 */
	public void setQuizStatus(QuizStatus quizStatus) {
		this.quizStatus = quizStatus;
	}

	/**
	 * Test of de quiz beschikbaar is voor het meegegeven leerjaar
	 *
	 * @param leerjaar
	 *            het leerjaar om te testen
	 * @return true als het meegegeven leerjaar een leerjaar is waarvoor de quiz
	 *         beschikbaar is
	 */
	public boolean isGeldigLeerjaar(int leerjaar) {
		if (leerjaar < 1 || leerjaar > 6) {
			return false;
		}
		return zijnDoelLeerjaren[leerjaar];
	}

	/**
	 * Enkel wanneer een quiz zich in de status 'Opengesteld' of 'Laatste kans' bevindt, staat ze open voor deelname
	 *
	 * @return true als de quiz open is voor deelname
	 */
	public boolean isDeelnameMogelijk() {
		return this.quizStatus.isDeelnameMogelijk();
	}

	/**
	 * Enkel wanneer een quiz zich in de status 'In constructie' of 'Afgewerkt'
	 * bevindt, is ze verwijderbaar
	 *
	 * @return true als de quiz de status 'In constructie' of 'Afgewerkt' heeft
	 *         en dus verwijderbaar is
	 */
	public boolean isVerwijderbaar() {
		return this.quizStatus.isVerwijderbaar();
	}
	
	/**
	 * Enkel wanneer een quiz zich in de status 'In constructie' bevindt, is ze aanpasbaar
	 * 
	 * @return true als de Quiz aanpasbaar is
	 */
	public boolean isAanpasbaar() {
		return this.quizStatus.isAanpasbaar();
	}

	/**
	 * Instellen van de indicator of deze quiz een test is of niet
	 *
	 * @param isTest
	 *            indicator of deze quiz een test is of niet
	 */
	public void setIsTest(boolean isTest) {
		this.isTest = isTest;
		if (isTest) {
			isUniekeDeelname = true;
		}
	}

	/**
	 * Instellen van de indicator of je aan deze quiz slechts 1x mag deelnemen
	 *
	 * @param isUniekeDeelname
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 */
	public void setIsUniekeDeelname(boolean isUniekeDeelname) {
		this.isUniekeDeelname = isUniekeDeelname;
	}

	@Override
	public String toString() {
		return "Quiz [ID=" + ID + "] met als onderwerp '" + this.getOnderwerp()
				+ "' is " + (this.isTest ? "een" : "geen") + " test "
				+ (this.isUniekeDeelname ? "met" : "zonder")
				+ " unieke deelname." + "\nDatum van aanmaak: " + aanmaakDatum
				+ ", auteur: " + auteur + "\nStatus: " + quizStatus
				+ "\nGeldig voor leerjaren: " + doelLeerjarenString();
	}

	/**
	 * Controle of deze quiz dezelfde als als de andere
	 *
	 * @param aQuiz
	 *            een quiz waarmee we gaan vergelijken
	 * @return true als de Quiz objecten hetzelfde zijn
	 */
	@Override
	public boolean equals(Object aQuiz) {
		if (aQuiz == null) {
			return false;
		}
		if (!(aQuiz instanceof Quiz)) {
			return false;
		}
		Quiz other = (Quiz) aQuiz;
		if (this.onderwerp != other.onderwerp) {
			return false;
		}
		if (this.isTest != other.isTest) {
			return false;
		}
		if (this.isUniekeDeelname != other.isUniekeDeelname) {
			return false;
		}
		if (!this.aanmaakDatum.equals(other.aanmaakDatum)) {
			return false;
		}
		if (this.auteur != other.auteur) {
			return false;
		}
		for (int i = 0; i < this.zijnDoelLeerjaren.length; i++) {
			if (this.zijnDoelLeerjaren[i] != other.zijnDoelLeerjaren[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Toevoegen van een nieuwe opdracht voor deze quiz
	 *
	 * @param quizOpdracht
	 *            de toe te voegen QuizOpdracht
	 */
	protected void addQuizOpdracht(QuizOpdracht quizOpdracht) {
		this.quizOpdrachten.add(quizOpdracht);
	}

	/**
	 * Verwijderen van een bestaande opdracht voor deze quiz
	 *
	 * @param quizOpdracht
	 *            de te verwijderen QuizOpdracht
	 */
	protected void removeQuizOpdracht(QuizOpdracht quizOpdracht) {
		this.quizOpdrachten.remove(quizOpdracht);
	}

	/**
	 * Ophalen van de list van opdrachten gelinkt aan deze quiz
	 *
	 * @return een ArrayList&lt;Opdracht&gt met de Opdrachten in deze Quiz
	 */
	public ArrayList<Opdracht> getOpdrachten() {
		ArrayList<Opdracht> opdrachten = new ArrayList<Opdracht>();
		for (QuizOpdracht quizOpdracht : this.quizOpdrachten) {
			opdrachten.add(quizOpdracht.getOpdracht().clone());
		}
		return opdrachten;
	}

	/**
	 * Ophalen van een kopie van de lijst van QuizOpdrachten geassocieerd aan
	 * deze Quiz
	 *
	 * @return een een kopie van de lijst van QuizOpdrachten geassocieerd aan
	 *         deze Quiz
	 */
	public ArrayList<QuizOpdracht> getQuizOpdrachten() {
		ArrayList<QuizOpdracht> kopieQuizOpdrachten = new ArrayList<QuizOpdracht>();
		for (QuizOpdracht quizOpdracht : quizOpdrachten) {
			kopieQuizOpdrachten.add(quizOpdracht);
		}
		return kopieQuizOpdrachten;
	}

	/**
	 * Haalt de QuizOpdracht op die het argument-opdracht linkt aant deze Quiz.
	 * Geeft null terug als het argument-opdracht zich niet in deze Quiz bevindt
	 * 
	 * @param opdracht
	 *            de Opdracht waarvan de overeenkomstige QuizOpdracht gezocht
	 *            wordt
	 * @return de gewenste QuizOpdracht
	 */
	public QuizOpdracht getQuizOpdracht(Opdracht opdracht) {
		QuizOpdracht result = null;
		for (QuizOpdracht quizOpdracht : quizOpdrachten) {
			if (quizOpdracht.getOpdracht().equals(opdracht)) {
				result = quizOpdracht;
				break;
			}
		}
		return result;
	}

	/**
	 * Toevoegen van een nieuwe deelname aan deze quiz
	 *
	 * @param quizDeelname
	 *            de QuizDeelname om toe te voegen
	 */
	protected void addQuizDeelname(QuizDeelname quizDeelname) {
		quizDeelnames.add(quizDeelname);
	}

	/**
	 * Verwijderen van een deelname aan deze quiz
	 *
	 * @param quizDeelname
	 *            de QuizDeelname om te verwijderen
	 */
	protected void removeQuizDeelname(QuizDeelname quizDeelname) {
		quizDeelnames.remove(quizDeelname);
	}

	/**
	 * Ophalen van de lijst van leerlingen die reeds deelnamen aan deze quiz
	 *
	 * @return een ArrayList&lt;Leerling&gt; met alle Leerling objecten die een
	 *         deelname relatie hebben met dit Quiz object
	 */
	public ArrayList<Leerling> getLeerlingenDieDeelnamen() {
		ArrayList<Leerling> deelnemers = new ArrayList<Leerling>();
		for (QuizDeelname quizDeelname : this.quizDeelnames) {
			deelnemers.add(quizDeelname.getLeerling().clone());
		}
		return deelnemers;
	}

	/**
	 * Ophalen van een kopie van de lijst van QuizDeelnames geassocieerd met
	 * deze Quiz
	 *
	 * @return een kopie van de lijst met QuizDeelnames geassocieerd met deze
	 *         Quiz
	 */
	public ArrayList<QuizDeelname> getQuizDeelnames() {
		ArrayList<QuizDeelname> kopieQuizDeelnames = new ArrayList<QuizDeelname>();
		for (QuizDeelname quizDeelname : quizDeelnames) {
			kopieQuizDeelnames.add(quizDeelname);
		}
		return kopieQuizDeelnames;
	}

	/**
	 * Vergelijk deze quiz met een andere quiz op basis van het onderwerp
	 *
	 * @return een negatief nummer, een nul of een positief nummer als de quiz
	 *         alfabetisch eerder, op dezelfde plaats of later komt
	 */
	@Override
	public int compareTo(Quiz aQuiz) {
		return this.onderwerp.compareTo(aQuiz.onderwerp);
	}

	/**
	 * Geeft een Quiz terug die een clone is van deze quiz (thx Bert)
	 * 
	 * @return een geclonede Quiz
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Quiz clone() {
		Quiz clone = null;
		try {
			clone = (Quiz) super.clone(); // first shallow cloning for the basic
											// types
			// then start deep cloning for the object types
			clone.aanmaakDatum = new Datum(aanmaakDatum);
			clone.zijnDoelLeerjaren = this.zijnDoelLeerjaren.clone();
			clone.quizDeelnames = (ArrayList<QuizDeelname>) this.quizDeelnames
					.clone();
			clone.quizOpdrachten = (ArrayList<QuizOpdracht>) this.quizOpdrachten
					.clone();
		} catch (CloneNotSupportedException ex) {
			// Quiz is Cloneable, kan geen CloneNotSupportedException throwen
			ex.printStackTrace();
		}
		return clone;
	}

	@Override
	public int hashCode() {
		long hash = 1;
		hash = hash * 13 + onderwerp.hashCode();
		hash = hash * 23 + (isTest ? 10 : 20);
		hash = hash * 7 + (isUniekeDeelname ? 80 : 120);
		hash = hash * 19 + aanmaakDatum.toString().hashCode();
		hash = hash * 31 + auteur.toString().hashCode();
		for (boolean bool : zijnDoelLeerjaren) {
			hash = hash * 13 + (bool ? 30 : 70);
		}
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	private String doelLeerjarenString() {
		if (getDoelLeerjaren().size() == 0) {
			return "geen enkel";
		}
		String doelLeerjarenString = "";
		for (int i = 0; i < getDoelLeerjaren().size(); i++) {
			if (i < getDoelLeerjaren().size() - 1) {
				doelLeerjarenString += getDoelLeerjaren().get(i) + ", ";
			} else {
				doelLeerjarenString += getDoelLeerjaren().get(i);
			}
		}
		return doelLeerjarenString;
	}

}
