package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johan, ben (constructor, zijnDoelLeerjaren)
 * @version 2014.10.25
 *
 */

public class Quiz implements Comparable<Quiz>, Cloneable {

	private String onderwerp = "";
	private boolean isTest = true;
	private boolean isUniekeDeelname = false;
	private String quizStatus = "new";
	private List<QuizOpdracht> quizOpdrachten;
	private List<QuizDeelname> quizDeelnames;
	private boolean[] zijnDoelLeerjaren = new boolean[7];

	/**
	 * lege constructor van de Quiz-class
	 */
	public Quiz() {
		this("", false, "");
	}

	/**
	 * constructor van de Quiz-class
	 *
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 */
	public Quiz(String onderwerp) {
		this(onderwerp, false, "");
	}

	/**
	 * constructor van de Quiz-class
	 *
	 * @param onderwerp
	 *            het onderwerp van deze quiz
	 * @param isUniekeDeelname
	 *            indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 */
	public Quiz(String onderwerp, boolean isUniekeDeelname) {
		this(onderwerp, isUniekeDeelname, "");
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
		quizDeelnames = new ArrayList<QuizDeelname>();

		for (boolean isDoelLeerjaar : zijnDoelLeerjaren) {
			isDoelLeerjaar = true;
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
	public String getQuizStatus() {
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
		for (boolean isDoelLeerjaar : zijnDoelLeerjaren) {
			isDoelLeerjaar = false;
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
	 *            het onderwerp van deze quiz
	 * @return
	 */
	public boolean setOnderwerp(String onderwerp) {
		this.onderwerp = onderwerp;
		return true;
	}

	/**
	 * Instellen van de status van de quiz
	 *
	 * @param quizStatus
	 *            de status van de quiz
	 * @return
	 */
	public boolean setQuizStatus(String quizStatus) {
		this.quizStatus = quizStatus;
		return true;
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
		// uit te breiden met nieuwe testen zodra extra attributen worden
		// toegevoegd in deze class
		return ((Quiz) aQuiz).getOnderwerp() == this.getOnderwerp()
				&& ((Quiz) aQuiz).getIsTest() == this.getIsTest()
				&& ((Quiz) aQuiz).getIsUniekeDeelname() == this
						.getIsUniekeDeelname()
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
	public void addQuizDeelname(QuizDeelname quizDeelname) {
		quizDeelnames.add(quizDeelname);
	}

	/**
	 * Verwijderen van een deelname aan deze quiz
	 *
	 * @param quizDeelname
	 *            de QuizDeelname om te verwijderen
	 */
	public void removeQuizDeelname(QuizDeelname quizDeelname) {
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
	 * @param volgnr het volgnummer van de QuizDeelname (base 1)
	 * @return een QuizDeelname die deze Quiz linkt aan een Leerling
	 */
	public QuizDeelname getQuizDeelname(int volgnr) {
		return this.quizDeelnames.get(volgnr - 1);
	}

	/**
	 * Vergelijk deze quiz met een andere quiz
	 *
	 * @return een negatief nummer, een nul of een positief nummer als de quiz
	 *         minder, dezelfde of meer opdrachten heeft
	 */
	@Override
	public int compareTo(Quiz aQuiz) {
		if (this.getOpdrachten().size() < aQuiz.getOpdrachten().size()) {
			return -1;
		} else if (this.getOpdrachten().size() > aQuiz.getOpdrachten().size()) {
			return 1;
		} else
			return 0;
	}

	@Override
	/**
	 * Geeft een Quiz terug die een clone is van deze quiz (thx Bert)
	 * @return een geclonede Quiz
	 */
	public Quiz clone() throws CloneNotSupportedException {
		return (Quiz) super.clone();
	}

	// hashCode (res)

}
