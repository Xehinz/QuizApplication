package model;

import java.util.ArrayList;
import java.util.List;

/**
 * De QuizCatalogus klasse, met 1 field: Een lijst met alle bestaande quizzen
 *
 * @author Bert Neyt
 * @version 27/10/2014
 *
 */

public class QuizCatalogus implements Comparable<QuizCatalogus>, Cloneable {
	private List<Quiz> quizcatalogus;

	/**
	 * Maakt een nieuwe QuizCatalogus aan
	 *
	 * Constructor zonder parameters waarbij we de List initialiseren als nieuwe List --> aangezien we geen
	 * List<Quiz>(); kunnen instanti�ren, gebruiken we ArrayList
	 * http://stackoverflow.com/questions/13395114/how-to-initialize-liststring-object-in-java
	 */
	public QuizCatalogus() {
		this.quizcatalogus = new ArrayList<Quiz>(); // added
	}

	/**
	 * Maakt een nieuwe QuizCatalogus aan van een reeds bestaande lijst van quizzen
	 *
	 * @param qc
	 *            een lijst van quizzen
	 */

	public QuizCatalogus(List<Quiz> qc) {
		this.quizcatalogus = qc;
	}

	/**
	 * Haalt de gekloonde QuizCatalogus op
	 *
	 * @return de gekloonde QuizCatalogus
	 */

	public QuizCatalogus getCloneQuizCatalogus() {
		try {
			return this.clone();
		} catch (Exception E) {
			return null;
		}
	}

	/**
	 * Kloont QuizCatalogus
	 *
	 * @return de gekloonde QuizCatalogus
	 */

	@Override
	public QuizCatalogus clone() throws CloneNotSupportedException {
		return (QuizCatalogus) super.clone();
	}

	/**
	 * voegt een quiz toe
	 *
	 * @param Q
	 *            de toe te voegen Quiz
	 */

	public void addQuiz(Quiz Q) {
		this.quizcatalogus.add(Q);
	}

	/**
	 * verwijdert een quiz
	 *
	 * @param Q
	 *            de te verwijderen Quiz
	 * @throws UnsupportedOperationException
	 *             als de quiz zich niet in de status 'In constructie' of 'Afgewerkt' bevindt en dus niet verwijderbaar
	 *             is
	 */

	public void removeQuiz(Quiz Q) throws UnsupportedOperationException {
		if (!Q.isVerwijderbaar()) {
			throw new UnsupportedOperationException(String.format(
					"De quiz bevindt zich in de status %s en is dus niet verwijderbaar", Q.getQuizStatus().toString()));
		}
		this.quizcatalogus.remove(Q);
	}

	/**
	 * Haalt een quiz op met een bepaald volgnummer
	 *
	 * @param volgnr
	 *            het volgnummer
	 * @return de quiz met het ingegeven volgnummer
	 */

	public Quiz getQuiz(int volgnr) {
		return this.quizcatalogus.get(volgnr - 1);
	}

	/**
	 * check of de catalogus een bepaalde quiz bevat
	 *
	 * @param quiz
	 *            de te zoeken quiz
	 * @return <code>true</code> als de quiz in de catalogus voorkomt
	 */

	public boolean hasQuiz(Quiz quiz) {
		return this.quizcatalogus.contains(quiz);
	}

	/**
	 * telt het aantal quizzen in de catalogus
	 *
	 * @return het aantal quizzen in de catalogus
	 */

	public int count() {
		return this.quizcatalogus.size();
	}

	/**
	 * Override van de toString methode
	 */

	@Override
	public String toString() {
		return "Quizcatalogus met " + this.count() + " quizzen";
	}

	/**
	 * checkt of de lijsten van 2 catalogi dezelfde quizzen bevat
	 *
	 * @param aQuizCatalogus
	 *            de te vergelijken catalogus
	 */

	public boolean equals(QuizCatalogus aQuizCatalogus) {
		return aQuizCatalogus.getCloneQuizCatalogus().quizcatalogus.containsAll(this.quizcatalogus);
	}

	/**
	 * checkt of een catalogus meer of minder of evenveel quizzen bevat
	 *
	 * @param QC
	 *            de te vergelijken catalogus
	 */

	@Override
	public int compareTo(QuizCatalogus QC) {
		if (this.count() < QC.count()) {
			return -1;
		}
		if (this.count() > QC.count()) {
			return 1;
		} else {
			return 0;
		}
	}
}
