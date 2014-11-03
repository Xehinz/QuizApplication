package model;

import java.util.ArrayList;

import util.datumWrapper.Datum;

/**
 *
 * @author johan, ben (constructor, zijnDoelLeerjaren)
 * @version 2014.10.28
 *
 */

public class Quiz implements Comparable<Quiz>, Cloneable {

	private int ID;
	private String onderwerp = "";
	private boolean isTest = false;
	private boolean isUniekeDeelname = false;
	private QuizStatus quizStatus = QuizStatus.IN_CONSTRUCTIE;
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
	 * @return
	 */
	public String getOnderwerp() {
		return this.onderwerp;
	}

	/**
	 * Ophalen van de status van de quiz
	 *
	 * @return
	 */
	public QuizStatus getQuizStatus() {
		return this.quizStatus;
	}

	/**
	 * Ophalen van de indicator of deze quiz een test is of niet
	 *
	 * @return
	 */
	public boolean getIsTest() {
		return this.isTest;
	}

	/**
	 * Ophalen van de indicator of aan deze quiz slechts 1x mag deelgenomen
	 * worden
	 *
	 * @return
	 */
	public boolean getIsUniekeDeelname() {
		return this.isUniekeDeelname;
	}

	/**
	 * Geeft een kopie van het Datum objectje terug dat de aanmaakdatum van deze
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
	 * Geeft de gemiddelde van alle deelnames aan deze Quiz terug
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

	public int getID() {
		return ID;
	}

	/**
	 * Method om in te stellen voor welke leerjaren de quiz bedoeld is. Default
	 * is een quiz beschikbaar voor alle leerjaren. Enkel de leerjaren die bij
	 * de láátste aanroep van deze method meegegeven zijn, bepalen voor welke
	 * leerjaren de quiz beschikbaar is.
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
	 *            de status van de quiz *
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
	 * Test of de quiz zich in een status bevindt die deelname eraan toelaat
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
		return this.quizStatus == QuizStatus.IN_CONSTRUCTIE
				|| this.quizStatus == QuizStatus.AFGEWERKT;
	}

	/**
	 * Instellen van de indicator of deze quiz een test is of niet
	 *
	 * @param isTest
	 *            indicator of deze quiz een test is of niet *
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
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden *
	 */
	public void setIsUniekeDeelname(boolean isUniekeDeelname) {
		this.isUniekeDeelname = isUniekeDeelname;
	}

	/**
	 * override toString --> levert 'onderwerp' af met Test(?), UniekeDeelname
	 * (?) en status
	 */
	@Override
	public String toString() {
		return "Quiz met als onderwerp '" + this.getOnderwerp() + "' is "
				+ (this.isTest ? "een" : "geen") + " test "
				+ (this.isUniekeDeelname ? "met" : "zonder")
				+ " unieke deelname.";
	}

	/**
	 * override equals --> controle of deze quiz dezelfde als als de andere
	 *
	 * @param aQuiz
	 *            een quiz waarmee we gaan vergelijken
	 * @return
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
		if (this.quizStatus != other.quizStatus) {
			return false;
		}
		if (!this.aanmaakDatum.equals(other.aanmaakDatum)) {
			return false;
		}
		if (this.auteur != other.auteur) {
			return false;
		}
		if (!this.getOpdrachten().equals(other.getOpdrachten())) {
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
	 */
	protected void addQuizOpdracht(QuizOpdracht quizOpdracht) {
		this.quizOpdrachten.add(quizOpdracht);
	}

	/**
	 * Verwijderen van een bestaande opdracht voor deze quiz
	 *
	 * @param quizOpdracht
	 */
	protected void removeQuizOpdracht(QuizOpdracht quizOpdracht) {
		this.quizOpdrachten.remove(quizOpdracht);
	}

	/**
	 * Ophalen van de list van opdrachten gelinkt aan deze quiz
	 *
	 * @return
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
	 * Ophalen van de list van leerlingen die reeds deelnamen aan deze quiz
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
	 * Vergelijk deze quiz met een andere quiz
	 *
	 * @return een negatief nummer, een nul of een positief nummer als de quiz
	 *         minder, hetzelfde aantal of meer opdrachten heeft
	 */
	@Override
	public int compareTo(Quiz aQuiz) {
		if (this.getOpdrachten().size() < aQuiz.getOpdrachten().size()) {
			return -1;
		} else if (this.getOpdrachten().size() > aQuiz.getOpdrachten().size()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	/**
	 * Geeft een Quiz terug die een clone is van deze quiz (thx Bert)
	 * @return een geclonede Quiz
	 */
	public Quiz clone() {
		Quiz clone = null;
		try {
			clone = (Quiz) super.clone(); //first shallow cloning for the basic types
			//then start deep cloning for the object types
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
		hash = hash * 17 + quizStatus.hashCode();
		hash = hash * 19 + aanmaakDatum.toString().hashCode();
		hash = hash * 31 + auteur.toString().hashCode();
		for (boolean bool : zijnDoelLeerjaren) {
			hash = hash * 13 + (bool ? 30 : 70);
		}
		for (Opdracht opdracht : getOpdrachten()) {
			hash = hash * 19 + opdracht.hashCode();
		}
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

}
