package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * De QuizCatalogus klasse, met 1 field: Een lijst met alle bestaande quizzen
 *
 * @author Bert Neyt
 * @version 27/10/2014
 *
 */

public class QuizCatalogus implements Comparable<QuizCatalogus>, Cloneable, Iterable<Quiz> {
	private HashSet<Quiz> quizcatalogus;

	/**
	 * Maakt een nieuwe QuizCatalogus aan
	 *
	 */
	public QuizCatalogus() {
		this.quizcatalogus = new HashSet<Quiz>(); // added
	}

	/**
	 * Maakt een nieuwe QuizCatalogus aan van een reeds bestaande lijst van quizzen
	 *
	 * @param qc
	 *            een lijst van quizzen
	 */

	public QuizCatalogus(Collection<Quiz> qc) {
		this.quizcatalogus = new HashSet<Quiz>(qc);
	}

	/**
	 * Haalt de gekloonde QuizCatalogus op
	 *
	 * @return de gekloonde QuizCatalogus
	 */

	@Override
	public QuizCatalogus clone() {
		QuizCatalogus clone = null;
		try {
			clone = (QuizCatalogus) super.clone();
			clone.quizcatalogus = new HashSet<Quiz>();
			for (Quiz quiz : this.quizcatalogus) {
				clone.quizcatalogus.add(quiz.clone());
			}
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return clone;
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
	 * Geeft een kopie van de interne lijst van quizzen terug
	 *
	 * @return een kopie van de interne lijst van quizzen terug
	 */
	public ArrayList<Quiz> getQuizzen() {
		ArrayList<Quiz> kopieQuizzen = new ArrayList<Quiz>();
		for (Quiz quiz : quizcatalogus) {
			kopieQuizzen.add(quiz);
		}
		return kopieQuizzen;
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

	@Override
	public boolean equals(Object aQuizCatalogus) {
		if (aQuizCatalogus == null) {
			return false;
		}
		if (!(aQuizCatalogus instanceof QuizCatalogus)) {
			return false;
		}
		QuizCatalogus other = (QuizCatalogus) aQuizCatalogus;
		return other.quizcatalogus.containsAll(this.quizcatalogus);
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

	@Override
	public int hashCode() {
		long hash = 1;
		for (Quiz quiz : this) {
			hash *= 19;
			hash += quiz.hashCode();
		}
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@Override
	public Iterator<Quiz> iterator() {
		return this.quizcatalogus.iterator();
	}
}
