package model;

import java.util.ArrayList;

import util.datumWrapper.Datum;

/**
 * Klasse die een Quiz aan een Leerling verbindt. Houdt, naast de Leerling en de
 * Quiz, ook de datum van deelname bij
 *
 * @author Adriaan Kuipers, Ben Vandenberk
 * @version 29/10/2014
 */
public class QuizDeelname implements Comparable<QuizDeelname>, Cloneable {

	private final int ID;
	private final Leerling leerling;
	private final Quiz quiz;
	private final Datum datum;
	private ArrayList<OpdrachtAntwoord> opdrachtAntwoorden;

	/**
	 * Constructor QuizDeelname met 2 parameters
	 *
	 * @param quiz
	 *            de Quiz waaraan de Leerling deelneemt
	 * @param leerling
	 *            de Leerling die deelneemt aan een Quiz
	 */
	private QuizDeelname(Quiz quiz, Leerling leerling, Datum deelnameDatum) {
		this.datum = deelnameDatum;
		this.quiz = quiz;
		this.leerling = leerling;
		opdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		ID = this.hashCode();
	}

	/**
	 * Haalt de deelnemende Leerling op
	 *
	 * @return de Leerling die deelneemt aan een Quiz
	 */
	public Leerling getLeerling() {
		return leerling.clone();
	}

	/**
	 * Haalt de Quiz waaraan wordt deelgenomen op
	 *
	 * @return de Quiz waaraan deelgenomen wordt
	 */
	public Quiz getQuiz() {
		return quiz.clone();
	}

	/**
	 * Haalt de datum van deelname op. Dit is een kopie van het interne Datum
	 * objectje
	 *
	 * @return de Datum die de datum van deelname voorstelt
	 */
	public Datum getDatum() {
		return new Datum(datum);
	}

	/**
	 * Geeft de score terug die voor deze quizdeelname behaald werd. De score
	 * staat op 10 en is afgerond tot op het gehele getal
	 *
	 * @return de score op 10 voor deze quizdeelname
	 */
	public int getBehaaldeScore() {
		double behaaldeScore = 0;
		for (OpdrachtAntwoord opdrachtAntwoord : this.opdrachtAntwoorden) {
			behaaldeScore += opdrachtAntwoord.getBehaaldeScore();
		}
		double opTien = behaaldeScore / this.quiz.getMaxScore() * 10.0;
		return (int) Math.round(opTien);
	}

	/**
	 * Haalt de ID van de QuizDeelname op
	 * 
	 * @return de ID van de QuizDeelname op
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Legt de relatie tussen een Leerling en een Quiz. Roep deze method aan om
	 * een Leerling te laten deelnemen aan een Quiz. Zet uitStorage op true om
	 * een koppeling vanuit storage (tekst / DB) te maken
	 *
	 * @param quiz
	 *            de Quiz waaraan de Leerling deelneemt
	 * @param leerling
	 *            de deelnemende Leerling
	 * @param deelnameDatum
	 *            de Datum van deelname
	 * @param uitStorage
	 *            zet op true om een koppeling vanuit storage (tekst / DB) te
	 *            maken
	 * @return de resulterende QuizDeelname
	 * @throws IllegalStateException
	 *             wanneer de status van de Quiz geen deelnames toelaat of
	 *             wanneer de Leerling niet in het juiste leerjaar zit om deel
	 *             te nemen
	 */
	public static QuizDeelname koppelQuizAanLeerling(Quiz quiz,
			Leerling leerling, Datum deelnameDatum, boolean uitStorage)
			throws IllegalStateException {
		if (!uitStorage) {
			if (!quiz.isDeelnameMogelijk()) {
				throw new IllegalStateException(
						String.format(
								"De status van de quiz(%s) laat niet toe aan de quiz deel te nemen",
								quiz.getQuizStatus()));
			}
		}
		if (!quiz.isGeldigLeerjaar(leerling.getLeerjaar())) {
			throw new IllegalStateException(
					String.format(
							"De quiz is niet opengesteld voor het leerjaar waarin de leerling zich bevindt (%d)",
							leerling.getLeerjaar()));
		}
		QuizDeelname quizDeelname = new QuizDeelname(quiz, leerling,
				deelnameDatum);
		leerling.addQuizDeelname(quizDeelname);
		quiz.addQuizDeelname(quizDeelname);
		return quizDeelname;
	}

	/**
	 * Legt de relatie tussen een Leerling en een Quiz. Roep deze method aan om
	 * een Leerling te laten deelnemen aan een Quiz
	 *
	 * @param quiz
	 *            de Quiz waaraan de Leerling deelneemt
	 * @param leerling
	 *            de deelnemende Leerling
	 * @return de resulterende QuizDeelname
	 * @throws IllegalStateException
	 *             wanneer de status van de Quiz geen deelnames toelaat of
	 *             wanneer de Leerling niet in het juiste leerjaar zit om deel
	 *             te nemen
	 */
	public static QuizDeelname koppelQuizAanLeerling(Quiz quiz,
			Leerling leerling) throws IllegalStateException {
		return koppelQuizAanLeerling(quiz, leerling, new Datum(), false);
	}

	/**
	 * geeft ovezicht van gestelde vragen met juiste antwoorden, gegeven
	 * antwoorden & behaalde score
	 */
	public String feedback() {
		throw new UnsupportedOperationException(
				"Method nog niet geïmplementeerd");
	}

	/**
	 * Toevoegen van OpdrachtAntwoord aan deze QuizDeelname
	 *
	 * @param opdrachtAntwoord
	 *            het OpdrachtAntwoord om toe te voegen
	 */
	protected void addOpdrachtAntwoord(OpdrachtAntwoord opdrachtAntwoord) {
		this.opdrachtAntwoorden.add(opdrachtAntwoord);
	}

	/**
	 * Geeft een shallow copy van de lijst met OpdrachtAntwoorden van deze
	 * QuizDeelname terug.
	 *
	 * @return een shallow copy van de ArrayList&lt;OpdrachtAntwoord&gt; van
	 *         deze QuizDeelname
	 */
	public ArrayList<OpdrachtAntwoord> getOpdrachtAntwoorden() {
		ArrayList<OpdrachtAntwoord> kopie = new ArrayList<OpdrachtAntwoord>();
		for (OpdrachtAntwoord opdrachtAntwoord : this.opdrachtAntwoorden) {
			kopie.add(opdrachtAntwoord);
		}
		return kopie;
	}

	/**
	 * Override van default toString met info over de deelname
	 */
	@Override
	public String toString() {
		String result = "QuizDeelname [ID=" + ID + "] - Datum: " + datum;
		result += "\nDIE\n" + leerling.toString().replaceAll("(?m)^", "\t");
		;
		result += "\nKOPPELT AAN\n" + quiz.toString().replaceAll("(?m)^", "\t");
		;
		return result;
	}

	/**
	 * Override van equals: controle of deze quizdeelname dezelfde is als de
	 * andere
	 *
	 * @param aQuizDeelname
	 *            een quizdeelname waarmee we gaan vergelijken
	 * @return true als de meegegeven QuizDeelname hetzelfde is als deze
	 *         QuizDeelname
	 */
	@Override
	public boolean equals(Object aQuizDeelname) {
		if (aQuizDeelname == null) {
			return false;
		}
		if (!(aQuizDeelname instanceof QuizDeelname)) {
			return false;
		}
		QuizDeelname other = (QuizDeelname) aQuizDeelname;
		if (!other.getLeerling().equals(this.leerling)) {
			return false;
		}
		if (!other.getQuiz().equals(this.quiz)) {
			return false;
		}
		if (!other.getDatum().equals(this.datum)) {
			return false;
		}
		return true;
	}

	/**
	 * Vergelijkt deze QuizDeelname met een andere op basis van de Leerling
	 * (alfabetisch) en dan op datum van deelname
	 *
	 * @return een negatief nummer, nul of een positief nummer als de
	 *         quizdeelname voor, op dezelfde plaats of na de andere
	 *         QuizDeelname komt
	 */
	@Override
	public int compareTo(QuizDeelname aQuizDeelname) {
		if (this.leerling.compareTo(aQuizDeelname.getLeerling()) == 0) {
			return this.datum.compareTo(aQuizDeelname.getDatum());
		} else {
			return this.leerling.compareTo(aQuizDeelname.getLeerling());
		}
	}

	@Override
	public int hashCode() {
		long hash = 1;
		hash = hash * 7 * leerling.hashCode();
		hash = hash * 31 * quiz.hashCode();
		// hash = hash * 19 * datum.toString().hashCode();
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QuizDeelname clone() {
		QuizDeelname clone = null;
		try {
			clone = (QuizDeelname) super.clone();
			clone.opdrachtAntwoorden = (ArrayList<OpdrachtAntwoord>) this.opdrachtAntwoorden
					.clone();
		} catch (CloneNotSupportedException ex) {
			// QuizDeelname implementeert Cloneable, mag geen
			// CloneNotSupportedException throwen
			ex.printStackTrace();
		}
		return clone;
	}

}
