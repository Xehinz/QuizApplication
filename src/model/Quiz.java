package model;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author johan
 * @version 2014.10.20
 *
 */

public class Quiz {

	private String onderwerp = "";
	private boolean isTest = true;
	private boolean isUniekeDeelname = false;
	private String quizStatus = "new";
	private List<QuizOpdracht> quizOpdrachten;

	/**
	 * lege constructor van de Quiz-class
	 */
	public Quiz() {
		this.quizOpdrachten = new ArrayList<QuizOpdracht>();
	}

	/**
	 * constructor van de Quiz-class
	 * 
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 */
	public Quiz(String onderwerp) {
		this.onderwerp = onderwerp;
		this.quizOpdrachten = new ArrayList<QuizOpdracht>();
	}

	/**
	 * constructor van de Quiz-class
	 * 
	 * @param onderwerp
	 *            het onderwper van deze quiz
	 * @param isUniekeDeelname
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 */
	public Quiz(String onderwerp, boolean isUniekeDeelname) {
		this.onderwerp = onderwerp;
		this.isUniekeDeelname = isUniekeDeelname;
		quizOpdrachten = new ArrayList<QuizOpdracht>();
	}

	/**
	 * constructor van de Quiz-class
	 * 
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 * @param isUniekeDeelname
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 * @param quizStatus
	 *            status van de quiz
	 */
	public Quiz(String onderwerp, boolean isUniekeDeelname, String quizStatus) {
		this.onderwerp = onderwerp;
		this.isUniekeDeelname = isUniekeDeelname;
		this.quizStatus = quizStatus;
		quizOpdrachten = new ArrayList<QuizOpdracht>();
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
	 * Instellen van het onderwerp van de quiz
	 * 
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 * @return
	 */
	public boolean setOnderwerp(String onderwerp) {
		this.onderwerp = onderwerp;
		return true;
	}

	/**
	 * Instellen van de indicator of deze quiz een test is of niet
	 * 
	 * @param isTest
	 *            indicator of deze quiz een test is of niet
	 * @return
	 */
	public boolean setIsTest(boolean isTest) {
		this.isTest = isTest;
		return true;
	}

	/**
	 * Instellen van de indicator of je aan deze quiz slechts 1x mag deelnemen
	 * 
	 * @param isUniekeDeelname
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 * @return
	 */
	public boolean setIsUniekeDeelname(boolean isUniekeDeelname) {
		this.isUniekeDeelname = isUniekeDeelname;
		return true;
	}

	/**
	 * override toString --> levert 'onderwerp' af met Test(?), UniekeDeelname
	 * (?) en status
	 */
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
	public boolean equals(Quiz aQuiz) {
		// uit te breiden naarmate andere attributen uitgewerkt worden (cfr. de
		// lijst met opdrachten vergelijken)
		return aQuiz.getOnderwerp() == this.getOnderwerp()
				&& aQuiz.getIsTest() == aQuiz.getIsTest()
				&& aQuiz.getIsUniekeDeelname() == this.getIsUniekeDeelname();
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
	 * Ophalen van een opdracht uit alle opdrachten gelinkt aan deze quiz
	 * 
	 * @param volgnr
	 *            het volgnummer van de opdracht (base 1)
	 * @return
	 */
	public QuizOpdracht getQuizOpdracht(int volgnr) {
		// in de opgave staat deze method gedeclareerd als getOpdracht() wat
		// verwarring kan brengen aangezien het returntype QuizOpdracht is en
		// niet Opdracht !
		return this.quizOpdrachten.get(volgnr - 1);
	}

	// Comparable --> implements Comparable (x)
	// - compareTo
	// hashCode (res)

}
