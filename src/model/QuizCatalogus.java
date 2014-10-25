package model;

import java.util.List;

public class QuizCatalogus implements Comparable<QuizCatalogus>, Cloneable {
	private List<Quiz> quizcatalogus;

	public QuizCatalogus() {
	}

	public QuizCatalogus(List<Quiz> qc) {
		this.quizcatalogus = qc;
	}

	public QuizCatalogus getCloneQuizCatalogus() {
		try {
			return this.clone();
		} catch (Exception E) {
			return null;
		}
	}

	@Override
	public QuizCatalogus clone() throws CloneNotSupportedException {
		return (QuizCatalogus) super.clone();
	}

	public void addOpdracht(Quiz Q) {
		this.quizcatalogus.add(Q);
	}

	public void removeOpdracht(Quiz Q) {
		this.quizcatalogus.remove(Q);
	}

	public Quiz getQuiz(int volgnr) {
		return this.quizcatalogus.get(volgnr - 1);
	}

	public boolean hasQuiz(Quiz quiz) {
		return this.quizcatalogus.contains(quiz);
	}

	public int count() {
		return this.quizcatalogus.size();
	}

	@Override
	public String toString() {
		return "Quizcatalogus met " + this.count() + " quizzen";
	}

	public boolean equals(QuizCatalogus aQuizCatalogus) {
		return aQuizCatalogus.getCloneQuizCatalogus().quizcatalogus
				.containsAll(this.quizcatalogus);
	}

	@Override
	public int compareTo(QuizCatalogus QC) {
		if (this.count() < QC.count()) {
			return -1;
		}
		if (this.count() > QC.count()) {
			return 1;
		} else
			return 0;
	}
}
