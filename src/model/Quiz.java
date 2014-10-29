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
	 * Ophalen van de indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 *
	 * @return
	 */
	public boolean getIsUniekeDeelname() {
		return this.isUniekeDeelname;
	}

	/**
	 * Geeft een kopie van het Datum objectje terug dat de aanmaakdatum van deze quiz voorstelt
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
	 * Geeft de som terug van de maximum te behalen scores van alle opdrachten van deze quiz
	 *
	 * @return de som van de maximum te behalen scores van alle opdrachten van deze quiz
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
	 * @return een double dat de gemiddelde score van alle deelnames aan deze Quiz voorstelt
	 */
	public double getGemiddeldeScore() {
		double somScores = 0;
		for (QuizDeelname quizDeelname : this.quizDeelnames) {
			somScores += quizDeelname.getBehaaldeScore();
		}
		return somScores / this.quizDeelnames.size();
	}

	/**
	 * Method om in te stellen voor welke leerjaren de quiz bedoeld is. Default is een quiz beschikbaar voor alle
	 * leerjaren. Enkel de leerjaren die bij de láátste aanroep van deze method meegegeven zijn, bepalen voor welke
	 * leerjaren de quiz beschikbaar is.
	 *
	 * @param doelLeerjaar
	 *            1 of meerdere leerjaren waarvoor de quiz beschikbaar wordt gemaakt
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
	 * @return true als het meegegeven leerjaar een leerjaar is waarvoor de quiz beschikbaar is
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
	 * Enkel wanneer een quiz zich in de status 'In constructie' of 'Afgewerkt' bevindt, is ze verwijderbaar
	 *
	 * @return true als de quiz de status 'In constructie' of 'Afgewerkt' heeft en dus verwijderbaar is
	 */
	public boolean isVerwijderbaar() {
		return this.quizStatus == QuizStatus.IN_CONSTRUCTIE || this.quizStatus == QuizStatus.AFGEWERKT;
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
	 * override toString --> levert 'onderwerp' af met Test(?), UniekeDeelname (?) en status
	 */
	@Override
	public String toString() {
		return "Quiz met als onderwerp '" + this.getOnderwerp() + "' is " + (this.isTest ? "een" : "geen") + " test "
				+ (this.isUniekeDeelname ? "met" : "zonder") + " unieke deelname.";
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
		// uit te breiden met nieuwe testen zodra extra attributen worden
		// toegevoegd in deze class
		return ((Quiz) aQuiz).getOnderwerp() == this.getOnderwerp() && ((Quiz) aQuiz).getIsTest() == this.getIsTest()
				&& ((Quiz) aQuiz).getIsUniekeDeelname() == this.getIsUniekeDeelname()
				&& ((Quiz) aQuiz).getQuizStatus() == this.getQuizStatus()
				&& this.getOpdrachten().equals(((Quiz) aQuiz).getOpdrachten());
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
			opdrachten.add(quizOpdracht.getOpdracht());
		}
		return opdrachten;
	}

	/**
	 * Ophalen van een QuizOpdracht uit alle QuizOpdrachten gelinkt aan deze quiz
	 *
	 * @param volgnr
	 *            het volgnummer van de QuizOpdracht (base 1)
	 * @return de link van de opdracht aan deze Quiz via QuizOpdracht
	 */
	public QuizOpdracht getQuizOpdracht(int volgnr) {
		// in de opgave staat deze method gedeclareerd als getOpdracht() wat
		// verwarring kan brengen aangezien het returntype QuizOpdracht is en
		// niet Opdracht !
		return this.quizOpdrachten.get(volgnr - 1);
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
	 * @return een ArrayList&lt;Leerling&gt; met alle Leerling objecten die een deelname relatie hebben met dit Quiz
	 *         object
	 */
	public ArrayList<Leerling> getLeerlingenDieDeelnamen() {
		ArrayList<Leerling> deelnemers = new ArrayList<Leerling>();
		for (QuizDeelname quizDeelname : this.quizDeelnames) {
			deelnemers.add(quizDeelname.getLeerling());
		}
		return deelnemers;
	}

	/**
	 * Ophalen van een QuizDeelname uit alle QuizDeelnames gelinkt aan deze Quiz
	 *
	 * @param volgnr
	 *            het volgnummer van de QuizDeelname (base 1)
	 * @return een QuizDeelname die deze Quiz linkt aan een Leerling
	 */
	public QuizDeelname getQuizDeelname(int volgnr) {
		return this.quizDeelnames.get(volgnr - 1);
	}

	/**
	 * Vergelijk deze quiz met een andere quiz
	 *
	 * @return een negatief nummer, een nul of een positief nummer als de quiz minder, hetzelfde aantal of meer
	 *         opdrachten heeft
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
			clone = (Quiz) super.clone();
			clone.aanmaakDatum = new Datum(aanmaakDatum);
			clone.quizDeelnames = (ArrayList<QuizDeelname>) this.quizDeelnames.clone();
			clone.quizOpdrachten = (ArrayList<QuizOpdracht>) this.quizOpdrachten.clone();
		} catch (CloneNotSupportedException ex) {
			// Quiz is Cloneable, kan geen CloneNotSupportedException throwen
			ex.printStackTrace();
		}
		return clone;
	}

	@Override
	public int hashCode() {
		return String.format("%s%s%s", onderwerp, auteur.toString(), aanmaakDatum).hashCode();
	}

}
