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
	 * Voegt een quiz toe
	 *
	 * @param Q
	 *            de toe te voegen Quiz
	 * @throws IllegalArgumentException
	 *             als het onderwerp van de meegegeven Quiz al voorkomt in de QuizCatalogus
	 */

	public void addQuiz(Quiz Q) throws IllegalArgumentException {
		if (onderwerpBestaatAl(Q.getOnderwerp())) {
			throw new IllegalArgumentException(
					"In de catalogus zit al een quiz met een onderwerp dat equivalent is aan dat van de meegegeven Quiz");
		}
		this.quizcatalogus.add(Q);
	}

	/**
	 * verwijdert een quiz
	 *
	 * @param Q
	 *            de te verwijderen Quiz
	 * @throws IllegalStateException
	 *             als de quiz zich niet in de status 'In constructie' of 'Afgewerkt' bevindt en dus niet verwijderbaar
	 *             is
	 */

	public void removeQuiz(Quiz Q) throws IllegalStateException {
		if (!Q.isVerwijderbaar()) {
			throw new IllegalStateException(String.format(
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
	 * Haalt de Quiz op uit de QuizCatalogus met een bepaalde ID
	 * 
	 * @param quizID de ID van de gewenste Quiz
	 * @return de Quiz met matchende ID
	 * @throws IllegalArgumentException wanneer er geen Quiz object gevonden wordt met de meegegeven ID
	 */
	public Quiz getQuiz(int quizID) throws IllegalArgumentException {
		for (Quiz quiz : this) {
			if (quiz.getID() == quizID) {
				return quiz;
			}
		}
		throw new IllegalArgumentException("De QuizCatalogus bevat geen Quiz met ID=" + quizID);
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

	private boolean onderwerpBestaatAl(String onderwerp) {
		for (Quiz quiz : quizcatalogus) {
			if (vormOmNaarElementairOnderwerp(onderwerp).equals(vormOmNaarElementairOnderwerp(quiz.getOnderwerp()))) {
				return true;
			}
		}
		return false;
	}

	private String vormOmNaarElementairOnderwerp(String onderwerp) {
		String[] teNegeren = new String[] { "de", "een", "het", "met", "van", "in" };

		onderwerp = onderwerp.toLowerCase();
		String[] woorden = onderwerp.split(" ");

		String result = "";
		boolean isNegeerWoord;
		for (int i = 0; i < woorden.length; i++) {
			if (woorden[i].isEmpty()) {
				continue;
			}
			woorden[i] = woorden[i].trim();
			woorden[i] = verwijderSpecialeTekens(woorden[i]);
			isNegeerWoord = false;
			for (int j = 0; j < teNegeren.length && !isNegeerWoord; j++) {
				if (woorden[i].equals(teNegeren[j])) {
					isNegeerWoord = true;
				}
			}
			if (!isNegeerWoord) {
				result += woorden[i] + " ";
			}
		}

		return result.trim();
	}

	private String verwijderSpecialeTekens(String input) {
		char[] charArray = input.toCharArray();
		String result = "";

		for (char c : charArray) {
			if (c >= 97 && c <= 122) {
				result += c;
			}
		}

		return result;
	}
}
